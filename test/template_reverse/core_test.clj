(ns template-reverse.core-test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]))

(deftest diff-test
  (testing "diff 결과테스트"
    (is (= '(\A \B \C) (diff "ABC" "ABC")))
    (is (= '(\A \B :*) (diff "ABC" "AB")))
    (is (= '(\A \B :*) (diff "ABC" "ABD")))
    (is (= '(\A :* \C) (diff "ABC" "A1C")))
    (is (= '(\A :* \C \D) (diff "ABCD" "A123CDE")))
    (is (= '(\A :* \C :* \E) (diff "ABCDE" "A123CE")))
    ))



(deftest freq-test
  (testing "freq test"
    (is (= '({:off 2, :cnt 2} {:off 3, :cnt 2})
           (key-frequency (seq "A") (seq "AABAAC"))))

    (is (= '({:off 0, :cnt 2})
           (key-frequency (seq "AAAA") (seq "AAAAAAAA"))))

    (is (= '({:key (\A), :freq ({:off 0, :cnt 4} {:off 1, :cnt 2} {:off 2, :cnt 2})} {:key (\A \A), :freq ({:off 0, :cnt 2})})
           (find-key-frequency (seq "AAAA"))))

    (is (= '({:key (\A), :freq ({:off 2, :cnt 3} {:off 5, :cnt 2})} {:key (\A \B), :freq ({:off 1, :cnt 3} {:off 4, :cnt 2})})
           (find-key-frequency (seq "AB1AB2AB"))))
    ))

