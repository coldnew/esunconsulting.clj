(ns coldnew.esunconsulting.core-test
  (:require [clojure.test :refer :all]
            [coldnew.esunconsulting :refer :all]))

(deftest get-page-num-test
  (testing "Page Num is integer type"
    (is (integer? (get-page-num)))))

(defn verify-report-map
  [coll]
  (and
   (contains? coll :title)
   (contains? coll :link)
   (contains? coll :date)))

(deftest get-report
  (testing "Report is hash-map in vector, contains keys: :title :link :date"
    (let [reoprt1 (get-report 1)]
      (is (every? true? (mapv verify-report-map report1)))
      )))
