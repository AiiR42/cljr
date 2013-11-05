(ns cljr.rlibm)

(defmacro defv [name]
  `(def ~name
    {:name ~(str name)
     :function (atom nil)
     :args (atom nil)
     :set-function (atom nil)
     :relations (atom '())}))
