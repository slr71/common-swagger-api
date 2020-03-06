(ns common-swagger-api.schema.data
  (:use [clojure-commons.error-codes]
        [common-swagger-api.schema :only [describe
                                          NonBlankString
                                          PagingParams
                                          SortFieldDocs
                                          SortFieldOptionalKey]]
        [common-swagger-api.schema.filetypes :only [ValidInfoTypesEnum]])
  (:require [schema.core :as s]
            [schema-tools.core :as st])
  (:import [java.util UUID]))

(def CommonErrorCodeResponses [ERR_UNCHECKED_EXCEPTION ERR_SCHEMA_VALIDATION])
(def CommonErrorCodeDocs "Potential Error Codes returned by this endpoint.")

(def DataIdPathParam (describe UUID "The UUID assigned to the file or folder"))

(def PermissionEnum (s/enum :read :write :own))

(def ZonePathParam (describe String "The IRODS zone"))
(def PathPathParam (describe String "The IRODS path under the zone"))

(s/defschema Paths
  {:paths (describe [(s/one NonBlankString "path") NonBlankString] "A list of iRODS paths")})

(s/defschema OptionalPaths
  {(s/optional-key :paths) (describe [NonBlankString] "A list of iRODS paths")})

(s/defschema DataIds
  {:ids (describe [UUID] "A list of iRODS data-object UUIDs")})

(s/defschema OptionalPathsOrDataIds
  (-> (merge DataIds OptionalPaths)
      st/optional-keys
      (describe "The path or data ids of the data objects to gather status information on.")))

(def ValidFolderListingSortFields
  #{:datecreated
    :datemodified
    :name
    :path
    :size})

(s/defschema FolderListingPagingParams
  (merge
   PagingParams
   {SortFieldOptionalKey
    (describe (apply s/enum ValidFolderListingSortFields) SortFieldDocs)}))

(s/defschema FolderListingParams
  (merge
   FolderListingPagingParams
   {(s/optional-key :entity-type)
    (describe (s/enum :any :file :folder) "The type of folder items to include in the response.")

    (s/optional-key :info-type)
    (describe (s/either [ValidInfoTypesEnum] ValidInfoTypesEnum)
              "A list of info-types with which to filter a folder's result items.")}))
