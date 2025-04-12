(ns common-swagger-api.schema.permanent-id-requests
  (:require [common-swagger-api.schema :refer [describe
                                               PagingParams
                                               SortFieldDocs
                                               SortFieldOptionalKey]]
            [schema-tools.core :as st]
            [schema.core :as s])
  (:import [java.util UUID]))

(def PermanentIDRequestAdminListSummary "List Permanent ID Requests")
(def PermanentIDRequestAdminListDescription
  "Allows administrators to list Permanent ID Requests from all users.")

(def PermanentIDRequestAdminDetailsSummary "Get Permanent ID Request Details")
(def PermanentIDRequestAdminDetailsDescription
  "Allows administrators to retrieve details for a Permanent ID Request from any user.")

(def PermanentIDRequestAdminStatusUpdateSummary "Update the Status of a Permanent ID Request")
(def PermanentIDRequestAdminStatusUpdateDescription
  "Allows administrators to update the status of a Permanent ID Request from any user.")

(def PermanentIDRequestSummary "Create a Permanent ID Request")
(def PermanentIDRequestDescription
  "Creates a Permanent ID Request for the requesting user.")

(def PermanentIDRequestDetailsSummary "Get Permanent ID Request Details")
(def PermanentIDRequestDetailsDescription
  "Allows a user to retrieve details for one of their Permanent ID Request submissions.")

(def PermanentIDRequestListSummary "List Permanent ID Requests")
(def PermanentIDRequestListDescription
  "Lists all Permanent ID Requests submitted by the requesting user.")

(def PermanentIDRequestStatusCodeListSummary "List Permanent ID Request Status Codes")
(def PermanentIDRequestStatusCodeListDescription
  "Lists all Permanent ID Request Status Codes that have been assigned to a request status update.
   This allows a status to easily be reused by admins in future status updates.")

(def PermanentIDRequestTypesSummary "List Permanent ID Request Types")
(def PermanentIDRequestTypesDescription
  "Lists the allowed Permanent ID Request Types the user can select when submitting a new request.")

(def PermanentIDRequestIdParam (describe UUID "The Permanent ID Request's UUID"))

(s/defschema PermanentID
  {(s/optional-key :permanent_id) (describe String "The identifier of a completed Permanent ID Request")})

(s/defschema PermanentIDRequestOrigPath
  {(s/optional-key :original_path)
   (describe String "The original path of the target data set at the time the Permanent ID Request was initially created")})

(s/defschema PermanentIDRequest
  {:type (describe String "The type of persistent ID requested")})

(s/defschema PermanentIDRequestBase
  (st/merge PermanentIDRequest
            PermanentID
            PermanentIDRequestOrigPath
            {:id           PermanentIDRequestIdParam
             :requested_by (describe String "The username of the user that submitted the Permanent ID Request")}))

(s/defschema PermanentIDRequestStatusComments
  {(s/optional-key :comments) (describe String "The curator comments of the Permanent ID Request status update")})

(s/defschema PermanentIDRequestStatusUpdate
  (st/merge PermanentIDRequestStatusComments
            {(s/optional-key :permanent_id)
             (describe String (str "The identifier of a completed Permanent ID Request."
                                   " If the `permanent_id` is provided in the request,"
                                   " then the Permanent ID Request must not already have a `permanent_id` set,"
                                   " otherwise an error is returned and the Status is not updated"))

             (s/optional-key :status)
             (describe String (str "The status code of the Permanent ID Request update."
                                   " The status code is case-sensitive, and if it isn't defined in the database already"
                                   " then it will be added to the list of known status codes"))}))

(s/defschema PermanentIDRequestStatus
  (st/merge PermanentID
            PermanentIDRequestStatusComments
            {(s/optional-key :status) (describe String "The status code of the Permanent ID Request update")
             :status_date             (describe Long "The timestamp of the Permanent ID Request status update")
             :updated_by              (describe String "The username that updated the Permanent ID Request status")}))

(s/defschema PermanentIDRequestDetails
  (st/merge PermanentIDRequestBase
            {:history (describe [PermanentIDRequestStatus] "A list of Permanent ID Request status updates")}))

(s/defschema PermanentIDRequestListing
  (st/merge PermanentIDRequestBase
            {:date_submitted (describe Long "The timestamp of the Permanent ID Request submission")
             :status         (describe String "The current status of the Permanent ID Request")
             :date_updated   (describe Long "The timestamp of the last Permanent ID Request status update")
             :updated_by     (describe String "The username of the user that last updated the Permanent ID Request status")}))

(s/defschema PermanentIDRequestList
  {:requests (describe [PermanentIDRequestListing] "A list of Permanent ID Requests")
   :total    (describe Long "The total number of permanent id requests in the listing")})

(def ValidPermanentIDRequestListSortFields
  (s/enum
   :type
   :target_type
   :requested_by
   :date_submitted
   :status
   :date_updated
   :updated_by))

(s/defschema PermanentIDRequestListPagingParams
  (st/assoc PagingParams
            SortFieldOptionalKey (describe ValidPermanentIDRequestListSortFields SortFieldDocs)
            (s/optional-key :statuses) (describe [String] "Status Codes with which to filter results")))

(s/defschema PermanentIDRequestStatusCode
  {:id          (describe UUID "The Status Code's UUID")
   :name        (describe String "The Status Code")
   :description (describe String "A brief description of the Status Code")})

(s/defschema PermanentIDRequestStatusCodeList
  {:status_codes (describe [PermanentIDRequestStatusCode] "A list of Permanent ID Request Status Codes")})

(s/defschema PermanentIDRequestType
  {:id          (describe UUID "The Request Type's UUID")
   :type        (describe String "The Request Type")
   :description (describe String "A brief description of the Request Type")})

(s/defschema PermanentIDRequestTypeList
  {:request_types (describe [PermanentIDRequestType] "A list of Permanent ID Request Types")})
