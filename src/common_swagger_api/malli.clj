(ns common-swagger-api.malli
  (:require [clojure.string :as string]
            [malli.core :as m]))

(def NonBlankString [:and :string [:fn (complement string/blank?)]])
