(ns coldnew.esunconsulting
  (:require [coldnew.esunconsulting.impl :as impl]))

(defn get-page-num
  "Get how many pages do we have in esunconsulting report page."
  []
  {:post [(integer? %)]}
  (impl/get-page-num))

(defn get-report
  "Get the page report according to page num."
  ([] (get-report 1))
  ([num] {:pre [(integer? num) (impl/is-valid-page-num? num)]}
   (impl/get-report num)))
