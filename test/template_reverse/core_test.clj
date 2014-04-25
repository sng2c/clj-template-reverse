(ns template-reverse.core-test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]))

(deftest diff-test
  (testing "diff 결과테스트"
    (is (= (diff "ABC" "ABC") '(\A \B \C) ))
    (is (= (diff "ABC" "AB") '(\A \B :*) ))
    (is (= (diff "ABC" "ABD") '(\A \B :*) ))
    (is (= (diff "ABC" "A1C") '(\A :* \C) ))
    (is (= (diff "ABCD" "A123CDE") '(\A :* \C \D) ))
    (is (= (diff "ABCDE" "A123CE") '(\A :* \C :* \E) ))
    ))