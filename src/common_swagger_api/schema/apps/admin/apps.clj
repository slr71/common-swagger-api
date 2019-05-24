(ns common-swagger-api.schema.apps.admin.apps
  (:use [common-swagger-api.schema
         :only [->optional-param
                optional-key->keyword
                describe
                PagingParams
                SortFieldDocs
                SortFieldOptionalKey]]
        [schema.core :only [defschema enum optional-key]])
  (:require [clojure.set :as sets]
            [common-swagger-api.schema.apps :as apps])
  (:import [java.util Date]))

(def AdminAppPatchSummary "Update App Details and Labels")

(def AppDeleteDocs
  "An app can be marked as deleted in the DE without being completely removed from the database using this service.
   This endpoint is the same as the non-admin endpoint,
   except an error is not returned if the user does not own the App.")

(def AppDetailsDocs "This service allows administrative users to view detailed information about private apps.")
(def AppDocumentationAddDocs "This service is used by DE administrators to add documentation for a single App.")
(def AppDocumentationUpdateDocs "This service is used by DE administrators to update documentation for a single App.")

(def AppIntegrationDataUpdateSummary "Update the Integration Data Record for an App")
(def AppIntegrationDataUpdateDocs
  "This service allows administrators to change the integration data record associated with an app.")

(def AppListingDocs
  "This service allows admins to list all public apps, including apps listed under the `Trash` category:
   deleted public apps and private apps that are 'orphaned' (not categorized in any user's workspace).
   If the `search` parameter is included, then the results are filtered by the App name, description,
   integrator's name, tool name, or category name the app is under.")

(def AppShredderSummary "Permanently Deleting Apps")
(def AppShredderDocs
  "This service physically removes an App from the database,
   which allows administrators to completely remove Apps that are causing problems.")

(defschema AdminAppListingJobStats
  (merge apps/AppListingJobStats
         {:job_count
          (describe Long "The number of times this app has run")

          :job_count_failed
          (describe Long "The number of times this app has run to `Failed` status")

          (optional-key :last_used)
          (describe Date "The start date this app was last run")}))

(defschema AdminAppListingDetail
  (merge apps/AppListingDetail
         {(optional-key :job_stats)
          (describe AdminAppListingJobStats apps/AppListingJobStatsDocs)}))

(defschema AdminAppListing
  (merge apps/AppListing
         {:apps (describe [AdminAppListingDetail] "A listing of App details")}))

(def AdminAppListingJobStatsKeys
  (->> AdminAppListingJobStats
       keys
       (map optional-key->keyword)
       set))

(def AdminAppSearchValidSortFields
  (sets/union apps/AppSearchValidSortFields
              AdminAppListingJobStatsKeys))

(def AppSubsetDocs "The subset of apps to search.")
(def AppSubsetOptionalKey (optional-key :app-subset))
(def AppSubsets [:public :private :all])

(defschema AdminAppSearchParams
  (merge apps/AppSearchParams
         {SortFieldOptionalKey
          (describe (apply enum AdminAppSearchValidSortFields) SortFieldDocs)

          AppSubsetOptionalKey
          (describe (apply enum AppSubsets) AppSubsetDocs :default :public)}))

(defschema AdminAppDetails
  (merge apps/AppDetails
         {(optional-key :job_stats)
          (describe AdminAppListingJobStats apps/AppListingJobStatsDocs)}))

(defschema AdminAppPatchRequest
  (-> apps/AppBase
      (->optional-param :id)
      (->optional-param :name)
      (->optional-param :description)
      (assoc (optional-key :wiki_url) apps/AppDocUrlParam
             (optional-key :references) apps/AppReferencesParam
             (optional-key :deleted) apps/AppDeletedParam
             (optional-key :disabled) apps/AppDisabledParam
             apps/OptionalGroupsKey (describe [apps/AppGroup] apps/GroupListDocs))
      (describe "The App to update.")))
