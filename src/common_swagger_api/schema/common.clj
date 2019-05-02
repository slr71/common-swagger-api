(ns common-swagger-api.schema.common
  (:use [common-swagger-api.schema :only [describe]]
        [schema.core :only [defschema optional-key]]))

(defschema IncludeHiddenParams
  {(optional-key :include-hidden)
   (describe Boolean "True if hidden elements should be included in the results.")})
