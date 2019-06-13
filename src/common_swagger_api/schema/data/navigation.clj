(ns common-swagger-api.schema.data.navigation
  (:use [common-swagger-api.schema :only [describe]]
        [common-swagger-api.schema.stats :as stats-schema])
  (:require [schema.core :as s]))

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
