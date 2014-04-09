(ns cljr.example.concat
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]))

(defn init []
  (let [a (wr/input-text-signal "concat1")
        b (wr/input-text-signal "concat2")
        result (r/lift str [a b] nil #(wr/to-input-text-func "concat-result" %))]
    nil))
