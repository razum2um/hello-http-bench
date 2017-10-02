(ns hello-http-bench.core
  (:require [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str "Hello World " (System/currentTimeMillis))})

(defn -main
  [& args]
  (println "Listening on 8288")
  ;; Cannot max=1: Insufficient threads: max=1 < needed(acceptors=1 + selectors=4 + request=1)
  (jetty/run-jetty handler {:port 8288 :min-threads 1 :max-threads 6}))
