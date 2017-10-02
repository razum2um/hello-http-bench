(defproject hello-http-bench "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [malabarba/lazy-map "1.3"]
                 [ring "1.6.2"]]
  :global-vars {*warn-on-reflection* true}
  :main ^:skip-aot hello-http-bench.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
