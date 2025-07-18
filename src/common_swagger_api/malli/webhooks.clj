(ns common-swagger-api.malli.webhooks
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def WebhookType
  [:map
   [:id
    {:description         "The webhook type ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:type
    {:description         "The webhook type name"
     :json-schema/example "analysis"}
    NonBlankString]
   [:template
    {:description         "The webhook template"
     :json-schema/example "{{analysis.id}} completed with status {{analysis.status}}"}
    :string]])

(def Webhook
  [:map
   [:id
    {:description         "The webhook ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:url
    {:description         "The webhook URL"
     :json-schema/example "https://example.com/webhook"}
    NonBlankString]
   [:type
    {:description         "The webhook type"
     :json-schema/example {}}
    WebhookType]
   [:topics
    {:description         "The webhook topics"
     :json-schema/example ["analysis.completion"]}
    [:vector NonBlankString]]
   [:active
    {:description         "Whether the webhook is active"
     :json-schema/example true}
    :boolean]])

(def WebhookCreateRequest
  [:map
   [:url
    {:description         "The webhook URL"
     :json-schema/example "https://example.com/webhook"}
    NonBlankString]
   [:type_id
    {:description         "The webhook type ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:topics
    {:description         "The webhook topics"
     :json-schema/example ["analysis.completion"]}
    [:vector NonBlankString]]
   [:active
    {:optional            true
     :description         "Whether the webhook is active"
     :json-schema/example true}
    :boolean]])

(def WebhookUpdateRequest
  [:map
   [:url
    {:optional            true
     :description         "The webhook URL"
     :json-schema/example "https://example.com/webhook"}
    NonBlankString]
   [:type_id
    {:optional            true
     :description         "The webhook type ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:topics
    {:optional            true
     :description         "The webhook topics"
     :json-schema/example ["analysis.completion"]}
    [:vector NonBlankString]]
   [:active
    {:optional            true
     :description         "Whether the webhook is active"
     :json-schema/example true}
    :boolean]])

(def WebhookListing
  [:map
   [:webhooks
    {:description         "The list of webhooks"
     :json-schema/example []}
    [:vector Webhook]]])

(def WebhookTypeListing
  [:map
   [:webhook_types
    {:description         "The list of webhook types"
     :json-schema/example []}
    [:vector WebhookType]]])

(def WebhookEvent
  [:map
   [:id
    {:description         "The webhook event ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440000"}
    :uuid]
   [:webhook_id
    {:description         "The webhook ID"
     :json-schema/example "550e8400-e29b-41d4-a716-446655440001"}
    :uuid]
   [:topic
    {:description         "The event topic"
     :json-schema/example "analysis.completion"}
    NonBlankString]
   [:payload
    {:description         "The event payload"
     :json-schema/example {}}
    :any]
   [:created_at
    {:description         "The event creation timestamp"
     :json-schema/example 1634567890000}
    :int]
   [:delivered_at
    {:optional            true
     :description         "The event delivery timestamp"
     :json-schema/example 1634567890000}
    :int]
   [:status
    {:description         "The event status"
     :json-schema/example "delivered"}
    [:enum "pending" "delivered" "failed"]]
   [:attempts
    {:description         "The number of delivery attempts"
     :json-schema/example 1}
    :int]])

(def WebhookEventListing
  [:map
   [:events
    {:description         "The list of webhook events"
     :json-schema/example []}
    [:vector WebhookEvent]]])