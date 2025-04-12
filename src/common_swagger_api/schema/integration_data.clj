(ns common-swagger-api.schema.integration-data
  (:require [common-swagger-api.schema :refer [describe NonBlankString]]
            [schema.core :refer [defschema optional-key]])
  (:import [java.util UUID]))

(def IntegrationDataIdPathParam (describe UUID "A UUID that is used to identify the integration data record"))

(defschema IntegrationDataUpdate
  {:email
   (describe NonBlankString "The user's email address.")

   :name
   (describe NonBlankString "The user's name.")})

(defschema IntegrationDataRequest
  (assoc IntegrationDataUpdate
         (optional-key :username)
         (describe NonBlankString "The username associated with the integration data entry.")))

(defschema IntegrationData
  (assoc IntegrationDataRequest
         :id (describe UUID "The integration data identifier.")))

(defschema IntegrationDataListing
  {:integration_data
   (describe [IntegrationData] "The list of integration data entries.")

   :total
   (describe Long "The total number of matching integration data entries.")})
