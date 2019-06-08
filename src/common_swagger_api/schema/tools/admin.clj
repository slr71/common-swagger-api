(ns common-swagger-api.schema.tools.admin
  (:use [common-swagger-api.schema
         :only [->optional-param
                describe
                CommonResponses
                ErrorResponseExists
                ErrorResponseNotFound
                ErrorResponseNotWritable]]
        [schema.core :only [defschema optional-key]])
  (:require [common-swagger-api.schema.tools :as schema])
  (:import [java.util UUID]))

(def ToolDeleteSummary "Delete a Tool")
(def ToolDeleteDocs
  "Deletes a tool, as long as it is not in use by any apps.")

(def ToolDetailsDocs "This endpoint returns the details for one tool.")

(def ToolsImportSummary "Add new Tools.")

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
  (dissoc schema/ToolRequestStatus :updated_by :status_date))

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
