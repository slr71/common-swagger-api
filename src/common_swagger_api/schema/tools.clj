(ns common-swagger-api.schema.tools
  (:require [clojure-commons.error-codes :as ce]
        [common-swagger-api.schema
         :refer [->optional-param
                CommonResponses
                describe
                ErrorResponse
                ErrorResponseForbidden
                ErrorResponseNotFound
                ErrorResponseNotWritable
                PagingParams]]
        [common-swagger-api.schema.common :refer [IncludeHiddenParams]]
        [common-swagger-api.schema.containers
         :refer [coerce-settings-long-values
                DevicesParamOptional
                Image
                NewToolContainer
                Settings
                ToolContainer
                VolumesFromParamOptional
                VolumesParamOptional]]
        [schema.core
         :refer [defschema
                enum
                Int
                optional-key]])
  (:import (java.util UUID)))

(defn- coerce-container-settings-long-values
  [tool]
  (if (contains? tool :container)
    (update tool :container coerce-settings-long-values)
    tool))

(defn coerce-tool-import-requests
  "Middleware that converts any container values in the given tool import/update request that should be a Long."
  [handler]
  (fn [request]
    (handler (update request :body-params coerce-container-settings-long-values))))

(defn coerce-tool-list-import-request
  "Middleware that converts any container values in the given tool list import request that should be a Long."
  [handler]
  (fn [request]
    (handler (update-in request [:body-params :tools] (partial map coerce-container-settings-long-values)))))

(def ToolAddSummary "Add Private Tool")

(def ToolAppListingSummary "Get Apps by Tool")
(def ToolAppListingDocs
  "This endpoint returns a listing of Apps using the given Tool.")

(def ToolDeleteSummary "Delete a Private Tool")
(def ToolDeleteDocs
  "Deletes a private Tool, as long as it is not in use by any Apps.
   The requesting user must have ownership permission for the Tool.
   If the Tool is already in use in private Apps,
   then an `ce/ERR_NOT_WRITEABLE` will be returned along with a listing of the Apps using this Tool,
   unless the `force-delete` flag is set to `true`.")

(def ToolDetailsSummary "Get a Tool")
(def ToolDetailsDocs
  "This endpoint returns the details for one tool accessible to the user.")

(def ToolInstallRequestSummary "Request Tool Installation")
(def ToolInstallRequestDocs
  "This service submits a request for a tool to be installed so that it can be used from within the Discovery Environment.
   The installation request and all status updates related to the tool request will be tracked in the Discovery Environment database.")

(def ToolInstallRequestListingSummary "List Tool Requests")
(def ToolInstallRequestListingDocs
  "This endpoint lists high level details about tool requests that have been submitted.
   A user may track their own tool requests with this endpoint.")

(def ToolInstallRequestStatusCodeListingSummary "List Tool Request Status Codes")
(def ToolInstallRequestStatusCodeListingDocs
  "Tool request status codes are largely arbitrary, but once they've been used once,
   they're stored in the database so that they can be reused easily.
   This endpoint allows the caller to list the known status codes.")

(def ToolIntegrationDataListingSummary "Return the Integration Data Record for a Tool")
(def ToolIntegrationDataListingDocs
  "This service returns the integration data associated with an app.")

(def ToolListingSummary "List Tools")
(def ToolListingDocs
  "This endpoint allows users to get a listing of all Tools accessible to the user.")

(def ToolPermissionsListingSummary "List Tool Permissions")
(def ToolPermissionsListingDocs
  "This endpoint allows the caller to list the permissions for one or more Tools.
   The authenticated user must have read permission on every Tool in the request body for this endpoint to succeed.")

(def ToolUpdateSummary "Update a Private Tool")

(def ToolIdParam (describe UUID "A UUID that is used to identify the Tool"))
(def ToolRequestIdParam (describe UUID "The Tool Requests's UUID"))
(def ToolRequestToolIdParam (describe UUID "The ID of the tool the user is requesting to be made public"))
(def ToolNameParam (describe String "The Tool's name (can be the file name or Docker image)"))
(def ToolDescriptionParam (describe String "A brief description of the Tool"))
(def VersionParam (describe String "The Tool's version"))
(def AttributionParam (describe String "The Tool's author or publisher"))
(def SubmittedByParam (describe String "The username of the user that submitted the Tool Request"))
(def ToolImplementationDocs "Information about the user who integrated the Tool into the DE")
(def ToolRequestStatusCodeId (describe UUID "The Status Code's UUID"))
(def Interactive (describe Boolean "Determines whether the tool is interactive."))

(defschema ToolSearchParams
  (merge IncludeHiddenParams
         PagingParams
         {(optional-key :search) (describe String "The pattern to match in a Tool's Name or Description.")
          (optional-key :public) (describe Boolean
                                           "Set to `true` to list only public Tools, `false` to list only private Tools,
                                            or leave unset to list all Tools.")}))
(defschema ToolDetailsParams
  {(optional-key :include-defaults)
   (describe Boolean "Flag to include defaults set by configuration or not")})

(defschema PrivateToolDeleteParams
  {(optional-key :force-delete)
   (describe Boolean "Flag to force deletion of a Tool already in use by Apps.")})

(defschema ToolTestData
  {(optional-key :params) (describe [String] "The list of command-line parameters")
   :input_files           (describe [String] "The list of paths to test input files in iRODS")
   :output_files          (describe [String] "The list of paths to expected output files in iRODS")})

(defschema ToolImplementor
  {:implementor       (describe String "The name of the implementor")
   :implementor_email (describe String "The email address of the implementor")})

(defschema ToolImplementation
  (merge ToolImplementor
         {:test (describe ToolTestData "The test data for the Tool")}))

(defschema Tool
  {:id                                ToolIdParam
   :name                              ToolNameParam
   (optional-key :description)        ToolDescriptionParam
   (optional-key :attribution)        AttributionParam
   (optional-key :location)           (describe String "The path of the directory containing the Tool")
   :version                           VersionParam
   :type                              (describe String "The Tool Type name")
   (optional-key :restricted)         (describe Boolean "Determines whether a time limit is applied and whether network access is granted")
   (optional-key :time_limit_seconds) (describe Int "The number of seconds that a tool is allowed to execute. A value of 0 means the time limit is disabled")
   (optional-key :interactive)        Interactive})

(defschema ToolDetails
  (merge Tool
         {:is_public      (describe Boolean "Whether the Tool has been published and is viewable by all users")
          :permission     (describe String "The user's access level for the Tool")
          :implementation (describe ToolImplementation ToolImplementationDocs)
          :container      ToolContainer}))

(defschema ToolListingImage
  (assoc (dissoc Settings :id)
    :image (dissoc Image :id)))

(defschema ToolImportRequest
  (-> Tool
      (->optional-param :id)
      (merge
        {:implementation (describe ToolImplementation ToolImplementationDocs)
         :container      NewToolContainer})))

(defschema PrivateToolContainerImportRequest
  (dissoc NewToolContainer
          DevicesParamOptional
          VolumesParamOptional
          VolumesFromParamOptional))

(defschema PrivateToolImportRequest
  (-> ToolImportRequest
      (->optional-param :type)
      (->optional-param :implementation)
      (merge {:container PrivateToolContainerImportRequest})
      (describe "The private Tool to import.")))

(defschema PrivateToolUpdateRequest
  (-> PrivateToolImportRequest
      (->optional-param :name)
      (->optional-param :version)
      (->optional-param :container)
      (describe "The private Tool to update.")))

(defschema ToolRequestStatus
  {(optional-key :status)
   (describe String
             "The status code of the Tool Request update. The status code is case-sensitive, and if it isn't
              defined in the database already then it will be added to the list of known status codes")

   :status_date
   (describe Long "The timestamp of the Tool Request status update")

   :updated_by
   (describe String "The username of the user that updated the Tool Request status")

   (optional-key :comments)
   (describe String "The administrator comments of the Tool Request status update")})

(defschema ToolRequestDetails
  {:id
   ToolRequestIdParam

   :submitted_by
   SubmittedByParam

   (optional-key :phone)
   (describe String "The phone number of the user submitting the request")

   (optional-key :tool_id)
   ToolRequestToolIdParam

   :name
   ToolNameParam

   :description
   ToolDescriptionParam

   (optional-key :source_url)
   (describe String "A link that can be used to obtain the tool")

   (optional-key :source_upload_file)
   (describe String "The path to a file that has been uploaded into iRODS")

   :documentation_url
   (describe String "A link to the tool documentation")

   :version
   VersionParam

   (optional-key :attribution)
   AttributionParam

   (optional-key :multithreaded)
   (describe Boolean
             "A flag indicating whether or not the tool is multithreaded. This can be `true` to indicate
              that the user requesting the tool knows that it is multithreaded, `false` to indicate that the
              user knows that the tool is not multithreaded, or omitted if the user does not know whether or
              not the tool is multithreaded")

   :test_data_path
   (describe String "The path to a test data file that has been uploaded to iRODS")

   :cmd_line
   (describe String "Instructions for using the tool")

   (optional-key :additional_info)
   (describe String
             "Any additional information that may be helpful during tool installation or validation")

   (optional-key :additional_data_file)
   (describe String
             "Any additional data file that may be helpful during tool installation or validation")

   (optional-key :architecture)
   (describe (enum "32-bit Generic" "64-bit Generic" "Others" "Don't know")
             "One of the architecture names known to the DE. Currently, the valid values are
              `32-bit Generic` for a 32-bit executable that will run in the DE,
              `64-bit Generic` for a 64-bit executable that will run in the DE,
              `Others` for tools run in a virtual machine or interpreter, and
              `Don't know` if the user requesting the tool doesn't know what the architecture is")

   :history
   (describe [ToolRequestStatus] "A history of status updates for this Tool Request")

   (optional-key :interactive)
   Interactive})

(defschema ToolRequest
  (-> ToolRequestDetails
      (dissoc :id :submitted_by :history)
      (describe
        "A tool installation request. One of `source_url` or `source_upload_file` fields are required, but not both.")))

(defschema ToolRequestSummary
  {:id                     ToolRequestIdParam
   :name                   ToolNameParam
   :version                VersionParam
   :requested_by           SubmittedByParam
   (optional-key :tool_id) ToolRequestToolIdParam
   :date_submitted         (describe Long "The timestamp of the Tool Request submission")
   :status                 (describe String "The current status of the Tool Request")
   :date_updated           (describe Long "The timestamp of the last Tool Request status update")
   :updated_by             (describe String "The username of the user that last updated the Tool Request status")})

(defschema ToolRequestListing
  {:tool_requests (describe [ToolRequestSummary]
                            "A listing of high level details about tool requests that have been submitted")})

(defschema ToolRequestListingParams
  (merge PagingParams
         {(optional-key :status)
          (describe String
                    "The name of a status code to include in the results. The name of the status code is case
                     sensitive. If the status code isn't already defined, it will be added to the database")}))

(defschema ToolRequestStatusCodeListingParams
  {(optional-key :filter)
   (describe String
             "If this parameter is set then only the status codes that contain the string passed in this
              query parameter will be listed. This is a case-insensitive search")})

(defschema ToolRequestStatusCode
  {:id          ToolRequestStatusCodeId
   :name        (describe String "The Status Code")
   :description (describe String "A brief description of the Status Code")})

(defschema ToolRequestStatusCodeListing
  {:status_codes (describe [ToolRequestStatusCode] "A listing of known Status Codes")})

(defschema ToolListingToolRequestSummary
  (select-keys ToolRequestSummary [:id :status]))

(defschema ToolListingItem
  (merge ToolDetails
         {:implementation              (describe ToolImplementor ToolImplementationDocs)
          :container                   ToolListingImage
          (optional-key :tool_request) ToolListingToolRequestSummary}))

(defschema ToolListing
  {:tools (describe [ToolListingItem] "Listing of App Tools")
   :total (describe Int "The total number of App Tools in the listing")})

(defschema ErrorPrivateToolRequestBadParam
  (assoc ErrorResponse
    :error_code (describe (enum ce/ERR_EXISTS ce/ERR_BAD_OR_MISSING_FIELD) "Exists or Bad Field error code")))

(def PrivateToolImportResponse400
  {:schema      ErrorPrivateToolRequestBadParam
   :description "
* `ce/ERR_EXISTS`: A Tool with the given `name` already exists.
* `ce/ERR_BAD_OR_MISSING_FIELD`: The image with the given `name` and `tag` has been deprecated."})

(def PrivateToolImportResponses
  (merge CommonResponses
         {200 {:schema      ToolDetails
               :description "The new Tool details."}
          400 PrivateToolImportResponse400}))

(def ToolDeleteResponses
  (merge CommonResponses
         {200 {:description "The Tool was successfully deleted."}
          400 {:schema      ErrorResponseNotWritable
               :description "The Tool could not be deleted."}
          403 {:schema      ErrorResponseForbidden
               :description "The requesting user does not have permission to delete this Tool."}
          404 {:schema      ErrorResponseNotFound
               :description "A Tool with the given `tool-id` does not exist."}}))

(def ToolDetailsResponses
  (merge CommonResponses
         {200 {:schema      ToolDetails
               :description "The Tool details."}
          403 {:schema      ErrorResponseForbidden
               :description "The requesting user does not have `read` permission for the Tool."}
          404 {:schema      ErrorResponseNotFound
               :description "The `tool-id` does not exist."}}))

(def ToolUpdateResponses
  (merge CommonResponses
         {200 {:schema      ToolDetails
               :description "The updated Tool details."}
          400 PrivateToolImportResponse400}))
