(ns common-swagger-api.malli.apps
  (:require
   [common-swagger-api.malli :refer [NonBlankString]]
   [malli.core :as m]))

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

(def AppReferencesParam
  [[:vector :string] {:description         "The App's references"
                      :json-schema/example ["https://doi.org/10.1093/nar/gkv416" "PMID: 25916842"]}])

(def StringAppIdParam
  [:and NonBlankString {:description         "The App identifier"
                        :json-schema/example "app-id-12345"}])

(def SystemId
  [:and NonBlankString {:description         "The ID of the app execution system"
                        :json-schema/example "de"}])

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


(def AppParameterValidator
  [:map {:closed true}
   [:type
    {:description         (str "The validation rule's type, which describes how a property value should be validated. For "
                               "example, if the type is `IntAbove` then the property value entered by the user must be an "
                               "integer above a specific value, which is specified in the parameter list. You can use the "
                               "`rule-types` endpoint to get a list of validation rule types")
     :json-schema/example "IntAbove"}
    :string]

   [:params
    {:description         (str "The list of parameters to use when validating a Parameter value. For example, to ensure that a "
                               "Parameter contains a value that is an integer greater than zero, you would use a validation "
                               "rule of type `IntAbove` along with a parameter list of `[0]`")
     :json-schema/example [0]}
    [:vector :any]]])
