(ns common-swagger-api.malli.stats
  (:require [common-swagger-api.malli :refer [NonBlankString]]
            [common-swagger-api.malli.data :refer [PermissionEnum]]))

(def DataTypeEnum
  [:enum :file :dir])

(def StatQueryParams
  [:map
   [:validation-behavior
    {:optional            true
     :description         "What level of permissions on the queried files should be validated?"
     :json-schema/example "read"}
    PermissionEnum]])

(def FilteredStatQueryParams
  [:map
   StatQueryParams
   [:filter-include
    {:optional            true
     :description         "Comma-separated list of keys to generate and return in each stat object. Defaults to all keys. If both this and filter-exclude are provided, includes are processed first, then excludes."
     :json-schema/example "id,path,type"}
    :string]
   [:filter-exclude
    {:optional            true
     :description         "Comma-separated list of keys to exclude from each stat object. Defaults to no keys. If both this and filter-include are provided, includes are processed first, then excludes."
     :json-schema/example "metadata"}
    :string]])

(def DataStatInfo
  [:map
   [:id
    {:description         "The UUID of this data item"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:path
    {:description         "The IRODS paths to this data item"
     :json-schema/example "/iplant/home/user/file.txt"}
    NonBlankString]
   [:type
    {:description         "The data item's type"
     :json-schema/example "file"}
    DataTypeEnum]
   [:label
    {:description         "The descriptive label for this item"
     :json-schema/example "file.txt"}
    :string]
   [:date-created
    {:description         "The date this data item was created"
     :json-schema/example 1634567890000}
    :int]
   [:date-modified
    {:description         "The date this data item was last modified"
     :json-schema/example 1634567890000}
    :int]
   [:file-size
    {:description         "The size of the data item in bytes"
     :json-schema/example 1024}
    :int]
   [:permission
    {:description         "The user's permission level on this data item"
     :json-schema/example "read"}
    PermissionEnum]
   [:share-count
    {:description         "The number of times this data item has been shared"
     :json-schema/example 0}
    :int]
   [:bad-chars
    {:description         "Any bad characters in the data item name"
     :json-schema/example []}
    [:vector :string]]
   [:bad-name
    {:description         "Whether the data item name contains bad characters"
     :json-schema/example false}
    :boolean]
   [:bad-path
    {:description         "Whether the data item path contains bad characters"
     :json-schema/example false}
    :boolean]
   [:inaccessible
    {:description         "Whether the data item is inaccessible"
     :json-schema/example false}
    :boolean]
   [:metadata
    {:description         "Metadata associated with the data item"
     :json-schema/example {}}
    :any]])

(def StatResponse
  [:map
   [:paths
    {:description         "The stat information for the requested paths"
     :json-schema/example []}
    [:vector DataStatInfo]]])

(def StatRequest
  [:map
   [:paths
    {:description         "The list of paths to get stat information for"
     :json-schema/example ["/iplant/home/user/file.txt"]}
    [:vector NonBlankString]]])

(def BatchStatRequest
  [:map
   [:paths
    {:description         "The list of paths to get stat information for"
     :json-schema/example ["/iplant/home/user/file.txt"]}
    [:vector NonBlankString]]
   [:ids
    {:optional            true
     :description         "The list of UUIDs to get stat information for"
     :json-schema/example ["550e8400-e29b-41d4-a716-446655440000"]}
    [:vector :uuid]]])

(def BatchStatResponse
  [:map
   [:paths
    {:description         "The stat information for the requested paths"
     :json-schema/example []}
    [:vector DataStatInfo]]])