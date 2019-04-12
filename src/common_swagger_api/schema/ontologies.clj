(ns common-swagger-api.schema.ontologies
  (:use [common-swagger-api.schema :only [describe NonBlankString]])
  (:require [schema.core :as s])
  (:import [java.util Date]))

(def OntologyVersionParam (describe String "The unique version of the Ontology"))
(def OntologyClassIRIParam (describe String "A unique Class IRI"))

(s/defschema OntologyHierarchyFilterParams
  {:attr (describe String "The metadata attribute that stores class IRIs under the given root IRI")})

(s/defschema OntologyDetails
  {:iri        (describe (s/maybe String) "The unique IRI for this Ontology")
   :version    OntologyVersionParam
   :created_by (describe NonBlankString "The user who uploaded this Ontology")
   :created_on (describe Date "The date this Ontology was uploaded")})

(s/defschema OntologyClass
  {:iri
   (describe String "The unique IRI for this Ontology Class")

   :label
   (describe (s/maybe String) "The label annotation of this Ontology Class")

   (s/optional-key :description)
   (describe (s/maybe String) "The description annotation of this Ontology Class")})

(s/defschema OntologyClassHierarchy
  (merge OntologyClass
         {(s/optional-key :subclasses)
          (describe [(s/recursive #'OntologyClassHierarchy)] "Subclasses of this Ontology Class")}))

(s/defschema OntologyHierarchy
  {:hierarchy (describe (s/maybe OntologyClassHierarchy) "An Ontology Class hierarchy")})

(s/defschema OntologyHierarchyList
  {:hierarchies (describe [OntologyClassHierarchy] "A list of Ontology Class hierarchies")})
