(ns cljr.server
  (:use compojure.core)
  (:require [ring.adapter.jetty :as jetty]
            [compojure.route :as route]
            [noir.util.middleware :as noir]
            [noir.response :as resp]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/html/index.html"))
  ;; (POST "/login" [login password] (do-login login password))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> [app-routes]
      noir/app-handler
      noir/war-handler))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000}))