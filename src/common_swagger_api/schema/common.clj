(ns common-swagger-api.schema.common
  (:require [common-swagger-api.schema :refer [describe]]
            [schema.core :refer [defschema optional-key]]))

(defschema IncludeHiddenParams
  {(optional-key :include-hidden)
   (describe Boolean "True if hidden elements should be included in the results")})

(defschema IncludeDeletedParams
  {(optional-key :include-deleted)
   (describe Boolean "True if deleted elements should be included in the results")})
