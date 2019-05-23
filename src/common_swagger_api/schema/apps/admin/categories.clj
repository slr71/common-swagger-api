(ns common-swagger-api.schema.apps.admin.categories
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.apps :only [SystemId]]
        [schema.core :only [defschema]])
  (:require [common-swagger-api.schema.apps.categories :as categories-schema]))

(def AppCategorizationSummary "Categorize Apps")
(def AppCategorizationDocs
  "This endpoint is used by the Admin interface to add or move Apps to into multiple Categories.")

(defschema AppCategoryIdList
  {:category_ids (describe [categories-schema/AppCategoryId] "A List of App Category identifiers")})

(defschema AppCategorization
  (merge AppCategoryIdList
         {:system_id SystemId
          :app_id    (describe String "The ID of the App to be Categorized")}))

(defschema AppCategorizationRequest
  (-> {:categories (describe [AppCategorization] "Apps and the Categories they should be listed under")}
      (describe "An App Categorization Request.")))
