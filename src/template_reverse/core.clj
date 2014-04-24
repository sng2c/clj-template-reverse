(ns template-reverse.core
  (:import [difflib])
  (:gen-class))



(defn -main []
  (println "Hello, World!"))

(declare detect -detect diff -diff -diff2 -set-wildcard -reduce-wildcard )


(defn detect [col1 col2 len] 
    (-detect (diff col1 col2) len)
)

(defn -detect [d len]
    (map #(hash-map :left (take-last len (first %)), :right (take len (last %))) (partition 3 2 (partition-by #(= % :*) (flatten (conj (conj [:BOF] d) :EOF))) ))
)

(defn diff [col1 col2] 
    
    (let [g (filter #(not (= (:type %) :INSERT)) (-diff col1 col2))] 
        (-reduce-wildcard (-set-wildcard col1 (-diff2 g)))
    )

    
)

(defn -diff2 [col] 
    (flatten (map #(range (:pos %) (+ (:pos %) (:len %))) col))
)

(defn -diff [col1 col2] 
    (let [a (java.util.ArrayList. (vec col1))  b (java.util.ArrayList. (vec col2)) ]
        (let [p (difflib.DiffUtils/diff a b)] 
        
            (map 
                (fn [itm] {
                    :type (keyword (str (.getType itm))) 
                    :pos (.getPosition (.getOriginal itm))
                    :len (.size (.getLines (.getOriginal itm)))
                     }) 
                (.getDeltas p)
            )
        
        )
    
    )
)

(defn -reduce-wildcard [col]
    (map 
        first
        (filter #(not (and (apply = %) (= :* (first %) ) ) ) 
            (partition 2 1 (conj col :EOF) ) 
        )   
    )   
)


(defn -set-wildcard [org idxs]
    (reduce (fn [a b] (assoc a b :*)  ) (vec org) idxs)
)
