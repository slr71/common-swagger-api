(ns common-swagger-api.schema.badges
  (:use [common-swagger-api.schema :only [describe NonBlankString]])
  (:require [schema.core :as s]
            [common-swagger-api.schema.apps :refer [AnalysisSubmission]])
  (:import [java.util UUID]))

(s/defschema Submission
  {:id
   (describe UUID "The UUID for this submission")

   :submission
   AnalysisSubmission})

(s/defschema NewSubmission
  (dissoc Submission :id))

(s/defschema Badge
  {:id
   (describe UUID "The UUID for the badge")

   :user
   (describe NonBlankString "The username of the user that owns the object")

   :submission
   AnalysisSubmission})

(s/defschema NewBadge
  (dissoc Badge :id))
