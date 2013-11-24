(ns cljr.webrlib
  (:require [cljr.rlib :as r]))

; (defn is-input? [selector]
;   true) ;; TODO fix it

; (defn is-output? [selector]
;   true) ;; TODO fix it

; (defn get-cookie [cookie-name]
;   (let [parts (.split (.-cookie js/document) (str cookie-name "="))]
;     (if (= (.-length parts) 2)
;       (.shift (.split (.pop parts) ";")))))

; (defn init-cookie [cell cookie-name]
;   (r/set-setter cell 
;                  #(set! 
;                    (.-cookie js/document)
;                    (str cookie-name "=" %))))

; (defn init-button [cell selector] ;; TODO fix it when event streams will be added
;   (let [element (.getElementById js/document selector)]
;     (r/set-rel cell (fn [] (#{"true"} (.getAttribute (.getElementById js/document selector) "state"))) [])
;     (.setAttribute element "state" "false")
;     (set! 
;       (.-onmousedown element) 
;       #(.setAttribute element "state" "true"))
;     (set! 
;       (.-onmouseup element) 
;       (fn [] 
;         (r/update cell)
;         (.setAttribute element "state" "false")))))

; (defn init [cell selector input output] ;; TODO fix to use real selectors
;   (if (and output (is-output? selector))
;     (r/set-setter cell 
;                    #(set! 
;                      (.-value 
;                        (.getElementById js/document selector))
;                      %)))
;   (if (and input (is-input? selector))
;     (do
;       (r/set-rel cell #(str (.-value (.getElementById js/document selector))) [])
;       (set! 
;         (.-onkeyup 
;           (.getElementById js/document selector)) 
;         #(r/update cell)))))

(defn input-text-signal [id] ;; string -> Signal a
  (let [signal (r/constant #(str (.-value (.getElementById js/document id))) 
                           #(set! (.-value (.getElementById js/document id)) %))]
    (set! (.-onkeyup 
            (.getElementById js/document id)) 
          #(r/update signal))
    signal))

(defn to-input-text [signal id]
  (r/to-view signal #(set! (.-value (.getElementById js/document id)) %)))