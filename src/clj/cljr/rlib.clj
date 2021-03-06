(ns cljr.rlib)

(defmacro defv [name]
  `(def ~name
    {:name ~(str name)
     :value (atom nil) ;; remove value while adding caching
     :function (atom nil)
     :args (atom nil)
     :set-function (atom nil)
     :relations (atom '())}))

(defn get-value [cell]
  (if (not (nil? @(:function cell)))
    (apply @(:function cell) (map get-value @(:args cell)))
    @(:value cell)))

(defn update [cell]
  (if (not (nil? @(:function cell)))
    (do 
      (doall (map update @(:relations cell)))
      (@(:set-function cell) (apply @(:function cell) (map get-value @(:args cell)))))))

(defn set-setter [cell set-function]
  (reset! (:set-function cell) set-function))

(defn set-val [cell value]
  (if (nil? @(:function cell))
    (do
      (reset! (:value cell) value)
      (doall (map update @(:relations cell)))
      nil)))

(defn set-rel [cell func args]
  (reset! (:function cell) func)
  (reset! (:args cell) args)
  (doall (map 
           #(swap! (:relations %) conj cell) 
           args)))
