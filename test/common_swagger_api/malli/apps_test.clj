(ns common-swagger-api.malli.apps-test
  (:require [clojure.test :refer [deftest testing is]]
            [common-swagger-api.malli.apps :as apps]
            [malli.core :as malli]))

(defn valid? [schema data]
  (malli/validate schema data))

(deftest test-AppParameterListItem
  (testing "AppParameterListItem validation"
    (testing "valid list item"
      (is (valid? apps/AppParameterListItem
                  {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"}))
      (is (valid? apps/AppParameterListItem
                  {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                   :name "small_genome"
                   :value "1"
                   :description "Small genome (< 2 Mb)"
                   :display "Small Genome"
                   :isDefault false}))
      (is (valid? apps/AppParameterListItem
                  {:id #uuid "456e7890-b12c-34d5-e678-901234567890"
                   :name "large_genome"
                   :isDefault true})))

    (testing "invalid list item"
      (is (not (valid? apps/AppParameterListItem {})))
      (is (not (valid? apps/AppParameterListItem {:name "small_genome"})))
      (is (not (valid? apps/AppParameterListItem
                       {:id "not-a-uuid"
                        :name "small_genome"})))
      (is (not (valid? apps/AppParameterListItem
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :isDefault "not-a-boolean"})))
      (is (not (valid? apps/AppParameterListItem
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :extra-field "not-allowed"}))))))
(deftest test-AppParameterListGroup
  (testing "AppParameterListGroup validation"
    (testing "valid list group"
      (is (valid? apps/AppParameterListGroup
                  {:id #uuid "123e4567-e89b-12d3-a456-426614174000"}))
      (is (valid? apps/AppParameterListGroup
                  {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                   :name "genome_size_group"
                   :value "size_group"
                   :description "Genome size selection group"
                   :display "Genome Size"
                   :isDefault false}))
      (is (valid? apps/AppParameterListGroup
                  {:id #uuid "456e7890-b12c-34d5-e678-901234567890"
                   :name "options_group"
                   :arguments [{:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                                :name "small_genome"
                                :value "1"}]
                   :groups [{:id #uuid "abc12345-def6-7890-1234-567890abcdef"
                             :name "sub_group"}]})))

    (testing "invalid list group"
      (is (not (valid? apps/AppParameterListGroup {})))
      (is (not (valid? apps/AppParameterListGroup {:name "genome_size_group"})))
      (is (not (valid? apps/AppParameterListGroup
                       {:id "not-a-uuid"
                        :name "genome_size_group"})))
      (is (not (valid? apps/AppParameterListGroup
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :arguments "not-a-vector"})))
      (is (not (valid? apps/AppParameterListGroup
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :groups "not-a-vector"})))
      (is (not (valid? apps/AppParameterListGroup
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :extra-field "not-allowed"}))))

(deftest test-AppParameterValidator
  (testing "AppParameterValidator validation"
    (testing "valid validator"
      (is (valid? apps/AppParameterValidator
                  {:type "IntAbove"
                   :params [0]}))
      (is (valid? apps/AppParameterValidator
                  {:type "StringMatches"
                   :params ["^[a-zA-Z0-9]+$"]}))
      (is (valid? apps/AppParameterValidator
                  {:type "Required"
                   :params []})))

    (testing "invalid validator"
      (is (not (valid? apps/AppParameterValidator {})))
      (is (not (valid? apps/AppParameterValidator {:type "IntAbove"})))
      (is (not (valid? apps/AppParameterValidator {:params [0]})))
      (is (not (valid? apps/AppParameterValidator
                       {:type 123
                        :params [0]})))
      (is (not (valid? apps/AppParameterValidator
                       {:type "IntAbove"
                        :params "not-a-vector"})))
      (is (not (valid? apps/AppParameterValidator
                       {:type "IntAbove"
                        :params [0]
                        :extra-field "not-allowed"}))))))))

