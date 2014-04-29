(ns cljr.example.plus
  (:require [cljr.rlib.core :as r]
            [cljr.rlib.web :as wr]
            [cljr.rlib.util :as u]))

(defn plus-function [x y]
  (str (+ (js/parseInt x) (js/parseInt y))))

(defn init []
  (let [a (wr/input-text-signal "plus1")
        b (wr/input-text-signal "plus2")
        result (wr/input-text-signal "plus-result" u/id-func [(r/lift plus-function [a b])])]
    nil))
