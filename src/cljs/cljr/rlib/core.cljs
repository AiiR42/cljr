(ns cljr.rlib.core
  (:require [cljr.rlib.util :as u]))

(defn add-relations [signal signals-list]
  ;; Add signal relation to all signals in signals-list
  (doall (map #(swap! (:relations %) conj signal) 
              signals-list))
  nil)

(defn create-signal [function args & [get-func set-func]]
  (let [signal {:function function
                :args args
                :set-func set-func
                :get-func get-func
                :relations (atom '())
                :signal? true}]
    (add-relations signal args)
    signal))

(defn create-event-stream [function signal-args event-args & [do-func]]
  (let [event {:function function
               :args signal-args
               :do do-func
               :relations (atom '())
               :event? true}]
    (add-relations event event-args)
    event)) ;; do - eval on every event

(defn get-value [signal]
  (if-not (nil? (:function signal))
    (let [value (apply (:function signal) (map get-value (:args signal)))]
      (if-not (nil? (:set-func signal))
        ((:set-func signal) value))
      value)
    (if-not (nil? (:get-func signal))
      ((:get-func signal)))))

(defn update [signal]
  (get-value signal)
  (doall (map update @(:relations signal))))

(defn lift [function args & [get-func set-func]] ;; ([a] -> b) -> [Signal a] -> Signal b
  (create-signal function args get-func set-func))

(defn propogate-event [event data]
  (let [value (apply (:function event) (conj (vec (map get-value (:args event))) data))]
    (if-not (nil? (:do event))
      ((:do event) value))
    (doall 
      (map 
        (fn [relation] 
          (if (:event? relation) 
            (propogate-event relation value)
            (update relation))) 
        @(:relations event)))
  nil))

(defn hold [event]
  (let [cache (atom nil)
        cache-event (create-event-stream u/id-func [] [event] #(reset! cache %))
        signal (create-signal (fn [] @cache) [])]
    (add-relations signal [cache-event])
    signal))
