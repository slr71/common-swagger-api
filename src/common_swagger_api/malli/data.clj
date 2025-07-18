(ns common-swagger-api.malli.data
  (:require [common-swagger-api.malli :refer [NonBlankString PagingParams]]
            [common-swagger-api.malli.filetypes :refer [ValidInfoTypesEnum]]))

(def PermissionEnum
  [:enum :read :write :own])

(def Paths
  [:map
   [:paths
    {:description         "A list of iRODS paths"
     :json-schema/example ["/iplant/home/user/file1.txt"]}
    [:vector NonBlankString]]])

(def OptionalPaths
  [:map
   [:paths
    {:optional            true
     :description         "A list of iRODS paths"
     :json-schema/example ["/iplant/home/user/file1.txt"]}
    [:vector NonBlankString]]])

(def DataIds
  [:map
   [:ids
    {:description         "A list of iRODS data-object UUIDs"
     :json-schema/example ["550e8400-e29b-41d4-a716-446655440000"]}
    [:vector :uuid]]])

(def OptionalPathsOrDataIds
  [:map
   [:paths
    {:optional            true
     :description         "A list of iRODS paths"
     :json-schema/example ["/iplant/home/user/file1.txt"]}
    [:vector NonBlankString]]

   [:ids
    {:optional            true
     :description         "A list of iRODS data-object UUIDs"
     :json-schema/example ["550e8400-e29b-41d4-a716-446655440000"]}
    [:vector :uuid]]])

(def ValidFolderListingSortFields
  [:enum :datecreated :datemodified :name :path :size])

(def FolderListingPagingParams
  [:map
   PagingParams
   [:sort-field
    {:optional            true
     :description         "The field to sort by"
     :json-schema/example "name"}
    ValidFolderListingSortFields]])

(def FolderListingParams
  [:map
   FolderListingPagingParams
   [:entity-type
    {:optional            true
     :description         "The type of folder items to include in the response"
     :json-schema/example "any"}
    [:enum :any :file :folder]]

   [:info-type
    {:optional            true
     :description         "A list of info-types with which to filter a folder's result items"
     :json-schema/example ["File"]}
    [:or ValidInfoTypesEnum [:vector ValidInfoTypesEnum]]]])