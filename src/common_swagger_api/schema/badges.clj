(ns common-swagger-api.schema.badges
  (:use [common-swagger-api.schema :only [describe]])
  (:require [schema.core :as s])
  (:import [java.util UUID]))

(def SubmissionField (describe String "The JSON-encoded submission"))
(def UsernameField (describe String "The username of the user that owns the object"))

(s/defschema Submission
  {:id
   (describe UUID "The UUID for this submission")

   :submission
   SubmissionField})

(s/defschema NewSubmission
  (dissoc Submission :id))

(s/defschema Badge
  {:id
   (describe UUID "The UUID for the badge")

   :user
   UsernameField

   :submission
   SubmissionField})

(s/defschema NewBadge
  (dissoc Badge :id))
