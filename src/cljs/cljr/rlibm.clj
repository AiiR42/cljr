(ns cljr.rlibm)

(defmacro defsignal [name]
  `(def ~name
    {:name ~(str name)
     :function (atom nil)
     :args (atom nil)
     :set-function (atom nil)
     :relations (atom '())}))

(defmacro defevent [name]
  `(def ~name
    {:name ~(str name)
     :relations (atom '())}))
