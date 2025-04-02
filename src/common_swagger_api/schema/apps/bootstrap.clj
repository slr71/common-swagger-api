(ns common-swagger-api.schema.apps.bootstrap
  (:require [common-swagger-api.schema :refer [describe]]
            [common-swagger-api.schema.apps :refer [SystemId]]
            [common-swagger-api.schema.apps.workspace :refer [Workspace]]
            [common-swagger-api.schema.webhooks :refer [WebhookList]]
            [schema.core :refer [defschema]]))

(defschema SystemIds
  {:de_system_id   (describe SystemId "The internal system ID used by the Discovery Environment.")
   :all_system_ids (describe [SystemId] "The list of system IDs available to the Discovery Environment.")})

(defschema AppsBootstrapResponse
  (merge WebhookList
         {:system_ids (describe SystemIds "Information about system IDs available to the Discovery Environment.")
          :workspace  (describe Workspace "Information about the user's Discovery Environment workspace.")}))
