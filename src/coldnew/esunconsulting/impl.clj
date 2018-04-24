(ns coldnew.esunconsulting.impl
  (:require [clj-http.client :as http]
            [hickory.core    :as hickory]
            [hickory.select  :as s]
            [clojure.string  :as str]
            [coldnew.esunconsulting.codec :as codec]))

(def ^{:doc "Main url to get esunconsulting data"}
  url-main (codec/decode "aHR0cDovL3d3dy5lc3VuY29uc3VsdGluZy5jb20udHcvcmVwb3J0d2ViL3JhZjMuYXNwP0FDPVBBMDAwMTAwX1BBMDAwMTEwX1BBMDAwMTIwX1BBMDAwMTMwX1BBMDAwMTQwX1BBMDAwMTUwX1BBMDAwMTYwX1BBMDAwMTcwX1BBMDAwMTgwX1BBMDAwMTkwX1BBMDAwMjAwX1BBMDAwMjEw"))

(declare is-valid-page-num?)
(defn get-url
  "Get url according to page num."
  [num]
  {:pre [(integer? num) (is-valid-page-num? num)]}
  (format (codec/decode
           "aHR0cDovL3d3dy5lc3VuY29uc3VsdGluZy5jb20udHcvcmVwb3J0d2ViL1JBZjMuYXNwP1MxPSVkJlMyPSZTMz05OTkmUzQ9JlM1PSZTNj0mUzc9JlM4PSZBQz1QQTAwMDEwMF9QQTAwMDExMF9QQTAwMDEyMF9QQTAwMDEzMF9QQTAwMDE0MF9QQTAwMDE1MF9QQTAwMDE2MF9QQTAwMDE3MF9QQTAwMDE4MF9QQTAwMDE5MF9QQTAwMDIwMF9QQTAwMDIxMA==")
          num))

;; 

(defn fetch-html
  "Fetch the HTML source of link, return nil when failed."
  [url]
  (try (-> (http/get url {:insecure? true :as "MS950"})
           :body)
       (catch Exception e
         (throw (ex-info "fetch-html failed: " {:url url})))))

(defn html->hickory
  "Convert html data to hickory format"
  [data]
  (->> data
       hickory/parse
       hickory/as-hickory))

(defn fetch-hickory
  "Fetch url as HTML data and convert it to hickory format"
  [url]
  (-> url
      fetch-html
      html->hickory))

;; 

(defn get-page-num
  "Get how many pages do we have"
  []
  {:post [(integer? %)]}
  (->> (fetch-hickory url-main)
       (s/select
        (s/child
         (s/and (s/attr :value)
                (s/tag :option))))
       last :content first Integer/parseInt))

;; 


(defn is-valid-page-num?
  "Check if num is valid page num"
  [num]
  (and (>= num 1) (<= num (get-page-num))))

;; 

(defn find-link-pdf? [str]
  (->> (or str "")
       (re-find
        (re-pattern (codec/decode
                     "c0Noa1Bhc3NcKCcvcmVwb3J0d2ViL0RzcC5hc3BcP0Zsb3dObz1cZCsmQT0ocmVwb3J0LipcLlBERikoJ1wpOyk=")))))

(defn parse-title [hickory]
  (-> hickory :content first (str/replace #"\s* " "") str/trim))

(defn parse-link [hickory]
  (-> (str (codec/decode "aHR0cDovL3d3dy5lc3VuY29uc3VsdGluZy5jb20udHcvcmVwb3J0d2ViLw==")
           (-> hickory :attrs :onclick find-link-pdf? (nth 1)))
      str/trim (str/replace #"\s" "%20")))

(defn parse-date [hickory]
  (-> (re-find (re-pattern (codec/decode "aHR0cDovLy4qL3JlcG9ydC9SQS9cZCtfKFxkKylfLiouUERG"))
               (parse-link hickory))
      (nth 1)))

(defn parse-type [hickory]
  (let [link (parse-link hickory)]
    (cond
      (str/includes? link "訪談摘要")         :訪談摘要
      (str/includes? link "法說摘要")         :法說摘要
      (str/includes? link "複委託晨訊")       :複委託晨訊
      (str/includes? link "台股晨訊")         :台股晨訊
      (str/includes? link "美股晨訊")         :美股晨訊
      (str/includes? link "訪談報告")         :訪談報告
      (str/includes? link "期貨晨訊")         :期貨晨訊
      (str/includes? link "產業週報")         :產業週報
      (str/includes? link "台股策略週報")     :台股策略週報
      (str/includes? link "期貨市場投資週報") :期貨市場投資週報
      (str/includes? link "研究報告")         :研究報告
      (str/includes? link "總經雙週報")       :總經雙週報
      (str/includes? link "期貨市場投資週報") :期貨市場投資週報
      (str/includes? link "訪談摘要")         :訪談摘要
      (str/includes? link "法說摘要")         :法說摘要
      (str/includes? link "即時評析")         :即時評析
      (str/includes? link "總體經濟暨台股週報")  :總體經濟暨台股週報
      :else :未分類)))

(defn get-report
  "Get the page report according to page num."
  [num]
  (->> (fetch-hickory (get-url num))
       (s/select (s/child (s/tag :a)))
       (filter #(-> % :attrs :onclick find-link-pdf?))
       (mapv (fn [coll] {:title (parse-title coll)
                        :link  (parse-link coll)
                        :date  (parse-date coll)
                        :type  (parse-type coll)}))))
