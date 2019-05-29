(ns common-swagger-api.schema.apps.admin.reference-genomes
  (:use [common-swagger-api.schema :only [->optional-param describe]]
        [common-swagger-api.schema.apps.reference-genomes :only [ReferenceGenome]]
        [schema.core :only [defschema optional-key]]))

(defschema ReferenceGenomeDeletionParams
  {(optional-key :permanent)
   (describe Boolean "If true, completely remove the reference genome from the database.")})

(defschema ReferenceGenomeRequest
  (-> ReferenceGenome
      (->optional-param :id)
      (->optional-param :created_by)
      (->optional-param :last_modified_by)))
