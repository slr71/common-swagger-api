(ns common-swagger-api.schema.metadata
  (:use [common-swagger-api.schema :only [->optional-param describe]])
  (:require [schema.core :as s])
  (:import (java.util UUID)))

(def TargetIdParam (describe UUID "The target item's UUID"))

(s/defschema Avu
  {:id                    (describe UUID "The AVU's UUID")
   :attr                  (describe String "The Attribute's name")
   :value                 (describe String "The Attribute's value")
   :unit                  (describe String "The Attribute's unit")
   :target_id             TargetIdParam
   :created_by            (describe String "The ID of the user who created the AVU")
   :modified_by           (describe String "The ID of the user who last modified the AVU")
   :created_on            (describe Long "The date the AVU was created in ms since the POSIX epoch")
   :modified_on           (describe Long "The date the AVU was last modified in ms since the POSIX epoch")
   (s/optional-key :avus) (describe [(s/recursive #'Avu)] "AVUs attached to this AVU")})

(s/defschema AvuList
  {:avus (describe [Avu] "The list of AVUs")})

(s/defschema AvuRequest
  (-> Avu
      (->optional-param :id)
      (->optional-param :target_id)
      (->optional-param :created_by)
      (->optional-param :modified_by)
      (->optional-param :created_on)
      (->optional-param :modified_on)
      (assoc (s/optional-key :avus)
             (describe [(s/recursive #'AvuRequest)] "AVUs attached to this AVU"))))

(s/defschema AvuListRequest
  (-> {:avus (describe [AvuRequest] "The AVUs to save for the target data item")}
      (describe "The Metadata AVU update request")))

(s/defschema SetAvuRequest
  (-> AvuListRequest
      (->optional-param :avus)
      (describe "The Metadata AVU save request")))
