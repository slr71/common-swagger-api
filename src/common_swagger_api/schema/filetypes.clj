(ns common-swagger-api.schema.filetypes
  (:require [common-swagger-api.schema :refer [describe NonBlankString]]
            [heuristomancer.core :as hm]
            [schema.core :as s]))

(def ValidInfoTypes (conj (hm/supported-formats) "unknown"))
(def ValidInfoTypesEnum (apply s/enum ValidInfoTypes))
(def ValidInfoTypesEnumPlusBlank (apply s/enum (conj ValidInfoTypes "")))

(s/defschema TypesList
  {:types (describe [String] "The available file types")})

(s/defschema FileType
  {:type (describe ValidInfoTypesEnumPlusBlank "The file's type")})

(s/defschema FileTypeReturn
  (assoc FileType
         :user (describe NonBlankString "The user performing the request")
         :path (describe NonBlankString "The iRODS path to the file")))
