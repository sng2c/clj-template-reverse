(ns template-reverse.freq-test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]
            ))

(deftest find-key-frequency-test
  (testing "find freq test. See https://gist.github.com/sng2c/6077247"
    (is (= '()
           (repetition "A")))
    (is (= '([(\A) 0 2])
           (repetition "AA")))
    (is (= '(
              [(\A) 0 4] [(\A) 1 2] [(\A) 2 2]
              [(\A \A) 0 2])
           (repetition "AAAA")))
    (is (= '(
              [(\A) 1 4], [(\A) 3 2], [(\A) 5 2],
              [(\A \B) 0 4], [(\A \B) 2 2], [(\A \B) 4 2],
              [(\A \B \A) 1 2],
              [(\A \B \A \B) 0 2])
           (repetition "ABABABAB")))
    (is (= '(
              [(\A) 2 3], [(\A) 5 2],
              [(\A \B) 1 2],
              [(\A \B \C) 0 2])
           (repetition "ABCABCAC")))
    (is (= '(
              [(\A) 0 2], [(\A) 2 2], [(\A) 3 2],
              [(\A \A) 1 2])
           (repetition "AABAAC")))
    ))

