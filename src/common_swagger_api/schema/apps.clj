(ns common-swagger-api.schema.apps
  (:use [common-swagger-api.schema :only [describe]])
  (:import [java.util UUID]))

(def AppIdParam (describe UUID "A UUID that is used to identify the App"))
