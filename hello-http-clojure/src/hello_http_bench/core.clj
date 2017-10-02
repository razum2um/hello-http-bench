(ns hello-http-bench.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.core.protocols :as protocols]
            [clojure.java.io :as io])
  (:gen-class))

(def hello (-> "Hello World "
               .getBytes
               io/input-stream
               (doto (.mark Integer/MAX_VALUE))))

(defrecord TimedResponse [time])

(extend-protocol protocols/StreamableResponseBody
  TimedResponse
  (write-body-to-stream [body _ ^java.io.OutputStream output-stream]
    (with-open [out output-stream]
      (io/copy hello out)
      (.write out (-> body :time str .getBytes)))
    (.reset hello)))

(defn handler [request]
  {:status 200
   :headers {}
   :body (->TimedResponse (System/currentTimeMillis))})

(defn -main
  [& args]
  (println "Listening on 8288")
  ;; Cannot max=1: Insufficient threads: max=1 < needed(acceptors=1 + selectors=4 + request=1)
  (jetty/run-jetty handler {:port 8288 :min-threads 1 :max-threads 6}))
