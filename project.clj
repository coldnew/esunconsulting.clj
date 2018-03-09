(defproject coldnew/esunconsulting "0.1.0"
  :description "Clojure library to fetch report link from esunconsulting."
  :url "https://github.com/coldnew/esunconsulting.clj"
  :license {:name "GNU Affero General Public License 3.0 (AGPL-3.0)"
            :url "https://www.gnu.org/licenses/agpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.8.0"]
                 [cheshire "5.8.0"]
                 [hickory "0.7.1"]]

  :plugins [[lein-codox "0.9.4"]]

  :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
  :profiles {:uberjar {:aot :all}}
  :codox {:source-uri "https://github.com/coldnew/esunconsulting.clj/blob/master/{filepath}#L{line}"})
