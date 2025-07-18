(ns common-swagger-api.malli.ontologies
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def OntologyHierarchyFilterParams
  [:map
   [:root-iri
    {:optional            true
     :description         "The root IRI for the ontology hierarchy"
     :json-schema/example "http://example.com/ontology#root"}
    NonBlankString]
   [:attr
    {:optional            true
     :description         "The attribute to filter by"
     :json-schema/example "name"}
    :string]
   [:value
    {:optional            true
     :description         "The value to filter by"
     :json-schema/example "biology"}
    :string]])

(def OntologyDetails
  [:map
   [:version
    {:description         "The ontology version"
     :json-schema/example "1.0"}
    :string]
   [:iri
    {:description         "The ontology IRI"
     :json-schema/example "http://example.com/ontology"}
    NonBlankString]
   [:label
    {:description         "The ontology label"
     :json-schema/example "Example Ontology"}
    :string]
   [:description
    {:optional            true
     :description         "The ontology description"
     :json-schema/example "An example ontology for demonstration"}
    :string]])

(def OntologyClass
  [:map
   [:iri
    {:description         "The class IRI"
     :json-schema/example "http://example.com/ontology#class1"}
    NonBlankString]
   [:label
    {:description         "The class label"
     :json-schema/example "Example Class"}
    :string]
   [:description
    {:optional            true
     :description         "The class description"
     :json-schema/example "An example class"}
    :string]])

(def OntologyClassHierarchy
  [:map
   [:class
    {:description         "The ontology class"
     :json-schema/example {}}
    OntologyClass]
   [:subclasses
    {:description         "The subclasses of this class"
     :json-schema/example []}
    [:vector [:ref ::OntologyClassHierarchy]]]])

(def OntologyListing
  [:map
   [:ontologies
    {:description         "The list of ontologies"
     :json-schema/example []}
    [:vector OntologyDetails]]])

(def OntologyClassListing
  [:map
   [:classes
    {:description         "The list of ontology classes"
     :json-schema/example []}
    [:vector OntologyClass]]])

(def OntologyHierarchyListing
  [:map
   [:hierarchy
    {:description         "The ontology class hierarchy"
     :json-schema/example []}
    [:vector OntologyClassHierarchy]]])