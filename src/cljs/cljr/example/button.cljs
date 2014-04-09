(ns cljr.example.button
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [cljr.util :as u]))

(defn init []
  (let [a (wr/input-text-signal "login")
        b (wr/input-text-signal "password")
        submit (wr/button-event "login-button")
        change-result (r/hold (r/create-event-stream 
                        (fn [login pass event]
                          (if (= login pass) "correct" "incorrect")) 
                        [a b] 
                        [submit]))
        result (wr/input-text-signal "login-result" u/id-func [change-result])]
    nil))
