(ns common-swagger-api.schema.sessions
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.oauth :only [RedirectUrisResponse]]
        [schema.core :only [defschema]]))

(def LogoutSummary "Record a User Logout")
(def LogoutDocs "This service records the fact that the user logged out.")

(defschema IPAddrParam
  {:ip-address (describe String "The IP address of the requesting user, for matching login to logout requests.")})

(defschema LoginResponse
  {:login_time    (describe Long "Login time as milliseconds since the epoch.")
   :auth_redirect RedirectUrisResponse})

(defschema LogoutParams
  (merge IPAddrParam
         {:login-time (describe Long "The login time returned by `POST /users/login` or `/bootstrap`.")}))
