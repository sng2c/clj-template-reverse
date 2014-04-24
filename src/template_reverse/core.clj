(ns template-reverse.core
  (:import [difflib]) )

(declare diff-detect detect diff -diff -diff-index -set-wildcard -reduce-wildcard )

(defn- -diff [src dst] 
    (let [a (java.util.ArrayList. (vec src))  b (java.util.ArrayList. (vec dst)) ]
        (let [p (difflib.DiffUtils/diff a b)] 
            (map 
                (fn [itm] {
                    :type (keyword (str         (.getType itm))) 
                    :pos  (.getPosition      (.getOriginal itm))
                    :len  (.size (.getLines (.getOriginal itm)))
                     }) 
                (.getDeltas p)))))


(defn- -diff-index [col] 
    (flatten (map #(range (:pos %) (+ (:pos %) (:len %))) col)))


(defn- -reduce-wildcard [idxs]
    (map 
        first
        (filter #(not (and (apply = %) (= :* (first %) ) ) ) 
            (partition 2 1 (conj idxs :EOF) ) 
        )   
    )   
)


(defn- -set-wildcard [org idxs]
    (reduce (fn [a b] (assoc a b :*)  ) (vec org) idxs)
)


(defn diff 
    "Get diff between Seq."
    [src dst] 
    (let [g (filter #(not (= (:type %) :INSERT)) (-diff src dst))] 
        (-reduce-wildcard (-set-wildcard src (-diff-index g)))
    )      
)

(defn detect 
    "Get chunks with BEGIN,END and limited size by 'len' from 'd'."
    [d len]
    (map #(hash-map :BEGIN (take-last len (first %)), :END (take len (last %))) (partition 3 2 (partition-by #(= % :*) (flatten (conj (conj [:BOF] d) :EOF))) ))
)

(defn diff-detect
    "Get diff and get chunks"
    ([src dst] (diff-detect-len src dst 20))
    ([src dst len] (detect (diff src dst) len))
)
