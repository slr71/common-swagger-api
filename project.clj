(defproject org.cyverse/common-swagger-api "2.8.1-SNAPSHOT"
  :description "Common library for Swagger documented RESTful APIs"
  :url "https://github.com/cyverse-de/common-swagger-api"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.6.3"
                  :exclusions [[com.fasterxml.jackson.dataformat/jackson-dataformat-cbor]
                               [com.fasterxml.jackson.dataformat/jackson-dataformat-smile]
                               [com.fasterxml.jackson.core/jackson-annotations]
                               [com.fasterxml.jackson.core/jackson-databind]
                               [com.fasterxml.jackson.core/jackson-core]]]
                 [metosin/compojure-api "1.1.8"]])
