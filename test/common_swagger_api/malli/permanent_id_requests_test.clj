(ns common-swagger-api.malli.permanent-id-requests-test
  (:require [clojure.test :refer [deftest testing is]]
            [common-swagger-api.malli.permanent-id-requests :as pir]
            [malli.core :as malli]
            [malli.error :as me]))

(defn valid? [schema data]
  (malli/validate schema data))

(deftest test-PermanentID
  (testing "PermanentID validation"
    (testing "valid params"
      ;; Empty map is valid since permanent_id is optional
      (is (valid? pir/PermanentID {}))

      ;; Valid permanent_id with DOI example from schema
      (is (valid? pir/PermanentID
                  {:permanent_id "https://doi.org/10.1093/nar/gkv416"}))

      ;; Valid permanent_id with other string values
      (is (valid? pir/PermanentID
                  {:permanent_id "https://doi.org/10.5061/dryad.12345"}))

      (is (valid? pir/PermanentID
                  {:permanent_id "ark:/12345/abcdef"}))

      ;; Single character string is valid
      (is (valid? pir/PermanentID
                  {:permanent_id "a"}))

      ;; Empty string is valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentID
                  {:permanent_id ""}))

      ;; Whitespace-only string is valid with current schema
      (is (valid? pir/PermanentID
                  {:permanent_id "   "})))

    (testing "invalid params"
      ;; Non-string values not allowed
      (is (not (valid? pir/PermanentID
                       {:permanent_id 123})))

      (is (not (valid? pir/PermanentID
                       {:permanent_id nil})))

      (is (not (valid? pir/PermanentID
                       {:permanent_id true})))

      (is (not (valid? pir/PermanentID
                       {:permanent_id ["https://doi.org/10.1093/nar/gkv416"]})))

      (is (not (valid? pir/PermanentID
                       {:permanent_id {:url "https://doi.org/10.1093/nar/gkv416"}})))

      ;; Extra fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentID
                       {:permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentID
                       {:unknown-field "value"}))))))

(deftest test-PermanentIDRequestOrigPath
  (testing "PermanentIDRequestOrigPath validation"
    (testing "valid params"
      ;; Empty map is valid since original_path is optional
      (is (valid? pir/PermanentIDRequestOrigPath {}))

      ;; Valid original_path with file path examples
      (is (valid? pir/PermanentIDRequestOrigPath
                  {:original_path "/iplant/home/username/analyses/example-analysis/output"}))

      (is (valid? pir/PermanentIDRequestOrigPath
                  {:original_path "/iplant/home/shared/commons_repo/curated/dataset_v1.0"}))

      (is (valid? pir/PermanentIDRequestOrigPath
                  {:original_path "/iplant/home/researcher/data/2023/experiment_results"}))

      ;; Single character string is valid
      (is (valid? pir/PermanentIDRequestOrigPath
                  {:original_path "/"}))

      ;; Empty string is valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentIDRequestOrigPath
                  {:original_path ""}))

      ;; Whitespace-only string is valid with current schema
      (is (valid? pir/PermanentIDRequestOrigPath
                  {:original_path "   "})))

    (testing "invalid params"
      ;; Non-string values not allowed
      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:original_path 123})))

      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:original_path nil})))

      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:original_path true})))

      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:original_path ["/iplant/home/username/data"]})))

      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:original_path {:path "/iplant/home/username/data"}})))

      ;; Extra fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:original_path "/iplant/home/username/data"
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentIDRequestOrigPath
                       {:unknown-field "value"}))))))

(deftest test-PermanentIDRequest
  (testing "PermanentIDRequest validation"
    (testing "valid params"
      ;; Valid type with DOI example from schema
      (is (valid? pir/PermanentIDRequest
                  {:type "DOI"}))

      ;; Valid type with other ID types
      (is (valid? pir/PermanentIDRequest
                  {:type "ARK"}))

      (is (valid? pir/PermanentIDRequest
                  {:type "Handle"}))

      ;; Single character string is valid
      (is (valid? pir/PermanentIDRequest
                  {:type "A"}))

      ;; Empty string is valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentIDRequest
                  {:type ""}))

      ;; Whitespace-only string is valid with current schema
      (is (valid? pir/PermanentIDRequest
                  {:type "   "})))

    (testing "invalid params"
      ;; Empty map is invalid since type is required
      (is (not (valid? pir/PermanentIDRequest {})))

      ;; Map with missing type field is invalid
      (is (not (valid? pir/PermanentIDRequest
                       {:other-field "value"})))

      ;; Non-string values not allowed
      (is (not (valid? pir/PermanentIDRequest
                       {:type 123})))

      (is (not (valid? pir/PermanentIDRequest
                       {:type nil})))

      (is (not (valid? pir/PermanentIDRequest
                       {:type true})))

      (is (not (valid? pir/PermanentIDRequest
                       {:type ["DOI"]})))

      (is (not (valid? pir/PermanentIDRequest
                       {:type {:name "DOI"}})))

      ;; Extra fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentIDRequest
                       {:type "DOI"
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentIDRequest
                       {:type "DOI"
                        :unknown-field "value"}))))))

(deftest test-PermanentIDRequestBase
  (testing "PermanentIDRequestBase validation"
    (testing "valid params"
      ;; Map with all required fields only
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"}))

      ;; Map with all fields (required + both optional fields)
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :original_path "/iplant/home/username/analyses/example-analysis/output"}))

      ;; Map with required fields + :permanent_id only
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"}))

      ;; Map with required fields + :original_path only
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :original_path "/iplant/home/username/analyses/example-analysis/output"}))

      ;; Map with different valid type values
      (is (valid? pir/PermanentIDRequestBase
                  {:type "ARK"
                   :id #uuid "a1b2c3d4-e5f6-4a5b-8c9d-123456789abc"
                   :requested_by "johndoe"}))

      (is (valid? pir/PermanentIDRequestBase
                  {:type "Handle"
                   :id #uuid "00000000-0000-0000-0000-000000000000"
                   :requested_by "researcher"}))

      ;; Map with different valid permanent_id values
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :permanent_id "https://doi.org/10.5061/dryad.12345"}))

      (is (valid? pir/PermanentIDRequestBase
                  {:type "ARK"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :permanent_id "ark:/12345/abcdef"}))

      ;; Map with different valid original_path values
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :original_path "/iplant/home/shared/commons_repo/curated/dataset_v1.0"}))

      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :original_path "/iplant/home/researcher/data/2023/experiment_results"}))

      ;; Map with different valid requested_by values
      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "user123"}))

      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "admin@example.com"}))

      ;; Empty string is valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentIDRequestBase
                  {:type ""
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by ""}))

      (is (valid? pir/PermanentIDRequestBase
                  {:type "DOI"
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "janedoe"
                   :permanent_id ""
                   :original_path ""}))

      ;; Whitespace-only string is valid with current schema
      (is (valid? pir/PermanentIDRequestBase
                  {:type "   "
                   :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                   :requested_by "   "})))

    (testing "invalid params"
      ;; Empty map is invalid (missing all required fields)
      (is (not (valid? pir/PermanentIDRequestBase {})))

      ;; Map missing :type (required field)
      (is (not (valid? pir/PermanentIDRequestBase
                       {:id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      ;; Map missing :id (required field)
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :requested_by "janedoe"})))

      ;; Map missing :requested_by (required field)
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"})))

      ;; Map with wrong type for :type field
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type 123
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type nil
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type true
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type ["DOI"]
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type {:name "DOI"}
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      ;; Map with wrong type for :id field
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id 123
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id nil
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id true
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id [#uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"]
                        :requested_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id {:uuid #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"}
                        :requested_by "janedoe"})))

      ;; Map with wrong type for :requested_by field
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by 123})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by nil})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by true})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by ["janedoe"]})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by {:username "janedoe"}})))

      ;; Map with wrong type for :permanent_id optional field
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :permanent_id 123})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :permanent_id nil})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :permanent_id true})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :permanent_id ["https://doi.org/10.1093/nar/gkv416"]})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :permanent_id {:url "https://doi.org/10.1093/nar/gkv416"}})))

      ;; Map with wrong type for :original_path optional field
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :original_path 123})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :original_path nil})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :original_path true})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :original_path ["/iplant/home/username/data"]})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :original_path {:path "/iplant/home/username/data"}})))

      ;; Extra/unknown fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :unknown-field "value"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :original_path "/iplant/home/username/data"
                        :status "pending"})))

      (is (not (valid? pir/PermanentIDRequestBase
                       {:type "DOI"
                        :id #uuid "18c3c84d-38ca-45a6-96d4-38541bf764b3"
                        :requested_by "janedoe"
                        :additional "field"}))))))

(deftest test-PermanentIDRequestStatusComments
  (testing "PermanentIDRequestStatusComments validation"
    (testing "valid params"
      ;; Empty map is valid since comments is optional
      (is (valid? pir/PermanentIDRequestStatusComments {}))

      ;; Valid comments with example from schema
      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments "Please fill out the DataCite metadata template for the data set."}))

      ;; Valid comments with other string values
      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments "Request approved by curator team."}))

      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments "Additional information needed for DOI registration."}))

      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments "The dataset meets all requirements for permanent ID assignment."}))

      ;; Single character string is valid
      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments "A"}))

      ;; Empty string is valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments ""}))

      ;; Whitespace-only string is valid with current schema
      (is (valid? pir/PermanentIDRequestStatusComments
                  {:comments "   "})))

    (testing "invalid params"
      ;; Non-string values not allowed
      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:comments 123})))

      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:comments nil})))

      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:comments true})))

      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:comments ["Please fill out the DataCite metadata template for the data set."]})))

      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:comments {:text "Please fill out the DataCite metadata template for the data set."}})))

      ;; Extra fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:comments "Please fill out the DataCite metadata template for the data set."
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentIDRequestStatusComments
                       {:unknown-field "value"}))))))

(deftest test-PermanentIDRequestStatusUpdate
  (testing "PermanentIDRequestStatusUpdate validation"
    (testing "valid params"
      ;; Empty map is valid since all three fields (comments, permanent_id, status) are optional
      (is (valid? pir/PermanentIDRequestStatusUpdate {}))

      ;; Valid map with each individual field
      ;; Valid with :comments only
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "Please fill out the DataCite metadata template for the data set."}))

      ;; Valid with :permanent_id only
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:permanent_id "https://doi.org/10.1093/nar/gkv416"}))

      ;; Valid with :status only
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status "submitted"}))

      ;; Valid maps with combinations of two fields
      ;; Valid with :comments and :permanent_id
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "Please fill out the DataCite metadata template for the data set."
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"}))

      ;; Valid with :comments and :status
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "Please fill out the DataCite metadata template for the data set."
                   :status "submitted"}))

      ;; Valid with :permanent_id and :status
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :status "submitted"}))

      ;; Valid map with all three fields
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "Please fill out the DataCite metadata template for the data set."
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :status "submitted"}))

      ;; Valid with different comments values
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "Request approved by curator team."}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "Additional information needed for DOI registration."}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "The dataset meets all requirements for permanent ID assignment."}))

      ;; Valid with different permanent_id values
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:permanent_id "https://doi.org/10.5061/dryad.12345"}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:permanent_id "ark:/12345/abcdef"}))

      ;; Valid with different status values
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status "approved"}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status "pending"}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status "rejected"}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status "completed"}))

      ;; Single character strings are valid
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "A"
                   :permanent_id "B"
                   :status "C"}))

      ;; Empty strings are valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments ""}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:permanent_id ""}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status ""}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments ""
                   :permanent_id ""
                   :status ""}))

      ;; Whitespace-only strings are valid with current schema
      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "   "}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:permanent_id "   "}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:status "   "}))

      (is (valid? pir/PermanentIDRequestStatusUpdate
                  {:comments "   "
                   :permanent_id "   "
                   :status "   "})))

    (testing "invalid params"
      ;; Map with wrong type for :comments field
      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments 123})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments nil})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments true})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments ["Please fill out the DataCite metadata template for the data set."]})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments {:text "Please fill out the DataCite metadata template for the data set."}})))

      ;; Map with wrong type for :permanent_id field
      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id 123})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id nil})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id true})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id ["https://doi.org/10.1093/nar/gkv416"]})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id {:url "https://doi.org/10.1093/nar/gkv416"}})))

      ;; Map with wrong type for :status field
      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:status 123})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:status nil})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:status true})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:status ["submitted"]})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:status {:code "submitted"}})))

      ;; Map with multiple wrong types (combination tests)
      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments 123
                        :permanent_id nil
                        :status true})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments nil
                        :status 456})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id false
                        :status ["approved"]})))

      ;; Extra/unknown fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments "Please fill out the DataCite metadata template for the data set."
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :unknown-field "value"})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:status "submitted"
                        :additional "field"})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:comments "Please fill out the DataCite metadata template for the data set."
                        :permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :status "submitted"
                        :curator "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:unknown-field "value"})))

      (is (not (valid? pir/PermanentIDRequestStatusUpdate
                       {:extra "field"
                        :another "extra"}))))))

(deftest test-PermanentIDRequestStatus
  (testing "PermanentIDRequestStatus validation"
    (testing "valid params"
      ;; Map with only required fields (:status_date and :updated_by)
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"}))

      ;; Map with required fields + :permanent_id only
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"}))

      ;; Map with required fields + :comments only
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :comments "Please fill out the DataCite metadata template for the data set."}))

      ;; Map with required fields + :status only
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :status "approved"}))

      ;; Map with required fields + combinations of two optional fields
      ;; Required + :permanent_id + :comments
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :comments "Please fill out the DataCite metadata template for the data set."}))

      ;; Required + :permanent_id + :status
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :status "approved"}))

      ;; Required + :comments + :status
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :comments "Please fill out the DataCite metadata template for the data set."
                   :status "approved"}))

      ;; Map with all fields (required + all optional)
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :comments "Please fill out the DataCite metadata template for the data set."
                   :status "approved"}))

      ;; Map using example values from schema
      (is (valid? pir/PermanentIDRequestStatus
                  {:permanent_id "https://doi.org/10.1093/nar/gkv416"
                   :comments "Please fill out the DataCite metadata template for the data set."
                   :status "approved"
                   :status_date 1762296097000
                   :updated_by "janedoe"}))

      ;; Map with different valid permanent_id values
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :permanent_id "https://doi.org/10.5061/dryad.12345"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :permanent_id "ark:/12345/abcdef"}))

      ;; Map with different valid comments values
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :comments "Request approved by curator team."}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :comments "Additional information needed for DOI registration."}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :comments "The dataset meets all requirements for permanent ID assignment."}))

      ;; Map with different valid status values
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :status "submitted"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :status "pending"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :status "rejected"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "janedoe"
                   :status "completed"}))

      ;; Map with different valid status_date values
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 0
                   :updated_by "janedoe"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1609459200000
                   :updated_by "janedoe"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 9999999999999
                   :updated_by "janedoe"}))

      ;; Map with different valid updated_by values
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "johndoe"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "admin@example.com"}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "curator123"}))

      ;; Single character strings are valid for optional string fields
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "A"
                   :permanent_id "B"
                   :comments "C"
                   :status "D"}))

      ;; Empty strings are valid with current schema (uses :string, not NonBlankString)
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by ""
                   :permanent_id ""}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by ""
                   :comments ""}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by ""
                   :status ""}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by ""
                   :permanent_id ""
                   :comments ""
                   :status ""}))

      ;; Whitespace-only strings are valid with current schema
      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "   "
                   :permanent_id "   "}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "   "
                   :comments "   "}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "   "
                   :status "   "}))

      (is (valid? pir/PermanentIDRequestStatus
                  {:status_date 1762296097000
                   :updated_by "   "
                   :permanent_id "   "
                   :comments "   "
                   :status "   "})))

    (testing "invalid params"
      ;; Empty map is invalid (missing required fields)
      (is (not (valid? pir/PermanentIDRequestStatus {})))

      ;; Map missing :status_date (required field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:updated_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:updated_by "janedoe"
                        :permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :comments "Please fill out the DataCite metadata template for the data set."
                        :status "approved"})))

      ;; Map missing :updated_by (required field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :comments "Please fill out the DataCite metadata template for the data set."
                        :status "approved"})))

      ;; Map with wrong type for :permanent_id (optional field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :permanent_id 123})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :permanent_id nil})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :permanent_id true})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :permanent_id ["https://doi.org/10.1093/nar/gkv416"]})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :permanent_id {:url "https://doi.org/10.1093/nar/gkv416"}})))

      ;; Map with wrong type for :comments (optional field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :comments 123})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :comments nil})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :comments true})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :comments ["Please fill out the DataCite metadata template for the data set."]})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :comments {:text "Please fill out the DataCite metadata template for the data set."}})))

      ;; Map with wrong type for :status (optional field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :status 123})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :status nil})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :status true})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :status ["approved"]})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :status {:code "approved"}})))

      ;; Map with wrong type for :status_date (required field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date "1762296097000"
                        :updated_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date nil
                        :updated_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date true
                        :updated_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000.5
                        :updated_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date [1762296097000]
                        :updated_by "janedoe"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date {:timestamp 1762296097000}
                        :updated_by "janedoe"})))

      ;; Map with wrong type for :updated_by (required field)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by 123})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by nil})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by true})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by ["janedoe"]})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by {:username "janedoe"}})))

      ;; Map with multiple wrong types (combination tests)
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date "invalid"
                        :updated_by 123
                        :permanent_id nil
                        :comments true
                        :status ["approved"]})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date nil
                        :updated_by nil})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date true
                        :updated_by false})))

      ;; Extra/unknown fields not allowed (schema has {:closed true})
      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :extra-field "not allowed"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :unknown-field "value"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :permanent_id "https://doi.org/10.1093/nar/gkv416"
                        :comments "Please fill out the DataCite metadata template for the data set."
                        :status "approved"
                        :curator "admin"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :additional "field"})))

      (is (not (valid? pir/PermanentIDRequestStatus
                       {:status_date 1762296097000
                        :updated_by "janedoe"
                        :extra "field"
                        :another "extra"}))))))
