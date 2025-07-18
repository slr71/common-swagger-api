(ns common-swagger-api.malli.filetypes
  (:require [common-swagger-api.malli :refer [NonBlankString]]))

;; Common file types - this would ideally be imported from heuristomancer
(def ValidInfoTypes 
  ["unknown" "txt" "json" "xml" "csv" "tsv" "fasta" "fastq" "gff" "gtf" "bed" "sam" "bam" "vcf" "pdf" "jpg" "png" "tiff"])

(def ValidInfoTypesEnum
  (apply vector :enum ValidInfoTypes))

(def ValidInfoTypesEnumPlusBlank
  (apply vector :enum (conj ValidInfoTypes "")))

(def TypesList
  [:map
   [:types
    {:description         "The available file types"
     :json-schema/example ["txt" "json" "xml"]}
    [:vector :string]]])

(def FileType
  [:map
   [:type
    {:description         "The file's type"
     :json-schema/example "txt"}
    ValidInfoTypesEnumPlusBlank]])

(def FileTypeReturn
  [:map
   FileType
   [:user
    {:description         "The user performing the request"
     :json-schema/example "user123"}
    NonBlankString]
   [:path
    {:description         "The iRODS path to the file"
     :json-schema/example "/iplant/home/user123/file.txt"}
    NonBlankString]])