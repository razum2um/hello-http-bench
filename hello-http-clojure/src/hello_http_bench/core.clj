(ns hello-http-bench.core
  (:require [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str "Hello World " (System/currentTimeMillis))})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Listening on 8288")
  (jetty/run-jetty handler {:port 8288}))
