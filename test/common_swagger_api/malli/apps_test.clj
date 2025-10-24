(ns common-swagger-api.malli.apps-test
  (:require
   [clojure.test :refer [deftest is testing]]
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
                        :extra-field "not-allowed"}))))))

(deftest test-AppParameterListItemOrTree
  (testing "AppParameterListItemOrTree validation"
    (testing "valid list item or tree"
      (is (valid? apps/AppParameterListItemOrTree
                  {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"}))
      (is (valid? apps/AppParameterListItemOrTree
                  {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                   :name "organism"
                   :value "human"
                   :description "Select organism type"
                   :display "Organism"
                   :isDefault false}))
      (is (valid? apps/AppParameterListItemOrTree
                  {:id #uuid "456e7890-b12c-34d5-e678-901234567890"
                   :isSingleSelect true
                   :selectionCascade "up"}))
      (is (valid? apps/AppParameterListItemOrTree
                  {:id #uuid "abc12345-def6-7890-1234-567890abcdef"
                   :name "tree_selector"
                   :arguments [{:id #uuid "111e1111-a11a-11a1-a111-111111111111"
                                :name "option1"
                                :value "val1"}
                               {:id #uuid "222e2222-b22b-22b2-b222-222222222222"
                                :name "option2"
                                :value "val2"}]}))
      (is (valid? apps/AppParameterListItemOrTree
                  {:id #uuid "def67890-abc1-2345-6789-0abcdef12345"
                   :name "grouped_tree"
                   :groups [{:id #uuid "333e3333-c33c-33c3-c333-333333333333"
                             :name "group1"}
                            {:id #uuid "444e4444-d44d-44d4-d444-444444444444"
                             :name "group2"
                             :arguments [{:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                                          :name "nested_option"}]}]}))
      (is (valid? apps/AppParameterListItemOrTree
                  {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                   :name "full_tree"
                   :display "Full Tree Selector"
                   :isSingleSelect false
                   :selectionCascade "down"
                   :arguments [{:id #uuid "666e6666-f66f-66f6-f666-666666666666"
                                :name "root_option"}]
                   :groups [{:id #uuid "777e7777-a77a-77a7-a777-777777777777"
                             :name "root_group"}]})))

    (testing "invalid list item or tree"
      (is (not (valid? apps/AppParameterListItemOrTree {})))
      (is (not (valid? apps/AppParameterListItemOrTree {:name "organism"})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id "not-a-uuid"
                        :name "organism"})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :isSingleSelect "not-a-boolean"})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :selectionCascade 123})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :arguments "not-a-vector"})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :groups "not-a-vector"})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :arguments [{:name "invalid-missing-id"}]})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :groups [{:name "invalid-missing-id"}]})))
      (is (not (valid? apps/AppParameterListItemOrTree
                       {:id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                        :extra-field "not-allowed"}))))))

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
                        :extra-field "not-allowed"}))))))

(deftest test-AppFileParameters
  (testing "AppFileParameters validation"
    (testing "valid file parameters"
      (is (valid? apps/AppFileParameters {}))
      (is (valid? apps/AppFileParameters
                  {:format "fasta"}))
      (is (valid? apps/AppFileParameters
                  {:format "fastq"
                   :file_info_type "SequenceFile"}))
      (is (valid? apps/AppFileParameters
                  {:format "txt"
                   :file_info_type "TextFile"
                   :is_implicit false
                   :repeat_option_flag false
                   :data_source "file"
                   :retain true}))
      (is (valid? apps/AppFileParameters
                  {:is_implicit true
                   :data_source "stdout"}))
      (is (valid? apps/AppFileParameters
                  {:repeat_option_flag true
                   :retain false})))

    (testing "invalid file parameters"
      (is (not (valid? apps/AppFileParameters
                       {:format 123})))
      (is (not (valid? apps/AppFileParameters
                       {:file_info_type true})))
      (is (not (valid? apps/AppFileParameters
                       {:is_implicit "not-a-boolean"})))
      (is (not (valid? apps/AppFileParameters
                       {:repeat_option_flag "not-a-boolean"})))
      (is (not (valid? apps/AppFileParameters
                       {:data_source 456})))
      (is (not (valid? apps/AppFileParameters
                       {:retain "not-a-boolean"})))
      (is (not (valid? apps/AppFileParameters
                       {:extra-field "not-allowed"}))))))

(deftest test-AppParameter
  (testing "AppParameter validation"
    (testing "valid parameter"
      (is (valid? apps/AppParameter
                  {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                   :type "FileInput"}))
      (is (valid? apps/AppParameter
                  {:id #uuid "111e1111-a11a-11a1-a111-111111111111"
                   :type "TextInput"
                   :name "--input"
                   :label "Input File"
                   :description "Specify the input file for processing"
                   :required true
                   :isVisible true}))
      (is (valid? apps/AppParameter
                  {:id #uuid "222e2222-b22b-22b2-b222-222222222222"
                   :type "IntegerInput"
                   :defaultValue 10
                   :value 20
                   :order 1
                   :omit_if_blank false}))
      (is (valid? apps/AppParameter
                  {:id #uuid "333e3333-c33c-33c3-c333-333333333333"
                   :type "FileInput"
                   :file_parameters {:format "fasta"
                                     :file_info_type "SequenceAlignment"
                                     :retain true}}))
      (is (valid? apps/AppParameter
                  {:id #uuid "444e4444-d44d-44d4-d444-444444444444"
                   :type "TextSelection"
                   :arguments [{:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                                :name "option1"
                                :value "val1"}
                               {:id #uuid "666e6666-f66f-66f6-f666-666666666666"
                                :name "option2"
                                :value "val2"}]}))
      (is (valid? apps/AppParameter
                  {:id #uuid "777e7777-a77a-77a7-a777-777777777777"
                   :type "IntegerInput"
                   :validators [{:type "IntAbove"
                                 :params [0]}
                                {:type "IntBelow"
                                 :params [100]}]})))

    (testing "invalid parameter"
      (is (not (valid? apps/AppParameter {})))
      (is (not (valid? apps/AppParameter {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"})))
      (is (not (valid? apps/AppParameter {:type "FileInput"})))
      (is (not (valid? apps/AppParameter
                       {:id "not-a-uuid"
                        :type "FileInput"})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type 123})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :name 123})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :label true})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :order "not-an-int"})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :required "not-a-boolean"})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :file_parameters "not-a-map"})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :arguments "not-a-vector"})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :validators "not-a-vector"})))
      (is (not (valid? apps/AppParameter
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"
                        :extra-field "not-allowed"}))))))

(deftest test-AppGroup
  (testing "AppGroup validation"
    (testing "valid group"
      (is (valid? apps/AppGroup
                  {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                   :label "Input Parameters"}))
      (is (valid? apps/AppGroup
                  {:id #uuid "111e1111-a11a-11a1-a111-111111111111"
                   :label "Output Parameters"
                   :name "output_parameters"
                   :description "Output file and result parameters"
                   :isVisible true}))
      (is (valid? apps/AppGroup
                  {:id #uuid "222e2222-b22b-22b2-b222-222222222222"
                   :label "Settings"
                   :parameters [{:id #uuid "333e3333-c33c-33c3-c333-333333333333"
                                 :type "TextInput"
                                 :label "Name"}
                                {:id #uuid "444e4444-d44d-44d4-d444-444444444444"
                                 :type "IntegerInput"
                                 :label "Count"}]}))
      (is (valid? apps/AppGroup
                  {:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                   :label "Advanced"
                   :name "advanced"
                   :isVisible false
                   :parameters []})))

    (testing "invalid group"
      (is (not (valid? apps/AppGroup {})))
      (is (not (valid? apps/AppGroup {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"})))
      (is (not (valid? apps/AppGroup {:label "Input Parameters"})))
      (is (not (valid? apps/AppGroup
                       {:id "not-a-uuid"
                        :label "Input Parameters"})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label 123})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :name true})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :description 456})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :isVisible "not-a-boolean"})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :parameters "not-a-vector"})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :parameters [{:id #uuid "333e3333-c33c-33c3-c333-333333333333"}]})))
      (is (not (valid? apps/AppGroup
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :extra-field "not-allowed"}))))))

(deftest test-AppVersionDetails
  (testing "AppVersionDetails validation"
    (testing "valid version details"
      (is (valid? apps/AppVersionDetails
                  {:version "1.0.0"
                   :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}))
      (is (valid? apps/AppVersionDetails
                  {:version "2.5.1"
                   :version_id #uuid "987e6543-e21b-32c1-b456-426614174000"})))

    (testing "invalid version details"
      (is (not (valid? apps/AppVersionDetails {})))
      (is (not (valid? apps/AppVersionDetails {:version "1.0.0"})))
      (is (not (valid? apps/AppVersionDetails {:version_id #uuid "123e4567-e89b-12d3-a456-426614174000"})))
      (is (not (valid? apps/AppVersionDetails
                       {:version 123
                        :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"})))
      (is (not (valid? apps/AppVersionDetails
                       {:version "1.0.0"
                        :version_id "not-a-uuid"})))
      (is (not (valid? apps/AppVersionDetails
                       {:version "1.0.0"
                        :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :extra-field "not-allowed"}))))))

(deftest test-AppVersionListing
  (testing "AppVersionListing validation"
    (testing "valid version listing"
      (is (valid? apps/AppVersionListing {}))
      (is (valid? apps/AppVersionListing
                  {:versions []}))
      (is (valid? apps/AppVersionListing
                  {:versions [{:version "1.0.0"
                               :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}]}))
      (is (valid? apps/AppVersionListing
                  {:versions [{:version "1.0.0"
                               :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}
                              {:version "2.0.0"
                               :version_id #uuid "987e6543-e21b-32c1-b456-426614174000"}
                              {:version "3.0.0"
                               :version_id #uuid "abc12345-def6-7890-abcd-ef1234567890"}]})))

    (testing "invalid version listing"
      (is (not (valid? apps/AppVersionListing
                       {:versions "not-a-vector"})))
      (is (not (valid? apps/AppVersionListing
                       {:versions [{}]})))
      (is (not (valid? apps/AppVersionListing
                       {:versions [{:version "1.0.0"}]})))
      (is (not (valid? apps/AppVersionListing
                       {:versions [{:version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}]})))
      (is (not (valid? apps/AppVersionListing
                       {:versions [{:version "1.0.0"
                                    :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}
                                   {:version "2.0.0"}]})))
      (is (not (valid? apps/AppVersionListing
                       {:extra-field "not-allowed"}))))))

(deftest test-AppVersionOrderRequest
  (testing "AppVersionOrderRequest validation"
    (testing "valid version order request"
      (is (valid? apps/AppVersionOrderRequest
                  {:versions []}))
      (is (valid? apps/AppVersionOrderRequest
                  {:versions [{:version "1.0.0"
                               :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}]}))
      (is (valid? apps/AppVersionOrderRequest
                  {:versions [{:version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}]}))
      (is (valid? apps/AppVersionOrderRequest
                  {:versions [{:version "3.0.0"
                               :version_id #uuid "abc12345-def6-7890-abcd-ef1234567890"}
                              {:version_id #uuid "987e6543-e21b-32c1-b456-426614174000"}
                              {:version "1.0.0"
                               :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}]})))

    (testing "invalid version order request"
      (is (not (valid? apps/AppVersionOrderRequest {})))
      (is (not (valid? apps/AppVersionOrderRequest
                       {:versions "not-a-vector"})))
      (is (not (valid? apps/AppVersionOrderRequest
                       {:versions [{}]})))
      (is (not (valid? apps/AppVersionOrderRequest
                       {:versions [{:version "1.0.0"}]})))
      (is (not (valid? apps/AppVersionOrderRequest
                       {:versions [{:version "1.0.0"
                                    :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"}
                                   {:version "2.0.0"}]})))
      (is (not (valid? apps/AppVersionOrderRequest
                       {:versions []
                        :extra-field "not-allowed"}))))))

(deftest test-AppBase
  (testing "AppBase validation"
    (testing "valid app base"
      (is (valid? apps/AppBase
                  {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                   :name "BLAST"
                   :description "Basic Local Alignment Search Tool"}))
      (is (valid? apps/AppBase
                  {:id #uuid "987e6543-e21b-32c1-b456-426614174000"
                   :name "FastQC"
                   :description "Quality control tool for high throughput sequence data"
                   :integration_date #inst "2024-01-15T10:30:00.000-00:00"
                   :edited_date #inst "2024-10-20T14:45:00.000-00:00"}))
      (is (valid? apps/AppBase
                  {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                   :name "BWA"
                   :description "Burrows-Wheeler Aligner"
                   :system_id "de"}))
      (is (valid? apps/AppBase
                  {:id #uuid "111e1111-a11a-11a1-a111-111111111111"
                   :name "Samtools"
                   :description "Tools for manipulating SAM/BAM files"
                   :version "1.2.0"
                   :version_id #uuid "222e2222-b22b-22b2-b222-222222222222"}))
      (is (valid? apps/AppBase
                  {:id #uuid "333e3333-c33c-33c3-c333-333333333333"
                   :name "Complete App"
                   :description "An app with all fields"
                   :integration_date #inst "2024-01-01T00:00:00.000-00:00"
                   :edited_date #inst "2024-12-31T23:59:59.000-00:00"
                   :system_id "condor"
                   :version "2.0.0"
                   :version_id #uuid "444e4444-d44d-44d4-d444-444444444444"})))

    (testing "invalid app base"
      (is (not (valid? apps/AppBase {})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :description "BLAST tool"})))
      (is (not (valid? apps/AppBase
                       {:name "BLAST"
                        :description "BLAST tool"})))
      (is (not (valid? apps/AppBase
                       {:id "not-a-uuid"
                        :name "BLAST"
                        :description "BLAST tool"})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name 123
                        :description "BLAST tool"})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description true})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :integration_date "not-a-date"})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :edited_date 12345})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :system_id 123})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :version 123})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :version_id "not-a-uuid"})))
      (is (not (valid? apps/AppBase
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :extra-field "not-allowed"}))))))

(deftest test-AppLimitCheckResult
  (testing "AppLimitCheckResult validation"
    (testing "valid limit check result"
      (is (valid? apps/AppLimitCheckResult
                  {:limitCheckID "concurrent-job-limit"
                   :reasonCodes ["MAX_JOBS_EXCEEDED"]
                   :additionalInfo {:current_jobs 10 :max_jobs 5}}))
      (is (valid? apps/AppLimitCheckResult
                  {:limitCheckID "quota-limit"
                   :reasonCodes ["QUOTA_EXCEEDED" "STORAGE_FULL"]
                   :additionalInfo {:quota_used 100 :quota_limit 100}}))
      (is (valid? apps/AppLimitCheckResult
                  {:limitCheckID "test-check"
                   :reasonCodes []
                   :additionalInfo nil}))
      (is (valid? apps/AppLimitCheckResult
                  {:limitCheckID "complex-check"
                   :reasonCodes ["CODE1" "CODE2" "CODE3"]
                   :additionalInfo {:nested {:data {:structure true}}}})))

    (testing "invalid limit check result"
      (is (not (valid? apps/AppLimitCheckResult {})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:limitCheckID "test"
                        :reasonCodes ["CODE1"]})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:limitCheckID "test"
                        :additionalInfo {}})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:reasonCodes ["CODE1"]
                        :additionalInfo {}})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:limitCheckID 123
                        :reasonCodes ["CODE1"]
                        :additionalInfo {}})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:limitCheckID "test"
                        :reasonCodes "not-a-vector"
                        :additionalInfo {}})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:limitCheckID "test"
                        :reasonCodes [123]
                        :additionalInfo {}})))
      (is (not (valid? apps/AppLimitCheckResult
                       {:limitCheckID "test"
                        :reasonCodes ["CODE1"]
                        :additionalInfo {}
                        :extra-field "not-allowed"}))))))

(deftest test-AppLimitCheckResultSummary
  (testing "AppLimitCheckResultSummary validation"
    (testing "valid limit check result summary"
      (is (valid? apps/AppLimitCheckResultSummary
                  {:canRun true
                   :results []}))
      (is (valid? apps/AppLimitCheckResultSummary
                  {:canRun false
                   :results [{:limitCheckID "concurrent-job-limit"
                              :reasonCodes ["MAX_JOBS_EXCEEDED"]
                              :additionalInfo {:current_jobs 10 :max_jobs 5}}]}))
      (is (valid? apps/AppLimitCheckResultSummary
                  {:canRun false
                   :results [{:limitCheckID "check1"
                              :reasonCodes ["CODE1"]
                              :additionalInfo nil}
                             {:limitCheckID "check2"
                              :reasonCodes ["CODE2" "CODE3"]
                              :additionalInfo {:data "value"}}]})))

    (testing "invalid limit check result summary"
      (is (not (valid? apps/AppLimitCheckResultSummary {})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:canRun true})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:results []})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:canRun "not-a-boolean"
                        :results []})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:canRun true
                        :results "not-a-vector"})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:canRun true
                        :results [{}]})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:canRun true
                        :results [{:limitCheckID "test"
                                   :reasonCodes ["CODE1"]}]})))
      (is (not (valid? apps/AppLimitCheckResultSummary
                       {:canRun true
                        :results []
                        :extra-field "not-allowed"}))))))

(deftest test-AppLimitChecks
  (testing "AppLimitChecks validation"
    (testing "valid limit checks"
      (is (valid? apps/AppLimitChecks {}))
      (is (valid? apps/AppLimitChecks
                  {:limitChecks {:canRun true
                                 :results []}}))
      (is (valid? apps/AppLimitChecks
                  {:limitChecks {:canRun false
                                 :results [{:limitCheckID "concurrent-job-limit"
                                            :reasonCodes ["MAX_JOBS_EXCEEDED"]
                                            :additionalInfo {:current_jobs 10 :max_jobs 5}}]}})))

    (testing "invalid limit checks"
      (is (not (valid? apps/AppLimitChecks
                       {:limitChecks "not-a-map"})))
      (is (not (valid? apps/AppLimitChecks
                       {:limitChecks {}})))
      (is (not (valid? apps/AppLimitChecks
                       {:limitChecks {:canRun true}})))
      (is (not (valid? apps/AppLimitChecks
                       {:limitChecks {:results []}})))
      (is (not (valid? apps/AppLimitChecks
                       {:limitChecks {:canRun true
                                      :results []
                                      :extra-field "not-allowed"}})))
      (is (not (valid? apps/AppLimitChecks
                       {:extra-field "not-allowed"}))))))

(deftest test-App
  (testing "App validation"
    (testing "valid app"
      (is (valid? apps/App
                  {:id #uuid "def67890-abc1-2345-6789-0abcdef12345"
                   :name "Samtools"
                   :description "Tools for manipulating SAM/BAM files"
                   :tools [{:id #uuid "444e4444-d44d-44d4-d444-444444444444"
                            :name "samtools"
                            :description "SAM/BAM file processing"
                            :version "1.15.1"
                            :type "executable"}
                           {:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                            :name "bcftools"
                            :description "Variant calling utilities"
                            :version "1.15.1"
                            :type "executable"
                            :deprecated false}]}))
      (is (valid? apps/App
                  {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                   :name "Complete App"
                   :description "An app with all fields"
                   :integration_date #inst "2024-01-01T00:00:00.000-00:00"
                   :edited_date #inst "2024-12-31T23:59:59.000-00:00"
                   :system_id "condor"
                   :version "2.0.0"
                   :version_id #uuid "666e6666-f66f-66f6-f666-666666666666"
                   :versions [{:version "1.0.0"
                               :version_id #uuid "777e7777-a77a-77a7-a777-777777777777"}
                              {:version "2.0.0"
                               :version_id #uuid "666e6666-f66f-66f6-f666-666666666666"}]
                   :tools [{:id #uuid "888e8888-a88a-88a8-a888-888888888888"
                            :name "tool1"
                            :version "1.0"
                            :type "executable"
                            :deprecated false}]
                   :references ["https://doi.org/10.1093/nar/gkv416" "PMID: 25916842"]
                   :groups [{:id #uuid "999e9999-b99b-99b9-b999-999999999999"
                             :label "Input Parameters"
                             :name "input_params"
                             :description "Input file parameters"
                             :isVisible true
                             :parameters [{:id #uuid "aaaeaaaa-caaa-aaa1-aaaa-aaaaaaaaaaaa"
                                           :type "FileInput"
                                           :label "Input File"
                                           :required true}]}]})))

    (testing "invalid app"
      (is (not (valid? apps/App {})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :description "BLAST tool"})))
      (is (not (valid? apps/App
                       {:name "BLAST"
                        :description "BLAST tool"})))
      (is (not (valid? apps/App
                       {:id "not-a-uuid"
                        :name "BLAST"
                        :description "BLAST tool"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name 123
                        :description "BLAST tool"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description true})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :integration_date "not-a-date"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :versions "not-a-vector"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :versions [{}]})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :tools "not-a-vector"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :tools [{:id #uuid "444e4444-d44d-44d4-d444-444444444444"
                                 :name "samtools"
                                 :version "1.15.1"
                                 :type "executable"
                                 :deprecated "not-a-boolean"}]})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :references "not-a-vector"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :references [123]})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :groups "not-a-vector"})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :groups [{}]})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :groups [{:id #uuid "999e9999-b99b-99b9-b999-999999999999"}]})))
      (is (not (valid? apps/App
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "BLAST"
                        :description "BLAST tool"
                        :extra-field "not-allowed"}))))))
