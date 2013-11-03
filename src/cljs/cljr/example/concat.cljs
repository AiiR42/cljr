(ns cljr.example.concat
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em])
  (:use-macros [cljr.rlibm :only [defv]]))

; Relations

(defv a)
(defv b)
(defv result)

(r/set-rel result str [a b])
 
; Implementation

(defn init []
  (wr/init a "concat1" true false)
  (wr/init b "concat2" true false)
  (wr/init result "concat-result" false true))
