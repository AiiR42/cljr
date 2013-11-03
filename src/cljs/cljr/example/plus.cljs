(ns cljr.example.plus
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em])
  (:use-macros [cljr.rlibm :only [defv]]))

; Relations

(defv a)
(defv b)
(defv result)

(r/set-rel result (fn [x y] (str (+ (js/parseInt x) (js/parseInt y)))) [a b])
 
; Implementation

(defn init []
  (wr/init a "plus1" true false)
  (wr/init b "plus2" true false)
  (wr/init result "plus-result" false true))
