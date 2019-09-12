(ns common-swagger-api.schema.apps.admin.apps
  (:use [common-swagger-api.schema
         :only [->optional-param
                optional-key->keyword
                describe
                CommonResponses
                ErrorResponseNotFound
                PagingParams
                SortFieldDocs
                SortFieldOptionalKey]]
        [schema.core :only [defschema enum optional-key]])
  (:require [clojure.set :as sets]
            [common-swagger-api.schema.apps :as apps])
  (:import [java.util Date UUID]))

(def AdminAppPatchSummary "Update App Details and Labels")

(def AppDeleteDocs
  "An app can be marked as deleted in the DE without being completely removed from the database using this service.
   This endpoint is the same as the non-admin endpoint,
   except an error is not returned if the user does not own the App.")

(def AppDetailsDocs "This service allows administrative users to view detailed information about private apps.")
(def AppDocumentationAddDocs "This service is used by DE administrators to add documentation for a single App.")
(def AppDocumentationUpdateDocs "This service is used by DE administrators to update documentation for a single App.")

(def AppIntegrationDataUpdateSummary "Update the Integration Data Record for an App")
(def AppIntegrationDataUpdateDocs
  "This service allows administrators to change the integration data record associated with an app.")

(def AppListingDocs
  "This service allows admins to list all public apps, including apps listed under the `Trash` category:
   deleted public apps and private apps that are 'orphaned' (not categorized in any user's workspace).
   If the `search` parameter is included, then the results are filtered by the App name, description,
   integrator's name, tool name, or category name the app is under.")

(def AppShredderSummary "Permanently Deleting Apps")
(def AppShredderDocs
  "This service physically removes an App from the database,
   which allows administrators to completely remove Apps that are causing problems.")

(def AppPublicationRequestsSummary "List App Publication Requests")
(def AppPublicationRequestsDocs
  "This service lists requests for app publication that require administrator intervention.")

(defschema AdminAppListingJobStats
  (merge apps/AppListingJobStats
         {:job_count
          (describe Long "The number of times this app has run")

          :job_count_failed
          (describe Long "The number of times this app has run to `Failed` status")

          (optional-key :last_used)
          (describe Date "The start date this app was last run")}))

(defschema AdminAppListingDetail
  (merge apps/AppListingDetail
         {(optional-key :job_stats)
          (describe AdminAppListingJobStats apps/AppListingJobStatsDocs)}))

(defschema AdminAppListing
  (merge apps/AppListing
         {:apps (describe [AdminAppListingDetail] "A listing of App details")}))

(def AdminAppListingJobStatsKeys
  (->> AdminAppListingJobStats
       keys
       (map optional-key->keyword)
       set))

(def AdminAppSearchValidSortFields
  (sets/union apps/AppSearchValidSortFields
              AdminAppListingJobStatsKeys))

(def AppSubsetDocs "The subset of apps to search.")
(def AppSubsetOptionalKey (optional-key :app-subset))
(def AppSubsets [:public :private :all])

(defschema AppPublicationRequestSearchParams
  {(optional-key :app_id)
   (describe UUID "The ID of the app to list publication requests for")

   (optional-key :requestor)
   (describe String "The username of the person to requested the app publication")})

(defschema AdminAppSearchParams
  (merge apps/AppSearchParams
         {SortFieldOptionalKey
          (describe (apply enum AdminAppSearchValidSortFields) SortFieldDocs)

          AppSubsetOptionalKey
          (describe (apply enum AppSubsets) AppSubsetDocs :default :public)}))

(defschema AppExtraInfo
  {:htcondor
   {:extra_requirements
    (describe String "A set of additional requirements to add to the HTCondor submit file")}})

(defschema AdminAppDetails
  (merge apps/AppDetails
         {(optional-key :job_stats)
          (describe AdminAppListingJobStats apps/AppListingJobStatsDocs)

          (optional-key :documentation)
          (describe apps/AppDocumentation "App documentation as returned by the specific app documentation endpoints.")

          (optional-key :extra)
          AppExtraInfo}))

(defschema AdminAppPatchRequest
  (-> apps/AppBase
      (->optional-param :id)
      (->optional-param :name)
      (->optional-param :description)
      (assoc (optional-key :extra) AppExtraInfo
             (optional-key :wiki_url) apps/AppDocUrlParam
             (optional-key :references) apps/AppReferencesParam
             (optional-key :deleted) apps/AppDeletedParam
             (optional-key :disabled) apps/AppDisabledParam
             apps/OptionalGroupsKey (describe [apps/AppGroup] apps/GroupListDocs))
      (describe "The App to update.")))

(defschema ToolAdminAppListingResponses
  (merge CommonResponses
         {200 {:schema      AdminAppListing
               :description "The listing of Apps using the given Tool."}
          404 {:schema      ErrorResponseNotFound
               :description "The `tool-id` does not exist."}}))

(defschema AppPublicationRequest
  {:id        (describe UUID "The app publication request identifier")
   :app       (describe AdminAppDetails "Details about the app that the user wants to publish")
   :requestor (describe String "The username of the person who requested the app publication")})

(defschema AppPublicationRequestListing
  {:publication_requests (describe [AppPublicationRequest] "The list of app publication requests")})
