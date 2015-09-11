(ns template-reverse.core-test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]
            ))

(deftest diff-test
  (testing "diff test"
    (is (= '(\A \B \C) (diff "ABC" "ABC")))
    (is (= '(\A \B :*) (diff "ABC" "AB")))
    (is (= '(\A \B :*) (diff "ABC" "ABD")))
    (is (= '(\A :* \C) (diff "ABC" "A1C")))
    (is (= '(\A :* \C \D) (diff "ABCD" "A123CDE")))
    (is (= '(\A :* \C :* \E) (diff "ABCDE" "A123CE")))
    ))

(deftest diff-detect-test
  (testing "diff detect test"
    (is (= '({:BEFORE (:BOF \A), :AFTER (\C)} {:BEFORE (\C), :AFTER (\E :EOF)})
           (pattern "ABCDE" "A1C2E")))
    ))

; find in token

(def sample1 ["I" "am" "A" "," "So" "I" "am" "Happy"])
(def sample2 ["I" "am" "Babo" "," "So" "I" "am" "sad"])
(def pat (pattern sample1 sample2))
(def ext (extract pat ["I" "am" "sng2c" "," "So" "I" "am" "KHS"]))
(deftest extract-test
  (testing "Extract test"
    (is (= ['("sng2c") '("KHS")] (map :val (:match ext))))
    ))
