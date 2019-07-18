(ns common-swagger-api.schema.data
  (:use [clojure-commons.error-codes]
        [common-swagger-api.schema :only [describe NonBlankString]])
  (:require [schema.core :as s])
  (:import [java.util UUID]))

(def CommonErrorCodeResponses [ERR_UNCHECKED_EXCEPTION ERR_SCHEMA_VALIDATION])

(def DataIdPathParam (describe UUID "The UUID assigned to the file or folder"))

(def PermissionEnum (s/enum :read :write :own))

(s/defschema Paths
  {:paths (describe [(s/one NonBlankString "path") NonBlankString] "A list of iRODS paths")})
