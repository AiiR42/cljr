(ns cljr.webrlib
  (:require [cljr.rlib :as r]))

(defn is-input? [selector]
  true) ;; TODO fix it

(defn is-output? [selector]
  true) ;; TODO fix it

(defn get-cookie [cookie-name]
  (let [parts (.split (.-cookie js/document) (str cookie-name "="))]
    (if (= (.-length parts) 2)
      (.shift (.split (.pop parts) ";")))))

(defn init-cookie [cell cookie-name]
  (r/set-setter cell 
                 #(set! 
                   (.-cookie js/document)
                   (str cookie-name "=" %))))

(defn init-button [cell selector]
  ;;(r/set-rel cell #() [])
  (set! 
    (.-onclick 
      (.getElementById js/document selector)) 
    #(r/update cell)))

(defn init [cell selector input output] ;; TODO fix to use real selectors
  (if (and output (is-output? selector))
    (r/set-setter cell 
                   #(set! 
                     (.-value 
                       (.getElementById js/document selector))
                     %)))
  (if (and input (is-input? selector))
    (do
      (r/set-rel cell #(str (.-value (.getElementById js/document selector))) [])
      (set! 
        (.-onkeyup 
          (.getElementById js/document selector)) 
        #(r/update cell)))))
