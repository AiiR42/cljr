(ns cljr.webrlib
  (:require [cljr.rlib :as r]))

(defn is-input? [selector]
  true) ;; TODO fix it

(defn is-output? [selector]
  true) ;; TODO fix it

(defn init-button [cell selector]
  (set! 
    (.-onclick 
      (.getElementById js/document selector)) 
    (fn [] 
      (r/set-val cell true)
      (r/set-val cell false))))

(defn init [cell selector input output] ;; TODO fix to use real selectors
  (if (and output (is-output? selector))
    (r/set-outfunc cell 
                   #(set! 
                     (.-value 
                       (.getElementById js/document selector))
                     %)))
  (if (and input (is-input? selector))
    (set! 
      (.-onkeyup 
        (.getElementById js/document selector)) 
      #(r/set-val cell 
        (str (.-value (.getElementById js/document selector)))))))
