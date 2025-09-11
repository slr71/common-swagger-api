(ns common-swagger-api.malli.oauth)

(def RedirectUrisResponse
  [:map-of {:closed      true
            :description "A mapping from API names to redirect URIs"}
   [:keyword {:description "The name of the API"}]
   [:string {:description "The redirect URI"}]])
