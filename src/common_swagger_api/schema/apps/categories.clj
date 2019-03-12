(ns common-swagger-api.schema.apps.categories
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.apps :only [AppCategoryIdPathParam AppListingDetail SystemId]]
        [schema.core :only [defschema optional-key recursive]]))

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
