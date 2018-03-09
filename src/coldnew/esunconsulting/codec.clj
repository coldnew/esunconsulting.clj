(ns coldnew.esunconsulting.codec
  (:import java.util.Base64))

(defn encode
  "Encode string S."
  [s]
  (->> (.getBytes s)
       (.encodeToString (Base64/getEncoder))))

(defn decode
  "Decode string S."
  [s]
  (->> (.getBytes s)
       (.decode (Base64/getDecoder))
       String.))
