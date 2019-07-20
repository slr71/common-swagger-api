(ns common-swagger-api.schema.data.navigation
  (:use [clojure-commons.error-codes]
        [common-swagger-api.schema :only [describe CommonResponses ErrorResponse ErrorResponseUnchecked]]
        [common-swagger-api.schema.data :as data-schema]
        [common-swagger-api.schema.stats :as stats-schema])
  (:require [schema.core :as s]))

(def NavigationRootSummary "Root Listing")
(def NavigationRootDocs
  "This endpoint provides a shortcut for the client to list the top-level directories
   (e.g. the user's home directory, trash, and shared directories).")

(def NavigationSummary "Directory List (Non-Recursive)")
(def NavigationDocs
  "Only lists subdirectories of the directory path passed into it.")

(s/defschema UserBasePaths
  {:user_home_path  (describe String "The absolute path to the user's home folder")
   :user_trash_path (describe String "The absolute path to the user's trash folder")
   :base_trash_path (describe String "The absolute path to the base trash folder")})

(s/defschema RootListing
  (dissoc stats-schema/DataStatInfo :type))

(s/defschema NavigationRootResponse
  {:roots      [RootListing]
   :base-paths UserBasePaths})

(s/defschema FolderListing
  (-> stats-schema/DataStatInfo
      (dissoc :type)
      (assoc (s/optional-key :folders)
             (describe [(s/recursive #'FolderListing)] "Subdirectories of this directory"))))

(s/defschema NavigationResponse
  {:folder FolderListing})

(def NavigationRootErrorCodeResponses
  (conj data-schema/CommonErrorCodeResponses
        ERR_DOES_NOT_EXIST
        ERR_NOT_READABLE
        ERR_NOT_A_USER))

(s/defschema NavigationRootErrorResponses
  (merge ErrorResponseUnchecked
         {:error_code (apply s/enum NavigationRootErrorCodeResponses)}))

(s/defschema NavigationErrorResponses
  (merge ErrorResponseUnchecked
         {:error_code (apply s/enum (conj NavigationRootErrorCodeResponses
                                          ERR_NOT_A_FOLDER))}))

(s/defschema NavigationRootResponses
  (merge CommonResponses
         {200 {:schema      NavigationRootResponse
               :description "The Root Listing."}
          500 {:schema      NavigationRootErrorResponses
               :description "Potential Error Codes returned by this endpoint."}}))

(s/defschema NavigationResponses
  (merge CommonResponses
         {200 {:schema      NavigationResponse
               :description "The Folder Listing."}
          500 {:schema      NavigationErrorResponses
               :description "Potential Error Codes returned by this endpoint."}}))
