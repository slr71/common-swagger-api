(ns common-swagger-api.schema.apps
  (:use [common-swagger-api.schema :only [describe
                                          optional-key->keyword
                                          NonBlankString
                                          PagingParams
                                          SortFieldDocs
                                          SortFieldOptionalKey]]
        [common-swagger-api.schema.apps.rating :only [Rating]]
        [schema.core :only [defschema
                            enum
                            optional-key
                            Any
                            Bool
                            Keyword]])
  (:require [clojure.set :as sets])
  (:import [java.util UUID Date]))

(def AppListingSummary "List Apps")

(def AppsShredderSummary "Logically Deleting Apps")
(def AppsShredderDocs
  "One or more Apps can be marked as deleted in the DE without being completely removed from the database using this service.
   <b>Note</b>: an attempt to delete an app that is already marked as deleted is treated as a no-op rather than an error condition.
   If the App doesn't exist in the database at all, however, then that is treated as an error condition.")

(def AppDeletedParam (describe Boolean "Whether the App is marked as deleted"))
(def AppDisabledParam (describe Boolean "Whether the App is marked as disabled"))
(def AppDocUrlParam (describe String "The App's documentation URL"))
(def AppIdParam (describe UUID "A UUID that is used to identify the App"))
(def AppPublicParam (describe Boolean "Whether the App has been published and is viewable by all users"))
(def SystemId (describe NonBlankString "The ID of the app execution system"))
(def StringAppIdParam (describe NonBlankString "The App identifier"))

(defschema IncludeHiddenParams
  {(optional-key :include-hidden)
   (describe Boolean "True if hidden elements should be included in the results.")})

(defschema AppBase
  {:id                              AppIdParam
   :name                            (describe String "The App's name")
   :description                     (describe String "The App's description")
   (optional-key :integration_date) (describe Date "The App's Date of public submission")
   (optional-key :edited_date)      (describe Date "The App's Date of its last edit")
   (optional-key :system_id)        SystemId})

(defschema PipelineEligibility
  {:is_valid (describe Boolean "Whether the App can be used in a Pipeline")
   :reason (describe String "The reason an App cannot be used in a Pipeline")})

(defschema AppListingDetail
  (merge AppBase
         {:id
          (describe String "The app ID.")

          :app_type
          (describe String "The type ID of the App")

          :can_favor
          (describe Boolean "Whether the current user can favorite this App")

          :can_rate
          (describe Boolean "Whether the current user can rate this App")

          :can_run
          (describe Boolean
                    "This flag is calculated by comparing the number of steps in the app to the number of steps
                     that have a tool associated with them. If the numbers are different then this flag is set to
                     `false`. The idea is that every step in the analysis has to have, at the very least, a tool
                     associated with it in order to run successfully")

          :deleted
          AppDeletedParam

          :disabled
          AppDisabledParam

          :integrator_email
          (describe String "The App integrator's email address")

          :integrator_name
          (describe String "The App integrator's full name")

          (optional-key :is_favorite)
          (describe Boolean "Whether the current user has marked the App as a favorite")

          :is_public
          AppPublicParam

          (optional-key :beta)
          (describe Boolean "Whether the App has been marked as `beta` release status")

          :pipeline_eligibility
          (describe PipelineEligibility "Whether the App can be used in a Pipeline")

          :rating
          (describe Rating "The App's rating details")

          :step_count
          (describe Long "The number of Tasks this App executes")

          (optional-key :wiki_url)
          AppDocUrlParam

          :permission
          (describe String "The user's access level for the app.")}))

(defschema AppListing
  {:total (describe Long "The total number of Apps in the listing")
   :apps  (describe [AppListingDetail] "A listing of App details")})

(def AppListingValidSortFields
  (-> (map optional-key->keyword (keys AppListingDetail))
      (conj :average_rating :user_rating)
      set
      (sets/difference #{:app_type
                         :can_favor
                         :can_rate
                         :can_run
                         :pipeline_eligibility
                         :rating})))

(def AppSearchValidSortFields
  (-> AppListingValidSortFields
      (sets/difference #{:average_rating :user_rating})
      (conj :average :total)))

(defschema AppFilterParams
  {(optional-key :app-type)
   (describe String "The type of app to include in the listing.")})

(defschema AppSearchParams
  (merge PagingParams
         AppFilterParams
         {(optional-key :search)
          (describe String
                    "The pattern to match in an App's Name, Description, Integrator Name, or Tool Name.")

          (optional-key :start_date)
          (describe Date "Filters out the app stats before this start date")

          (optional-key :end_date)
          (describe Date "Filters out the apps stats after this end date")

          SortFieldOptionalKey
          (describe (apply enum AppSearchValidSortFields) SortFieldDocs)}))

(defschema QualifiedAppId
  {:system_id SystemId
   :app_id    StringAppIdParam})

(defschema AppDeletionRequest
  (describe
    {:app_ids                              (describe [QualifiedAppId] "A List of qualified app identifiers")
     (optional-key :root_deletion_request) (describe Boolean "Set to `true` to  delete one or more public apps")}
    "List of App IDs to delete."))

(defschema FileMetadata
  {:attr  (describe String "The attribute name.")
   :value (describe String "The attribute value.")
   :unit  (describe String "The attribute unit.")})

(defschema AnalysisSubmissionConfig
  {(describe Keyword "The step-ID_param-ID") (describe Any "The param-value")})

(defschema AnalysisSubmission
  {:system_id
   SystemId

   :app_id
   (describe String "The ID of the app used to perform the analysis.")

   (optional-key :job_id)
   (describe UUID "The UUID of the job being submitted.")

   (optional-key :callback)
   (describe String "The callback URL to use for job status updates.")

   :config
   (describe AnalysisSubmissionConfig "A map from (str step-id \"_\" param-id) to param-value.")

   (optional-key :create_output_subdir)
   (describe Bool (str "Indicates whether a subdirectory should be created beneath "
                       "the specified output directory."))

   :debug
   (describe Bool "A flag indicating whether or not job debugging should be enabled.")

   (optional-key :description)
   (describe String "An optional description of the analysis.")

   :name
   (describe String "The name assigned to the analysis by the user.")

   :notify
   (describe Bool (str "Indicates whether the user wants to receive job status update "
                       "notifications."))

   :output_dir
   (describe String "The path to the analysis output directory in the data store.")

   (optional-key :starting_step)
   (describe Long "The ordinal number of the step to start the job with.")

   (optional-key :uuid)
   (describe UUID (str "The UUID of the analysis. A random UUID will be assigned if one isn't "
                       "provided."))

   (optional-key :skip-parent-meta)
   (describe Bool "True if metadata should not associate metadata with the parent directory.")

   (optional-key :file-metadata)
   (describe [FileMetadata] "Custom file attributes to associate with result files.")

   (optional-key :archive_logs)
   (describe Bool "True if the job logs should be uploaded to the data store.")})
