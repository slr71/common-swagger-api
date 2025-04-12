(ns common-swagger-api.schema.oauth
  (:require [common-swagger-api.schema :refer [describe doc-only]]
            [schema.core :as s]))

(s/defschema RedirectUris
  {(describe s/Keyword "The name of the API")
   (describe String "The redirect URI")})

(s/defschema RedirectUrisDoc
  {:api-name (describe String "The redirect URI.")})

(def RedirectUrisResponse (doc-only RedirectUris RedirectUrisDoc))
