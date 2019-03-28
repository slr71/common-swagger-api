(ns common-swagger-api.schema.tools
  (:use [common-swagger-api.schema :only [->optional-param describe]]
        [common-swagger-api.schema.containers :only [Image
                                                     Settings
                                                     ToolContainer]]
        [schema.core :only [defschema enum optional-key]])
  (:require [schema.core :as s])
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
