(ns common-swagger-api.schema.tools.admin
  (:use [common-swagger-api.schema :only [->optional-param describe]]
        [schema.core :only [defschema optional-key]])
  (:require [common-swagger-api.schema.tools :as schema])
  (:import [java.util UUID]))

(defschema ToolIdsList
  {:tool_ids (describe [UUID] "A List of Tool IDs")})

(defschema ToolUpdateParams
  {(optional-key :overwrite-public)
   (describe Boolean "Flag to force container settings updates of public tools.")})

(defschema ToolsImportRequest
  {:tools (describe [schema/ToolImportRequest] "zero or more Tool definitions")})

(defschema ToolUpdateRequest
  (-> schema/ToolImportRequest
      (->optional-param :name)
      (->optional-param :version)
      (->optional-param :type)
      (->optional-param :implementation)
      (->optional-param :container)))
