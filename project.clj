(defproject org.cyverse/common-swagger-api "3.1.1-SNAPSHOT"
  :description "Common library for Swagger documented RESTful APIs"
  :url "https://github.com/cyverse-de/common-swagger-api"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [cheshire "5.10.0"]
                 [metosin/compojure-api "1.1.13"]
                 [metosin/schema-tools "0.12.3"]
                 [org.cyverse/clojure-commons "3.0.6"]
                 [org.cyverse/heuristomancer "2.8.6"]
                 [org.flatland/ordered "1.5.9"]]
  :eastwood {:exclude-namespaces [common-swagger-api.schema.data.exists
                                  common-swagger-api.schema.data.tickets
                                  common-swagger-api.schema.stats]
             :linters [:wrong-arity :wrong-ns-form :wrong-pre-post :wrong-tag :misplaced-docstrings]}
  :plugins [[lein-ancient "0.7.0"]
            [test2junit "1.2.2"]
            [jonase/eastwood "0.3.14"]])
