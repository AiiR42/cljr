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
                      [submit])
        fired-cookie (r/hold fire-cookie)
        simple-cookie (wr/cookie-signal "simple-cookie" u/id-func [fired-cookie])
        simple-cookie-text (wr/inner-text-signal "simple-cookie-text" u/id-func [simple-cookie])]
    nil))
