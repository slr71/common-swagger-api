(ns common-swagger-api.schema.apps.communities)

(def AppCommunityMetadataAddSummary "Add/Update Community Metadata AVUs")
(def AppCommunityMetadataAddDocs
  "Adds or updates Community Metadata AVUs on the app.
   The authenticated user must be a community admin for every Community AVU in the request,
   in order to add or edit this metadata.")

(def AppCommunityMetadataDeleteSummary "Remove Community Metadata AVUs")
(def AppCommunityMetadataDeleteDocs
  "Removes the given Community AVUs associated with an app.
   The authenticated user must be a community admin for every Community AVU in the request,
   in order to remove those AVUs.")
