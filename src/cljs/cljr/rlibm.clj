(ns cljr.rlibm)

(defmacro defv [name]
  `(def ~name
    {:name ~(str name)
     :value (atom nil) ;; remove value while adding caching
     :function (atom nil)
     :args (atom nil)
     :view-function (atom nil)
     :relations (atom '())}))
