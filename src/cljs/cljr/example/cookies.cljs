(ns cljr.example.cookies
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [cljr.util :as u]))

(defn init []
  (let [cookie-value (wr/input-text-signal "cookie-value")
        submit (wr/button-event "fire-cookie")
        fire-cookie (r/create-event-stream 
                      (fn [cookie-value event]
                        cookie-value) 
                      [cookie-value] 
                      [submit] 
                      #(wr/set-cookie "simple-cookie" %))
        simple-cookie (wr/cookie-signal "simple-cookie" [fire-cookie])
        simple-cookie-text (wr/to-inner-text (r/lift u/id-func [simple-cookie] []) "simple-cookie-text")]
    nil))
