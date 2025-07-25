(ns common-swagger-api.malli
  (:require [clojure.string :as string]
            [clojure-commons.error-codes :as ce]
            [malli.core :as m]
            [malli.experimental.time :as met]
            [malli.registry :as mr]))

(def init-registry
  (memoize (fn []
             (mr/set-default-registry!
              (mr/composite-registry
               (m/default-schemas)
               (met/schemas))))))
(init-registry)

(def NonBlankString [:and :string [:fn (complement string/blank?)]])
(def PositiveInt [:and :int [:fn pos?]])
(def NonNegativeInt [:and :int [:fn #(<= 0 %)]])

(def StandardUserQueryParams
  [:map
   [:user
    {:description         "The username of the authenticated, requesting user"
     :json-schema/example "a_user"}
    NonBlankString]])

(def StatusParams
  [:map
   [:expecting
    {:optional            true
     :description         (str "The service which the requesting client is expecting to see here. "
                               "Should throw a 500 error if provided and does not match the actual "
                               "service running here.")
     :json-schema/example "example-api"}
    NonBlankString]])

(def PagingParams
  [:map
   [:limit
    {:optional            true
     :description         (str "Limits the response to X number of results in the listing array. "
                               "See https://www.postgresql.org/docs/current/queries-limit.html")
     :json-schema/example 50}
    PositiveInt]

   [:offset
    {:optional            true
     :description         (str "Skips the first X number of results in the listing array. "
                               "See https://www.postgresql.org/docs/current/queries-limit.html")
     :json-schema/example 0}
    NonNegativeInt]

   [:sort-field
    {:optional            true
     :description         (str "Sorts the results in the listing array by the given field, before limits and offsets "
                               "are applied. See https://www.postgresql.org/docs/current/queries-order.html")
     :json-schema/example "name"}
    :string]

   [:sort-dir
    {:optional            true
     :description         (str "Only used when sort-field is present. Sorts the results in either ascending (`ASC`) or "
                               "descending (`DESC`) order, before limits and offsets are applied. Defaults to `ASC`. "
                               "See https://www.postgresql.org/docs/current/queries-order.html")
     :json-schema/example "ASC"}
    [:enum "ASC" "DESC"]]])

(def StatusResponse
  [:map {:closed true}
   [:service
    {:description         "The name of the service"
     :json-schema/example "example-api"}
    NonBlankString]

   [:description
    {:description         "The service description"
     :json-schema/example "An API"}
    NonBlankString]

   [:version
    {:description         "The service version"
     :json-schema/example "1.2.3"}
    NonBlankString]

   [:docs-url
    {:description         "The URL for the API documentation"
     :json-schema/example "http://example-api/docs"}
    NonBlankString]

   [:expecting
    {:optional            true
     :description         "The service the requesting client was expecting to see, if any"
     :json-schema/example "example-api"}
    :string]])

(def ErrorResponse
  [:map
   [:error_code
    {:description         "The code identifying the type of error"
     :json-schema/example ce/ERR_BAD_REQUEST}
    NonBlankString]

   [:reason
    {:optional            true
     :description         "A brief description of the reason for the error"
     :json-schema/example "Invalid parameter value"}
    NonBlankString]])

(def ErrorResponseExists
  [:map
   [:error_code
    {:description         "Exists error code"
     :json-schema/example ce/ERR_EXISTS}
    [:enum ce/ERR_EXISTS]]

   [:reason
    {:optional            true
     :description         "A brief description of the reason for the error"
     :json-schema/example "Resource already exists"}
    NonBlankString]])

(def ErrorResponseNotWritable
  [:map
   [:error_code
    {:description         "Not Writeable error code"
     :json-schema/example ce/ERR_NOT_WRITEABLE}
    [:enum ce/ERR_NOT_WRITEABLE]]

   [:reason
    {:optional            true
     :description         "A brief description of the reason for the error"
     :json-schema/example "Resource is not writeable"}
    NonBlankString]])

(def ErrorResponseForbidden
  [:map
   [:error_code
    {:description         "Insufficient privileges error code"
     :json-schema/example ce/ERR_FORBIDDEN}
    [:enum ce/ERR_FORBIDDEN]]

   [:reason
    {:optional            true
     :description         "A brief description of the reason for the error"
     :json-schema/example "Insufficient privileges"}
    NonBlankString]])

(def ErrorResponseNotFound
  [:map
   [:error_code
    {:description         "Not Found error code"
     :json-schema/example ce/ERR_NOT_FOUND}
    [:enum ce/ERR_NOT_FOUND]]

   [:reason
    {:optional            true
     :description         "A brief description of the reason for the error"
     :json-schema/example "Resource not found"}
    NonBlankString]])

(def ErrorResponseIllegalArgument
  [:map
   [:error_code
    {:description         "Illegal Argument error code"
     :json-schema/example ce/ERR_ILLEGAL_ARGUMENT}
    [:enum ce/ERR_ILLEGAL_ARGUMENT]]

   [:reason
    {:optional            true
     :description         "A brief description of the reason for the error"
     :json-schema/example "Invalid argument provided"}
    NonBlankString]])

(def ErrorResponseUnchecked
  [:map
   [:error_code
    {:description         "Response schema validation and Unchecked error codes"
     :json-schema/example ce/ERR_UNCHECKED_EXCEPTION}
    [:enum ce/ERR_UNCHECKED_EXCEPTION ce/ERR_SCHEMA_VALIDATION]]

   [:reason
    {:optional            true
     :description         "A brief text or object describing the reason for the error"
     :json-schema/example "Unexpected error occurred"}
    :any]])
