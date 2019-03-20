(ns common-swagger-api.routes
  (:require [clojure.java.io :as io]
            [compojure.api.meta]))

(defmethod compojure.api.meta/restructure-param :description-file
  [_ path acc]
  (update-in acc [:swagger] assoc :description (slurp (io/resource path))))

(defn get-endpoint-delegate-block
  [service endpoint]
  (str "

#### Delegates to " service " service
    " endpoint "
"))
