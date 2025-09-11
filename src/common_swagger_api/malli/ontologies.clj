(ns common-swagger-api.malli.ontologies
  (:require [common-swagger-api.malli :refer [NonBlankString]]
            [malli.core :as m]
            [malli.util :as mu]))

(def OntologyVersionParam
  [:string {:description "The unique version of the Ontology"}])

(def OntologyClassIRIParam
  [:string {:description "A unique Class IRI"}])

(def OntologyHierarchyFilterParams
  [:map {:closed true}
   [:attr
    {:description "The metadata attribute that stores class IRIs under the given root IRI"}
    :string]])

(def OntologyDetails
  [:map {:closed true}
   [:iri
    {:description "The unique IRI for this Ontology"}
    [:maybe :string]]

   [:version
    {:description "The unique version of the Ontology"}
    :string]

   [:created_by
    {:description "The user who uploaded this Ontology"}
    NonBlankString]

   [:created_on
    {:description "The date this Ontology was uploaded"}
    inst?]])

(def OntologyClass
  [:map {:closed true}
   [:iri
    {:description "The unique IRI for this Ontology Class"}
    :string]

   [:label
    {:description "The label annotation of this Ontology Class"}
    [:maybe :string]]

   [:description
    {:optional    true
     :description "The description annotation of this Ontology Class"}
    [:maybe :string]]])

(def OntologyClassHierarchy
  (m/schema
    [:schema {:registry {::OntologyClassHierarchy
                         [:map {:closed true}
                          [:iri
                           {:description "The unique IRI for this Ontology Class"}
                           :string]

                          [:label
                           {:description "The label annotation of this Ontology Class"}
                           [:maybe :string]]

                          [:description
                           {:optional    true
                            :description "The description annotation of this Ontology Class"}
                           [:maybe :string]]

                          [:subclasses
                           {:optional    true
                            :description "Subclasses of this Ontology Class"}
                           [:vector [:ref ::OntologyClassHierarchy]]]]}}
     [:ref ::OntologyClassHierarchy]]))

(def OntologyHierarchy
  [:map {:closed true}
   [:hierarchy
    {:description "An Ontology Class hierarchy"}
    [:maybe OntologyClassHierarchy]]])

(def OntologyHierarchyList
  [:map {:closed true}
   [:hierarchies
    {:description "A list of Ontology Class hierarchies"}
    [:vector OntologyClassHierarchy]]])