(ns common-swagger-api.malli
  (:require [clojure.string :as string]
            [malli.core :as m]))

(def NonBlankString [:and :string [:fn (complement string/blank?)]])

(def StatusResponse
  [:map {:closed true}
   [:service
    {:description         "The name of the service"
     :json-schema/example "example-api"}
    NonBlankString]

   [:description
    {:description         "The service description"
     :json-schema/example "An API"}
    NonBlankString]

   [:version
    {:description         "The service version"
     :json-schema/example "1.2.3"}
    NonBlankString]

   [:docs-url
    {:description         "The URL for the API documentation"
     :json-schema/example "http://example-api/docs"}
    NonBlankString]])
