(ns common-swagger-api.coerce
  (:require [ring.swagger.coerce :as rc]))

(defn string->long
  "When the given map contains the given key, converts its string value to a long."
  [m k]
  (if (contains? m k)
    (update m k rc/string->long)
    m))
