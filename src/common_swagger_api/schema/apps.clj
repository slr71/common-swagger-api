(ns common-swagger-api.schema.apps
  (:use [common-swagger-api.schema :only [->optional-param
                                          describe
                                          optional-key->keyword
                                          NonBlankString
                                          PagingParams
                                          SortFieldDocs
                                          SortFieldOptionalKey]]
        [common-swagger-api.schema.apps.rating :only [Rating]]
        [common-swagger-api.schema.tools :only [Tool ToolDetails ToolListingImage ToolListingItem]]
        [schema.core :only [defschema
                            enum
                            optional-key
                            recursive
                            Any
                            Bool
                            Keyword]])
  (:require [clojure.set :as sets])
  (:import [java.util UUID Date]))

(def AppCopySummary "Make a Copy of an App Available for Editing")
(def AppCopyDocs "This service can be used to make a copy of an App in the user's workspace.")

(def AppCreateSummary "Add a new App.")
(def AppCreateDocs "This service adds a new App to the user's workspace.")

(def AppDeleteSummary "Logically Deleting an App")
(def AppDeleteDocs
  "An app can be marked as deleted in the DE without being completely removed from the database using this service.
   **Note**: an attempt to delete an App that is already marked as deleted is treated as a no-op rather than an error condition.
   If the App doesn't exist in the database at all, however, then that is treated as an error condition.")

(def AppDetailsSummary "Get App Details")

(def AppDocumentationSummary "Get App Documentation")
(def AppDocumentationDocs "This service is used by the DE to obtain documentation for a single App.")

(def AppDocumentationUpdateSummary "Update App Documentation")
(def AppDocumentationUpdateDocs "This service is used by the DE to update documentation for a single App.")

(def AppJobViewSummary "Obtain an app description.")
(def AppJobViewDocs
  "This service allows the Discovery Environment user interface to obtain an app description
   that can be used to construct a job submission form.")

(def AppLabelUpdateSummary "Update App Labels")

(def AppListingSummary "List Apps")

(def AppPreviewSummary "Preview Command Line Arguments")
(def AppPreviewDocs
  "The app integration utility in the DE uses this service to obtain an example list of command-line arguments
   so that the user can tell what the command-line might look like without having to run a job using the app that is being integrated first.
   The App request body also requires that each parameter contain a `value` field that contains the parameter value to include on the command line.
   The response body is in the same format as the `/arg-preview` service in the JEX.
   Please see the JEX documentation for more information.")

(def AppsShredderSummary "Logically Deleting Apps")
(def AppsShredderDocs
  "One or more Apps can be marked as deleted in the DE without being completely removed from the database using this service.
   **Note**: an attempt to delete an app that is already marked as deleted is treated as a no-op rather than an error condition.
   If the App doesn't exist in the database at all, however, then that is treated as an error condition.")

(def AppUpdateSummary "Update an App")

(def AppCategoryIdPathParam (describe UUID "The App Category's UUID"))
(def AppDeletedParam (describe Boolean "Whether the App is marked as deleted"))
(def AppDisabledParam (describe Boolean "Whether the App is marked as disabled"))
(def AppDocParam (describe String "The App's documentation"))
(def AppDocUrlParam (describe String "The App's documentation URL"))
(def AppIdParam (describe UUID "A UUID that is used to identify the App"))
(def AppPublicParam (describe Boolean "Whether the App has been published and is viewable by all users"))
(def AppReferencesParam (describe [String] "The App's references"))
(def StringAppIdParam (describe NonBlankString "The App identifier"))
(def SystemId (describe NonBlankString "The ID of the app execution system"))
(def ToolDeprecatedParam (describe Boolean "Flag indicating if this Tool has been deprecated"))

(def OptionalDebugKey (optional-key :debug))
(def OptionalDeprecatedKey (optional-key :deprecated))
(def OptionalGroupsKey (optional-key :groups))
(def OptionalToolsKey (optional-key :tools))
(def OptionalParametersKey (optional-key :parameters))
(def OptionalParameterArgumentsKey (optional-key :arguments))

(def ToolListDocs "The tools used to execute the App")
(def GroupListDocs "The list of Parameter Groups associated with the App")
(def ParameterListDocs "The list of Parameters in this Group")
(def ListItemOrTreeDocs
  "The List Parameter's arguments. Only used in cases where the user is given a fixed number of
   values to choose from. This can occur for Parameters such as `TextSelection` or
   `IntegerSelection` Parameters")
(def TreeSelectorParameterListDocs "The TreeSelector root's arguments")
(def TreeSelectorGroupListDocs "The TreeSelector root's groups")
(def TreeSelectorGroupParameterListDocs "The TreeSelector Group's arguments")
(def TreeSelectorGroupGroupListDocs "The TreeSelector Group's groups")
(def AppListingJobStatsDocs "Some launch statistics associated with the App")

(defschema IncludeHiddenParams
  {(optional-key :include-hidden)
   (describe Boolean "True if hidden elements should be included in the results.")})

(defschema AppParameterListItem
  {:id                         (describe UUID "A UUID that is used to identify the List Item")
   (optional-key :name)        (describe String "The List Item's name")
   (optional-key :value)       (describe String "The List Item's value")
   (optional-key :description) (describe String "The List Item's description")
   (optional-key :display)     (describe String "The List Item's display label")
   (optional-key :isDefault)   (describe Boolean "Flags this Item as the List's default selection")})

(defschema AppParameterListGroup
  (merge AppParameterListItem
         {OptionalParameterArgumentsKey
          (describe [AppParameterListItem] TreeSelectorGroupParameterListDocs)

          OptionalGroupsKey
          (describe [(recursive #'AppParameterListGroup)] TreeSelectorGroupGroupListDocs)}))

(defschema AppParameterListItemOrTree
  (merge AppParameterListItem
         {(optional-key :isSingleSelect)
          (describe Boolean "The TreeSelector root's single-selection flag")

          (optional-key :selectionCascade)
          (describe String "The TreeSelector root's cascace option")

          OptionalParameterArgumentsKey
          (describe [AppParameterListItem] TreeSelectorParameterListDocs)

          OptionalGroupsKey
          (describe [AppParameterListGroup] TreeSelectorGroupListDocs)}))

(defschema AppParameterValidator
  {:type
   (describe String
             "The validation rule's type, which describes how a property value should be validated. For
              example, if the type is `IntAbove` then the property value entered by the user must be an
              integer above a specific value, which is specified in the parameter list. You can use the
              `rule-types` endpoint to get a list of validation rule types")

   :params
   (describe [Any]
             "The list of parameters to use when validating a Parameter value. For example, to ensure that a
              Parameter contains a value that is an integer greater than zero, you would use a validation
              rule of type `IntAbove` along with a parameter list of `[0]`")})

(defschema AppFileParameters
  {(optional-key :format)
   (describe String "The Input/Output Parameter's file format")

   (optional-key :file_info_type)
   (describe String "The Input/Output Parameter's info type")

   (optional-key :is_implicit)
   (describe Boolean
             "Whether the Output Parameter name is specified on the command line (but still be referenced in
              Pipelines), or implicitly determined by the app itself. If the output file name is implicit
              then the output file name either must always be the same or it must follow a naming convention
              that can easily be matched with a glob pattern")

   (optional-key :repeat_option_flag)
   (describe Boolean
             "Whether or not the command-line option flag should preceed each file of a MultiFileSelector
             on the command line when the App is run")

   (optional-key :data_source)
   (describe String "The Output Parameter's source")

   (optional-key :retain)
   (describe Boolean
             "Whether or not the Input should be copied back to the job output directory in iRODS")})

(defschema AppParameter
  {:id
   (describe UUID "A UUID that is used to identify the Parameter")

   (optional-key :name)
   (describe String
             "The Parameter's name. In most cases, this field indicates the command-line option used to
              identify the Parameter on the command line. In these cases, the Parameter is assumed to be
              positional and no command-line option is used if the name is blank. For Parameters that
              specify a limited set of selection values, however, this is not the case. Instead, the
              Parameter arguments specify both the command-line flag and the Parameter value to use for each
              option that is selected")

   (optional-key :defaultValue)
   (describe Any "The Parameter's default value")

   (optional-key :value)
   (describe Any "The Parameter's value, used for previewing this parameter on the command-line.")

   (optional-key :label)
   (describe String "The Parameter's prompt to display in the UI")

   (optional-key :description)
   (describe String "The Parameter's description")

   (optional-key :order)
   (describe Long
             "The relative command-line order for the Parameter. If this field is not specified then the
              arguments will appear on the command-line in the order in which they appear in the import JSON.
              If you're not specifying the order, please be sure that the argument order is unimportant for
              the tool being integrated")

   (optional-key :required)
   (describe Boolean "Whether or not a value is required for this Parameter")

   (optional-key :isVisible)
   (describe Boolean "The Parameter's intended visibility in the job submission UI")

   (optional-key :omit_if_blank)
   (describe Boolean
             "Whether the command-line option should be omitted if the Parameter value is blank. This is
              most useful for optional arguments that use command-line flags in conjunction with a value. In
              this case, it is an error to include the command-line flag without a corresponding value. This
              flag indicates that the command-line flag should be omitted if the value is blank. This can
              also be used for positional arguments, but this flag tends to be useful only for trailing
              positional arguments")

   :type
   (describe String
             "The Parameter's type name. Must contain the name of one of the Parameter types defined in the
              database. You can get the list of defined and undeprecated Parameter types using the
              `parameter-types` endpoint")

   (optional-key :file_parameters)
   (describe AppFileParameters "The File Parameter specific details")

   OptionalParameterArgumentsKey
   (describe [AppParameterListItemOrTree] ListItemOrTreeDocs)

   (optional-key :validators)
   (describe [AppParameterValidator]
             "The Parameter's validation rules, which contains a list of rules that can be used to verify
              that Parameter values entered by a user are valid. Note that in cases where the user is given
              a list of possibilities to choose from, no validation rules are required because the selection
              list itself can be used to validate the Parameter value")})

(defschema AppGroup
  {:id
   (describe UUID "A UUID that is used to identify the Parameter Group")

   (optional-key :name)
   (describe String "The Parameter Group's name")

   (optional-key :description)
   (describe String "The Parameter Group's description")

   :label
   (describe String "The label used to identify the Parameter Group in the UI")

   (optional-key :isVisible)
   (describe Boolean "The Parameter Group's intended visibility in the job submission UI")

   OptionalParametersKey
   (describe [AppParameter] ParameterListDocs)})

(defschema AppBase
  {:id                              AppIdParam
   :name                            (describe String "The App's name")
   :description                     (describe String "The App's description")
   (optional-key :integration_date) (describe Date "The App's Date of public submission")
   (optional-key :edited_date)      (describe Date "The App's Date of its last edit")
   (optional-key :system_id)        SystemId})

(defschema App
  (merge AppBase
         {OptionalToolsKey           (describe [(merge Tool {OptionalDeprecatedKey ToolDeprecatedParam})] ToolListDocs)
          (optional-key :references) AppReferencesParam
          OptionalGroupsKey          (describe [AppGroup] GroupListDocs)}))

(def AppLabelUpdateRequest (describe App "The App to update."))

(defschema AppFileParameterDetails
  {:id          (describe String "The Parameter's ID")
   :name        (describe String "The Parameter's name")
   :description (describe String "The Parameter's description")
   :label       (describe String "The Input Parameter's label or the Output Parameter's value")
   :format      (describe String "The Parameter's file format")
   :required    (describe Boolean "Whether or not a value is required for this Parameter")})

(defschema AppTask
  {:system_id           (describe String "The Task's System ID")
   :id                  (describe String "The Task's ID")
   :name                (describe String "The Task's name")
   :description         (describe String "The Task's description")
   (optional-key :tool) ToolDetails
   :inputs              (describe [AppFileParameterDetails] "The Task's input parameters")
   :outputs             (describe [AppFileParameterDetails] "The Task's output parameters")})

(defschema AppTaskListing
  (assoc AppBase
    :id    (describe String "The App's ID.")
    :tasks (describe [AppTask] "The App's tasks")))

(defschema AppParameterJobView
  (assoc AppParameter
    :id
    (describe String
              "A string consisting of the App's step ID and the Parameter ID separated by an underscore.
               Both identifiers are necessary because the same task may be associated with a single App,
               which would cause duplicate keys in the job submission JSON. The step ID is prepended to
               the Parameter ID in order to ensure that all parameter value keys are unique.")))

(defschema AppGroupJobView
  (assoc AppGroup
    :id                   (describe String "The app group ID.")
    :step_number          (describe Long "The step number associated with this parameter group")
    OptionalParametersKey (describe [AppParameterJobView] ParameterListDocs)))

(defschema AppJobView
  (assoc AppBase
    :app_type         (describe String "DE or External.")
    :id               (describe String "The app ID.")
    :label            (describe String "An alias for the App's name")
    :deleted          AppDeletedParam
    :disabled         AppDisabledParam
    OptionalDebugKey  (describe Boolean "True if input files should be retained for the job by default.")
    OptionalGroupsKey (describe [AppGroupJobView] GroupListDocs)))

(defschema AppDetailCategory
  {:id AppCategoryIdPathParam
   :name (describe String "The App Category's name")})

(defschema AppDetailsTool
  (merge Tool
         {:id                       (describe String "The tool identifier.")
          (optional-key :container) ToolListingImage}))

(defschema AppListingJobStats
  {:job_count_completed
   (describe Long "The number of times this app has run to `Completed` status")

   (optional-key :job_last_completed)
   (describe Date "The last date this app has run to `Completed` status")})

(defschema AppDetails
  (merge AppBase
         {:id
          (describe String "The app identifier.")

          :tools
          (describe [AppDetailsTool] ToolListDocs)

          :deleted
          AppDeletedParam

          :disabled
          AppDisabledParam

          :integrator_email
          (describe String "The App integrator's email address.")

          :integrator_name
          (describe String "The App integrator's full name.")

          (optional-key :wiki_url)
          AppDocUrlParam

          :references
          AppReferencesParam

          (optional-key :job_stats)
          (describe AppListingJobStats AppListingJobStatsDocs)

          (optional-key :hierarchies)
          (describe Any
                    "The ontology hierarchies associated with the App")

          :categories
          (describe [AppDetailCategory]
                    "The list of Categories associated with the App")

          :suggested_categories
          (describe [AppDetailCategory]
                    "The list of Categories the integrator wishes to associate with the App")}))

(defschema AppDocumentation
  {(optional-key :app_id)
   StringAppIdParam

   :documentation
   AppDocParam

   :references
   AppReferencesParam

   (optional-key :created_on)
   (describe Date "The Date the App's documentation was created")

   (optional-key :modified_on)
   (describe Date "The Date the App's documentation was last modified")

   (optional-key :created_by)
   (describe String "The user that created the App's documentation")

   (optional-key :modified_by)
   (describe String "The user that last modified the App's documentation")})

(defschema AppDocumentationRequest
  (-> AppDocumentation
      (dissoc :references)
      (describe "The App Documentation Request")))

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

(defschema AppParameterListItemRequest
  (->optional-param AppParameterListItem :id))

(defschema AppParameterListGroupRequest
  (-> AppParameterListGroup
      (->optional-param :id)
      (assoc OptionalParameterArgumentsKey
             (describe [AppParameterListItemRequest] TreeSelectorGroupParameterListDocs)
             OptionalGroupsKey
             (describe [(recursive #'AppParameterListGroupRequest)] TreeSelectorGroupGroupListDocs))))

(defschema AppParameterListItemOrTreeRequest
  (-> AppParameterListItemOrTree
      (->optional-param :id)
      (assoc OptionalParameterArgumentsKey
             (describe [AppParameterListItemRequest] TreeSelectorParameterListDocs))
      (assoc OptionalGroupsKey
             (describe [AppParameterListGroupRequest] TreeSelectorGroupListDocs))))

(defschema AppParameterRequest
  (-> AppParameter
      (->optional-param :id)
      (assoc OptionalParameterArgumentsKey
             (describe [AppParameterListItemOrTreeRequest] ListItemOrTreeDocs))))

(defschema AppGroupRequest
  (-> AppGroup
      (->optional-param :id)
      (assoc OptionalParametersKey (describe [AppParameterRequest] ParameterListDocs))))

(defschema AppToolRequest
  (-> ToolListingItem
      (->optional-param :is_public)
      (->optional-param :permission)
      (->optional-param :implementation)
      (->optional-param :container)
      (assoc OptionalDeprecatedKey ToolDeprecatedParam)))

(defschema AppRequest
  (-> App
      (->optional-param :id)
      (assoc OptionalGroupsKey (describe [AppGroupRequest] GroupListDocs)
             OptionalToolsKey  (describe [AppToolRequest] ToolListDocs))))

(def AppCreateRequest (describe AppRequest "The App to add."))
(def AppUpdateRequest (describe AppRequest "The App to update."))

(defschema AppPreviewRequest
  (-> App
      (->optional-param :id)
      (->optional-param :name)
      (->optional-param :description)
      (assoc OptionalGroupsKey (describe [AppGroupRequest] GroupListDocs)
             (optional-key :is_public) AppPublicParam
             OptionalToolsKey (describe [AppToolRequest] ToolListDocs))
      (describe "The App to preview.")))

(defschema AppCategoryMetadata
  {(optional-key :avus) (describe [Any] "A listing of App Category metadata")})

(defschema PublishAppRequest
  (-> AppBase
      (->optional-param :id)
      (->optional-param :name)
      (->optional-param :description)
      (assoc (optional-key :documentation) AppDocParam
             (optional-key :references) AppReferencesParam)
      (merge AppCategoryMetadata)))

(defschema AppPublishableResponse
  {:publishable           (describe Boolean "True if the app is publishable.")
   (optional-key :reason) (describe String "The reason the app can't be published if it's not publishable.")})

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
