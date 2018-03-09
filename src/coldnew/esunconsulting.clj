(ns coldnew.esunconsulting
  (:require [coldnew.esunconsulting.impl :as impl]))

(defn get-page-num
  "Get how many pages do we have in esunconsulting report page."
  []
  {:post [(integer? %)]}
  (impl/get-page-num))

(defn get-report
  "Get the page report according to page num.

The result is build in vector contains hash-map, like following:

  [{:title \"AAA\" :link \"http:/bbb.pdf\" :date \"20180309\"}
   {:title \"CCC\" :link \"http:/ddd.pdf\" :date \"20180309\"}]"
  ([] (get-report 1))
  ([num] {:pre [(integer? num) (impl/is-valid-page-num? num)]}
   (impl/get-report num)))
