(ns common-swagger-api.malli.apps
  (:require
   [common-swagger-api.malli.containers :refer [Settings]]
   [common-swagger-api.malli.tools :refer [ToolDetails]]
   [malli.core :as m]
   [malli.util :as mu]))

;; Endpoint description definitions

(def AppCopySummary "Make a Copy of an App Available for Editing")
(def AppCopyDocs "This service can be used to make a copy of an App in the user's workspace.")

(def AppCreateSummary "Add a new App.")
(def AppCreateDocs "This service adds a new App to the user's workspace.")

(def AppVersionCreateSummary "Add a new App Version.")
(def AppVersionCreateDocs "This service adds a new App Version to an existing App.")

(def AppVersionOrderSummary "Set App Version Order")
(def AppVersionOrderDocs "This service allows App Versions to be reordered.")

(def AppDeleteSummary "Logically Deleting an App")
(def AppDeleteDocs
  (str "An app can be marked as deleted in the DE without being completely removed from the database using this "
       "service. **Note**: an attempt to delete an App that is already marked as deleted is treated as a no-op "
       "rather than an error condition. If the App doesn't exist in the database at all, however, then that is "
       "treated as an error condition."))

(def AppVersionDeleteSummary "Logically Deleting an App Version")
(def AppVersionDeleteDocs
  (str "An app version can be marked as deleted in the DE without being completely removed from the database using "
       "this service. **Note**: an attempt to delete an app version that is already marked as deleted is treated "
       "as a no-op rather than an error condition. If the app version ID doesn't exist for the given app ID, "
       "however, then that is treated as an error condition."))

(def AppDetailsSummary "Get App Details")
(def AppDetailsDocs "This service is used by the DE to obtain high-level details about a single App.")

(def AppDocumentationSummary "Get App Documentation")
(def AppDocumentationDocs "This service is used by the DE to obtain documentation for a single App.")

(def AppDocumentationAddSummary "Add App Documentation")
(def AppDocumentationAddDocs "This service is used by the DE to add documentation for a single App.")

(def AppDocumentationUpdateSummary "Update App Documentation")
(def AppDocumentationUpdateDocs "This service is used by the DE to update documentation for a single App.")

(def AppEditingViewSummary "Make an App Available for Editing")
(def AppEditingViewDocs
  (str "The app integration utility in the DE uses this service to obtain the App description JSON so that it "
       "can be edited. The App must have been integrated by the requesting user."))

(def AppFavoriteAddSummary "Marking an App as a Favorite")
(def AppFavoriteAddDocs
  (str "Apps can be marked as favorites in the DE, which allows users to access them without having to search. "
       "This service is used to add an App to a user's favorites list."))

(def AppFavoriteDeleteSummary "Removing an App as a Favorite")
(def AppFavoriteDeleteDocs
  (str "Apps can be marked as favorites in the DE, which allows users to access them without having to search. "
       "This service is used to remove an App from a user's favorites list."))

(def AppIntegrationDataSummary "Return the Integration Data Record for an App")
(def AppIntegrationDataDocs "This service returns the integration data associated with an app.")

(def AppJobViewSummary "Obtain an app description.")
(def AppJobViewDocs
  (str "This service allows the Discovery Environment user interface to obtain an app description that can be "
       "used to construct a job submission form."))

(def AppLabelUpdateSummary "Update App Labels")

(def AppListingSummary "List Apps")
(def AppListingDocs
  (str "This service allows users to get a paged listing of all Apps accessible to the user. If the `search` "
       "parameter is included, then the results are filtered by the App name, description, integrator's name, "
       "tool name, or category name the app is under."))

(def SingleAppListingSummary "List An App")
(def SingleAppListingDocs
  (str "This service returns listing information for a single app. The Sonora UI uses this to provide a way to "
       "link to a single app without displaying the app launch dialog."))

(def AppPublishableSummary "Determine if an App Can be Made Public")
(def AppPublishableDocs
  (str "A multi-step App can't be made public if any of the Tasks that are included in it are not public. This "
       "endpoint returns a true flag if the App is a single-step App or it's a multistep App in which all of the "
       "Tasks included in the pipeline are public."))

(def AppPreviewSummary "Preview Command Line Arguments")
(def AppPreviewDocs
  (str "The app integration utility in the DE uses this service to obtain an example list of command-line "
       "arguments so that the user can tell what the command-line might look like without having to run a job "
       "using the app that is being integrated first. The App request body also requires that each parameter "
       "contain a `value` field that contains the parameter value to include on the command line. The response "
       "body is in the same format as the `/arg-preview` service in the JEX. Please see the JEX documentation "
       "for more information."))

(def AppRatingSummary "Rate an App")
(def AppRatingDocs
  (str "Users have the ability to rate an App for its usefulness, and this service provides the means to store "
       "the App rating. This service accepts a rating level between one and five, inclusive, and a comment "
       "identifier that refers to a comment in iPlant's Confluence wiki. The rating is stored in the database "
       "and associated with the authenticated user."))

(def AppRatingDeleteSummary "Delete an App Rating")
(def AppRatingDeleteDocs
  (str "The DE uses this service to remove a rating that a user has previously made. This service deletes the "
       "authenticated user's rating for the corresponding app-id."))

(def AppsShredderSummary "Logically Deleting Apps")
(def AppsShredderDocs
  (str "One or more Apps can be marked as deleted in the DE without being completely removed from the database "
       "using this service. **Note**: an attempt to delete an app that is already marked as deleted is treated "
       "as a no-op rather than an error condition. If the App doesn't exist in the database at all, however, "
       "then that is treated as an error condition."))

(def AppTaskListingSummary "List Tasks with File Parameters in an App")
(def AppTaskListingDocs
  (str "When a pipeline is being created, the UI needs to know what types of files are consumed by and what "
       "types of files are produced by each App's task in the pipeline. This service provides that information."))

(def AppToolListingSummary "List Tools used by an App")
(def AppToolListingDocs
  (str "This service lists information for all of the tools that are associated with an App. This information "
       "used to be included in the results of the App listing service."))

(def AppUpdateSummary "Update an App")
(def AppUpdateDocs
  (str "This service updates a single-step App in the database, as long as the App has not been submitted for "
       "public use, and the app's name must not duplicate the name of any other app (visible to the requesting "
       "user) under the same categories as this app."))

(def PublishAppSummary "Submit an App for Public Use")
(def PublishAppDocs
  (str "This service can be used to submit a private App for public use. The user supplies basic information "
       "about the App and a suggested location for it. The service records the information and suggested location "
       "then places the App in the Beta category. A Tito administrator can subsequently move the App to the "
       "suggested location at a later time if it proves to be useful."))

(def ToolListDocs "The tools used to execute the App")
(def GroupListDocs "The list of Parameter Groups associated with the App")
(def ParameterListDocs "The list of Parameters in this Group")
(def ListItemOrTreeDocs
  (str "The List Parameter's arguments. Only used in cases where the user is given a fixed number of values to choose "
       "from. This can occur for Parameters such as `TextSelection` or `IntegerSelection` Parameters"))
(def TreeSelectorParameterListDocs "The TreeSelector root's arguments")
(def TreeSelectorGroupListDocs "The TreeSelector root's groups")
(def TreeSelectorGroupParameterListDocs "The TreeSelector Group's arguments")
(def TreeSelectorGroupGroupListDocs "The TreeSelector Group's groups")
(def AppListingJobStatsDocs "Some launch statistics associated with the App")

;; Field definitions

(def AppCategoryIdPathParam
  [:uuid {:description         "The App Category's UUID"
          :json-schema/example #uuid "123e4567-e89b-12d3-a456-426614174000"}])

(def AppDeletedParam
  [:boolean {:description         "Whether the App is marked as deleted"
             :json-schema/example false}])

(def AppDisabledParam
  [:boolean {:description         "Whether the App is marked as disabled"
             :json-schema/example false}])

(def AppDocParam
  [:string {:description         "The App's documentation"
            :json-schema/example "This tool performs sequence alignment using BLAST algorithm."}])

(def AppDocUrlParam
  [:string {:description         "The App's documentation URL"
            :json-schema/example "https://wiki.example.org/apps/blast"}])

(def AppIdParam
  [:uuid {:description         "A UUID that is used to identify the App"
          :json-schema/example #uuid "987e6543-e21b-32c1-b456-426614174000"}])

(def AppVersionParam
  [:string {:description         "The App's version"
            :json-schema/example "1.2.0"}])

(def AppVersionIdParam
  [:uuid {:description         "The App's version ID"
          :json-schema/example #uuid "456e7890-b12c-34d5-e678-901234567890"}])

(def AppLatestVersionParam
  [:string {:description         "The App's latest version"
            :json-schema/example "2.1.0"}])

(def AppLatestVersionIdParam
  [:string {:description         "The latest App version ID"
            :json-schema/example "latest-version-id-123"}])

(def AppPublicParam
  [:boolean {:description         "Whether the App has been published and is viewable by all users"
             :json-schema/example true}])

(def StringAppIdParam
  [:string {:description         "The App identifier"
            :json-schema/example "app-id-12345"
            :min                 1}])

(def SystemId
  [:string {:description         "The ID of the app execution system"
            :json-schema/example "de"
            :min                 1}])

(def ToolDeprecatedParam
  [:boolean {:description         "Flag indicating if this Tool has been deprecated"
             :json-schema/example false}])

;; Schema definitions

(def AppParameterListItem
  [:map {:closed true}
   [:id
    {:description         "A UUID that is used to identify the List Item"
     :json-schema/example #uuid "789a0123-c45d-67e8-f901-234567890abc"}
    :uuid]

   [:name
    {:optional            true
     :description         "The List Item's name"
     :json-schema/example "small_genome"}
    :string]

   [:value
    {:optional            true
     :description         "The List Item's value"
     :json-schema/example "1"}
    :string]

   [:description
    {:optional            true
     :description         "The List Item's description"
     :json-schema/example "Small genome (< 2 Mb)"}
    :string]

   [:display
    {:optional            true
     :description         "The List Item's display label"
     :json-schema/example "Small Genome"}
    :string]

   [:isDefault
    {:optional            true
     :description         "Flags this Item as the List's default selection"
     :json-schema/example false}
    :boolean]])

;; FIXME: This is a little clunky because it replicates all of the fields in AppParameterListItem. I haven't found a way
;; to get it to work with merging schemas defined outside of the registry, though, and I'm not sure why. I'm leaving
;; this as-is for now with the hopes of returning to it later.
(def AppParameterListGroup
  (m/schema
    [:schema {:registry {::AppParameterListGroup
                         [:map {:closed true}
                          [:id
                           {:description         "A UUID that is used to identify the List Item"
                            :json-schema/example #uuid "789a0123-c45d-67e8-f901-234567890abc"}
                           :uuid]

                          [:name
                           {:optional            true
                            :description         "The List Item's name"
                            :json-schema/example "genome_size_group"}
                           :string]

                          [:value
                           {:optional            true
                            :description         "The List Item's value"
                            :json-schema/example "size_group"}
                           :string]

                          [:description
                           {:optional            true
                            :description         "The List Item's description"
                            :json-schema/example "Genome size selection group"}
                           :string]

                          [:display
                           {:optional            true
                            :description         "The List Item's display label"
                            :json-schema/example "Genome Size"}
                           :string]

                          [:isDefault
                           {:optional            true
                            :description         "Flags this Item as the List's default selection"
                            :json-schema/example false}
                           :boolean]

                          [:arguments
                           {:optional    true
                            :description TreeSelectorGroupParameterListDocs}
                           [:vector AppParameterListItem]]

                          [:groups
                           {:optional    true
                            :description TreeSelectorGroupGroupListDocs}
                           [:vector [:ref ::AppParameterListGroup]]]]}}
     [:ref ::AppParameterListGroup]]))

(def AppParameterListItemOrTree
  (mu/merge
   AppParameterListItem
   [:map
    [:isSingleSelect
     {:optional            true
      :description         "The TreeSelector root's single-selection flag"
      :json-schema/example true}
     :boolean]

    [:selectionCascade
     {:optional            true
      :description         "The TreeSelector root's cascace option"
      :json-schema/example "up"}
     :string]

    [:arguments
     {:optional    true
      :description TreeSelectorParameterListDocs}
     [:vector AppParameterListItem]]

    [:groups
     {:optional    true
      :description TreeSelectorGroupListDocs}
     [:vector AppParameterListGroup]]]))

(def AppParameterValidator
  [:map {:closed true}
   [:type
    {:description
     (str "The validation rule's type, which describes how a property value should be validated. For "
          "example, if the type is `IntAbove` then the property value entered by the user must be an "
          "integer above a specific value, which is specified in the parameter list. You can use the "
          "`rule-types` endpoint to get a list of validation rule types")
     :json-schema/example "IntAbove"}
    :string]

   [:params
    {:description
     (str "The list of parameters to use when validating a Parameter value. For example, to ensure that a "
          "Parameter contains a value that is an integer greater than zero, you would use a validation "
          "rule of type `IntAbove` along with a parameter list of `[0]`")
     :json-schema/example [0]}
    [:vector :any]]])

(def AppFileParameters
  [:map {:closed true}
   [:format
    {:optional            true
     :description         "The Input/Output Parameter's file format"
     :json-schema/example "fasta"}
    :string]

   [:file_info_type
    {:optional            true
     :description         "The Input/Output Parameter's info type"
     :json-schema/example "SequenceAlignment"}
    :string]

   [:is_implicit
    {:optional            true
     :description
     (str "Whether the Output Parameter name is specified on the command line (but still be referenced in "
          "Pipelines), or implicitly determined by the app itself. If the output file name is implicit "
          "then the output file name either must always be the same or it must follow a naming convention "
          "that can easily be matched with a glob pattern")
     :json-schema/example false}
    :boolean]

   [:repeat_option_flag
    {:optional            true
     :description
     (str "Whether or not the command-line option flag should preceed each file of a MultiFileSelector "
          "on the command line when the App is run")
     :json-schema/example false}
    :boolean]

   [:data_source
    {:optional            true
     :description         "The Output Parameter's source"
     :json-schema/example "stdout"}
    :string]

   [:retain
    {:optional            true
     :description         "Whether or not the Input should be copied back to the job output directory in iRODS"
     :json-schema/example true}
    :boolean]])

(def AppParameter
  [:map {:closed true}
   [:id
    {:description         "A UUID that is used to identify the Parameter"
     :json-schema/example #uuid "abc12345-def6-7890-abcd-ef1234567890"}
    :uuid]

   [:name
    {:optional    true
     :description
     (str "The Parameter's name. In most cases, this field indicates the command-line option used to "
          "identify the Parameter on the command line. In these cases, the Parameter is assumed to be "
          "positional and no command-line option is used if the name is blank. For Parameters that "
          "specify a limited set of selection values, however, this is not the case. Instead, the "
          "Parameter arguments specify both the command-line flag and the Parameter value to use for each "
          "option that is selected")
     :json-schema/example "--input"}
    :string]

   [:defaultValue
    {:optional            true
     :description         "The Parameter's default value"
     :json-schema/example "default_value"}
    :any]

   [:value
    {:optional            true
     :description         "The Parameter's value, used for previewing this parameter on the command-line."
     :json-schema/example "example_value"}
    :any]

   [:label
    {:optional            true
     :description         "The Parameter's prompt to display in the UI"
     :json-schema/example "Input File"}
    :string]

   [:description
    {:optional            true
     :description         "The Parameter's description"
     :json-schema/example "Specify the input file for processing"}
    :string]

   [:order
    {:optional    true
     :description
     (str "The relative command-line order for the Parameter. If this field is not specified then the "
          "arguments will appear on the command-line in the order in which they appear in the import JSON. "
          "If you're not specifying the order, please be sure that the argument order is unimportant for "
          "the tool being integrated")
     :json-schema/example 1}
    :int]

   [:required
    {:optional            true
     :description         "Whether or not a value is required for this Parameter"
     :json-schema/example true}
    :boolean]

   [:isVisible
    {:optional            true
     :description         "The Parameter's intended visibility in the job submission UI"
     :json-schema/example true}
    :boolean]

   [:omit_if_blank
    {:optional    true
     :description
     (str "Whether the command-line option should be omitted if the Parameter value is blank. This is "
          "most useful for optional arguments that use command-line flags in conjunction with a value. In "
          "this case, it is an error to include the command-line flag without a corresponding value. This "
          "flag indicates that the command-line flag should be omitted if the value is blank. This can "
          "also be used for positional arguments, but this flag tends to be useful only for trailing "
          "positional arguments")
     :json-schema/example false}
    :boolean]

   [:type
    {:description
     (str "The Parameter's type name. Must contain the name of one of the Parameter types defined in the "
          "database. You can get the list of defined and undeprecated Parameter types using the "
          "`parameter-types` endpoint")
     :json-schema/example "FileInput"}
    :string]

   [:file_parameters
    {:optional    true
     :description "The File Parameter specific details"}
    AppFileParameters]

   [:arguments
    {:optional    true
     :description ListItemOrTreeDocs}
    [:vector AppParameterListItemOrTree]]

   [:validators
    {:optional    true
     :description
     (str "The Parameter's validation rules, which contains a list of rules that can be used to verify "
          "that Parameter values entered by a user are valid. Note that in cases where the user is given "
          "a list of possibilities to choose from, no validation rules are required because the selection "
          "list itself can be used to validate the Parameter value")}
    [:vector AppParameterValidator]]])

(def AppGroup
  [:map {:closed true}
   [:id
    {:description         "A UUID that is used to identify the Parameter Group"
     :json-schema/example #uuid "fedcba98-7654-3210-fedc-ba9876543210"}
    :uuid]

   [:name
    {:optional            true
     :description         "The Parameter Group's name"
     :json-schema/example "input_parameters"}
    :string]

   [:description
    {:optional            true
     :description         "The Parameter Group's description"
     :json-schema/example "Input file and configuration parameters"}
    :string]

   [:label
    {:description         "The label used to identify the Parameter Group in the UI"
     :json-schema/example "Input Parameters"}
    :string]

   [:isVisible
    {:optional            true
     :description         "The Parameter Group's intended visibility in the job submission UI"
     :json-schema/example true}
    :boolean]

   [:parameters
    {:optional    true
     :description ParameterListDocs}
    [:vector AppParameter]]])

(def AppVersionDetails
  [:map {:closed true}
   [:version AppVersionParam]
   [:version_id AppVersionIdParam]])

(def AppVersionListing
  [:map {:closed true}
   [:versions
    {:optional    true
     :description "The list of available versions for this app"}
    [:vector AppVersionDetails]]])

(def AppVersionOrderRequest
  [:map {:closed true}
   [:versions
    {:description "The app versions in descending order, with the newest (or latest) first."}
    [:vector (mu/optional-keys AppVersionDetails [:version])]]])

(def AppBase
  (mu/merge
   [:map
    [:id AppIdParam]

    [:name
     {:description         "The App's name"
      :json-schema/example "BLAST"}
     :string]

    [:description
     {:description         "The App's description"
      :json-schema/example "Basic Local Alignment Search Tool for sequence comparison"}
     :string]

    [:integration_date
     {:optional            true
      :description         "The App's Date of public submission"
      :json-schema/example #inst "2024-01-15T10:30:00.000-00:00"}
     inst?]

    [:edited_date
     {:optional            true
      :description         "The App's Date of its last edit"
      :json-schema/example #inst "2024-10-20T14:45:00.000-00:00"}
     inst?]

    [:system_id
     {:optional true}
     SystemId]]
   (mu/optional-keys AppVersionDetails)))

(def AppLimitCheckResult
  [:map {:closed true}
   [:limitCheckID
    {:description         "An identifier indicating which limit check failed"
     :json-schema/example "concurrent-job-limit"}
    :string]

   [:reasonCodes
    {:description         "A list of codes indicating the reason for the limit check failure"
     :json-schema/example ["MAX_JOBS_EXCEEDED" "QUOTA_LIMIT_REACHED"]}
    [:vector :string]]

   [:additionalInfo
    {:description         "An arbitrary object providing information relevant to the limit check"
     :json-schema/example {:current_jobs 10 :max_jobs 5}}
    :any]])

(def AppLimitCheckResultSummary
  [:map {:closed true}
   [:canRun
    {:description
     "True if the user is currently permitted to launch an analysis using the app"
     :json-schema/example true}
    :boolean]

   [:results
    {:description
     (str "A list of individual limit check results providing information about why "
          "the check failed. This list will be empty if the user is permitted to use "
          "the app")
     :json-schema/example []}
    [:vector AppLimitCheckResult]]])

(def AppLimitChecks
  [:map {:closed true}
   [:limitChecks
    {:optional    true
     :description
     "Indicates whether or not the user is currently permitted to launch an analysis using the app"}
    AppLimitCheckResultSummary]])

(def AppTools
  [:map
   [:tools
    {:description ToolListDocs}
    [:vector {:min 1} (mu/merge ToolDeprecatedParam [:map [:deprecated {:optional true} ToolDeprecatedParam]])]]

   [:references
    {:description         "The App's references"
     :json-schema/example ["https://doi.org/10.1093/nar/gkv416" "PMID: 25916842"]
     :optional            true}
    [:vector :string]]

   [:groups
    {:optional    true
     :description GroupListDocs}
    [:vector AppGroup]]])

(def App
  (-> AppBase
      (mu/merge AppVersionListing)
      (mu/merge AppTools)))

(def AppLabelUpdateRequest
  (mu/update-properties
   (mu/dissoc App :versions)
   assoc :description "The App to update."))

(def AppFileParameterDetails
  [:map {:closed true}
   [:id
    {:description         "The Parameter's ID"
     :json-schema/example "param_123"}
    :string]

   [:name
    {:description         "The Parameter's name"
     :json-schema/example "output_alignment"}
    :string]

   [:description
    {:description         "The Parameter's description"
     :json-schema/example "The output alignment file from the analysis"}
    :string]

   [:label
    {:description         "The Input Parameter's label or the Output Parameter's value"
     :json-schema/example "Output Alignment"}
    :string]

   [:format
    {:description         "The Parameter's file format"
     :json-schema/example "bam"}
    :string]

   [:required
    {:description         "Whether or not a value is required for this Parameter"
     :json-schema/example true}
    :boolean]])

(def AppTask
  [:map {:closed true}
   [:system_id
    {:description         "The Task's System ID"
     :json-schema/example "de"}
    :string]

   [:id
    {:description         "The Task's ID"
     :json-schema/example "task_456"}
    :string]

   [:name
    {:description         "The Task's name"
     :json-schema/example "BLAST Analysis"}
    :string]

   [:description
    {:description         "The Task's description"
     :json-schema/example "Performs BLAST sequence alignment analysis"}
    :string]

   [:tool
    {:optional    true
     :description "The Task's tool details"}
    ToolDetails]

   [:inputs
    {:description "The Task's input parameters"}
    [:vector AppFileParameterDetails]]

   [:outputs
    {:description "The Task's output parameters"}
    [:vector AppFileParameterDetails]]])

(def AppTaskListing
  (mu/merge
   AppBase
   [:map
    [:id
     {:description         "The App's ID."
      :json-schema/example "app_789"}
     :string]

    [:tasks
     {:description "The App's tasks"}
     [:vector AppTask]]]))

(def AppParameterJobView
  (mu/merge
   AppParameter
   [:map
    {:closed true}

    [:id
     {:description
      (str "A string consisting of the App's step ID and the Parameter ID separated by an underscore. "
           "Both identifiers are necessary because the same task may be associated with a single App, "
           "which would cause duplicate keys in the job submission JSON. The step ID is prepended to "
           "the Parameter ID in order to ensure that all parameter value keys are unique.")
      :json-schema/example "step_123_param_456"}
     :string]]))

(def AppStepResourceRequirements
  (-> (mu/select-keys
       Settings
       [:memory_limit
        :min_memory_limit
        :min_cpu_cores
        :max_cpu_cores
        :min_disk_space])
      (mu/merge
       [:map
        {:description "The Tool resource requirements for this step"
         :closed      true}

        [:default_max_cpu_cores
         {:optional            true
          :description         "The default limit of CPU cores requested to run the tool container"
          :json-schema/example 2.0}
         :double]

        [:default_cpu_cores
         {:optional            true
          :description         "The default minimum of CPU cores requested to run the tool container"
          :json-schema/example 1.0}
         :double]

        [:default_memory
         {:optional            true
          :description         "The default amount of memory (in bytes) requested to run the tool container"
          :json-schema/example 536870912}
         :int]

        [:default_disk_space
         {:optional            true
          :description         "The default amount of disk space requested to run the tool container"
          :json-schema/example 5368709120}
         :int]

        [:step_number
         {:description         "The sequential step number of the Tool in the analysis"
          :json-schema/example 1}
         :int]])))

(def AppGroupJobView
  (mu/merge
   AppGroup
   [:map
    {:closed true}

    [:id
     {:description         "The app group ID."
      :json-schema/example "step_123_group_456"}
     :string]

    [:step_number
     {:description         "The step number associated with this parameter group"
      :json-schema/example 1}
     :int]

    [:parameters
     {:optional    true
      :description ParameterListDocs}
     [:vector AppParameterJobView]]]))

(def AppJobView
  (-> (mu/merge AppBase AppLimitChecks)
      (mu/merge AppVersionListing)
      (mu/merge
        [:map
    {:closed true}

    [:id
     {:description         "The app ID."
      :json-schema/example "app-id-abc123"}
     :string]

    [:app_type
     {:description         "DE or External."
      :json-schema/example "DE"}
     :string]

    [:label
     {:description         "An alias for the App's name"
      :json-schema/example "BLAST Analysis Tool"}
     :string]

    [:deleted AppDeletedParam]

    [:disabled AppDisabledParam]

    [:debug
     {:optional            true
      :description         "True if input files should be retained for the job by default."
      :json-schema/example false}
     :boolean]

    [:requirements
     {:optional    true
      :description "The list of resource requirements for each step"}
     [:vector AppStepResourceRequirements]]

    [:groups
     {:optional    true
      :description GroupListDocs}
     [:vector AppGroupJobView]]])))
