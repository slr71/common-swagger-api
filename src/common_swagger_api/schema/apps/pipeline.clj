(ns common-swagger-api.schema.apps.pipeline
  (:require [common-swagger-api.schema :refer [describe]]
            [common-swagger-api.schema.apps :refer [AppTaskListing AppVersionListing]]
            [schema.core :refer [defschema optional-key Keyword]]
            [schema-tools.core :as st])
  (:import [java.util UUID]))

(def PipelineCopySummary "Make a Copy of a Pipeline Available for Editing")
(def PipelineCopyDocs
  "This service can be used to make a copy of a Pipeline in the user's workspace.
   This endpoint will copy the App details, steps, and mappings, but will not copy tasks used in the Pipeline steps.")

(def PipelineCreateSummary "Create a Pipeline")
(def PipelineCreateDocs "This service adds a new Pipeline.")

(def PipelineVersionCreateSummary "Add a new Pipeline Version")
(def PipelineVersionCreateDocs "This service adds a new Version to an existing Pipeline.")

(def PipelineEditingViewSummary "Make a Pipeline Available for Editing")
(def PipelineEditingViewDocs
  "The DE uses this service to obtain a JSON representation of a Pipeline for editing.
   The requesting user must have write permissions for the Pipeline, and it must not already be public.")

(def PipelineVersionEditingViewSummary "Make a Pipeline Version Available for Editing")
(def PipelineVersionEditingViewDocs
  "The DE uses this service to obtain a JSON representation of a Pipeline Version for editing.
   The requesting user must have write permissions for the Pipeline, and it must not already be public.")

(def PipelineUpdateSummary "Update a Pipeline")
(def PipelineUpdateDocs
  "This service updates an existing Pipeline in the database,
   as long as the Pipeline has not been submitted for public use.")

(defschema PipelineMappingMap
  {(describe Keyword "The input ID") (describe String "The output ID")})

(defschema PipelineMapping
  {:source_step (describe Long "The step index of the Source Step")
   :target_step (describe Long "The step index of the Target Step")
   :map (describe PipelineMappingMap "The {'input-id': 'output-id'} mappings")})

(defschema PipelineStep
  {:name
   (describe String "The Step's name")

   :description
   (describe String "The Step's description")

   :system_id
   (describe String "The ID of the execution system.")

   (optional-key :task_id)
   (describe String "A String referring to either an internal task or an external app. If the
                     string refers to an internal task then this must be a string representation
                     of a UUID. Otherwise, it should be the ID of the external app.")

   (optional-key :external_app_id)
   (describe String "A string referring to an external app that is used to perform the step. This
                     field is required any time the task ID isn't provided.")

   :app_type
   (describe String "The Step's App type")})

(defschema Pipeline
  (merge AppTaskListing
         AppVersionListing
         {:id
          (describe UUID "The pipeline's ID")

          :steps
          (describe [PipelineStep] "The Pipeline's steps")

          :mappings
          (describe [PipelineMapping] "The Pipeline's input/output mappings")}))

(defschema PipelineUpdateRequest
  (-> Pipeline
      (st/dissoc :versions)
      (st/optional-keys [:tasks])
      (describe "The Pipeline to update.")))

(defschema PipelineCreateRequest
  (-> PipelineUpdateRequest
      (st/optional-keys [:id])
      (describe "The Pipeline to create.")))

(defschema PipelineVersionRequest
  (-> PipelineCreateRequest
      (st/dissoc :version_id)
      (st/required-keys [:version])
      (describe "The Pipeline Version to add.")))
