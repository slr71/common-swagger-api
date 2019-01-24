(defproject org.cyverse/common-swagger-api "2.9.1"
  :description "Common library for Swagger documented RESTful APIs"
  :url "https://github.com/cyverse-de/common-swagger-api"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.6.3"]
                 [metosin/compojure-api "1.1.8"]
                 [org.cyverse/clojure-commons "2.8.1"]]
  :eastwood {:linters [:wrong-arity :wrong-ns-form :wrong-pre-post :wrong-tag :misplaced-docstrings]}
  :plugins [[test2junit "1.2.2"]
            [jonase/eastwood "0.3.4"]])
