(ns common-swagger-api.schema.sessions
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.oauth :only [RedirectUrisResponse]]
        [schema.core :only [defschema optional-key]]))

(def LogoutSummary "Record a User Logout")
(def LogoutDocs "This service records the fact that the user logged out.")

(defschema IPAddrParam
  {(optional-key :ip-address)
   (describe String "The IP address of the requesting user, for matching login to logout requests.")})

(defschema LoginTimeResponseParam
  {:login_time    (describe Long "Login time as milliseconds since the epoch.")})

(defschema LoginResponse
  (merge LoginTimeResponseParam
         {:auth_redirect RedirectUrisResponse}))

(defschema LogoutParams
  (merge IPAddrParam
         {:login-time (describe Long "The login time returned by `POST /users/login` or `/bootstrap`.")}))

(defschema Login
  ;; NOTE: other schemas use a mix of hyphens and underscores for these fields
  ;; This schema uses underscores for both, which doesn't match the IPAddrParam above
  ;; This should be the only _response_ that has an IP address, while the other is a query parameter.
  (merge LoginTimeResponseParam
         {(optional-key :ip_address)
          (describe String "The IP address associated with the login, if available")}))

(defschema ListLoginsResponse
  {:logins (describe [Login] "The set of most recent logins up to the limit")})
