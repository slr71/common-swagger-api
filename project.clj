(defproject org.cyverse/common-swagger-api "2.11.22"
  :description "Common library for Swagger documented RESTful APIs"
  :url "https://github.com/cyverse-de/common-swagger-api"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [cheshire "5.8.1"]
                 [metosin/compojure-api "1.1.12"]
                 [metosin/schema-tools "0.11.0"]
                 [org.cyverse/clojure-commons "3.0.3"]
                 [org.cyverse/heuristomancer "2.8.6"]
                 [org.flatland/ordered "1.5.7"]]
  :eastwood {:exclude-namespaces [common-swagger-api.schema.data.exists
                                  common-swagger-api.schema.data.tickets]
             :linters [:wrong-arity :wrong-ns-form :wrong-pre-post :wrong-tag :misplaced-docstrings]}
  :plugins [[test2junit "1.2.2"]
            [jonase/eastwood "0.3.5"]])
