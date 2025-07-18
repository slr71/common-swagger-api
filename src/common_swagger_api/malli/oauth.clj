(ns common-swagger-api.malli.oauth
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def RedirectUrisResponse
  [:map
   [:redirect_uris
    {:description         "List of redirect URIs"
     :json-schema/example ["https://example.com/callback"]}
    [:vector NonBlankString]]])

(def AuthorizationRequest
  [:map
   [:response_type
    {:description         "The response type for the authorization request"
     :json-schema/example "code"}
    [:enum "code" "token"]]
   [:client_id
    {:description         "The client identifier"
     :json-schema/example "client123"}
    NonBlankString]
   [:redirect_uri
    {:optional            true
     :description         "The redirect URI after authorization"
     :json-schema/example "https://example.com/callback"}
    NonBlankString]
   [:scope
    {:optional            true
     :description         "The scope of the authorization request"
     :json-schema/example "read write"}
    :string]
   [:state
    {:optional            true
     :description         "An opaque value used by the client to maintain state"
     :json-schema/example "xyz"}
    :string]])

(def TokenRequest
  [:map
   [:grant_type
    {:description         "The grant type for the token request"
     :json-schema/example "authorization_code"}
    [:enum "authorization_code" "refresh_token" "client_credentials"]]
   [:code
    {:optional            true
     :description         "The authorization code (required for authorization_code grant)"
     :json-schema/example "abc123"}
    :string]
   [:redirect_uri
    {:optional            true
     :description         "The redirect URI (required for authorization_code grant)"
     :json-schema/example "https://example.com/callback"}
    NonBlankString]
   [:client_id
    {:optional            true
     :description         "The client identifier"
     :json-schema/example "client123"}
    NonBlankString]
   [:client_secret
    {:optional            true
     :description         "The client secret"
     :json-schema/example "secret123"}
    :string]
   [:refresh_token
    {:optional            true
     :description         "The refresh token (required for refresh_token grant)"
     :json-schema/example "refresh123"}
    :string]
   [:scope
    {:optional            true
     :description         "The scope of the token request"
     :json-schema/example "read write"}
    :string]])

(def TokenResponse
  [:map
   [:access_token
    {:description         "The access token"
     :json-schema/example "access123"}
    NonBlankString]
   [:token_type
    {:description         "The token type"
     :json-schema/example "Bearer"}
    NonBlankString]
   [:expires_in
    {:optional            true
     :description         "The token expiration time in seconds"
     :json-schema/example 3600}
    :int]
   [:refresh_token
    {:optional            true
     :description         "The refresh token"
     :json-schema/example "refresh123"}
    :string]
   [:scope
    {:optional            true
     :description         "The scope of the token"
     :json-schema/example "read write"}
    :string]])

(def ErrorResponse
  [:map
   [:error
    {:description         "The error code"
     :json-schema/example "invalid_request"}
    [:enum "invalid_request" "invalid_client" "invalid_grant" "unauthorized_client" "unsupported_grant_type" "invalid_scope"]]
   [:error_description
    {:optional            true
     :description         "A human-readable description of the error"
     :json-schema/example "The request is missing a required parameter"}
    :string]
   [:error_uri
    {:optional            true
     :description         "A URI identifying a human-readable web page with error information"
     :json-schema/example "https://example.com/error"}
    :string]
   [:state
    {:optional            true
     :description         "The state parameter from the authorization request"
     :json-schema/example "xyz"}
    :string]])

(def ClientRegistrationRequest
  [:map
   [:client_name
    {:description         "The client name"
     :json-schema/example "My Application"}
    NonBlankString]
   [:redirect_uris
    {:description         "List of redirect URIs"
     :json-schema/example ["https://example.com/callback"]}
    [:vector NonBlankString]]
   [:scope
    {:optional            true
     :description         "The default scope for the client"
     :json-schema/example "read write"}
    :string]
   [:grant_types
    {:optional            true
     :description         "The grant types supported by the client"
     :json-schema/example ["authorization_code" "refresh_token"]}
    [:vector [:enum "authorization_code" "refresh_token" "client_credentials"]]]
   [:response_types
    {:optional            true
     :description         "The response types supported by the client"
     :json-schema/example ["code"]}
    [:vector [:enum "code" "token"]]]])

(def ClientRegistrationResponse
  [:map
   [:client_id
    {:description         "The client identifier"
     :json-schema/example "client123"}
    NonBlankString]
   [:client_secret
    {:optional            true
     :description         "The client secret"
     :json-schema/example "secret123"}
    :string]
   [:client_name
    {:description         "The client name"
     :json-schema/example "My Application"}
    NonBlankString]
   [:redirect_uris
    {:description         "List of redirect URIs"
     :json-schema/example ["https://example.com/callback"]}
    [:vector NonBlankString]]
   [:scope
    {:optional            true
     :description         "The default scope for the client"
     :json-schema/example "read write"}
    :string]
   [:grant_types
    {:optional            true
     :description         "The grant types supported by the client"
     :json-schema/example ["authorization_code" "refresh_token"]}
    [:vector [:enum "authorization_code" "refresh_token" "client_credentials"]]]
   [:response_types
    {:optional            true
     :description         "The response types supported by the client"
     :json-schema/example ["code"]}
    [:vector [:enum "code" "token"]]]])