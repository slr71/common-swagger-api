(ns common-swagger-api.schema.analyses.listing
  (:use [common-swagger-api.schema :only [describe NonBlankString PagingParams]]
        [common-swagger-api.schema.analyses]
        [common-swagger-api.schema.apps :only [SystemId]]
        [common-swagger-api.schema.common :only [IncludeHiddenParams]]
        [schema.core
         :only [defschema
                Int
                maybe
                optional-key]])
  (:require [clojure.java.io :as io])
  (:import (java.util UUID)))

(def AnalysesDeleteSummary "Delete Multiple Analyses")
(def AnalysesDeleteDocs
  "This service allows the caller to mark one or more analyses as deleted in the apps database.")

(def AnalysesListingSummary "List Analyses")
(def AnalysesListingDocs
  "This service allows users to list analyses that they've previously submitted for execution.")

(def AnalysisDeleteSummary "Delete an Analysis")
(def AnalysisDeleteDocs
  "This service marks an analysis as deleted in the DE database.")

(def AnalysisHistorySummary "Get the Status Update History of an Analysis")
(def AnalysisHistoryDocs
  "This endpoint returns a status update history for each step in an analysis.")

(def AnalysisStepsSummary "Display the steps of an analysis.")
(def AnalysisStepsDocs
  "This service returns a list of steps in an analysis.")

(def AnalysisSubmitSummary "Submit an Analysis")
(def AnalysisSubmitDocs
  "This service allows users to submit analyses for execution.
   The `config` element in the analysis submission is a map
   from parameter IDs as they appear in the response from the `/apps/:app-id` endpoint
   to the desired values for those parameters.")

(def AnalysisUpdateSummary "Update an Analysis")
(def AnalysisUpdateDocs
  "This service allows an analysis name or description to be updated.")

;; JSON query params are not currently supported by compojure-api,
;; so we have to define "filter" in this schema as a String for now.
(def OptionalKeyFilter (optional-key :filter))

(defschema AnalysisListingParams
  (merge IncludeHiddenParams
         PagingParams
         {OptionalKeyFilter
          (describe String (slurp (io/resource "docs/analyses/listing/filter-param.md")))}))

(def ResultsTotalParam
  (describe Long "The total number of results that would be returned without limits and offsets applied."))

(def Timestamp (describe String "A timestamp in milliseconds since the epoch."))
(def ExternalId (describe NonBlankString "The analysis identifier from the job execution system."))

(defschema BatchStatus
  {:total     (describe Int "The total number of jobs in the batch.")
   :completed (describe Int "The number of completed jobs in the batch.")
   :running   (describe Int "The number of running jobs in the batch.")
   :submitted (describe Int "The number of submitted jobs in the batch.")})

(defschema BaseAnalysis
  {(optional-key :app_description)
   (describe String "A description of the app used to perform the analysis.")

   :app_id
   (describe String "The ID of the app used to perform the analysis.")

   :system_id
   SystemId

   (optional-key :app_name)
   (describe String "The name of the app used to perform the analysis.")

   (optional-key :batch)
   (describe Boolean "Indicates whether the analysis is a batch analysis.")

   (optional-key :description)
   (describe String "The analysis description.")

   (optional-key :enddate)
   (describe Timestamp "The time the analysis ended.")

   :id
   (describe UUID "The analysis ID.")

   (optional-key :name)
   (describe String "The analysis name.")

   :notify
   (describe Boolean "Indicates whether the user wants status updates via email.")

   (optional-key :resultfolderid)
   (describe String "The path to the folder containing the anlaysis results.")

   (optional-key :startdate)
   (describe Timestamp "The time the analysis started.")

   :status
   (describe String "The status of the analysis.")

   :username
   (describe String "The name of the user who submitted the analysis.")

   (optional-key :wiki_url)
   (describe String "The URL to app documentation in Confluence.")

   (optional-key :parent_id)
   (describe UUID "The identifier of the parent analysis.")

   (optional-key :batch_status)
   (describe BatchStatus "A summary of the status of the batch.")

   (optional-key :interactive_urls)
   (describe [String] "The list of externally accessible interactive analysis URLs.")})

(defschema Analysis
  (assoc BaseAnalysis
    :app_disabled
    (describe Boolean "Indicates whether the app is currently disabled. DEPRECATED - always returns `false`.")

    :can_share
    (describe Boolean "Indicates whether or not the analysis can be shared.")))

(defschema AnalysisList
  (merge {:analyses  (describe [Analysis] "The list of analyses.")
          :timestamp (describe Timestamp "The time the analysis list was retrieved.")
          :total     ResultsTotalParam}
         AnalysisStats))

(defschema AnalysisUpdate
  (select-keys Analysis (map optional-key [:description :name])))

(defschema AnalysisUpdateResponse
  (select-keys Analysis (cons :id (map optional-key [:description :name]))))

(def AppStepNumber
  (describe Int (str "The sequential step number from the app, which might be different "
                     "from the analysis step number if app steps have been combined.")))

(defschema AnalysisStep
  {:step_number
   (describe Int "The sequential step number in the analysis.")

   (optional-key :external_id)
   (describe String "The step ID from the execution system.")

   (optional-key :startdate)
   (describe Timestamp "The time the step started.")

   (optional-key :enddate)
   (describe Timestamp "The time the step ended.")

   (optional-key :status)
   (describe String "The status of the step.")

   (optional-key :app_step_number)
   AppStepNumber

   (optional-key :step_type)
   (describe String "The analysis type associated with the step.")})

(defschema AnalysisStepList
  {:analysis_id (describe UUID "The analysis ID.")
   :steps       (describe [AnalysisStep] "The list of analysis steps.")
   :timestamp   (describe Timestamp "The time the list of analysis steps was retrieved.")
   :total       ResultsTotalParam})

(defschema AnalysisStatusUpdate
  {:status    (describe NonBlankString "The job status associated with the update.")
   :message   (describe String "A brief description of the job status update.")
   :timestamp (describe String "The date and time when the job status update was created.")})

(defschema AnalysisStepHistory
  (assoc AnalysisStep
    :updates (describe [AnalysisStatusUpdate] "The list of updates received for the analysis step.")))

(defschema AnalysisHistory
  (assoc AnalysisStepList
    :steps (describe [AnalysisStepHistory] "The history of each step in the analysis.")))
