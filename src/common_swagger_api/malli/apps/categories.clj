(ns common-swagger-api.malli.apps.categories
  (:require [common-swagger-api.malli.apps :refer [SystemId]]
            [common-swagger-api.malli.ontologies :refer [OntologyHierarchyFilterParams]]))

(def CategoryListingParams
  [:map
   [:public
    {:optional            true
     :description         "If set to `true`, then only app categories that are in a workspace that is marked as public in the database are returned. If set to `false`, then only app categories that are in the user's workspace are returned. If not set, then both public and the user's private categories are returned."
     :json-schema/example true}
    :boolean]])

(def AppCategoryId
  [:map
   SystemId
   [:id
    {:description         "The app category ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]])

(def AppCategoryBase
  [:map
   AppCategoryId
   [:name
    {:description         "The App Category's name"
     :json-schema/example "Bioinformatics"}
    :string]])

(def AppCategory
  [:map
   AppCategoryBase
   [:total
    {:description         "The number of Apps under this Category and all of its children"
     :json-schema/example 25}
    :int]

   [:is_public
    {:description         "Whether this App Category is viewable to all users or private to only the user that owns its Workspace"
     :json-schema/example true}
    :boolean]

   [:categories
    {:optional            true
     :description         "A listing of child App Categories under this App Category"
     :json-schema/example []}
    [:vector [:ref ::AppCategory]]]])

(def AppCategoryListing
  [:map
   [:categories
    {:description         "A listing of App Categories visible to the requesting user"
     :json-schema/example []}
    [:vector AppCategory]]])

(def AppCategoryAppListing
  [:map
   AppCategoryBase
   [:total
    {:description         "The number of Apps under this Category and all of its children"
     :json-schema/example 25}
    :int]

   [:is_public
    {:description         "Whether this App Category is viewable to all users or private to only the user that owns its Workspace"
     :json-schema/example true}
    :boolean]

   [:apps
    {:description         "A listing of Apps under this Category"
     :json-schema/example []}
    [:vector :any]]]) ; AppListingDetail would be imported from apps module

(def OntologyAppListingPagingParams
  [:map
   ; This would merge AppListingPagingParams and OntologyHierarchyFilterParams
   OntologyHierarchyFilterParams
   [:limit
    {:optional            true
     :description         "The maximum number of results to return"
     :json-schema/example 50}
    [:and :int [:fn pos?]]]
   [:offset
    {:optional            true
     :description         "The number of results to skip"
     :json-schema/example 0}
    [:and :int [:fn #(<= 0 %)]]]
   [:sort-field
    {:optional            true
     :description         "The field to sort by"
     :json-schema/example "name"}
    :string]
   [:sort-dir
    {:optional            true
     :description         "The sort direction"
     :json-schema/example "ASC"}
    [:enum "ASC" "DESC"]]])