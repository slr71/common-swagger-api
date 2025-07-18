(ns common-swagger-api.malli.integration-data
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def IntegrationDataUpdate
  [:map
   [:email
    {:description         "The user's email address"
     :json-schema/example "user@example.com"}
    NonBlankString]

   [:name
    {:description         "The user's name"
     :json-schema/example "John Doe"}
    NonBlankString]])

(def IntegrationDataRequest
  [:map
   [:email
    {:description         "The user's email address"
     :json-schema/example "user@example.com"}
    NonBlankString]

   [:name
    {:description         "The user's name"
     :json-schema/example "John Doe"}
    NonBlankString]

   [:username
    {:optional            true
     :description         "The username associated with the integration data entry"
     :json-schema/example "jdoe"}
    NonBlankString]])

(def IntegrationData
  [:map
   [:id
    {:description         "The integration data identifier"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:email
    {:description         "The user's email address"
     :json-schema/example "user@example.com"}
    NonBlankString]

   [:name
    {:description         "The user's name"
     :json-schema/example "John Doe"}
    NonBlankString]

   [:username
    {:optional            true
     :description         "The username associated with the integration data entry"
     :json-schema/example "jdoe"}
    NonBlankString]])

(def IntegrationDataListing
  [:map
   [:integration_data
    {:description         "The list of integration data entries"
     :json-schema/example []}
    [:vector IntegrationData]]

   [:total
    {:description         "The total number of matching integration data entries"
     :json-schema/example 42}
    :int]])