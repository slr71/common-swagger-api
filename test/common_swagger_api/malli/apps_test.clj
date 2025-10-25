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

(deftest test-AppFileParameterDetails
  (testing "AppFileParameterDetails validation"
    (testing "valid file parameter details"
      (is (valid? apps/AppFileParameterDetails
                  {:id "param_001"
                   :name "output_alignment"
                   :description "The output alignment file from the analysis"
                   :label "Output Alignment"
                   :format "bam"
                   :required true}))
      (is (valid? apps/AppFileParameterDetails
                  {:id "param_002"
                   :name "input_fastq"
                   :description "Input FASTQ file for quality control"
                   :label "Input FASTQ"
                   :format "fastq"
                   :required false}))
      (is (valid? apps/AppFileParameterDetails
                  {:id "param_003"
                   :name "output_vcf"
                   :description "Variant calling output"
                   :label "VCF Output"
                   :format "vcf"
                   :required true})))

    (testing "invalid file parameter details - missing required fields"
      (is (not (valid? apps/AppFileParameterDetails {})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :label "Output Alignment"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"}))))

    (testing "invalid file parameter details - incorrect field types"
      (is (not (valid? apps/AppFileParameterDetails
                       {:id 123
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name 123
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description true
                        :label "Output Alignment"
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label 456
                        :format "bam"
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format 789
                        :required true})))
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"
                        :required "not-a-boolean"}))))

    (testing "invalid file parameter details - extra fields not allowed"
      (is (not (valid? apps/AppFileParameterDetails
                       {:id "param_001"
                        :name "output_alignment"
                        :description "The output alignment file from the analysis"
                        :label "Output Alignment"
                        :format "bam"
                        :required true
                        :extra-field "not-allowed"}))))))

(deftest test-AppTask
  (testing "AppTask validation"
    (testing "valid app task with all required fields"
      (is (valid? apps/AppTask
                  {:system_id "de"
                   :id "task_001"
                   :name "BLAST Analysis"
                   :description "Performs BLAST sequence alignment analysis"
                   :inputs []
                   :outputs []}))
      (is (valid? apps/AppTask
                  {:system_id "condor"
                   :id "task_002"
                   :name "FastQC"
                   :description "Quality control for high throughput sequence data"
                   :inputs [{:id "param_fastqc_in"
                             :name "input_fastq"
                             :description "Input FASTQ file"
                             :label "Input File"
                             :format "fastq"
                             :required true}]
                   :outputs [{:id "param_fastqc_out"
                              :name "output_html"
                              :description "QC report in HTML format"
                              :label "QC Report"
                              :format "html"
                              :required true}]})))

    (testing "valid app task with optional tool field"
      (is (valid? apps/AppTask
                  {:system_id "de"
                   :id "task_003"
                   :name "Samtools Sort"
                   :description "Sort BAM files by coordinate"
                   :tool {:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                          :name "samtools"
                          :version "1.15.1"
                          :type "executable"
                          :is_public true
                          :permission "own"
                          :implementation {:implementor "Jane Smith"
                                           :implementor_email "jane.smith@example.org"
                                           :test {:input_files ["/test/input.bam"]
                                                  :output_files ["/test/output.sorted.bam"]}}
                          :container {:id #uuid "666e6666-f66f-66f6-f666-666666666666"
                                      :name "samtools-container"
                                      :image {:id #uuid "777e7777-a77a-77a7-a777-777777777777"
                                              :name "biocontainers/samtools"
                                              :tag "1.15.1"}}}
                   :inputs [{:id "param_samtools_in"
                             :name "input_bam"
                             :description "Unsorted BAM file"
                             :label "Input BAM"
                             :format "bam"
                             :required true}]
                   :outputs [{:id "param_samtools_out"
                              :name "sorted_bam"
                              :description "Sorted BAM file"
                              :label "Sorted BAM"
                              :format "bam"
                              :required true}]})))

    (testing "valid app task with multiple inputs and outputs"
      (is (valid? apps/AppTask
                  {:system_id "de"
                   :id "task_004"
                   :name "BWA Alignment"
                   :description "Align sequences using BWA"
                   :inputs [{:id "param_bwa_ref"
                             :name "reference_genome"
                             :description "Reference genome FASTA file"
                             :label "Reference Genome"
                             :format "fasta"
                             :required true}
                            {:id "param_bwa_r1"
                             :name "reads_r1"
                             :description "Forward reads FASTQ file"
                             :label "Forward Reads"
                             :format "fastq"
                             :required true}
                            {:id "param_bwa_r2"
                             :name "reads_r2"
                             :description "Reverse reads FASTQ file"
                             :label "Reverse Reads"
                             :format "fastq"
                             :required false}]
                   :outputs [{:id "param_bwa_sam"
                              :name "aligned_sam"
                              :description "Aligned sequences in SAM format"
                              :label "Alignment SAM"
                              :format "sam"
                              :required true}
                             {:id "param_bwa_log"
                              :name "alignment_log"
                              :description "Alignment statistics log"
                              :label "Log File"
                              :format "txt"
                              :required false}]})))

    (testing "invalid app task - missing required fields"
      (is (not (valid? apps/AppTask {})))
      (is (not (valid? apps/AppTask
                       {:id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []}))))

    (testing "invalid app task - incorrect field types"
      (is (not (valid? apps/AppTask
                       {:system_id 123
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id 456
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name 456
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description true
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs "not-a-vector"
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs "not-a-vector"})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :tool "not-a-map"
                        :inputs []
                        :outputs []}))))

    (testing "invalid app task - invalid nested AppFileParameterDetails in inputs"
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs [{:id 123
                                  :name "input_fasta"
                                  :description "Input sequence"
                                  :label "Input"
                                  :format "fasta"
                                  :required true}]
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs [{:name "input_fasta"
                                  :description "Input sequence"
                                  :label "Input"
                                  :format "fasta"
                                  :required true}]
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs [{:id "param_input"
                                  :description "Input sequence"
                                  :label "Input"
                                  :format "fasta"
                                  :required true}]
                        :outputs []}))))

    (testing "invalid app task - invalid nested AppFileParameterDetails in outputs"
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs [{:id 456
                                   :name "output_txt"
                                   :description "BLAST results"
                                   :label "Results"
                                   :format "txt"
                                   :required true}]})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs [{:id "param_output"
                                   :name "output_txt"
                                   :description "BLAST results"
                                   :label "Results"
                                   :format "txt"}]})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs [{:id "param_output"
                                   :description "BLAST results"
                                   :label "Results"
                                   :format "txt"
                                   :required true}]}))))

    (testing "invalid app task - invalid ToolDetails in tool field"
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :tool {:id "not-a-uuid"
                               :name "blast"
                               :version "2.12.0"
                               :type "executable"
                               :is_public true
                               :permission "own"}
                        :inputs []
                        :outputs []})))
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :tool {:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                               :name "blast"
                               :version "2.12.0"
                               :type "executable"}
                        :inputs []
                        :outputs []}))))

    (testing "invalid app task - extra fields not allowed"
      (is (not (valid? apps/AppTask
                       {:system_id "de"
                        :id "task_001"
                        :name "BLAST Analysis"
                        :description "Performs BLAST sequence alignment analysis"
                        :inputs []
                        :outputs []
                        :extra-field "not-allowed"}))))))

(deftest test-AppTaskListing
  (testing "AppTaskListing validation"
    (testing "valid app task listing with all required fields from AppBase"
      (is (valid? apps/AppTaskListing
                  {:id "app_001"
                   :name "Multi-step Workflow"
                   :description "A workflow consisting of multiple analysis tasks"
                   :tasks []}))
      (is (valid? apps/AppTaskListing
                  {:id "app_workflow_123"
                   :name "BLAST and FastQC Pipeline"
                   :description "Combined sequence alignment and quality control workflow"
                   :tasks []})))

    (testing "valid app task listing with empty tasks vector"
      (is (valid? apps/AppTaskListing
                  {:id "app_empty_tasks"
                   :name "Empty Workflow"
                   :description "A workflow with no tasks defined yet"
                   :tasks []})))

    (testing "valid app task listing with single task"
      (is (valid? apps/AppTaskListing
                  {:id "app_single_task"
                   :name "Single Task Workflow"
                   :description "Workflow with one task"
                   :tasks [{:system_id "de"
                            :id "task_001"
                            :name "BLAST Analysis"
                            :description "Performs BLAST sequence alignment analysis"
                            :inputs []
                            :outputs []}]})))

    (testing "valid app task listing with multiple tasks"
      (is (valid? apps/AppTaskListing
                  {:id "app_multi_task"
                   :name "Multi-Task Pipeline"
                   :description "A comprehensive pipeline with multiple analysis steps"
                   :tasks [{:system_id "de"
                            :id "task_001"
                            :name "FastQC"
                            :description "Quality control for sequence data"
                            :inputs []
                            :outputs []}
                           {:system_id "condor"
                            :id "task_002"
                            :name "BLAST"
                            :description "Sequence alignment"
                            :inputs []
                            :outputs []}
                           {:system_id "de"
                            :id "task_003"
                            :name "Report Generation"
                            :description "Generate analysis report"
                            :inputs []
                            :outputs []}]})))

    (testing "valid app task listing with realistic AppTask objects"
      (is (valid? apps/AppTaskListing
                  {:id "app_realistic"
                   :name "RNA-Seq Analysis"
                   :description "Complete RNA-Seq analysis workflow"
                   :tasks [{:system_id "de"
                            :id "task_qc"
                            :name "FastQC"
                            :description "Quality control for FASTQ files"
                            :inputs [{:id "param_fastq_in"
                                      :name "input_fastq"
                                      :description "Input FASTQ file"
                                      :label "Input File"
                                      :format "fastq"
                                      :required true}]
                            :outputs [{:id "param_qc_out"
                                       :name "qc_report"
                                       :description "QC report"
                                       :label "QC Report"
                                       :format "html"
                                       :required true}]}
                           {:system_id "de"
                            :id "task_align"
                            :name "STAR Alignment"
                            :description "Align reads to reference genome"
                            :inputs [{:id "param_fastq"
                                      :name "reads"
                                      :description "Sequencing reads"
                                      :label "Reads"
                                      :format "fastq"
                                      :required true}
                                     {:id "param_ref"
                                      :name "reference"
                                      :description "Reference genome"
                                      :label "Reference"
                                      :format "fasta"
                                      :required true}]
                            :outputs [{:id "param_bam"
                                       :name "aligned_bam"
                                       :description "Aligned reads"
                                       :label "BAM File"
                                       :format "bam"
                                       :required true}]}]})))

    (testing "valid app task listing with optional AppBase fields"
      (is (valid? apps/AppTaskListing
                  {:id "app_with_optional"
                   :name "Complete Workflow"
                   :description "Workflow with all optional fields"
                   :integration_date #inst "2024-01-15T10:30:00.000-00:00"
                   :edited_date #inst "2024-10-20T14:45:00.000-00:00"
                   :system_id "de"
                   :version "1.0.0"
                   :version_id #uuid "123e4567-e89b-12d3-a456-426614174000"
                   :tasks []})))

    (testing "id field accepts strings (not UUIDs)"
      (is (valid? apps/AppTaskListing
                  {:id "string_id_123"
                   :name "String ID App"
                   :description "App with string ID"
                   :tasks []}))
      (is (valid? apps/AppTaskListing
                  {:id "app-with-dashes-456"
                   :name "Dashed ID App"
                   :description "App with dashed string ID"
                   :tasks []}))
      (is (valid? apps/AppTaskListing
                  {:id "app_underscore_789"
                   :name "Underscore ID App"
                   :description "App with underscored string ID"
                   :tasks []})))

    (testing "invalid app task listing - missing required fields from AppBase"
      (is (not (valid? apps/AppTaskListing {})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_001"
                        :name "Missing Description"})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_002"
                        :description "Missing name"})))
      (is (not (valid? apps/AppTaskListing
                       {:name "No ID"
                        :description "Missing id"})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_003"
                        :name "No Tasks"
                        :description "Missing tasks field"}))))

    (testing "invalid app task listing - id field rejects non-string types"
      (is (not (valid? apps/AppTaskListing
                       {:id 123
                        :name "Numeric ID"
                        :description "ID is a number"
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id true
                        :name "Boolean ID"
                        :description "ID is a boolean"
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id #uuid "123e4567-e89b-12d3-a456-426614174000"
                        :name "UUID ID"
                        :description "ID is a UUID instead of string"
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id nil
                        :name "Nil ID"
                        :description "ID is nil"
                        :tasks []}))))

    (testing "invalid app task listing - incorrect field types"
      (is (not (valid? apps/AppTaskListing
                       {:id "app_004"
                        :name 123
                        :description "Name is not a string"
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_005"
                        :name "Valid Name"
                        :description true
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_006"
                        :name "Valid Name"
                        :description "Valid description"
                        :tasks "not-a-vector"})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_007"
                        :name "Valid Name"
                        :description "Valid description"
                        :integration_date "not-a-date"
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_008"
                        :name "Valid Name"
                        :description "Valid description"
                        :edited_date 12345
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_009"
                        :name "Valid Name"
                        :description "Valid description"
                        :system_id 123
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_010"
                        :name "Valid Name"
                        :description "Valid description"
                        :version 456
                        :tasks []})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_011"
                        :name "Valid Name"
                        :description "Valid description"
                        :version_id "not-a-uuid"
                        :tasks []}))))

    (testing "invalid app task listing - invalid nested AppTask data"
      (is (not (valid? apps/AppTaskListing
                       {:id "app_invalid_task"
                        :name "Invalid Task"
                        :description "Contains invalid task"
                        :tasks [{:id "task_001"
                                 :name "Missing system_id"}]})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_invalid_task_2"
                        :name "Invalid Task 2"
                        :description "Task missing required field"
                        :tasks [{:system_id "de"
                                 :id "task_002"
                                 :name "Missing description"
                                 :inputs []
                                 :outputs []}]})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_invalid_task_3"
                        :name "Invalid Task 3"
                        :description "Task with wrong type"
                        :tasks [{:system_id 123
                                 :id "task_003"
                                 :name "Task Name"
                                 :description "Task description"
                                 :inputs []
                                 :outputs []}]})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_invalid_nested"
                        :name "Invalid Nested Data"
                        :description "Task with invalid nested parameter"
                        :tasks [{:system_id "de"
                                 :id "task_004"
                                 :name "Task Name"
                                 :description "Task description"
                                 :inputs [{:id 123
                                           :name "invalid_param"
                                           :description "Invalid parameter"
                                           :label "Label"
                                           :format "txt"
                                           :required true}]
                                 :outputs []}]})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_mixed_tasks"
                        :name "Mixed Valid and Invalid"
                        :description "One valid and one invalid task"
                        :tasks [{:system_id "de"
                                 :id "task_valid"
                                 :name "Valid Task"
                                 :description "This task is valid"
                                 :inputs []
                                 :outputs []}
                                {:system_id "de"
                                 :id "task_invalid"
                                 :name "Invalid Task"}]}))))

    (testing "invalid app task listing - extra fields not allowed"
      (is (not (valid? apps/AppTaskListing
                       {:id "app_extra"
                        :name "Extra Field"
                        :description "Has extra field"
                        :tasks []
                        :extra-field "not-allowed"})))
      (is (not (valid? apps/AppTaskListing
                       {:id "app_extra_2"
                        :name "Another Extra"
                        :description "Another extra field"
                        :tasks []
                        :unexpected "also-not-allowed"}))))))

(deftest test-AppParameterJobView
  (testing "AppParameterJobView validation"
    (testing "valid parameter with composite string id"
      (is (valid? apps/AppParameterJobView
                  {:id "step_123_param_456"
                   :type "FileInput"}))
      (is (valid? apps/AppParameterJobView
                  {:id "step_abc_param_def"
                   :type "TextInput"
                   :name "--input"
                   :label "Input File"
                   :description "Specify the input file for processing"
                   :required true
                   :isVisible true}))
      (is (valid? apps/AppParameterJobView
                  {:id "step_001_param_002"
                   :type "IntegerInput"
                   :defaultValue 10
                   :value 20
                   :order 1
                   :omit_if_blank false})))

    (testing "id field accepts various string formats"
      (is (valid? apps/AppParameterJobView
                  {:id "step-123-param-456"
                   :type "FileInput"}))
      (is (valid? apps/AppParameterJobView
                  {:id "step123param456"
                   :type "TextInput"}))
      (is (valid? apps/AppParameterJobView
                  {:id "STEP_ABC_PARAM_DEF"
                   :type "TextInput"}))
      (is (valid? apps/AppParameterJobView
                  {:id "step_1_param_2_suffix_3"
                   :type "TextInput"}))
      (is (valid? apps/AppParameterJobView
                  {:id "any-valid-string-format"
                   :type "FileInput"})))

    (testing "valid parameter with file_parameters"
      (is (valid? apps/AppParameterJobView
                  {:id "step_111_param_222"
                   :type "FileInput"
                   :file_parameters {:format "fasta"
                                     :file_info_type "SequenceAlignment"
                                     :retain true}}))
      (is (valid? apps/AppParameterJobView
                  {:id "step_file_param_out"
                   :type "FileOutput"
                   :file_parameters {:format "fastq"
                                     :file_info_type "SequenceFile"
                                     :is_implicit false
                                     :repeat_option_flag false
                                     :data_source "file"
                                     :retain true}})))

    (testing "valid parameter with arguments"
      (is (valid? apps/AppParameterJobView
                  {:id "step_args_param_select"
                   :type "TextSelection"
                   :arguments [{:id #uuid "555e5555-e55e-55e5-e555-555555555555"
                                :name "option1"
                                :value "val1"}
                               {:id #uuid "666e6666-f66f-66f6-f666-666666666666"
                                :name "option2"
                                :value "val2"}]}))
      (is (valid? apps/AppParameterJobView
                  {:id "step_tree_param_multi"
                   :type "TreeSelection"
                   :arguments [{:id #uuid "777e7777-a77a-77a7-a777-777777777777"
                                :name "root_option"
                                :isSingleSelect false
                                :groups [{:id #uuid "888e8888-a88a-88a8-a888-888888888888"
                                          :name "sub_group"}]}]})))

    (testing "valid parameter with validators"
      (is (valid? apps/AppParameterJobView
                  {:id "step_val_param_int"
                   :type "IntegerInput"
                   :validators [{:type "IntAbove"
                                 :params [0]}
                                {:type "IntBelow"
                                 :params [100]}]}))
      (is (valid? apps/AppParameterJobView
                  {:id "step_val_param_str"
                   :type "TextInput"
                   :validators [{:type "StringMatches"
                                 :params ["^[a-zA-Z0-9]+$"]}
                                {:type "Required"
                                 :params []}]})))

    (testing "valid parameter with all fields from AppParameter"
      (is (valid? apps/AppParameterJobView
                  {:id "step_complete_param_full"
                   :type "FileInput"
                   :name "--input-file"
                   :label "Input File"
                   :description "The primary input file for analysis"
                   :defaultValue "/path/to/default.txt"
                   :value "/path/to/input.txt"
                   :order 1
                   :required true
                   :isVisible true
                   :omit_if_blank false
                   :file_parameters {:format "txt"
                                     :file_info_type "TextFile"
                                     :is_implicit false
                                     :repeat_option_flag false
                                     :data_source "file"
                                     :retain true}
                   :validators [{:type "Required"
                                 :params []}]})))

    (testing "invalid parameter - missing required fields"
      (is (not (valid? apps/AppParameterJobView {})))
      (is (not (valid? apps/AppParameterJobView {:id "step_123_param_456"})))
      (is (not (valid? apps/AppParameterJobView {:type "FileInput"}))))

    (testing "invalid parameter - id field rejects non-string types"
      (is (not (valid? apps/AppParameterJobView
                       {:id 123
                        :type "FileInput"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id true
                        :type "FileInput"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                        :type "FileInput"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id nil
                        :type "FileInput"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id [:step "123" :param "456"]
                        :type "FileInput"}))))

    (testing "invalid parameter - incorrect field types"
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_type"
                        :type 123})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_name"
                        :type "FileInput"
                        :name 123})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_label"
                        :type "FileInput"
                        :label true})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_order"
                        :type "FileInput"
                        :order "not-an-int"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_required"
                        :type "FileInput"
                        :required "not-a-boolean"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_visible"
                        :type "FileInput"
                        :isVisible "not-a-boolean"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_invalid_param_omit"
                        :type "FileInput"
                        :omit_if_blank "not-a-boolean"}))))

    (testing "invalid parameter - invalid file_parameters"
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_file"
                        :type "FileInput"
                        :file_parameters "not-a-map"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_file2"
                        :type "FileInput"
                        :file_parameters {:format 123}})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_file3"
                        :type "FileInput"
                        :file_parameters {:is_implicit "not-a-boolean"}})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_file4"
                        :type "FileInput"
                        :file_parameters {:extra-field "not-allowed"}}))))

    (testing "invalid parameter - invalid arguments"
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_args"
                        :type "TextSelection"
                        :arguments "not-a-vector"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_args2"
                        :type "TextSelection"
                        :arguments [{:name "invalid-missing-id"}]})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_args3"
                        :type "TextSelection"
                        :arguments [{:id "not-a-uuid"
                                     :name "option1"}]}))))

    (testing "invalid parameter - invalid validators"
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_val"
                        :type "IntegerInput"
                        :validators "not-a-vector"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_val2"
                        :type "IntegerInput"
                        :validators [{}]})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_val3"
                        :type "IntegerInput"
                        :validators [{:type "IntAbove"}]})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_val4"
                        :type "IntegerInput"
                        :validators [{:type 123
                                      :params [0]}]})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_bad_param_val5"
                        :type "IntegerInput"
                        :validators [{:type "IntAbove"
                                      :params [0]
                                      :extra-field "not-allowed"}]}))))

    (testing "invalid parameter - extra fields not allowed"
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_extra_param_field"
                        :type "FileInput"
                        :extra-field "not-allowed"})))
      (is (not (valid? apps/AppParameterJobView
                       {:id "step_extra_param_field2"
                        :type "TextInput"
                        :name "--input"
                        :unexpected "also-not-allowed"}))))))

(deftest test-AppStepResourceRequirements
  (testing "AppStepResourceRequirements validation"
    (testing "valid with only required step_number field"
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 2}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 10})))

    (testing "valid with step_number and fields from Settings"
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :memory_limit 1073741824}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 2
                   :min_memory_limit 536870912}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :min_cpu_cores 1.0}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 3
                   :max_cpu_cores 4.0}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :min_disk_space 10737418240})))

    (testing "valid with step_number and default resource fields"
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :default_max_cpu_cores 2.0}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 2
                   :default_cpu_cores 1.0}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :default_memory 536870912}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 3
                   :default_disk_space 5368709120})))

    (testing "valid with realistic complete example"
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :memory_limit 2147483648
                   :min_memory_limit 536870912
                   :min_cpu_cores 1.0
                   :max_cpu_cores 8.0
                   :min_disk_space 10737418240
                   :default_max_cpu_cores 2.0
                   :default_cpu_cores 1.0
                   :default_memory 536870912
                   :default_disk_space 5368709120})))

    (testing "valid with combination of Settings and default fields"
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 2
                   :min_cpu_cores 2.0
                   :max_cpu_cores 16.0
                   :default_cpu_cores 4.0
                   :default_max_cpu_cores 8.0}))
      (is (valid? apps/AppStepResourceRequirements
                  {:step_number 1
                   :memory_limit 4294967296
                   :min_memory_limit 1073741824
                   :default_memory 2147483648
                   :min_disk_space 21474836480
                   :default_disk_space 10737418240})))

    (testing "invalid - missing required step_number field"
      (is (not (valid? apps/AppStepResourceRequirements {})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:memory_limit 1073741824})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:min_cpu_cores 1.0
                        :max_cpu_cores 4.0})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:default_memory 536870912
                        :default_disk_space 5368709120}))))

    (testing "invalid - incorrect type for step_number"
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number "1"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1.5})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number true})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number nil}))))

    (testing "invalid - incorrect types for memory and disk fields (int fields)"
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :memory_limit "1073741824"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :min_memory_limit 1.5})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :default_memory "536870912"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :min_disk_space "10737418240"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :default_disk_space 5.5}))))

    (testing "invalid - incorrect types for cpu core fields (double fields)"
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :min_cpu_cores "1.0"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :max_cpu_cores 4})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :default_cpu_cores "1.0"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :default_max_cpu_cores true}))))

    (testing "invalid - extra fields not allowed (closed map)"
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :extra-field "not-allowed"})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 1
                        :memory_limit 1073741824
                        :unknown_field 123})))
      (is (not (valid? apps/AppStepResourceRequirements
                       {:step_number 2
                        :min_cpu_cores 1.0
                        :max_cpu_cores 4.0
                        :default_cpu_cores 2.0
                        :unexpected "value"}))))))

(deftest test-AppGroupJobView
  (testing "AppGroupJobView validation"
    (testing "valid group with all required fields"
      (is (valid? apps/AppGroupJobView
                  {:id "step_123_group_456"
                   :label "Input Parameters"
                   :step_number 1}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_001_group_002"
                   :label "Output Parameters"
                   :step_number 1}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_abc_group_def"
                   :label "Settings"
                   :step_number 2})))

    (testing "valid group with string id field (not UUID like AppGroup)"
      (is (valid? apps/AppGroupJobView
                  {:id "step_123_group_456"
                   :label "Input"
                   :step_number 1}))
      (is (valid? apps/AppGroupJobView
                  {:id "step-with-dashes-789"
                   :label "Settings"
                   :step_number 3}))
      (is (valid? apps/AppGroupJobView
                  {:id "STEP_UPPER_GROUP_999"
                   :label "Advanced"
                   :step_number 5})))

    (testing "valid group with optional fields from AppGroup"
      (is (valid? apps/AppGroupJobView
                  {:id "step_111_group_222"
                   :label "Output Parameters"
                   :name "output_parameters"
                   :description "Output file and result parameters"
                   :step_number 1}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_333_group_444"
                   :label "Advanced"
                   :name "advanced"
                   :isVisible false
                   :step_number 2}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_visible_group_yes"
                   :label "Basic Settings"
                   :isVisible true
                   :step_number 1})))

    (testing "valid group with optional parameters field"
      (is (valid? apps/AppGroupJobView
                  {:id "step_params_group_001"
                   :label "Input Settings"
                   :step_number 1
                   :parameters []}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_params_group_002"
                   :label "Analysis Settings"
                   :step_number 2
                   :parameters [{:id "step_002_param_001"
                                 :type "TextInput"
                                 :label "Name"}]}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_params_group_003"
                   :label "Complete Settings"
                   :step_number 1
                   :parameters [{:id "step_001_param_file"
                                 :type "FileInput"
                                 :label "Input File"}
                                {:id "step_001_param_int"
                                 :type "IntegerInput"
                                 :label "Count"
                                 :defaultValue 10}]})))

    (testing "valid group with parameters containing composite IDs"
      (is (valid? apps/AppGroupJobView
                  {:id "step_123_group_456"
                   :label "Processing Parameters"
                   :step_number 1
                   :parameters [{:id "step_123_param_789"
                                 :type "FileInput"
                                 :name "--input"
                                 :label "Input File"
                                 :description "Primary input for analysis"
                                 :required true}
                                {:id "step_123_param_abc"
                                 :type "IntegerInput"
                                 :name "--threads"
                                 :label "Thread Count"
                                 :description "Number of processing threads"
                                 :defaultValue 4
                                 :value 8
                                 :required false}]}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_workflow_group_main"
                   :label "Main Parameters"
                   :step_number 2
                   :parameters [{:id "step_workflow_param_selection"
                                 :type "TextSelection"
                                 :label "Options"
                                 :arguments [{:id #uuid "111e1111-a11a-11a1-a111-111111111111"
                                              :name "option1"
                                              :value "val1"}
                                             {:id #uuid "222e2222-b22b-22b2-b222-222222222222"
                                              :name "option2"
                                              :value "val2"}]}]})))

    (testing "valid group with all optional fields and complex parameters"
      (is (valid? apps/AppGroupJobView
                  {:id "step_full_group_complete"
                   :label "Complete Parameter Group"
                   :name "complete_params"
                   :description "A fully specified parameter group for testing"
                   :isVisible true
                   :step_number 3
                   :parameters [{:id "step_full_param_file"
                                 :type "FileInput"
                                 :name "--input-file"
                                 :label "Input File"
                                 :description "Primary input file"
                                 :required true
                                 :file_parameters {:format "fasta"
                                                   :file_info_type "SequenceFile"
                                                   :retain true}}
                                {:id "step_full_param_validated"
                                 :type "IntegerInput"
                                 :name "--max-size"
                                 :label "Maximum Size"
                                 :description "Maximum processing size"
                                 :defaultValue 100
                                 :required true
                                 :validators [{:type "IntAbove"
                                               :params [0]}
                                              {:type "IntBelow"
                                               :params [1000]}]}]})))

    (testing "valid group with step_number field required"
      (is (valid? apps/AppGroupJobView
                  {:id "step_001_group_001"
                   :label "Step 1 Parameters"
                   :step_number 1}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_999_group_999"
                   :label "Step 999 Parameters"
                   :step_number 999}))
      (is (valid? apps/AppGroupJobView
                  {:id "step_042_group_042"
                   :label "Step 42 Parameters"
                   :step_number 42})))

    (testing "invalid group - missing required fields"
      (is (not (valid? apps/AppGroupJobView {})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"})))
      (is (not (valid? apps/AppGroupJobView
                       {:label "Input Parameters"})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:label "Input Parameters"
                        :step_number 1}))))

    (testing "invalid group - id field rejects non-string types"
      (is (not (valid? apps/AppGroupJobView
                       {:id 123
                        :label "Input Parameters"
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id true
                        :label "Input Parameters"
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id #uuid "fedcba98-7654-3210-fedc-ba9876543210"
                        :label "Input Parameters"
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id nil
                        :label "Input Parameters"
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id [:step "123"]
                        :label "Input Parameters"
                        :step_number 1}))))

    (testing "invalid group - step_number rejects non-integer types"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"
                        :label "Input Parameters"
                        :step_number "1"})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"
                        :label "Input Parameters"
                        :step_number 1.5})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"
                        :label "Input Parameters"
                        :step_number true})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"
                        :label "Input Parameters"
                        :step_number nil})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_123_group_456"
                        :label "Input Parameters"
                        :step_number [1]}))))

    (testing "invalid group - incorrect types for optional fields"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_name"
                        :label "Settings"
                        :name 123
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_desc"
                        :label "Settings"
                        :description true
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_label"
                        :label 456
                        :step_number 1})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_visible"
                        :label "Settings"
                        :isVisible "not-a-boolean"
                        :step_number 1}))))

    (testing "invalid group - parameters field with wrong type"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_params1"
                        :label "Settings"
                        :step_number 1
                        :parameters "not-a-vector"})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_params2"
                        :label "Settings"
                        :step_number 1
                        :parameters {:not "a vector"}})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_group_params3"
                        :label "Settings"
                        :step_number 1
                        :parameters 123}))))

    (testing "invalid group - invalid nested AppParameterJobView data"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_invalid_nested_param1"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_001"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_invalid_nested_param2"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:type "FileInput"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_invalid_nested_param3"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id 123
                                      :type "FileInput"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_invalid_nested_param4"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id #uuid "abc12345-def6-7890-abcd-ef1234567890"
                                      :type "FileInput"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_invalid_nested_param5"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_001"
                                      :type 123}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_mixed_params"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_valid"
                                      :type "TextInput"
                                      :label "Valid"}
                                     {:id "step_001_param_invalid"
                                      :label "Missing type"}]}))))

    (testing "invalid group - invalid nested parameter with bad file_parameters"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_file_params"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_file"
                                      :type "FileInput"
                                      :file_parameters "not-a-map"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_file_params2"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_file"
                                      :type "FileInput"
                                      :file_parameters {:format 123}}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_file_params3"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_file"
                                      :type "FileInput"
                                      :file_parameters {:extra-field "not-allowed"}}]}))))

    (testing "invalid group - invalid nested parameter with bad arguments"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_arguments"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_select"
                                      :type "TextSelection"
                                      :arguments "not-a-vector"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_arguments2"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_select"
                                      :type "TextSelection"
                                      :arguments [{:name "option1"}]}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_arguments3"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_select"
                                      :type "TextSelection"
                                      :arguments [{:id "not-a-uuid"
                                                   :name "option1"}]}]}))))

    (testing "invalid group - invalid nested parameter with bad validators"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_validators"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_int"
                                      :type "IntegerInput"
                                      :validators "not-a-vector"}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_validators2"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_int"
                                      :type "IntegerInput"
                                      :validators [{}]}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_validators3"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_int"
                                      :type "IntegerInput"
                                      :validators [{:type "IntAbove"}]}]})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_bad_validators4"
                        :label "Settings"
                        :step_number 1
                        :parameters [{:id "step_001_param_int"
                                      :type "IntegerInput"
                                      :validators [{:type "IntAbove"
                                                    :params [0]
                                                    :extra-field "not-allowed"}]}]}))))

    (testing "invalid group - extra fields not allowed (closed map)"
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_extra_field"
                        :label "Settings"
                        :step_number 1
                        :extra-field "not-allowed"})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_extra_field2"
                        :label "Settings"
                        :step_number 1
                        :name "settings"
                        :description "Description"
                        :unexpected "also-not-allowed"})))
      (is (not (valid? apps/AppGroupJobView
                       {:id "step_extra_field3"
                        :label "Settings"
                        :step_number 1
                        :parameters []
                        :unknown_key "value"}))))))

(deftest test-AppJobView
  (testing "AppJobView validation"
    (testing "valid app job view with minimal required fields"
      (is (valid? apps/AppJobView
                  {:id "app-minimal-123"
                   :name "Minimal App"
                   :description "Minimal app description"
                   :version "1.0.0"
                   :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                   :app_type "DE"
                   :label "Minimal Label"
                   :deleted false
                   :disabled false})))

    (testing "valid app job view with all required and optional fields from AppBase"
      (is (valid? apps/AppJobView
                  {:id "app-complete-abc456"
                   :name "Complete App"
                   :description "Complete app with all fields"
                   :version "2.1.0"
                   :version_id #uuid "789a0123-c45d-67e8-f901-234567890abc"
                   :integration_date #inst "2024-01-15T10:30:00.000-00:00"
                   :edited_date #inst "2024-10-20T14:45:00.000-00:00"
                   :system_id "de"
                   :app_type "DE"
                   :label "Complete Label"
                   :deleted false
                   :disabled true})))

    (testing "valid app job view with AppVersionListing"
      (is (valid? apps/AppJobView
                  {:id "app-versions-xyz789"
                   :name "Versioned App"
                   :description "App with version listing"
                   :version "1.0.0"
                   :version_id #uuid "111e1111-a11a-11a1-a111-111111111111"
                   :app_type "External"
                   :label "Versioned Label"
                   :deleted false
                   :disabled false
                   :versions [{:version "1.0.0"
                               :version_id #uuid "111e1111-a11a-11a1-a111-111111111111"}
                              {:version "1.1.0"
                               :version_id #uuid "222e2222-b22b-22b2-b222-222222222222"}]})))

    (testing "valid app job view with AppLimitChecks"
      (is (valid? apps/AppJobView
                  {:id "app-limits-def123"
                   :name "Limited App"
                   :description "App with limit checks"
                   :version "1.0.0"
                   :version_id #uuid "333e3333-c33c-33c3-c333-333333333333"
                   :app_type "DE"
                   :label "Limited Label"
                   :deleted false
                   :disabled false
                   :limitChecks {:canRun true
                                 :results []}}))
      (is (valid? apps/AppJobView
                  {:id "app-limits-failed-ghi456"
                   :name "Failed Limit App"
                   :description "App with failed limit checks"
                   :version "1.0.0"
                   :version_id #uuid "444e4444-d44d-44d4-d444-444444444444"
                   :app_type "DE"
                   :label "Failed Limit Label"
                   :deleted false
                   :disabled false
                   :limitChecks {:canRun false
                                 :results [{:limitCheckID "concurrent-job-limit"
                                            :reasonCodes ["MAX_JOBS_EXCEEDED"]
                                            :additionalInfo {:current_jobs 10 :max_jobs 5}}]}})))

    (testing "valid app job view with debug flag"
      (is (valid? apps/AppJobView
                  {:id "app-debug-jkl789"
                   :name "Debug App"
                   :description "App with debug flag"
                   :version "1.0.0"
                   :version_id #uuid "555e5555-e55e-55e5-e555-555555555555"
                   :app_type "DE"
                   :label "Debug Label"
                   :deleted false
                   :disabled false
                   :debug true}))
      (is (valid? apps/AppJobView
                  {:id "app-no-debug-mno012"
                   :name "No Debug App"
                   :description "App without debug flag"
                   :version "1.0.0"
                   :version_id #uuid "666e6666-f66f-66f6-f666-666666666666"
                   :app_type "DE"
                   :label "No Debug Label"
                   :deleted false
                   :disabled false
                   :debug false})))

    (testing "valid app job view with requirements"
      (is (valid? apps/AppJobView
                  {:id "app-requirements-pqr345"
                   :name "Requirements App"
                   :description "App with resource requirements"
                   :version "1.0.0"
                   :version_id #uuid "777e7777-a77a-77a7-a777-777777777777"
                   :app_type "DE"
                   :label "Requirements Label"
                   :deleted false
                   :disabled false
                   :requirements [{:step_number 1
                                   :default_cpu_cores 1.0
                                   :default_max_cpu_cores 2.0
                                   :default_memory 536870912
                                   :default_disk_space 5368709120}
                                  {:step_number 2
                                   :default_cpu_cores 2.0
                                   :default_max_cpu_cores 4.0}]})))

    (testing "valid app job view with groups"
      (is (valid? apps/AppJobView
                  {:id "app-groups-stu678"
                   :name "Groups App"
                   :description "App with parameter groups"
                   :version "1.0.0"
                   :version_id #uuid "888e8888-b88b-88b8-b888-888888888888"
                   :app_type "DE"
                   :label "Groups Label"
                   :deleted false
                   :disabled false
                   :groups [{:id "step_001"
                             :label "Input Parameters"
                             :step_number 1}
                            {:id "step_002"
                             :label "Analysis Settings"
                             :step_number 2
                             :parameters [{:id "param_001"
                                           :type "TextInput"}
                                          {:id "param_002"
                                           :type "IntegerInput"}]}]})))

    (testing "valid app job view with all optional fields"
      (is (valid? apps/AppJobView
                  {:id "app-complete-vwx901"
                   :name "Fully Featured App"
                   :description "App with all possible fields"
                   :version "3.0.0"
                   :version_id #uuid "999e9999-c99c-99c9-c999-999999999999"
                   :integration_date #inst "2023-06-10T08:00:00.000-00:00"
                   :edited_date #inst "2024-11-01T16:20:00.000-00:00"
                   :system_id "condor"
                   :app_type "External"
                   :label "Fully Featured Label"
                   :deleted true
                   :disabled true
                   :debug true
                   :versions [{:version "2.0.0"
                               :version_id #uuid "aaa0aaaa-daaa-aaaa-daaa-aaaaaaaaaaaa"}
                              {:version "3.0.0"
                               :version_id #uuid "999e9999-c99c-99c9-c999-999999999999"}]
                   :limitChecks {:canRun true
                                 :results []}
                   :requirements [{:step_number 1
                                   :min_cpu_cores 1.0
                                   :max_cpu_cores 8.0
                                   :default_cpu_cores 2.0
                                   :default_max_cpu_cores 4.0
                                   :min_memory_limit 268435456
                                   :memory_limit 2147483648
                                   :default_memory 1073741824
                                   :min_disk_space 1073741824
                                   :default_disk_space 10737418240}]
                   :groups [{:id "analysis_step"
                             :label "Analysis Configuration"
                             :step_number 1
                             :name "config"
                             :description "Configure analysis parameters"
                             :parameters [{:id "input_file"
                                           :type "FileInput"
                                           :name "input"
                                           :label "Input File"
                                           :description "Select input data file"
                                           :required true
                                           :order 0}
                                          {:id "threshold"
                                           :type "DoubleInput"
                                           :name "threshold"
                                           :label "Threshold"
                                           :defaultValue 0.05
                                           :validators [{:type "DoubleAbove"
                                                         :params [0.0]}
                                                        {:type "DoubleBelow"
                                                         :params [1.0]}]}]}]})))

    (testing "invalid app job view - id field must be string, not UUID"
      (is (not (valid? apps/AppJobView
                       {:id #uuid "987e6543-e21b-32c1-b456-426614174000"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false}))))

    (testing "invalid app job view - id field rejects non-string types"
      (is (not (valid? apps/AppJobView
                       {:id 12345
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false})))
      (is (not (valid? apps/AppJobView
                       {:id true
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false}))))

    (testing "invalid app job view - missing required fields"
      (is (not (valid? apps/AppJobView {})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-name"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-desc"
                        :name "App"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-version"
                        :name "App"
                        :description "Description"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-version-id"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-app-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-label"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-deleted"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-missing-disabled"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false}))))

    (testing "invalid app job view - incorrect field types"
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-name-type"
                        :name 12345
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-version-type"
                        :name "App"
                        :description "Description"
                        :version 100
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-version-id-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id "not-a-uuid"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-deleted-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted "false"
                        :disabled false})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-disabled-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled "false"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-debug-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :debug "true"}))))

    (testing "invalid app job view - invalid nested AppStepResourceRequirements"
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-requirements"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :requirements "not-a-vector"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-requirements-missing-step"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :requirements [{:default_cpu_cores 1.0}]})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-requirements-wrong-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :requirements [{:step_number "not-an-int"
                                        :default_cpu_cores 1.0}]})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-requirements-extra-field"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :requirements [{:step_number 1
                                        :extra_field "not-allowed"}]}))))

    (testing "invalid app job view - invalid nested AppGroupJobView"
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-groups"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :groups "not-a-vector"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-groups-missing-label"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :groups [{:id "step_001"
                                  :step_number 1}]})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-groups-wrong-param-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :groups [{:id "step_001"
                                  :label "Settings"
                                  :step_number 1
                                  :parameters "not-a-vector"}]})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-groups-extra-field"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :groups [{:id "step_001"
                                  :label "Settings"
                                  :step_number 1
                                  :unknown "not-allowed"}]}))))

    (testing "invalid app job view - invalid nested limitChecks"
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-limitchecks"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :limitChecks "not-a-map"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-limitchecks-missing-canrun"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :limitChecks {:results []}})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-limitchecks-results-type"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :limitChecks {:canRun true
                                      :results "not-a-vector"}}))))

    (testing "invalid app job view - invalid versions list"
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-versions"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :versions "not-a-vector"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-bad-versions-missing-fields"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :versions [{:version "1.0.0"}]}))))

    (testing "invalid app job view - extra fields not allowed (closed map)"
      (is (not (valid? apps/AppJobView
                       {:id "app-extra-field"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :extra-field "not-allowed"})))
      (is (not (valid? apps/AppJobView
                       {:id "app-extra-field2"
                        :name "App"
                        :description "Description"
                        :version "1.0.0"
                        :version_id #uuid "456e7890-b12c-34d5-e678-901234567890"
                        :app_type "DE"
                        :label "Label"
                        :deleted false
                        :disabled false
                        :unknown_key "also-not-allowed"}))))))
