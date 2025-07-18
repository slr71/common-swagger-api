(ns common-swagger-api.malli.callbacks
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def CallbackRequest
  [:map
   [:state
    {:description         "The callback state"
     :json-schema/example "analysis_completed"}
    NonBlankString]
   [:payload
    {:description         "The callback payload"
     :json-schema/example {}}
    :any]
   [:callback_url
    {:description         "The callback URL"
     :json-schema/example "https://example.com/callback"}
    NonBlankString]])

(def CallbackResponse
  [:map
   [:status
    {:description         "The callback status"
     :json-schema/example "success"}
    [:enum "success" "failure"]]
   [:message
    {:optional            true
     :description         "A status message"
     :json-schema/example "Callback processed successfully"}
    :string]])

(def CallbackRegistration
  [:map
   [:id
    {:description         "The callback registration ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:callback_url
    {:description         "The callback URL"
     :json-schema/example "https://example.com/callback"}
    NonBlankString]
   [:event_types
    {:description         "The event types to subscribe to"
     :json-schema/example ["analysis.completed" "analysis.failed"]}
    [:vector NonBlankString]]
   [:active
    {:description         "Whether the callback is active"
     :json-schema/example true}
    :boolean]])

(def CallbackRegistrationRequest
  [:map
   [:callback_url
    {:description         "The callback URL"
     :json-schema/example "https://example.com/callback"}
    NonBlankString]
   [:event_types
    {:description         "The event types to subscribe to"
     :json-schema/example ["analysis.completed" "analysis.failed"]}
    [:vector NonBlankString]]
   [:active
    {:optional            true
     :description         "Whether the callback is active"
     :json-schema/example true}
    :boolean]])

(def CallbackRegistrationListing
  [:map
   [:callbacks
    {:description         "The list of callback registrations"
     :json-schema/example []}
    [:vector CallbackRegistration]]])