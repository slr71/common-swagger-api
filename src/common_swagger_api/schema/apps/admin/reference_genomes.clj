(ns common-swagger-api.schema.apps.admin.reference-genomes
  (:require [common-swagger-api.schema :refer [->optional-param describe]]
            [common-swagger-api.schema.apps.reference-genomes :refer [ReferenceGenome]]
            [schema.core :refer [defschema optional-key]]))

(def ReferenceGenomeAddSummary "Add a Reference Genome")
(def ReferenceGenomeAddDocs
  "This endpoint adds a Reference Genome to the Discovery Environment.")

(def ReferenceGenomeDeleteSummary "Delete a Reference Genome")
(def ReferenceGenomeDeleteDocs
  "A Reference Genome can be marked as deleted in the DE without being completely removed from the database using this service.
   **Note**: an attempt to delete a Reference Genome that is already marked as deleted is treated as a no-op rather than an error condition.
   If the Reference Genome doesn't exist in the database at all, however, then that is treated as an error condition.")

(def ReferenceGenomeUpdateSummary "Update a Reference Genome")
(def ReferenceGenomeUpdateDocs
  "This endpoint modifies the `name`, `path`, and `deleted` fields of a Reference Genome in the Discovery Environment.")

(defschema ReferenceGenomeDeletionParams
  {(optional-key :permanent)
   (describe Boolean "If true, completely remove the reference genome from the database.")})

(defschema ReferenceGenomeRequest
  (-> ReferenceGenome
      (->optional-param :id)
      (->optional-param :created_by)
      (->optional-param :last_modified_by)))

(defschema ReferenceGenomeAddRequest
  (-> ReferenceGenomeRequest
      (describe "The Reference Genome to add.")))

(defschema ReferenceGenomeUpdateRequest
  (-> ReferenceGenomeRequest
      (describe "The Reference Genome fields to update.")))
