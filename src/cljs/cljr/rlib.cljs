(ns cljr.rlib)

(defn get-value [cell]
  (apply @(:function cell) (map get-value @(:args cell))))

(defn update [cell]
  (doall (map update @(:relations cell)))
  (let [value (apply @(:function cell) (map get-value @(:args cell)))]
    (if-not (nil? value)
      (@(:set-function cell) value))))

(defn set-setter [cell set-function]
  (reset! (:set-function cell) set-function))

(defn set-rel [cell func args]
  (reset! (:function cell) func)
  (reset! (:args cell) args)
  (doall (map 
           #(swap! (:relations %) conj cell) 
           args)))
