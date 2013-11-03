(ns cljr.example.button
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em])
  (:use-macros [cljr.rlibm :only [defv]]))

; Relations

(defv login)
(defv password)
(defv submit)
(defv result)

(r/set-rel result (fn [login password submit] (if submit (if (= login password) "correct" "incorrect"))) [login password submit])
 
; Implementation

(defn init []
  (wr/init login "login" true false)
  (wr/init password "password" true false)
  (wr/init-button submit "login-button")
  (wr/init result "login-result" false true))
