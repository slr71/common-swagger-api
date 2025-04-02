(ns common-swagger-api.schema.data.exists
  (:require [clojure-commons.error-codes :as ce]
            [common-swagger-api.schema
             :refer [describe
                     doc-only
                     CommonResponses
                     ErrorResponseUnchecked]]
            [common-swagger-api.schema.data :as data-schema]
            [schema.core :as s]))

(def ExistenceSummary "File and Folder Existence")
(def ExistenceDocs
  "This endpoint allows the caller to check for the existence of a set of files and folders.")

(s/defschema ExistenceRequest
  (describe data-schema/Paths "The paths to check for existence."))

(s/defschema PathExistenceMap
  {(describe s/Keyword "The iRODS data item's path")
   (describe Boolean "Whether this path from the request exists")})

(s/defschema ExistenceInfo
  {:paths
   (describe PathExistenceMap "Paths existence mapping")})

;; Used only for display as documentation in Swagger UI
(s/defschema ExistenceResponsePathsMap
  {(keyword "/path/from/request/to/a/file/or/folder")
   (describe Boolean "Whether this path from the request exists")})

;; Used only for display as documentation in Swagger UI
(s/defschema ExistenceResponse
  {:paths
   (describe ExistenceResponsePathsMap "A map of paths from the request to their existence info")})

(def ExistenceErrorCodeResponses
  (conj data-schema/CommonErrorCodeResponses
        ce/ERR_NOT_A_USER
        ce/ERR_TOO_MANY_RESULTS))

(s/defschema ExistenceErrorResponses
  (merge ErrorResponseUnchecked
         {:error_code (apply s/enum ExistenceErrorCodeResponses)}))

(s/defschema ExistenceResponses
  (merge CommonResponses
         {200 {:schema      (doc-only ExistenceInfo ExistenceResponse)
               :description "A map of paths from the request to their existence info"}
          500 {:schema      ExistenceErrorResponses
               :description data-schema/CommonErrorCodeDocs}}))
