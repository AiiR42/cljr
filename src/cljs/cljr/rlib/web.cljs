(ns cljr.rlib.web
  (:require [cljr.rlib.util :as u]
            [cljr.rlib.core :as core]))

(defn get-cookie [cookie-name]
  (let [parts (.split (.-cookie js/document) (str cookie-name "="))]
    (if (= (.-length parts) 2)
      (.shift (.split (.pop parts) ";")))))

(defn set-cookie [cookie-name value]
  (set! 
    (.-cookie js/document)
    (str cookie-name "=" value)))

(defn input-text-signal [id & [function args]]
  (let [signal (core/create-signal function args #(str (.-value (.getElementById js/document id))) 
                                                 #(set! (.-value (.getElementById js/document id)) %))]
    (set! (.-onkeyup 
            (.getElementById js/document id)) 
          #(core/update signal))
    signal))

(defn inner-text-signal [id & [function args]]
  (let [signal (core/create-signal function args #(str (.-innerHTML (.getElementById js/document id))) 
                                                 #(set! (.-innerHTML (.getElementById js/document id)) %))]
    signal))

(defn button-event [id]
  (let [element (.getElementById js/document id)
        event (core/create-event-stream u/id-func [] [])]
    (set!
      (.-onclick element) 
      #(core/propogate-event event true))
    event))

(defn cookie-signal [cookie-name & [function args]]
  (core/create-signal function args #(get-cookie cookie-name) #(set-cookie cookie-name %)))
