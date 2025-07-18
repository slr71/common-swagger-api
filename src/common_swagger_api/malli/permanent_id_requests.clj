(ns common-swagger-api.malli.permanent-id-requests)

(def PermanentID
  [:map
   [:permanent_id
    {:optional            true
     :description         "The identifier of a completed Permanent ID Request"
     :json-schema/example "doi:10.1000/182"}
    :string]])

(def PermanentIDRequestOrigPath
  [:map
   [:original_path
    {:optional            true
     :description         "The original path of the target data set at the time the Permanent ID Request was initially created"
     :json-schema/example "/iplant/home/user/my_data"}
    :string]])

(def PermanentIDRequest
  [:map
   [:type
    {:description         "The type of persistent ID requested"
     :json-schema/example "DOI"}
    :string]])

(def PermanentIDRequestBase
  [:map
   [:id
    {:description         "The Permanent ID Request's UUID"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:requested_by
    {:description         "The username of the user that submitted the Permanent ID Request"
     :json-schema/example "jdoe"}
    :string]

   [:type
    {:description         "The type of persistent ID requested"
     :json-schema/example "DOI"}
    :string]

   [:permanent_id
    {:optional            true
     :description         "The identifier of a completed Permanent ID Request"
     :json-schema/example "doi:10.1000/182"}
    :string]

   [:original_path
    {:optional            true
     :description         "The original path of the target data set at the time the Permanent ID Request was initially created"
     :json-schema/example "/iplant/home/user/my_data"}
    :string]])

(def PermanentIDRequestStatusComments
  [:map
   [:comments
    {:optional            true
     :description         "The curator comments of the Permanent ID Request status update"
     :json-schema/example "Request approved and DOI assigned"}
    :string]])

(def PermanentIDRequestStatusUpdate
  [:map
   [:permanent_id
    {:optional            true
     :description         (str "The identifier of a completed Permanent ID Request. "
                               "If the `permanent_id` is provided in the request, "
                               "then the Permanent ID Request must not already have a `permanent_id` set, "
                               "otherwise an error is returned and the Status is not updated")
     :json-schema/example "doi:10.1000/182"}
    :string]

   [:status
    {:optional            true
     :description         (str "The status code of the Permanent ID Request update. "
                               "The status code is case-sensitive, and if it isn't defined in the database already "
                               "then it will be added to the list of known status codes")
     :json-schema/example "Approved"}
    :string]

   [:comments
    {:optional            true
     :description         "The curator comments of the Permanent ID Request status update"
     :json-schema/example "Request approved and DOI assigned"}
    :string]])

(def PermanentIDRequestStatus
  [:map
   [:permanent_id
    {:optional            true
     :description         "The identifier of a completed Permanent ID Request"
     :json-schema/example "doi:10.1000/182"}
    :string]

   [:status
    {:optional            true
     :description         "The status code of the Permanent ID Request update"
     :json-schema/example "Approved"}
    :string]

   [:status_date
    {:description         "The timestamp of the Permanent ID Request status update"
     :json-schema/example 1640995200000}
    :int]

   [:updated_by
    {:description         "The username that updated the Permanent ID Request status"
     :json-schema/example "admin"}
    :string]

   [:comments
    {:optional            true
     :description         "The curator comments of the Permanent ID Request status update"
     :json-schema/example "Request approved and DOI assigned"}
    :string]])

(def PermanentIDRequestDetails
  [:map
   [:id
    {:description         "The Permanent ID Request's UUID"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:requested_by
    {:description         "The username of the user that submitted the Permanent ID Request"
     :json-schema/example "jdoe"}
    :string]

   [:type
    {:description         "The type of persistent ID requested"
     :json-schema/example "DOI"}
    :string]

   [:permanent_id
    {:optional            true
     :description         "The identifier of a completed Permanent ID Request"
     :json-schema/example "doi:10.1000/182"}
    :string]

   [:original_path
    {:optional            true
     :description         "The original path of the target data set at the time the Permanent ID Request was initially created"
     :json-schema/example "/iplant/home/user/my_data"}
    :string]

   [:history
    {:description         "A list of Permanent ID Request status updates"
     :json-schema/example []}
    [:vector PermanentIDRequestStatus]]])

(def PermanentIDRequestListing
  [:map
   [:id
    {:description         "The Permanent ID Request's UUID"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:requested_by
    {:description         "The username of the user that submitted the Permanent ID Request"
     :json-schema/example "jdoe"}
    :string]

   [:type
    {:description         "The type of persistent ID requested"
     :json-schema/example "DOI"}
    :string]

   [:permanent_id
    {:optional            true
     :description         "The identifier of a completed Permanent ID Request"
     :json-schema/example "doi:10.1000/182"}
    :string]

   [:original_path
    {:optional            true
     :description         "The original path of the target data set at the time the Permanent ID Request was initially created"
     :json-schema/example "/iplant/home/user/my_data"}
    :string]

   [:date_submitted
    {:description         "The timestamp of the Permanent ID Request submission"
     :json-schema/example 1640995200000}
    :int]

   [:status
    {:description         "The current status of the Permanent ID Request"
     :json-schema/example "Approved"}
    :string]

   [:date_updated
    {:description         "The timestamp of the last Permanent ID Request status update"
     :json-schema/example 1640995200000}
    :int]

   [:updated_by
    {:description         "The username of the user that last updated the Permanent ID Request status"
     :json-schema/example "admin"}
    :string]])

(def PermanentIDRequestList
  [:map
   [:requests
    {:description         "A list of Permanent ID Requests"
     :json-schema/example []}
    [:vector PermanentIDRequestListing]]

   [:total
    {:description         "The total number of permanent id requests in the listing"
     :json-schema/example 42}
    :int]])

(def ValidPermanentIDRequestListSortFields
  [:enum
   "type"
   "target_type"
   "requested_by"
   "date_submitted"
   "status"
   "date_updated"
   "updated_by"])

(def PermanentIDRequestListPagingParams
  [:map
   [:limit
    {:optional            true
     :description         (str "Limits the response to X number of results in the listing array. "
                               "See http://www.postgresql.org/docs/9.2/interactive/queries-limit.html")
     :json-schema/example 50}
    [:and :int [:fn pos?]]]

   [:offset
    {:optional            true
     :description         (str "Skips the first X number of results in the listing array. "
                               "See http://www.postgresql.org/docs/9.2/interactive/queries-limit.html")
     :json-schema/example 0}
    [:and :int [:fn #(<= 0 %)]]]

   [:sort-field
    {:optional            true
     :description         (str "Sorts the results in the listing array by the given field, before limits and offsets are applied. "
                               "See http://www.postgresql.org/docs/9.2/interactive/queries-order.html")
     :json-schema/example "date_submitted"}
    ValidPermanentIDRequestListSortFields]

   [:sort-dir
    {:optional            true
     :description         (str "Only used when sort-field is present. Sorts the results in either ascending (`ASC`) or "
                               "descending (`DESC`) order, before limits and offsets are applied. Defaults to `ASC`. "
                               "See http://www.postgresql.org/docs/9.2/interactive/queries-order.html")
     :json-schema/example "ASC"}
    [:enum "ASC" "DESC"]]

   [:statuses
    {:optional            true
     :description         "Status Codes with which to filter results"
     :json-schema/example ["Approved" "Pending"]}
    [:vector :string]]])

(def PermanentIDRequestStatusCode
  [:map
   [:id
    {:description         "The Status Code's UUID"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:name
    {:description         "The Status Code"
     :json-schema/example "Approved"}
    :string]

   [:description
    {:description         "A brief description of the Status Code"
     :json-schema/example "Request has been approved by curator"}
    :string]])

(def PermanentIDRequestStatusCodeList
  [:map
   [:status_codes
    {:description         "A list of Permanent ID Request Status Codes"
     :json-schema/example []}
    [:vector PermanentIDRequestStatusCode]]])

(def PermanentIDRequestType
  [:map
   [:id
    {:description         "The Request Type's UUID"
     :json-schema/example "f47ac10b-58cc-4372-a567-0e02b2c3d479"}
    :uuid]

   [:type
    {:description         "The Request Type"
     :json-schema/example "DOI"}
    :string]

   [:description
    {:description         "A brief description of the Request Type"
     :json-schema/example "Digital Object Identifier"}
    :string]])

(def PermanentIDRequestTypeList
  [:map
   [:request_types
    {:description         "A list of Permanent ID Request Types"
     :json-schema/example []}
    [:vector PermanentIDRequestType]]])
