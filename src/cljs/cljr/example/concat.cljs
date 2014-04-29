(ns cljr.example.concat
  (:require [cljr.rlib.core :as r]
            [cljr.rlib.web :as wr]
            [cljr.rlib.util :as u]))

(defn init []
  (let [a (wr/input-text-signal "concat1")
        b (wr/input-text-signal "concat2")
        result (wr/input-text-signal "concat-result" u/id-func 
                                                     [(r/lift str [a b])])]
    nil))
