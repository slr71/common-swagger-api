(defproject org.cyverse/common-swagger-api "3.4.6"
  :description "Common library for Swagger documented RESTful APIs"
  :url "https://github.com/cyverse-de/common-swagger-api"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.11.3"]
                 [cheshire "5.13.0"]
                 [metosin/compojure-api "1.1.14"]
                 [metosin/schema-tools "0.13.1"]
                 [org.cyverse/clojure-commons "3.0.8"]
                 [org.cyverse/heuristomancer "2.8.7"]
                 [org.flatland/ordered "1.15.12"]]
  :eastwood {:exclude-namespaces [common-swagger-api.schema.data.exists
                                  common-swagger-api.schema.data.tickets
                                  common-swagger-api.schema.stats]
             :linters [:wrong-arity :wrong-ns-form :wrong-pre-post :wrong-tag :misplaced-docstrings]}
  :plugins [[lein-ancient "0.7.0"]
            [test2junit "1.4.4"]
            [jonase/eastwood "1.4.3"]])
