(ns common-swagger-api.schema.apps
  (:use [common-swagger-api.schema :only [describe NonBlankString]])
  (:require [schema.core :refer [defschema optional-key Any Bool Keyword]])
  (:import [java.util UUID]))

(def AppIdParam (describe UUID "A UUID that is used to identify the App"))
(def SystemId (describe NonBlankString "The ID of the app execution system"))

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
