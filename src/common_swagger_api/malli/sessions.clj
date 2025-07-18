(ns common-swagger-api.malli.sessions
  (:require [common-swagger-api.malli.oauth :refer [RedirectUrisResponse]]))

(def IPAddrParam
  [:map
   [:ip-address
    {:optional            true
     :description         "The IP address of the requesting user, for matching login to logout requests"
     :json-schema/example "192.168.1.100"}
    :string]])

(def LoginTimeResponseParam
  [:map
   [:login_time
    {:description         "Login time as milliseconds since the epoch"
     :json-schema/example 1634567890000}
    :int]])

(def LoginResponse
  [:map
   LoginTimeResponseParam
   [:auth_redirect
    {:description         "Authentication redirect information"
     :json-schema/example {}}
    RedirectUrisResponse]])

(def LogoutParams
  [:map
   IPAddrParam
   [:login-time
    {:description         "The login time returned by `POST /users/login` or `/bootstrap`"
     :json-schema/example 1634567890000}
    :int]])

(def Login
  [:map
   LoginTimeResponseParam
   [:ip_address
    {:optional            true
     :description         "The IP address associated with the login, if available"
     :json-schema/example "192.168.1.100"}
    :string]])