(ns cljr.example.button
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em])
  (:use-macros [cljr.rlibm :only [defsignal defevent]]))

(defn init []
  (let [a (wr/input-text-signal "login")
        b (wr/input-text-signal "password")
        submit (wr/button-event "login-button")
        result (wr/input-text-signal "login-result" 
                 [(r/create-event-stream 
                    (fn [login pass event]
                      (if (= login pass) "correct" "incorrect")) 
                    [a b] 
                    [submit] 
                    #(wr/set-value "login-result" %))])]
    nil))
