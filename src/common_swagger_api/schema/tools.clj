(ns common-swagger-api.schema.tools
  (:use [clojure-commons.error-codes]
        [common-swagger-api.schema :only [->optional-param describe ErrorResponse]]
        [common-swagger-api.schema.common :only [IncludeHiddenParams]]
        [common-swagger-api.schema.containers
         :only [DevicesParamOptional
                Image
                NewToolContainer
                Settings
                ToolContainer
                VolumesFromParamOptional
                VolumesParamOptional]]
        [schema.core :only [defschema enum optional-key]])
  (:import (java.util UUID)))

(def ToolIdParam (describe UUID "A UUID that is used to identify the Tool"))
(def ToolRequestIdParam (describe UUID "The Tool Requests's UUID"))
(def ToolRequestToolIdParam (describe UUID "The ID of the tool the user is requesting to be made public"))
(def ToolNameParam (describe String "The Tool's name (can be the file name or Docker image)"))
(def ToolDescriptionParam (describe String "A brief description of the Tool"))
(def VersionParam (describe String "The Tool's version"))
(def AttributionParam (describe String "The Tool's author or publisher"))
(def SubmittedByParam (describe String "The username of the user that submitted the Tool Request"))
(def ToolImplementationDocs "Information about the user who integrated the Tool into the DE")
(def Interactive (describe Boolean "Determines whether the tool is interactive."))

(defschema ToolSearchParams
  (merge IncludeHiddenParams
         {(optional-key :search) (describe String "The pattern to match in an Tool's Name or Description.")
          (optional-key :public) (describe Boolean
                                           "Set to `true` to list only public Tools, `false` to list only private Tools,
                                            or leave unset to list all Tools.")}))

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
   (optional-key :time_limit_seconds) (describe Integer "The number of seconds that a tool is allowed to execute. A value of 0 means the time limit is disabled")
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
      (merge {:container PrivateToolContainerImportRequest})))

(defschema PrivateToolUpdateRequest
  (-> PrivateToolImportRequest
      (->optional-param :name)
      (->optional-param :version)
      (->optional-param :container)))

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

(defschema ToolListingToolRequestSummary
  (select-keys ToolRequestSummary [:id :status]))

(defschema ToolListingItem
  (merge ToolDetails
         {:implementation              (describe ToolImplementor ToolImplementationDocs)
          :container                   ToolListingImage
          (optional-key :tool_request) ToolListingToolRequestSummary}))

(defschema ToolListing
  {:tools (describe [ToolListingItem] "Listing of App Tools")})

(defschema ErrorPrivateToolRequestBadParam
  (assoc ErrorResponse
    :error_code (describe (enum ERR_EXISTS ERR_BAD_OR_MISSING_FIELD) "Exists or Bad Field error code")))

(def PrivateToolImportResponse400
  {:schema      ErrorPrivateToolRequestBadParam
   :description "
* `ERR_EXISTS`: A Tool with the given `name` already exists.
* `ERR_BAD_OR_MISSING_FIELD`: The image with the given `name` and `tag` has been deprecated."})
