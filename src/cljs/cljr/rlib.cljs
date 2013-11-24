(ns cljr.rlib)

(defn create-signal [function signals]
  {:function function
   :args signals
   :to-view nil
   :relations (atom '())})

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

(defn apply-mods [signal mods] ;; T -> [(T -> T)] -> T
  (if (not (empty? mods))
    (recur ((first mods) signal) (rest mods))
    signal))

(defn to-view-raw [signal to-view-function]
  ;; All signal modifications must create new relations atom 
  (assoc signal :to-view to-view-function :relations (atom '())))


  ; (let [new-signal (to-view-raw signal to-view-function)]
  ;   (add-relations new-signal [signal])
  ;   new-signal))

(defn modify [signal apply-func] ;; Signal a -> (Signal a -> Signal a) -> Signal a
  (let [new-signal (apply-func (create-signal (fn [a] a) [signal]))]
    (add-relations new-signal [signal])
    new-signal))

(defn id [signal] ;; Signal a -> Signal a
  (let [new-signal (lift (fn [a] a) [signal])]
    new-signal))

(defn constant [from-view-func to-view-func] ;; a -> Signal a
  (to-view-raw (create-signal from-view-func nil) to-view-func))

(defn lift [func signals] ;; ([a] -> b) -> [Signal a] -> [(Signal a -> Signal a)] -> Signal b
  (let [signal (create-signal func signals)]
    (add-relations signal signals)
    signal))

(defn to-view [signal to-view-function] ;; Signal a -> (a -> nil) -> Signal a
  (modify signal #(assoc % :to-view to-view-function)))