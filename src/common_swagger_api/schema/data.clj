(ns common-swagger-api.schema.data
  (:use [common-swagger-api.schema :only [describe NonBlankString]])
  (:require [schema.core :as s])
  (:import [java.util UUID]))

(def DataIdPathParam (describe UUID "The UUID assigned to the file or folder"))

(def PermissionEnum (s/enum :read :write :own))
