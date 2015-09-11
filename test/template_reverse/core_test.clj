(ns template-reverse.core-test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]
            [clojure.core.match :refer [match]]
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
           (diff-detect "ABCDE" "A1C2E")))
    ))

; find in token
(def sam1 ["I" "am" "A" "," "So" "I" "am" "Happy"])
(def sam2 ["I" "am" "Babo" "," "So" "I" "was" "sad"])
(def df (diff-detect sam1 sam2))
(extract df ["I" "am" "KHS" "," "So" "I" "am" "HS"])

(deftest key-frequency-test
  (testing "freq test"
    (is (= '([(\A) 0 2] [(\A) 2 2] [(\A) 3 2])
           (key-frequency "A" "AABAAC")))
    ))


(deftest find-key-frequency-test
  (testing "find freq test. See https://gist.github.com/sng2c/6077247"
    (is (= '()
           (find-key-frequency "A")))
    (is (= '([(\A) 0 2])
           (find-key-frequency "AA")))
    (is (= '(
              [(\A) 0 4] [(\A) 1 2] [(\A) 2 2]
              [(\A \A) 0 2])
           (find-key-frequency "AAAA")))
    (is (= '(
              [(\A) 1 4], [(\A) 3 2], [(\A) 5 2],
              [(\A \B) 0 4], [(\A \B) 2 2], [(\A \B) 4 2],
              [(\A \B \A) 1 2],
              [(\A \B \A \B) 0 2])
           (find-key-frequency "ABABABAB")))
    (is (= '(
              [(\A) 2 3], [(\A) 5 2],
              [(\A \B) 1 2],
              [(\A \B \C) 0 2])
           (find-key-frequency "ABCABCAC")))
    (is (= '(
              [(\A) 0 2], [(\A) 2 2], [(\A) 3 2],
              [(\A \A) 1 2])
           (find-key-frequency "AABAAC")))
    ))

(find-key-frequency "AABAAC")