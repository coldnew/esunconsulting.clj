(defproject coldnew/esunconsulting "0.1.0-SNAPSHOT"
  :description "Clojure library to fetch report link from esunconsulting."
  :url "https://github.com/coldnew/esunconsulting.clj"
  :license {:name "GNU Affero General Public License 3.0 (AGPL-3.0)"
            :url "https://www.gnu.org/licenses/agpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.3.0"]
                 [cheshire "5.6.3"]
                 [hickory "0.6.0"]]
  :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
  :profiles {:uberjar {:aot :all}})
