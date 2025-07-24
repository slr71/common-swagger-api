(ns common-swagger-api.malli.analyses
  (:require [common-swagger-api.malli.apps :refer [SystemId]]
            [common-swagger-api.malli.common :refer [IncludeDeletedParams IncludeHiddenParams]]))

(def ParameterValue
  [:map
   [:value
    {:description         "The value of the parameter"
     :json-schema/example "some_value"}
    :any]])

(def AnalysisParameter
  [:map
   [:full_param_id
    {:description         "The fully qualified parameter ID"
     :json-schema/example "4a15eaf3-545b-4f24-8e17-abb4a54841cc_60c827c8-99d2-46f2-9813-f05f68db7020"}
    :string]

   [:param_id
    {:description         "The unqualified parameter ID"
     :json-schema/example "60c827c8-99d2-46f2-9813-f05f68db7020"}
    :string]

   [:param_name
    {:optional            true
     :description         "The name of the parameter"
     :json-schema/example "Input file"}
    :string]

   [:param_value
    {:optional            true
     :description         "The value of the parameter"}
    ParameterValue]

   [:param_type
    {:description         "The type of the parameter"
     :json-schema/example "FileInput"}
    :string]

   [:info_type
    {:optional            true
     :description         "The type of information associated with an input or output parameter"
     :json-schema/example "NucleotideOrPeptideSequence"}
    :string]

   [:data_format
    {:optional            true
     :description         "The data format associated with an input or output parameter"
     :json-schema/example "FASTA"}
    :string]

   [:is_default_value
    {:optional            true
     :description         "Indicates whether the default parameter value was used"
     :json-schema/example false}
    :boolean]

   [:is_visible
    {:optional            true
     :description         "Indicates whether the parameter is visible in the app UI"
     :json-schema/example true}
    :boolean]])

(def AnalysisParameters
  [:map
   [:app_id
    {:description         "The ID of the app used to perform the analysis"
     :json-schema/example "84d900df-5ba1-4f88-92c0-35e3296acbaf"}
    :string]

   [:app_version_id
    {:optional            true
     :description         "The version ID of the app used to perform the analysis"
     :json-schema/example "cb6d52b5-65b1-4390-a1cb-8b4777c013f1"}
    :string]

   (SystemId :system_id)

   [:parameters
    {:description "The list of parameters"}
    [:vector AnalysisParameter]]])

(def AnalysesRelauncherRequest
  [:map
   [:analyses
    {:description         "The identifiers of the analyses to be relaunched"
     :json-schema/example ["550e8400-e29b-41d4-a716-446655440000"]}
    [:vector :uuid]]])

(def AnalysisShredderRequest
  [:map
   [:analyses
    {:description         "The identifiers of the analyses to be deleted"
     :json-schema/example ["550e8400-e29b-41d4-a716-446655440000"]}
    [:vector :uuid]]])

(def StopAnalysisRequest
  [:map
   [:job_status
    {:optional            true
     :description         "The job status to set. Defaults to 'Canceled'"
     :json-schema/example "Canceled"}
    [:enum "Canceled" "Completed" "Failed"]]])

(def StopAnalysisResponse
  [:map
   [:id
    {:description         "The ID of the stopped analysis"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def FileMetadata
  [:map
   [:attr
    {:description         "The attribute name"
     :json-schema/example "file_type"}
    :string]

   [:value
    {:description         "The attribute value"
     :json-schema/example "text"}
    :string]

   [:unit
    {:description         "The attribute unit"
     :json-schema/example ""}
    :string]])

(def AnalysisSubmissionConfig
  [:map-of :keyword :any])

(def AnalysisStepResourceRequirements
  [:map
   [:min_memory_limit
    {:optional            true
     :description         "The minimum memory limit for the step"
     :json-schema/example 1024}
    :int]

   [:min_cpu_cores
    {:optional            true
     :description         "The minimum number of CPU cores for the step"
     :json-schema/example 1}
    :int]

   [:max_cpu_cores
    {:optional            true
     :description         "The maximum number of CPU cores for the step"
     :json-schema/example 4}
    :int]

   [:min_disk_space
    {:optional            true
     :description         "The minimum disk space for the step"
     :json-schema/example 1024}
    :int]

   [:step_number
    {:optional            true
     :description         "The step number"
     :json-schema/example 1}
    :int]])

(def AnalysisSubmission
  [:map
   (SystemId :system_id)

   [:app_id
    {:description         "The ID of the app used to perform the analysis"
     :json-schema/example "84d900df-5ba1-4f88-92c0-35e3296acbaf"}
    :string]

   [:app_version_id
    {:optional            true
     :description         "The ID of the app version used to perform the analysis. If not provided, then it is assumed the submission is for the latest version of the app"
     :json-schema/example "cb6d52b5-65b1-4390-a1cb-8b4777c013f1"}
    :uuid]

   [:job_id
    {:optional            true
     :description         "The UUID of the job being submitted"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:callback
    {:optional            true
     :description         "The callback URL to use for job status updates"
     :json-schema/example "https://example.com/callback"}
    :string]

   [:config
    {:description         "A map from (str step-id '_' param-id) to param-value"
     :json-schema/example {"step1_param1" "value1"}}
    AnalysisSubmissionConfig]

   [:requirements
    {:optional            true
     :description         "The list of optional resource requirements requested for any step"
     :json-schema/example []}
    [:vector AnalysisStepResourceRequirements]]

   [:create_output_subdir
    {:optional            true
     :description         "Indicates whether a subdirectory should be created beneath the specified output directory"
     :json-schema/example true}
    :boolean]

   [:debug
    {:description         "A flag indicating whether or not job debugging should be enabled"
     :json-schema/example false}
    :boolean]

   [:description
    {:optional            true
     :description         "An optional description of the analysis"
     :json-schema/example "My analysis description"}
    :string]

   [:name
    {:description         "The name assigned to the analysis by the user"
     :json-schema/example "My Analysis"}
    :string]

   [:notify
    {:description         "Indicates whether the user wants to receive job status update notifications"
     :json-schema/example true}
    :boolean]

   [:notify_periodic
    {:optional            true
     :description         "Indicates whether the user wants to receive periodic email notifications that the job is still running, for interactive jobs"
     :json-schema/example false}
    :boolean]

   [:periodic_period
    {:optional            true
     :description         "The number of seconds between periodic notifications, if the user has opted to receive them"
     :json-schema/example 3600}
    :int]

   [:output_dir
    {:description         "The path to the analysis output directory in the data store"
     :json-schema/example "/iplant/home/user/analyses/output"}
    :string]

   [:starting_step
    {:optional            true
     :description         "The ordinal number of the step to start the job with"
     :json-schema/example 1}
    :int]

   [:uuid
    {:optional            true
     :description         "The UUID of the analysis. A random UUID will be assigned if one isn't provided"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:skip-parent-meta
    {:optional            true
     :description         "True if metadata should not associate metadata with the parent directory"
     :json-schema/example false}
    :boolean]

   [:file-metadata
    {:optional            true
     :description         "Custom file attributes to associate with result files"
     :json-schema/example []}
    [:vector FileMetadata]]

   [:archive_logs
    {:optional            true
     :description         "True if the job logs should be uploaded to the data store"
     :json-schema/example true}
    :boolean]

   [:mount_data_store
    {:optional            true
     :description         "True if iRODS CSI Driver mounts should be created in the container"
     :json-schema/example true}
    :boolean]])

(def AnalysisResponse
  [:map
   [:id
    {:description         "The ID of the submitted analysis"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:name
    {:description         "The name of the submitted analysis"
     :json-schema/example "My Analysis"}
    :string]

   [:status
    {:description         "The current status of the analysis"
     :json-schema/example "Submitted"}
    :string]

   [:start-date
    {:description         "The analysis start date as milliseconds since the epoch"
     :json-schema/example "1634567890000"}
    :string]

   [:missing-paths
    {:optional            true
     :description         "Any paths parsed from an HT Analysis Path List that no longer exist"
     :json-schema/example []}
    [:vector :string]]])

(def AnalysisPod
  [:map
   [:name
    {:description         "The name of a pod in Kubernetes associated with an analysis"
     :json-schema/example "analysis-pod-12345"}
    :string]

   [:external_id
    {:description         "The external ID associated with the pod"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def AnalysisPodList
  [:map
   [:pods
    {:description         "A list of pods in Kubernetes associated with an analysis"
     :json-schema/example []}
    [:vector AnalysisPod]]])

(def AnalysisPodLogParameters
  [:map
   [:previous
    {:optional            true
     :description         "True if the logs of a previously terminated container should be returned"
     :json-schema/example false}
    :boolean]

   [:since
    {:optional            true
     :description         "Number of seconds in the past to start showing logs"
     :json-schema/example 3600}
    :int]

   [:since-time
    {:optional            true
     :description         "The time at which to start showing log lines. Expressed as seconds since the epoch"
     :json-schema/example "1634567890"}
    :string]

   [:tail-lines
    {:optional            true
     :description         "Number of lines from the end of the log to show"
     :json-schema/example 100}
    :int]

   [:timestamps
    {:optional            true
     :description         "True if timestamps should be prepended to the log lines"
     :json-schema/example true}
    :boolean]

   [:container
    {:optional            true
     :description         "Name of the container to display logs from. Defaults to 'analysis'"
     :json-schema/example "analysis"}
    :string]])

(def AnalysisPodLogEntry
  [:map
   [:since_time
    {:description         "Contains the seconds since the epoch for the time when the log entry was retrieved"
     :json-schema/example "1634567890"}
    :string]

   [:lines
    {:description         "The lines that make up the log entry"
     :json-schema/example ["Log line 1", "Log line 2"]}
    [:vector :string]]])

(def AnalysisTimeLimit
  [:map
   [:time_limit
    {:description         "Contains the seconds since the epoch for the analysis's time limit or the string 'null' if the time limit isn't set"
     :json-schema/example "1634567890"}
    :string]])

(def ConcurrentJobLimitListItem
  [:map
   [:username
    {:optional            true
     :description         "The username of the limited user, omitted for the default setting"
     :json-schema/example "user123"}
    :string]

   [:concurrent_jobs
    {:description         "The maximum number of concurrently running jobs"
     :json-schema/example 5}
    :int]

   [:is_default
    {:description         "True for the default setting"
     :json-schema/example false}
    :boolean]])

(def ConcurrentJobLimit
  [:map
   [:username
    {:optional            true
     :description         "The username of the limited user, omitted for the default setting"
     :json-schema/example "user123"}
    :string]

   [:concurrent_jobs
    {:description         "The maximum number of concurrently running jobs"
     :json-schema/example 5}
    :int]

   [:is_default
    {:description         "True if the default setting is being used for the user"
     :json-schema/example false}
    :boolean]])

(def ConcurrentJobLimits
  [:map
   [:limits
    {:description         "The list of concurrent job limits"
     :json-schema/example []}
    [:vector ConcurrentJobLimitListItem]]])

(def ConcurrentJobLimitUpdate
  [:map
   [:concurrent_jobs
    {:description         "The maximum number of concurrently running jobs"
     :json-schema/example 5}
    :int]])

(def AnalysisCount
  [:map
   [:count
    {:description         "The total number of jobs with the attached job status"
     :json-schema/example 10}
    :int]

   [:status
    {:description         "The status for the attached job count"
     :json-schema/example "Completed"}
    :string]])

(def AnalysisStats
  [:map
   [:status-count
    {:description         "List the total number of jobs grouped by job status for a user"
     :json-schema/example []}
    [:vector AnalysisCount]]])

(def AnalysisStatParams
  [:map
   IncludeHiddenParams
   IncludeDeletedParams
   [:filter
    {:optional            true
     :description         "A filter expression to limit the results"
     :json-schema/example "status=Completed"}
    :string]])
