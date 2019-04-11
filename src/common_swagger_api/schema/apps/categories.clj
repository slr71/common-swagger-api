(ns common-swagger-api.schema.apps.categories
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.apps
         :only [AppCategoryIdPathParam
                AppListingDetail
                AppListingPagingParams
                SystemId]]
        [common-swagger-api.schema.ontologies :only [OntologyHierarchyFilterParams]]
        [schema.core :only [defschema optional-key recursive]]))

(def AppCategoryListingSummary "List App Categories")
(def AppCategoryListingDocs
  "This service is used by the DE to obtain the list of app categories that are visible to the user.")
(def AppCategoryAppListingSummary "List Apps in a Category")
(def AppCategoryAppListingDocs
  "This service lists all of the apps within an app category or any of its descendents.
   The DE uses this service to obtain the list of apps when a user clicks on a category in the _Apps_ window.
   This endpoint accepts optional URL query parameters to limit and sort Apps, which will allow pagination of results.")

(def AppCategoryHierarchyListingSummary "List App Category Hierarchy")
(def AppHierarchiesListingSummary "List App Hierarchies")
(def AppHierarchyUnclassifiedListingSummary "List Unclassified Apps")

(def AppCategoryNameParam (describe String "The App Category's name"))

(defschema CategoryListingParams
  {(optional-key :public)
   (describe
     Boolean
     "If set to `true`, then only app categories that are in a workspace that is marked as public in the database are returned.
      If set to `false`, then only app categories that are in the user's workspace are returned.
      If not set, then both public and the user's private categories are returned.")})

(defschema AppCategoryId
  {:system_id SystemId
   :id        AppCategoryIdPathParam})

(defschema AppCategoryBase
  (merge AppCategoryId
         {:name
          AppCategoryNameParam}))

(defschema AppCategory
  (merge AppCategoryBase
         {:total
          (describe Long "The number of Apps under this Category and all of its children")

          :is_public
          (describe Boolean
                    (str "Whether this App Category is viewable to all users or private to only the user that owns its"
                         " Workspace"))

          (optional-key :categories)
          (describe [(recursive #'AppCategory)]
                    "A listing of child App Categories under this App Category")}))

(defschema AppCategoryListing
  {:categories (describe [AppCategory] "A listing of App Categories visisble to the requesting user")})

(defschema AppCategoryAppListing
  (merge (dissoc AppCategory :categories)
         {:apps (describe [AppListingDetail] "A listing of Apps under this Category")}))

(defschema OntologyAppListingPagingParams
  (merge AppListingPagingParams
         OntologyHierarchyFilterParams))
