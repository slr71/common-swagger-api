(ns common-swagger-api.malli.apps
  (:require [common-swagger-api.malli :refer [NonBlankString]]
            [malli.util :as mu]))

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
  "An app can be marked as deleted in the DE without being completely removed from the database using this service.
   **Note**: an attempt to delete an App that is already marked as deleted is treated as a no-op rather than an error
   condition.  If the App doesn't exist in the database at all, however, then that is treated as an error condition.")

(def AppVersionDeleteSummary "Logically Deleting an App Version")
(def AppVersionDeleteDocs
  "An app version can be marked as deleted in the DE without being completely removed from the database using this
   service. **Note**: an attempt to delete an app version that is already marked as deleted is treated as a no-op rather
   than an error condition.  If the app version ID doesn't exist for the given app ID, however, then that is treated as
   an error condition.")

(def AppDetailsSummary "Get App Details")
(def AppDetailsDocs
  "This service is used by the DE to obtain high-level details about a single App.")

(def AppDocumentationSummary "Get App Documentation")
(def AppDocumentationDocs "This service is used by the DE to obtain documentation for a single App.")

(def AppDocumentationAddSummary "Add App Documentation")
(def AppDocumentationAddDocs "This service is used by the DE to add documentation for a single App.")

(def AppDocumentationUpdateSummary "Update App Documentation")
(def AppDocumentationUpdateDocs "This service is used by the DE to update documentation for a single App.")

(def AppEditingViewSummary "Make an App Available for Editing")
(def AppEditingViewDocs
  "The app integration utility in the DE uses this service to obtain the App description JSON so that it can be edited.
   The App must have been integrated by the requesting user.")

(def AppFavoriteAddSummary "Marking an App as a Favorite")
(def AppFavoriteAddDocs
  "Apps can be marked as favorites in the DE, which allows users to access them without having to search. This service
   is used to add an App to a user's favorites list.")

(def AppFavoriteDeleteSummary "Removing an App as a Favorite")
(def AppFavoriteDeleteDocs
  "Apps can be marked as favorites in the DE, which allows users to access them without having to search. This service
   is used to remove an App from a user's favorites list.")

(def AppIntegrationDataSummary "Return the Integration Data Record for an App")
(def AppIntegrationDataDocs "This service returns the integration data associated with an app.")

(def AppJobViewSummary "Obtain an app description.")
(def AppJobViewDocs
  "This service allows the Discovery Environment user interface to obtain an app description that can be used to
   construct a job submission form.")

(def AppLabelUpdateSummary "Update App Labels")

(def AppListingSummary "List Apps")
(def AppListingDocs
  "This service allows users to get a paged listing of all Apps accessible to the user. If the `search` parameter is
   included, then the results are filtered by the App name, description, integrator's name, tool name, or category name
   the app is under.")

(def SingleAppListingSummary "List An App")
(def SingleAppListingDocs
  "This service returns listing information for a single app. The Sonora UI uses this to provide a way to link to a
   single app without displaying the app launch dialog.")

(def AppPublishableSummary "Determine if an App Can be Made Public")
(def AppPublishableDocs
  "A multi-step App can't be made public if any of the Tasks that are included in it are not public. This endpoint
   returns a true flag if the App is a single-step App or it's a multistep App in which all of the Tasks included in the
   pipeline are public.")

(def AppPreviewSummary "Preview Command Line Arguments")
(def AppPreviewDocs
  "The app integration utility in the DE uses this service to obtain an example list of command-line arguments so that
   the user can tell what the command-line might look like without having to run a job using the app that is being
   integrated first. The App request body also requires that each parameter contain a `value` field that contains the
   parameter value to include on the command line. The response body is in the same format as the `/arg-preview` service
   in the JEX. Please see the JEX documentation for more information.")

(def AppRatingSummary "Rate an App")
(def AppRatingDocs
  "Users have the ability to rate an App for its usefulness, and this service provides the means to store the App
   rating. This service accepts a rating level between one and five, inclusive, and a comment identifier that refers to a
   comment in iPlant's Confluence wiki. The rating is stored in the database and associated with the authenticated
   user.")

(def AppRatingDeleteSummary "Delete an App Rating")
(def AppRatingDeleteDocs
  "The DE uses this service to remove a rating that a user has previously made. This service deletes the authenticated
   user's rating for the corresponding app-id.")

(def AppsShredderSummary "Logically Deleting Apps")
(def AppsShredderDocs
  "One or more Apps can be marked as deleted in the DE without being completely removed from the database using this
   service. **Note**: an attempt to delete an app that is already marked as deleted is treated as a no-op rather than an
   error condition. If the App doesn't exist in the database at all, however, then that is treated as an error
   condition.")

(def AppTaskListingSummary "List Tasks with File Parameters in an App")
(def AppTaskListingDocs
  "When a pipeline is being created, the UI needs to know what types of files are consumed by and what types of files
   are produced by each App's task in the pipeline. This service provides that information.")

(def AppToolListingSummary "List Tools used by an App")
(def AppToolListingDocs
  "This service lists information for all of the tools that are associated with an App. This information used to be
   included in the results of the App listing service.")

(def AppUpdateSummary "Update an App")
(def AppUpdateDocs
  "This service updates a single-step App in the database, as long as the App has not been submitted for public use, and
   the app's name must not duplicate the name of any other app (visible to the requesting user) under the same categories
   as this app.")

(def PublishAppSummary "Submit an App for Public Use")
(def PublishAppDocs
  "This service can be used to submit a private App for public use. The user supplies basic information about the App
   and a suggested location for it. The service records the information and suggested location then places the App in the
   Beta category. A Tito administrator can subsequently move the App to the suggested location at a later time if it
   proves to be useful.")

(defn AppCategoryIdPathParam [kw]
  [kw
   {:description         "The App Category's UUID"
    :json-schema/example "67d15627-22c5-42bd-8daf-9af5deecceab"}
   :uuid])

(defn AppDeletedParam [kw]
  [kw
   {:description         "Whether the App is marked as deleted"
    :json-schema/example false}
   :boolean])

(defn AppDisabledParam [kw]
  [kw
   {:description         "Whether the App is marked as disabled"
    :json-schema/example false}
   :boolean])

(defn AppDocParam [kw]
  [kw
   {:description         "The App's documentation"
    :json-schema/example "This app performs data analysis..."}
   :string])

(defn AppDocUrlParam [kw]
  [kw
   {:description         "The App's documentation URL"
    :json-schema/example "https://wiki.cyverse.org/wiki/display/DEmanual/Example+App"}
   :string])

(defn AppIdParam [kw]
  [kw
   {:description         "A UUID that is used to identify the App"
    :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
   :uuid])

(defn AppVersionParam [kw]
  [kw
   {:description         "The App's version"
    :json-schema/example "1.0.0"}
   :string])

(defn AppVersionIdParam [kw]
  [kw
   {:description         "The App's version ID"
    :json-schema/example "6c9b4f2e-8b3a-4d5e-9c7f-1a2b3c4d5e6f"}
   :uuid])

(defn AppLatestVersionParam [kw]
  [kw
   {:description         "The App's latest version"
    :json-schema/example "2.1.3"}
   :string])

(defn AppLatestVersionIdParam [kw]
  [kw
   {:description         "The latest App version ID"
    :json-schema/example "8e5f7a1b-2c3d-4e5f-9a7b-8c9d0e1f2a3b"}
   :string])

(defn AppPublicParam [kw]
  [kw
   {:description         "Whether the App has been published and is viewable by all users"
    :json-schema/example true}
   :boolean])

(defn AppReferencesParam [kw]
  [kw
   {:description         "The App's references"
    :json-schema/example ["https://example.com/paper1" "https://example.com/manual"]}
   [:vector :string]])

(defn StringAppIdParam [kw]
  [kw
   {:description         "The App identifier"
    :json-schema/example "my-analysis-app"}
   NonBlankString])

(defn SystemId [kw]
  [kw
   {:description         "The ID of the app execution system"
    :json-schema/example "de"}
   NonBlankString])

(defn ToolDeprecatedParam [kw]
  [kw
   {:description "Flag indicating if this Tool has been deprecated"
    :json-schema/example false}
   :boolean])

(def ToolListDocs "The tools used to execute the App")
(def GroupListDocs "The list of Parameter Groups associated with the App")
(def ParameterListDocs "The list of Parameters in this Group")
(def ListItemOrTreeDocs
  "The List Parameter's arguments. Only used in cases where the user is given a fixed number of values to choose
   from. This can occur for Parameters such as `TextSelection` or `IntegerSelection` Parameters")
(def TreeSelectorParameterListDocs "The TreeSelector root's arguments")
(def TreeSelectorGroupListDocs "The TreeSelector root's groups")
(def TreeSelectorGroupParameterListDocs "The TreeSelector Group's arguments")
(def TreeSelectorGroupGroupListDocs "The TreeSelector Group's groups")
(def AppListingJobStatsDocs "Some launch statistics associated with the App")

(def AppParameterListItem
  [:map
   [:id
    {:description         "A UUID that is used to identify the List Item"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:name
    {:description         "The List Item's name"
     :optional            true
     :json-schema/example "-c"}
    :string]

   [:value
    {:description         "The List Item's value"
     :optional            true
     :json-schema/example ""}
    :string]

   [:description
    {:description         "The List Item's description"
     :optional            true
     :json-schema/example "Count the number of bytes in the input files"}
    :string]

   [:display
    {:description         "The List Item's display label"
     :optional            true
     :json-schema/example "Bytes"}
    :string]

   [:isDefault
    {:description         "Flags this Item as the List's default selection"
     :optional            true
     :json-schema/example true}
    :boolean]])

(def AppParameterListGroup
  [:schema
   {:registry {::group (mu/merge
                        AppParameterListItem
                        [:map
                         [:arguments
                          {:description TreeSelectorGroupParameterListDocs
                           :optional    true}
                          [:vector AppParameterListItem]]

                         [:groups
                          {:description TreeSelectorGroupGroupListDocs
                           :optional    true}
                          [:vector [:ref ::group]]]])}}
   [:ref ::group]])

(def AppParameterListItemOrTree
  (mu/merge
   AppParameterListItem
   [:map
    [:isSingleSelect
     {:description         "The TreeSelector root's single-selection flag"
      :optional            true
      :json-schema/example true}
     :boolean]

    [:selectionCascade
     {:description         "The TreeSelector root's cascade option"
      :optional            true
      :json-schema/example false}
     :boolean]

    [:arguments
     {:description TreeSelectorParameterListDocs
      :optional    true}
     [:vector AppParameterListItem]]

    [:groups
     {:description TreeSelectorGroupListDocs
      :optional    true}
     [:vector AppParameterListGroup]]]))

(def AppParameterValidator
  [:map
   [:type
    {:description         (str "The validation rule's type, which describes how a property value should be validated. "
                               "For example, if the type is `IntAbove` then the property value entered by the user "
                               "must be an integer above a specific value, which is specified in the parameter list. "
                               "You can use the `rule-types` endpoint to get a list of validation rule types")
     :json-schema/example "IntAbove"}
    :string]

   [:params
    {:description         (str "The list of parameters to use when validating a Parameter value. For example, to "
                               "that a parameter contains a value that is an integer greater than zero, you would "
                               "use a validation rule of type `IntAbove` along with a parameter list of `[0]`")
     :json-schema/example [0]}
    [:vector :any]]])

(def AppFileParameters
  [:map
   [:format
    {:description         "The Input/Output Parameter's file format"
     :optional            true
     :json-schema/example "FASTA"}
    :string]

   [:file_info_type
    {:description         "The Input/Output Parameter's info type"
     :optional            true
     :json-schema/example "Nucleotide or Peptide Sequende"}
    :string]

   [:is_implicit
    {:description         (str "Whether the Output Parameter name is specified on the command line (but still be "
                               "referenced in Pipelines), or implicitly determined by the app itself. If the output "
                               "file name is implicit then the output file name either must always be the same or it "
                               "must follow a naming convention that can easily be matched with a glob pattern")
     :optional            true
     :json-schema/example false}
    :boolean]

   [:repeat_option_flag
    {:description         (str "Whether or not the command-line option flag should preceed each file of a "
                               "MultiFileSelector on the command line when the App is run")
     :optional            true
     :json-schema/example true}
    :boolean]

   [:data_source
    {:description         "The Output Parameter's source"
     :optional            true
     :json-schema/example "file"}
    :string]

   [:retain
    {:description         "Whether or not the Input should be copied back to the job output directory in iRODS"
     :optional            true
     :json-schema/example true}
    :boolean]])

(def AppParameter
  [:map
   [:id
    {:description         "A UUID that is used to identify the Parameter"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:name
    {:description         (str "The Parameter's name. In most cases, this field indicates the command-line option "
                               "used to identify the Parameter on the command line. In these cases, the Parameter is "
                               "assumed to be positional and no command-line option is used if the name is blank. For "
                               "Parameters that specify a limited set of selection values, however, this is not the "
                               "case. Instead, the Parameter arguments specify both the command-line flag and the "
                               "Parameter value to use for each option that is selected")
     :optional            true
     :json-schema/example "--input-file"}
    :string]

   [:defaultValue
    {:description         "The Parameter's default value"
     :optional            true
     :json-schema/example "default.txt"}
    :any]

   [:value
    {:description         "The Parameter's value, used for previewing this parameter on the command-line."
     :optional            true
     :json-schema/example "example.txt"}
    :any]

   [:label
    {:description         "The Parameter's prompt to display in the UI"
     :optional            true
     :json-schema/example "Input File"}
    :string]

   [:description
    {:description         "The Parameter's description"
     :optional            true
     :json-schema/example "Select the input file for analysis"}
    :string]

   [:order
    {:description         (str "The relative command-line order for the Parameter. If this field is not specified "
                               "then the arguments will appear on the command-line in the order in which they appear "
                               "in the import JSON. If you're not specifying the order, please be sure that the "
                               "argument order is unimportant for the tool being integrated")
     :optional            true
     :json-schema/example 1}
    :int]

   [:required
    {:description         "Whether or not a value is required for this Parameter"
     :optional            true
     :json-schema/example true}
    :boolean]

   [:isVisible
    {:description         "The Parameter's intended visibility in the job submission UI"
     :optional            true
     :json-schema/example true}
    :boolean]

   [:omit_if_blank
    {:description         (str "Whether the command-line option should be omitted if the Parameter value is blank. "
                               "This is most useful for optional arguments that use command-line flags in conjunction "
                               "with a value. In this case, it is an error to include the command-line flag without a "
                               "corresponding value. This flag indicates that the command-line flag should be omitted "
                               "if the value is blank. This can also be used for positional arguments, but this flag "
                               "tends to be useful only for trailing positional arguments")
     :optional            true
     :json-schema/example false}
    :boolean]

   [:type
    {:description         (str "The Parameter's type name. Must contain the name of one of the Parameter types "
                               "defined in the database. You can get the list of defined and undeprecated Parameter "
                               "types using the `parameter-types` endpoint")
     :json-schema/example "FileInput"}
    :string]

   [:file_parameters
    {:description         "The File Parameter specific details"
     :optional            true}
    AppFileParameters]

   [:arguments
    {:description         ListItemOrTreeDocs
     :optional            true}
    [:vector AppParameterListItemOrTree]]

   [:validators
    {:description         (str "The Parameter's validation rules, which contains a list of rules that can be used "
                               "to verify that Parameter values entered by a user are valid. Note that in cases where "
                               "the user is given a list of possibilities to choose from, no validation rules are "
                               "required because the selection list itself can be used to validate the Parameter value")
     :optional            true}
    [:vector AppParameterValidator]]])

(def AppGroup
  [:map
   [:id
    {:description         "A UUID that is used to identify the Parameter Group"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]

   [:name
    {:description         "The Parameter Group's name"
     :optional            true
     :json-schema/example "Input Parameters"}
    :string]

   [:description
    {:description         "The Parameter Group's description"
     :optional            true
     :json-schema/example "Parameters for configuring input files and options"}
    :string]

   [:label
    {:description         "The label used to identify the Parameter Group in the UI"
     :json-schema/example "Input Configuration"}
    :string]

   [:isVisible
    {:description         "The Parameter Group's intended visibility in the job submission UI"
     :optional            true
     :json-schema/example true}
    :boolean]

   [:parameters
    {:description         ParameterListDocs
     :optional            true}
    [:vector AppParameter]]])

(def AppVersionDetails
  [:map
   [:version
    {:description         "The App's version"
     :json-schema/example "1.0.0"}
    :string]

   [:version_id
    {:description         "The App's version ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def AppVersionListing
  [:map
   [:versions
    {:description         "The list of available versions for this app"
     :optional            true}
    [:vector AppVersionDetails]]])

(def AppVersionOrderRequest
  [:map
   [:versions
    {:description         "The app versions in descending order, with the newest (or latest) first."}
    [:vector (mu/optional-keys AppVersionDetails [:version])]]])
