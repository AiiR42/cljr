(ns cljr.example.index
  (:require [cljr.example.concat :as ex-concat]
            [cljr.example.plus :as ex-plus]
            [cljr.example.button :as ex-button]
            [cljr.example.cookies :as ex-cookies]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em]))

; Global initialization

(defn init []
  (ex-concat/init)
  (ex-plus/init)
  (ex-cookies/init)
  (ex-button/init)
  )
(set! (.-onload js/window) #(em/wait-for-load (init)))
