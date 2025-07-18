(ns common-swagger-api.malli.common)

(def IncludeHiddenParams
  [:map
   [:include-hidden
    {:optional            true
     :description         "True if hidden elements should be included in the results"
     :json-schema/example false}
    :boolean]])

(def IncludeDeletedParams
  [:map
   [:include-deleted
    {:optional            true
     :description         "True if deleted elements should be included in the results"
     :json-schema/example false}
    :boolean]])