(ns common-swagger-api.schema.webhooks
  (:use [common-swagger-api.schema :only [describe NonBlankString]]
        [schema.core :only [defschema optional-key]])
  (:import [java.util UUID]))

(def GetWebhooksSummary "List Webhooks")
(def GetWebhooksDesc "Returns all of the webhooks defined for the user.")
(def PutWebhooksSummary "Add a Webhook")
(def PutWebhooksDesc "Adds a new webhook to the system.")
(def GetWebhooksTopicSummary "List notification topics")
(def GetWebhooksTopicDesc "Returns all of the notification topics defined")
(def GetWebhookTypesSummary "List webhooks types")
(def GetWebhookTypesDesc "Returns all webhook types defined")

(defschema WebhookType
  {(optional-key :id)       (describe UUID "A UUID for the type")
   :type                    (describe NonBlankString "Webhook type")
   (optional-key :template) (describe String "Template for this Webhook type")})

(def WebhookIdParam (describe UUID "A UUID that is used to identify the Webhook"))

(defschema Webhook
  {(optional-key :id) WebhookIdParam
   :type              (describe WebhookType "Type of webhook subscription")
   :url               (describe NonBlankString "Url to post the notification")
   :topics            (describe [NonBlankString] "A List of topic names")})

(defschema WebhookList
  {:webhooks (describe [Webhook] "A List of webhooks")})

(defschema Topic
  {:id    (describe UUID "A UUID for the topic")
   :topic (describe NonBlankString "The topic")})
(defschema TopicList
  {:topics (describe [Topic] "A List of topics")})

(defschema WebhookTypeList
  {:webhooktypes (describe [WebhookType] "A List of webhook types")})
