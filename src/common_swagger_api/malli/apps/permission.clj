(ns common-swagger-api.malli.apps.permission
  (:require
   [common-swagger-api.malli :refer [ErrorResponse NonBlankString]]
   [common-swagger-api.malli.apps :refer [QualifiedAppId]]
   [common-swagger-api.malli.subjects :refer [BaseSubject]]
   [malli.util :as mu]))

(def AnalysisPermissionListingSummary "List Analysis Permissions")
(def AnalysisPermissionListingDocs
  (str "This endpoint allows the caller to list the permissions for one or more analyses. The authenticated user must "
       "have read permission on every analysis in the request body for this endpoint to succeed."))

(def AnalysisSharingSummary "Add Analysis Permissions")
(def AnalysisSharingDocs
  (str "This endpoint allows the caller to share multiple analyses with multiple users. The authenticated user must "
       "have ownership permission to every analysis in the request body for this endpoint to fully succeed. Note: "
       "this is a potentially slow operation and the response is returned synchronously. The DE UI handles this by "
       "allowing the user to continue working while the request is being processed. When calling this endpoint, "
       "please be sure that the response timeout is long enough. Using a response timeout that is too short will "
       "result in an exception on the client side. On the server side, the result of the sharing operation when a "
       "connection is lost is undefined. It may be worthwhile to repeat failed or timed out calls to this endpoint."))

(def AnalysisUnsharingSummary "Revoke Analysis Permissions")
(def AnalysisUnsharingDocs
  (str "This endpoint allows the caller to revoke permission to access one or more analyses from one or more users. "
       "The authenticate user must have ownership permission to every analysis in the request body for this endoint "
       "to fully succeed. Note: like analysis sharing, this is a potentially slow operation."))

(def AppPermissionListingSummary "List App Permissions")
(def AppPermissionListingDocs
  (str "This endpoint allows the caller to list the permissions for one or more apps. The authenticated user must "
       "have read permission on every app in the request body for this endpoint to succeed."))

(def AppSharingSummary "Add App Permissions")
(def AppSharingDocs
  (str "This endpoint allows the caller to share multiple apps with multiple users. The authenticated user must have "
       "ownership permission to every app in the request body for this endpoint to fully succeed. Note: this is a "
       "potentially slow operation and the response is returned synchronously. The DE UI handles this by allowing the "
       "user to continue working while the request is being processed. When calling this endpoint, please be sure "
       "that the response timeout is long enough. Using a response timeout that is too short will result in an "
       "exception on the client side. On the server side, the result of the sharing operation when a connection is "
       "lost is undefined. It may be worthwhile to repeat failed or timed out calls to this endpoint."))

(def AppUnsharingSummary "Revoke App Permissions")
(def AppUnsharingDocs
  (str "This endpoint allows the caller to revoke permission to access one or more apps from one or more users. The "
       "authenticated user must have ownership permission to every app in the request body for this endoint to fully "
       "succeed. Note: like app sharing, this is a potentially slow operation."))

(def ToolSharingSummary "Add Tool Permissions")
(def ToolSharingDocs
  (str "This endpoint allows the caller to share multiple Tools with multiple users. The authenticated user must have "
       "ownership permission to every Tool in the request body for this endpoint to fully succeed. Note: this is a "
       "potentially slow operation and the response is returned synchronously. The DE UI handles this by allowing the "
       "user to continue working while the request is being processed. When calling this endpoint, please be sure that "
       "the response timeout is long enough. Using a response timeout that is too short will result in an exception "
       "on the client side. On the server side, the result of the sharing operation when a connection is lost is "
       "undefined. It may be worthwhile to repeat failed or timed out calls to this endpoint."))

(def ToolUnsharingSummary "Revoke Tool Permissions")
(def ToolUnsharingDocs
  (str "This endpoint allows the caller to revoke permission to access one or more Tools from one or more users. The "
       "authenticated user must have ownership permission to every Tool in the request body for this endoint to fully "
       "succeed. Note: like Tool sharing, this is a potentially slow operation."))

(def AppPermissionEnum [:enum "read" "write" "own" ""])
(def AnalysisPermissionEnum AppPermissionEnum)
(def ToolPermissionEnum AppPermissionEnum)

(def PermissionListerQueryParams
  (mu/closed-schema
   [:map
    [:full-listing
     {:optional            true
      :description         "If true, include permissions for the authenticated user as well"
      :json-schema/example true}
     :boolean]]))

(def AppPermissionListingRequest
  (mu/closed-schema
   [:map
    {:description "The app permission listing request."}
    [:apps
     {:description "A List of qualified app identifiers"}
     [:vector QualifiedAppId]]]))

(def SubjectPermissionListElement
  (mu/closed-schema
   [:map
    [:subject
     {:description "The user or group identification"}
     BaseSubject]

    [:permission
     {:description "The permission level assigned to the subject"}
     AppPermissionEnum]]))

(def AppPermissionListElement
  (mu/closed-schema
   (mu/merge
    QualifiedAppId
    [:map
     [:name
      {:optional            true
       :description         "The app name"
       :json-schema/example "FASTQ"}
      NonBlankString]

     [:permissions
      {:description "The list of subject permissions for the app"}
      [:vector SubjectPermissionListElement]]])))

(def AppPermissionListing
  (mu/closed-schema
   [:map
    [:apps
     {:description "The list of app permissions"}
     [:vector AppPermissionListElement]]]))

(def AppSharingRequestElement
  (mu/closed-schema
   (mu/merge
    QualifiedAppId
    [:map
     [:permission
      {:description "The requested permission level"}
      AppPermissionEnum]])))

(def AppSharingResponseElement
  (mu/closed-schema
   (mu/merge
    AppSharingRequestElement
    [:map
     [:app_name
      {:description         "The app name"
       :json-schema/example "JupyterLab Datascience"}
      NonBlankString]

     [:success
      {:description         "A Boolean flag indicating whether the sharing request succeeded"
       :json-schema/example true}
      :boolean]

     [:error
      {:optional true
       :description "Information about any error that may have occurred"}
      ErrorResponse]])))

(def SubjectAppSharingRequestElement
  (mu/closed-schema
   [:map
    [:subject
     {:description "The user or group identification"}
     BaseSubject]

    [:apps
     {:description "The list of app sharing requests for the subject"}
     [:vector AppSharingRequestElement]]]))

(def SubjectAppSharingResponseElement
  (mu/closed-schema
   (mu/merge
    SubjectAppSharingRequestElement
    [:map
     [:apps
      {:description "The list of app sharing responses for the subject"}
      [:vector AppSharingResponseElement]]])))

(def AppSharingRequest
  (mu/closed-schema
   [:map
    {:description "The app sharing request."}
    [:sharing
     {:description "The list of app sharing requests"}
     [:vector SubjectAppSharingRequestElement]]]))

(def AppSharingResponse
  (mu/closed-schema
    [:map
     [:sharing
      {:description "The list of app sharing responses"}
      [:vector SubjectAppSharingResponseElement]]]))

(def AppUnsharingResponseElement
  (mu/closed-schema
   (mu/merge
    QualifiedAppId
    [:map
     [:app_name
      {:description         "The app name"
       :json-schema/example "CloudShell"}
      NonBlankString]

     [:success
      {:description         "A Boolean flag indicating whether the unsharing request succeeded"
       :json-schema/example true}
      :boolean]

     [:error
      {:optional    true
       :description "Information about any error that may have occurred"}
      ErrorResponse]])))

(def SubjectAppUnsharingRequestElement
  (mu/closed-schema
   [:map
    [:subject
     {:description "The user or group identification"}
     BaseSubject]

    [:apps
     {:description "The list of app unsharing requests for the subject"}
     [:vector QualifiedAppId]]]))

(def SubjectAppUnsharingResponseElement
  (mu/closed-schema
   (mu/merge
     SubjectAppUnsharingRequestElement
     [:map
      [:apps
       {:description "The list of app sharing responses for the subject"}
       [:vector AppUnsharingResponseElement]]])))

(def AppUnsharingRequest
  (mu/closed-schema
   [:map
    {:description "The app unsharing request."}
    [:unsharing
     {:description "The list of app unsharing requests"}
     [:vector SubjectAppUnsharingRequestElement]]]))

(def AppUnsharingResponse
  (mu/closed-schema
   [:map
    [:unsharing
     {:description "The list of app unsharing responses"}
     [:vector SubjectAppUnsharingResponseElement]]]))

(def AnalysisIdList
  (mu/closed-schema
   [:map
    {:description "The analysis permission listing request."}
    [:analyses
     {:description         "A List of analysis IDs"
      :json-schema/example [#uuid "b3c03345-13ce-40e7-8dd5-05bbabe32722"
                            #uuid "3fc0f950-4f02-4220-a803-24a8a613c456"]}
     [:vector :uuid]]]))

(def AnalysisPermissionListElement
  (mu/closed-schema
   [:map
    [:id
     {:description         "The analysis ID"
      :json-schema/example #uuid "df011ebc-4a79-4d7e-8d72-62d21a83cb43"}
     :uuid]

    [:name
     {:description         "The analysis name"
      :json-schema/example "BLAST Sequence Alignment for Zea Mays"}
     NonBlankString]

    [:permissions
     {:description "The list of subject permissions for the analysis"}
     [:vector SubjectPermissionListElement]]]))

(def AnalysisPermissionListing
  (mu/closed-schema
   [:map
    [:analyses
     {:description "The list of analysis permissions"}
     [:vector AnalysisPermissionListElement]]]))

(def AnalysisSharingRequestElement
  (mu/closed-schema
   [:map
    [:analysis_id
     {:description         "The analysis ID"
      :json-schema/example #uuid "a509d124-beea-450d-a7c5-f0675d1941f2"}
     :uuid]

    [:permission
     {:description         "The requested permission level"
      :json-schema/example "own"}
     AnalysisPermissionEnum]]))

(def AnalysisSharingResponseElement
  (mu/closed-schema
   (mu/merge
    AnalysisSharingRequestElement
    [:map
     [:analysis_name
      {:description         "The analysis name"
       :json-schema/example "Arabidopsis Thaliana Sequence Visualization in JupyterLab"}
      NonBlankString]

     [:ok
      {:description         "A Boolean flag indicating whether or not the request passed validation"
       :json-schema/example true}
      :boolean]

     [:error
      {:optional    true
       :description "Information about any validation error that may have occurred"}
      ErrorResponse]])))

(def SubjectAnalysisSharingRequestElement
  (mu/closed-schema
   [:map
    [:subject
     {:description "The user or group identification."}
     BaseSubject]

    [:analyses
     {:description "The list of sharing requests for individual analyses"}
     [:vector AnalysisSharingRequestElement]]]))

(def SubjectAnalysisSharingResponseElement
  (mu/closed-schema
   (mu/merge
    SubjectAnalysisSharingRequestElement
    [:map
     [:analyses
      {:description "The list of analysis sharing responses for the subject"}
      [:vector AnalysisSharingResponseElement]]])))

(def AnalysisSharingRequest
  (mu/closed-schema
   [:map
    {:description "The analysis sharing request."}
    [:sharing
     {:description "The list of sharing requests for individual subjects"}
     [:vector SubjectAnalysisSharingRequestElement]]]))

(def AnalysisSharingResponse
  (mu/closed-schema
   [:map
    [:sharing
     {:description "The list of sharing responses for individual subjects"}
     [:vector SubjectAnalysisSharingResponseElement]]

    [:asyncTaskID
     {:optional            true
      :description         "The ID of the asynchronous task being used to track the sharing request"
      :json-schema/example #uuid "1536c7cb-c1e3-413d-aa28-7ee8ef448e2a"}
     :uuid]]))
