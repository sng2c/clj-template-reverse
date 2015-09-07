(ns template-reverse.core
  (:import [difflib]))

(declare diff-detect detect diff -diff -diff-index -set-wildcard -reduce-wildcard)

(defn- -diff [src dst]
  (let [a (java.util.ArrayList. (vec src)) b (java.util.ArrayList. (vec dst))]
    (let [p (difflib.DiffUtils/diff a b)]
      (map
        #(hash-map
          :type (keyword (str (.getType %)))
          :pos (.getPosition (.getOriginal %))
          :len (.size (.getLines (.getOriginal %))))
        (.getDeltas p)))))


(defn- -diff-index [col]
  (flatten
    (map #(range (:pos %) (+ (:pos %) (:len %)))
         col)))


(defn- -set-wildcard [org idxs]
  (reduce
    (fn [a b] (assoc a b :*))
    (vec org) idxs))

(defn- -reduce-wildcard [idxs]
  (map
    first
    (filter
      #(not (and (apply = %) (= :* (first %))))
      (partition 2 1 (conj idxs :EOF)))))

(defn diff
  "Get diff between Seq."
  [src dst]
  (let [g (filter #(not (= (:type %) :INSERT)) (-diff src dst))]
    (-reduce-wildcard (-set-wildcard src (-diff-index g)))))

(defn detect
  "Get chunks with BEGIN,END and limited size by 'len' from 'd'."
  [d len]
  (map
    #(hash-map
      :BEGIN (take-last len (first %))
      :END (take len (last %)))
    (partition 3 2
               (partition-by #(= % :*)
                             (flatten [:BOF d :EOF])))))

(defn diff-detect
  "Get diff and get chunks"
  ([src dst] (detect (diff src dst) 20))
  ([src dst len] (detect (diff src dst) len)))




(defn- count-contains [src coll] (count (filter #{src} coll)))
(defn- get-count-item
  [key coll n]
  (let [keylen (count key)
        parts (partition keylen (+ keylen n) coll)
        cnt (count-contains key parts)]
    (do
      (print n)
      {:off n, :all (= cnt (count parts)), :cnt cnt}))
  )

(defn key-frequency [key coll]
  (let [nums (range (inc (- (count coll) (count key))))]
    (for [n nums, :let [item (get-count-item key coll n)] :when (and (:all item) (< 1 (:cnt item)))]
      (dissoc item :all)
      )))


(defn find-key-frequency [coll]
  (let [lastn (quot (count coll) 2)
        nums (range 1 (inc lastn))]
    (for [n nums :let [key (take n coll), freq (key-frequency key coll)] :when (not (empty? freq))]
      (do
        {:key key, :freq freq}))))


