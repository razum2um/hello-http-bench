(ns hello-http-bench.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.core.protocols :as protocols]
            [ring.util.servlet :as servlet]
            [lazy-map.core :as lazy-map]
            [clojure.java.io :as io])
  (:import [javax.servlet.http HttpServlet HttpServletRequest HttpServletResponse])
  (:gen-class))

(def #^String prefix "Hello World ")

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
(defrecord TimedResponse [time])
(extend-protocol protocols/StreamableResponseBody
  TimedResponse
  (write-body-to-stream [body _ ^java.io.OutputStream output-stream]
    (with-open [out output-stream]
      (.write out (-> ^String prefix .getBytes))
      (.write out (-> body :time str .getBytes)))))

(defn handler [request]
  {:status 200
   :headers {}
   :body (->TimedResponse (System/currentTimeMillis))})

;; with lazy-maps instead of headers
(defmacro with-lazy-ring-request [& body]
  `(with-redefs [servlet/build-request-map (fn [^HttpServletRequest request#]
                                             (lazy-map/lazy-map
                                              {:server-port        (.getServerPort request#)
                                               :server-name        (.getServerName request#)
                                               :remote-addr        (.getRemoteAddr request#)
                                               :uri                (.getRequestURI request#)
                                               :query-string       (.getQueryString request#)
                                               :scheme             (keyword (.getScheme request#))
                                               :request-method     (keyword (.toLowerCase (.getMethod request#)
                                                                                           java.util.Locale/ENGLISH))
                                               :protocol           (.getProtocol request#)
                                               :headers            (#'servlet/get-headers request#)
                                               :content-type       (.getContentType request#)
                                               :content-length     (#'servlet/get-content-length request#)
                                               :character-encoding (.getCharacterEncoding request#)
                                               :ssl-client-cert    (#'servlet/get-client-cert request#)
                                               :body               (.getInputStream request#)}))
                 ;; skip legacy
                 servlet/merge-servlet-keys (fn [request-map#
                                                 ^HttpServlet servlet#
                                                 ^HttpServletRequest request#
                                                 ^HttpServletResponse response#]
                                              request-map#)]
     ~@body))

(defn -main
  [& args]
  ;; Cannot max=1: Insufficient threads: max=1 < needed(acceptors=1 + selectors=4 + request=1)
  (some-> "ring.util.servlet/build-request-map" symbol resolve println)

  (if-let [app (some-> args first (->> (str "hello-http-bench.core/")) symbol resolve)]
    (do (println "Listening" app "on 8288")
        (jetty/run-jetty app {:port 8288 :min-threads 1 :max-threads 6}))
    (do (println "Listening using handler & lazy-ring on 8288")
        (with-lazy-ring-request (jetty/run-jetty #'handler {:port 8288 :min-threads 1 :max-threads 6})))))
