(ns common-swagger-api.malli.subjects
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

(def Subject
  [:map
   [:id
    {:description         "The subject's unique identifier"
     :json-schema/example "user123"}
    NonBlankString]
   [:name
    {:description         "The subject's name"
     :json-schema/example "user123"}
    NonBlankString]
   [:first_name
    {:optional            true
     :description         "The subject's first name"
     :json-schema/example "John"}
    :string]
   [:last_name
    {:optional            true
     :description         "The subject's last name"
     :json-schema/example "Doe"}
    :string]
   [:email
    {:optional            true
     :description         "The subject's email address"
     :json-schema/example "john.doe@example.com"}
    :string]
   [:institution
    {:optional            true
     :description         "The subject's institution"
     :json-schema/example "Example University"}
    :string]
   [:source_id
    {:optional            true
     :description         "The subject's source ID"
     :json-schema/example "ldap"}
    :string]])

(def SubjectSearchRequest
  [:map
   [:search
    {:description         "The search string to match against subject names"
     :json-schema/example "john"}
    NonBlankString]
   [:limit
    {:optional            true
     :description         "The maximum number of results to return"
     :json-schema/example 50}
    [:and :int [:fn pos?]]]])

(def SubjectSearchResponse
  [:map
   [:subjects
    {:description         "The list of matching subjects"
     :json-schema/example []}
    [:vector Subject]]])

(def SubjectLookupRequest
  [:map
   [:source_id
    {:description         "The source ID to look up"
     :json-schema/example "ldap"}
    NonBlankString]
   [:subject_id
    {:description         "The subject ID to look up"
     :json-schema/example "user123"}
    NonBlankString]])

(def SubjectLookupResponse
  [:map
   [:subject
    {:description         "The subject information"
     :json-schema/example {}}
    Subject]])