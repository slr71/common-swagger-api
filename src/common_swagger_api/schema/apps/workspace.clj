(ns common-swagger-api.schema.apps.workspace
  (:require [common-swagger-api.schema :refer [describe]]
            [schema.core :refer [defschema]])
  (:import (java.util UUID)))

(def WorkspaceId (describe UUID "The workspace ID."))

(defschema Workspace
  {:id               WorkspaceId
   :user_id          (describe UUID "The user's internal ID.")
   :root_category_id (describe UUID "The ID of the user's root app category.")
   :is_public        (describe Boolean "Indicates whether the workspace is public.")
   :new_workspace    (describe Boolean "Indicates whether the workspace was just created.")})
