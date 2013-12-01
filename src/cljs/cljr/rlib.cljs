(ns cljr.rlib
  (:require [cljr.util :as u]))

(defn create-signal [function signals events]
  {:function function
   :args signals
   :to-view nil
   :relations (atom '())
   :signal? true
   :events events})

(defn create-event-stream [function signals events do-func]
  (let [event {:function function
               :args signals
               :relations (atom '())
               :event-stream? true
               :do do-func}]
    (add-relations event events)
    event)) ;; do - eval on every event

(defn add-relations [signal signals-list] ;; Signal a -> [Signal b] -> nil
  ;; Add signal relation to all signals in signals-list
  (doall (map #(swap! (:relations %) conj signal) 
              signals-list))
  nil)

(defn get-value [cell]
  (apply (:function cell) (map get-value (:args cell))))

(defn update [cell]
  (let [value (apply (:function cell) (map get-value (:args cell)))]
    (if-not (or (nil? value) (nil? (:to-view cell)))
      ((:to-view cell) value)))
  (doall (map update @(:relations cell))))

(defn to-view-raw [signal to-view-function]
  ;; All signal modifications must create new relations atom
  (assoc signal :to-view to-view-function :relations (atom '())))

(defn modify [signal apply-func] ;; Signal a -> (Signal a -> Signal a) -> Signal a
  (let [new-signal (apply-func (create-signal (fn [a] a) [signal] []))]
    (add-relations new-signal [signal])
    new-signal))

(defn id [signal] ;; Signal a -> Signal a
  (let [new-signal (lift (fn [a] a) [signal] [])]
    new-signal))

(defn constant [from-view-func to-view-func events] ;; a -> Signal a
  (to-view-raw (create-signal from-view-func nil events) to-view-func))

(defn lift [func signals events] ;; ([a] -> b) -> [Signal a] -> Signal b
  (let [signal (create-signal func signals events)]
    (add-relations signal signals)
    signal))

(defn to-view [signal to-view-function] ;; Signal a -> (a -> nil) -> Signal a
  (modify signal #(assoc % :to-view to-view-function)))

(defn event-change [signal event] ;; Signal a -> Event b -> Event a
  (let [new-signal (create-event-stream u/id-func [signal] [] nil)]
    (add-relations new-signal [event])
    new-signal))

(defn propogate-event [event-stream data]
  (let [value (apply (:function event-stream) (conj (vec (map get-value (:args event-stream))) data))]
    (if-not (nil? (:do event-stream))
      ((:do event-stream) value))
    (doall (map #(propogate-event % value) @(:relations event-stream))))
  nil)
