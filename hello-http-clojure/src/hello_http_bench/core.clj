(ns hello-http-bench.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.core.protocols :as protocols]
            [clojure.java.io :as io])
  (:gen-class))

(def prefix "Hello World ")

;; naive, straight from wiki https://github.com/ring-clojure/ring/wiki/Getting-Started
(defn tutorial [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str prefix (System/currentTimeMillis))})

;; get rid of regexp call to detect charset
;; https://github.com/ring-clojure/ring/blob/1.6.2/ring-core/src/ring/util/response.clj#L210L215
(defn nocontenttype [request]
  {:status 200
   :headers {}
   :body (str prefix (System/currentTimeMillis))})

;; don't allocate full body string
(def hello-input-stream (-> prefix .getBytes io/input-stream (doto (.mark Integer/MAX_VALUE))))
(defrecord TimedResponse [time])
(extend-protocol protocols/StreamableResponseBody
  TimedResponse
  (write-body-to-stream [body _ ^java.io.OutputStream output-stream]
    (with-open [out output-stream]
      (io/copy hello-input-stream out)
      (.write out (-> body :time str .getBytes)))
    (.reset hello-input-stream)))
(defn cache-input-stream [request]
  {:status 200
   :headers {}
   :body (->TimedResponse (System/currentTimeMillis))})

(defn -main
  [& args]
  ;; Cannot max=1: Insufficient threads: max=1 < needed(acceptors=1 + selectors=4 + request=1)
  (let [app (or (some-> args first (->> (str "hello-http-bench.core/")) symbol resolve)
                cache-input-stream)]
    (println "Listening" app "on 8288")
    (jetty/run-jetty app {:port 8288 :min-threads 1 :max-threads 6})))
