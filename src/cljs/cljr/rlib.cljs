(ns cljr.rlib)

(defn get-value [cell]
  (if (not (nil? @(:function cell)))
    (apply @(:function cell) (map get-value @(:args cell)))
    @(:value cell)))

(defn update [cell]
  (if (not (nil? @(:function cell)))
    (do 
      (doall (map update @(:relations cell)))
      (let [value (apply @(:function cell) (map get-value @(:args cell)))]
        (if-not (nil? value)
          (@(:view-function cell) value))))))

(defn set-outfunc [cell view-function]
  (reset! (:view-function cell) view-function))

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
