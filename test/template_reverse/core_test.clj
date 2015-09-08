(ns template-reverse.core-test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]))

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


(deftest key-frequency-test
  (testing "freq test"
    (is (= '([0 (\A)] [2 (\A)] [3 (\A)])
           (key-frequency "A" "AABAAC")))
    (is (= '([0 (\A)] [2 (\A)] [3 (\A)])
           (key-frequency "A" "AABAAC")))
    ))


(deftest find-key-frequency-test
  (testing "find freq test. See https://gist.github.com/sng2c/6077247"
    (is (= '()
           (find-key-frequency "A")))
    (is (= '([0 (\A)])
           (find-key-frequency "AA")))
    (is (= '([0 (\A)] [1 (\A)] [2 (\A)] [0 (\A \A)])
           (find-key-frequency "AAAA")))
    (is (= '(
              [1, (\A)], [3, (\A)], [5, (\A)],
              [0, (\A \B)], [2, (\A \B)], [4, (\A \B)],
              [1, (\A \B \A)],
              [0, (\A \B \A \B)])
           (find-key-frequency "ABABABAB")))
    (is (= '(
              [2, (\A)], [5, (\A)],
              [1, (\A \B)],
              [0, (\A \B \C)])
           (find-key-frequency "ABCABCAC")))
    (is (= '(
              [0, (\A)], [2, (\A)], [3, (\A)],
              [1, (\A \A)])
           (find-key-frequency "AABAAC")))
    ))

(find-key-frequency "AABAAC")