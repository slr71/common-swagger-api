(ns common-swagger-api.schema.tools.admin
  (:require [common-swagger-api.schema
             :refer [->optional-param
                     describe
                     CommonResponses
                     ErrorResponseExists
                     ErrorResponseNotFound
                     ErrorResponseNotWritable]]
            [common-swagger-api.schema.tools :as schema]
            [schema.core :refer [defschema optional-key]])
  (:import [java.util UUID]))

(def ToolDeleteSummary "Delete a Tool")
(def ToolDeleteDocs
  "Deletes a tool, as long as it is not in use by any apps.")

(def ToolDetailsDocs "This endpoint returns the details for one tool.")

(def ToolsImportSummary "Add new Tools.")

(def ToolInstallRequestDeleteSummary "Delete a Tool Request")
(def ToolInstallRequestDeleteDocs
  "This service allows administrators to delete a tool request.
   This endpoint is primarily intended for use in the QA cleanup suite.")

(def ToolInstallRequestDetailsSummary "Obtain Tool Request Details")
(def ToolInstallRequestDetailsDocs
  "This service obtains detailed information about a tool request.
   This is the service that the DE support team uses to obtain the request details.")

(def ToolInstallRequestListingDocs
  "This endpoint lists high level details about tool requests that have been submitted.
   Administrators may use this endpoint to track tool requests for all users.")

(def ToolInstallRequestStatusCodeDeleteSummary "Delete a Tool Request Status Code")
(def ToolInstallRequestStatusCodeDeleteDocs
  "This service allows administrators to delete a tool request status code
   provided that the status code isn't in use in a tool request.")

(def ToolInstallRequestStatusUpdateSummary "Update the Status of a Tool Request")
(def ToolInstallRequestStatusUpdateDocs
  "This endpoint is used by Discovery Environment administrators to update the status of a tool request.")

(def ToolIntegrationUpdateSummary "Update the Integration Data Record for a Tool")
(def ToolIntegrationUpdateDocs
  "This service allows administrators to change the integration data record associated with a tool.")

(def ToolListingDocs "This endpoint allows admins to get a listing of all Tools.")

(def ToolPublishSummary "Make a Private Tool Public")
(def ToolPublishDocs
  "This service makes a Private Tool public and available to all users.
   The request body fields are optional and allow the admin to make updates to the tool in the same request.")

(def ToolUpdateSummary "Update a Tool")

(defschema ToolIdsList
  {:tool_ids (describe [UUID] "A List of Tool IDs")})

(defschema ToolUpdateParams
  {(optional-key :overwrite-public)
   (describe Boolean "Flag to force container settings updates of public tools.")})

(defschema ToolsImportRequest
  (-> {:tools (describe [schema/ToolImportRequest] "zero or more Tool definitions")}
      (describe "The Tools to import.")))

(defschema ToolUpdateRequest
  (-> schema/ToolImportRequest
      (->optional-param :name)
      (->optional-param :version)
      (->optional-param :type)
      (->optional-param :implementation)
      (->optional-param :container)
      (describe "The Tool to update.")))

(defschema ToolRequestStatusUpdate
  (-> schema/ToolRequestStatus
      (dissoc :updated_by :status_date)
      (describe "A Tool Request status update.")))

(defschema ToolDeleteResponses
  (merge CommonResponses
         {200 {:description "The Tool was successfully deleted."}
          400 {:schema      ErrorResponseNotWritable
               :description "The Tool is already in use by apps and could not be deleted."}
          404 {:schema      ErrorResponseNotFound
               :description "A Tool with the given `tool-id` does not exist."}}))

(defschema ToolDetailsResponses
  (merge CommonResponses
         {200 {:schema      schema/ToolDetails
               :description "The Tool details."}
          404 {:schema      ErrorResponseNotFound
               :description "The `tool-id` does not exist."}}))

(defschema ToolsImportResponses
  (merge CommonResponses
         {200 {:schema      ToolIdsList
               :description "A list of the new Tool IDs."}
          400 {:schema      ErrorResponseExists
               :description "A Tool with the given `name` already exists."}}))

(defschema ToolPublishResponses
  (merge CommonResponses
         {200 {:schema      schema/ToolDetails
               :description "The Tool details."}
          400 {:schema      ErrorResponseNotWritable
               :description "The Tool is already public."}
          404 {:schema      ErrorResponseNotFound
               :description "The `tool-id` does not exist."}}))

(defschema ToolUpdateResponses
  (merge CommonResponses
         {200 {:schema      schema/ToolDetails
               :description "The Tool details."}
          400 {:schema      ErrorResponseNotWritable
               :description "The Tool is in use by public apps its container could not be updated."}
          404 {:schema      ErrorResponseNotFound
               :description "The `tool-id` does not exist."}}))
