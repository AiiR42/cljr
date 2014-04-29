(ns cljr.rlib.general
  (:require [cljr.rlib.core :as core]
            [cljr.rlib.util :as u]))

(defn id [signal & [get-func set-func]] ;; Signal a -> Signal a
  (core/lift u/id-func [signal] get-func set-func))

(defn constant [& [get-func set-func]] ;; a -> Signal a
  (core/create-signal nil [] get-func set-func))