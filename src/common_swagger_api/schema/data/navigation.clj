(ns common-swagger-api.schema.data.navigation
  (:use [common-swagger-api.schema :only [describe]])
  (:require [schema.core :as s]))

(s/defschema UserBasePaths
  {:user_home_path  (describe String "The absolute path to the user's home folder")
   :user_trash_path (describe String "The absolute path to the user's trash folder")
   :base_trash_path (describe String "The absolute path to the base trash folder")})
