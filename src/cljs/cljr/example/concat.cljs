(ns cljr.example.concat
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em])
  (:use-macros [cljr.rlibm :only [defsignal]]))

(defn init []
  (let [a (wr/input-text-signal "concat1")
        b (wr/input-text-signal "concat2")
        result (wr/to-input-text (r/lift str [a b]) "concat-result")]
    nil))
