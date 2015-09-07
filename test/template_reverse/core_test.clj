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

(deftest freq-test
  (testing "freq test"
    (is (= '(
              {:off 2, :cnt 2}                              ; '(A ? ? A ? ?)
              {:off 3, :cnt 2})                             ; '(A ? ? ? A ?)
           (key-frequency "A" "AABAAC")))

    (is (= '(
              {:off 0, :cnt 2})                             ; '(AAAA AAAA)
           (key-frequency "AAAA" "AAAAAAAA")))

    (is (= '(
              {
               :key  (\A),
               :freq (
                       {:off 0, :cnt 4}                     ; '(A A A A) 4 repetitions by offset 0
                       {:off 1, :cnt 2}                     ; '(A ? A ?) 2 repetitions by offset 1
                       {:off 2, :cnt 2})}                   ; '(A ? ? A) 2 repetitions by offset 2
              {:key  (\A \A),
               :freq (
                       {:off 0, :cnt 2})})                  ; '(AA AA) 2 repetitions by offset 0
           (find-key-frequency "AAAA")))

    (is (= '(
              {:key  (\A),
               :freq (
                       {:off 2, :cnt 3}
                       {:off 5, :cnt 2})}
              {:key  (\A \B),
               :freq (
                       {:off 1, :cnt 3}                     ; '(AB ? AB ? AB)
                       {:off 4, :cnt 2})})                  ; '(AB ? ?? ? AB)
           (find-key-frequency "AB1AB2AB")))
    ))

