(ns cljr.example.plus
  (:require [cljr.rlib :as r]
            [cljr.webrlib :as wr]
            [enfocus.core :as ef])
  (:require-macros [enfocus.macros :as em])
  (:use-macros [cljr.rlibm :only [defsignal]]))

(defn plus-function [x y]
  (str (+ (js/parseInt x) (js/parseInt y))))

(defn init []
  (let [a (wr/input-text-signal "plus1")
        b (wr/input-text-signal "plus2")
        result (wr/to-input-text (r/lift plus-function [a b]) "plus-result")]
    nil))
