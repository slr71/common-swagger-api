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

(def AppSubsets (enum :public :private :all))

(defschema AdminAppSearchParams
  (merge apps/AppSearchParams
         {SortFieldOptionalKey
          (describe (apply enum AdminAppSearchValidSortFields) SortFieldDocs)

          (optional-key :app-subset)
          (describe AppSubsets "The subset of apps to search." :default :public)}))

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
             apps/OptionalGroupsKey (describe [apps/AppGroup] apps/GroupListDocs))))
