(ns common-swagger-api.schema.apps.permission
  (:use [common-swagger-api.schema :only [describe ErrorResponse NonBlankString]]
        [common-swagger-api.schema.apps :only [QualifiedAppId]]
        [schema.core :only [defschema enum optional-key]]))

(def AppPermissionListingSummary "List App Permissions")
(def AppPermissionListingDocs
  "This endpoint allows the caller to list the permissions for one or more apps.
   The authenticated user must have read permission on every app in the request body for this endpoint to succeed.")

(def AppSharingSummary "Add App Permissions")
(def AppSharingDocs
  "This endpoint allows the caller to share multiple apps with multiple users.
   The authenticated user must have ownership permission to every app in the request body for this endpoint to fully succeed.
   Note: this is a potentially slow operation and the response is returned synchronously.
   The DE UI handles this by allowing the user to continue working while the request is being processed.
   When calling this endpoint, please be sure that the response timeout is long enough.
   Using a response timeout that is too short will result in an exception on the client side.
   On the server side, the result of the sharing operation when a connection is lost is undefined.
   It may be worthwhile to repeat failed or timed out calls to this endpoint.")

(def AppUnsharingSummary "Revoke App Permissions")
(def AppUnsharingDocs
  "This endpoint allows the caller to revoke permission to access one or more apps from one or more users.
   The authenticate user must have ownership permission to every app in the request body for this endoint to fully succeed.
   Note: like app sharing, this is a potentially slow operation.")

(def AppPermissionEnum (enum "read" "write" "own" ""))

(defschema PermissionListerQueryParams
  {(optional-key :full-listing)
   (describe Boolean "If true, include permissions for the authenticated user as well")})

(defschema Subject
  {:source_id (describe NonBlankString "The identifier of the subject source (for exmaple, 'ldap')")
   :id        (describe NonBlankString "The subject identifier")})

(defschema AppPermissionListingRequest
  (describe
    {:apps (describe [QualifiedAppId] "A List of qualified app identifiers")}
    "The app permission listing request."))

(defschema SubjectPermissionListElement
  {:subject    (describe Subject "The user or group identification")
   :permission (describe AppPermissionEnum "The permission level assigned to the subject")})

(defschema AppPermissionListElement
  (assoc QualifiedAppId
    (optional-key :name) (describe NonBlankString "The app name")
    :permissions         (describe [SubjectPermissionListElement] "The list of subject permissions for the app")))

(defschema AppPermissionListing
  {:apps (describe [AppPermissionListElement] "The list of app permissions")})

(defschema AppSharingRequestElement
  (assoc QualifiedAppId
    :permission (describe AppPermissionEnum "The requested permission level")))

(defschema AppSharingResponseElement
  (assoc AppSharingRequestElement
    :app_name             (describe NonBlankString "The app name")
    :success              (describe Boolean "A Boolean flag indicating whether the sharing request succeeded")
    (optional-key :error) (describe ErrorResponse "Information about any error that may have occurred")))

(defschema SubjectAppSharingRequestElement
  {:subject (describe Subject "The user or group identification")
   :apps    (describe [AppSharingRequestElement] "The list of app sharing requests for the subject")})

(defschema SubjectAppSharingResponseElement
  (assoc SubjectAppSharingRequestElement
    :apps (describe [AppSharingResponseElement] "The list of app sharing responses for the subject")))

(defschema AppSharingRequest
  (describe
    {:sharing (describe [SubjectAppSharingRequestElement] "The list of app sharing requests")}
    "The app sharing request."))

(defschema AppSharingResponse
  {:sharing (describe [SubjectAppSharingResponseElement] "The list of app sharing responses")})

(defschema AppUnsharingResponseElement
  (assoc QualifiedAppId
    :app_name             (describe NonBlankString "The app name")
    :success              (describe Boolean "A Boolean flag indicating whether the unsharing request succeeded")
    (optional-key :error) (describe ErrorResponse "Information about any error that may have occurred")))

(defschema SubjectAppUnsharingRequestElement
  {:subject (describe Subject "The user or group identification")
   :apps    (describe [QualifiedAppId] "The list of app unsharing requests for the subject")})

(defschema SubjectAppUnsharingResponseElement
  (assoc SubjectAppUnsharingRequestElement
    :apps (describe [AppUnsharingResponseElement] "The list of app sharing responses for the subject")))

(defschema AppUnsharingRequest
  (describe
    {:unsharing (describe [SubjectAppUnsharingRequestElement] "The list of app unsharing requests")}
    "The app unsharing request."))

(defschema AppUnsharingResponse
  {:unsharing (describe [SubjectAppUnsharingResponseElement] "The list of app unsharing responses")})
