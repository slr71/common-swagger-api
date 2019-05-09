(ns common-swagger-api.schema.sessions
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.oauth :only [RedirectUrisResponse]]
        [schema.core :only [defschema]]))

(defschema IPAddrParam
  {:ip-address (describe String "The IP address of the requesting user, for matching login to logout requests.")})

(defschema LoginResponse
  {:login_time    (describe Long "Login time as milliseconds since the epoch.")
   :auth_redirect RedirectUrisResponse})
