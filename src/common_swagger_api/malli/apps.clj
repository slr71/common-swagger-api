(ns common-swagger-api.malli.apps
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def SystemId
  [:system_id
   {:description         "The ID of the app execution system"
    :json-schema/example "de"}
   NonBlankString])
