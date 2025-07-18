(ns common-swagger-api.malli-test
  (:require [clojure.test :as t]
            [common-swagger-api.malli :as m]))

(t/deftest make-fields-optional
  (t/is (= [:map [:foo {:optional true} :string]]
           (m/make-fields-optional [:map [:foo :string]] :foo)))
  (t/is (= [:map [:foo :string]]
           (m/make-fields-optional [:map [:foo :string]] :bar)))
  (t/is (= [:map
            [:foo {:optional true :description "foo description"} :string]
            [:bar {:description "bar description"} :string]]
           (m/make-fields-optional
            [:map
             [:foo {:description "foo description"} :string]
             [:bar {:description "bar description"} :string]]
            :foo)))
  (t/is (= [:map
            [:foo {:optional true :description "foo description"} :string]
            [:bar {:description "bar description"} :string]
            [:baz {:optional true :description "baz description"} :int]]
           (m/make-fields-optional
            [:map
             [:foo {:description "foo description"} :string]
             [:bar {:description "bar description"} :string]
             [:baz {:description "baz description"} :int]]
            :foo :baz))))

(t/deftest remove-fields
  (t/is (= [:map
            [:bar {:description "bar description"} :string]]
           (m/remove-fields
            [:map
             [:foo {:description "foo description"} :string]
             [:bar {:description "bar description"} :string]]
            :foo)))
  (t/is (= [:map
            [:bar {:description "bar description"} :string]]
           (m/remove-fields
            [:map
             [:foo {:description "foo description"} :string]
             [:bar {:description "bar description"} :string]
             [:baz {:description "baz description"} :int]]
            :foo :baz))))
