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

