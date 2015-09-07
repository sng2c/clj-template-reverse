(ns template-reverse.test
  (:require [clojure.test :refer :all]
            [template-reverse.core :refer :all]))




(key-frequency (seq "A") (seq "AABAAC"))
(key-frequency (seq "AAAA") (seq "AAAAAAAA"))
(find-key-frequency (seq "AAAA"))
(find-key-frequency (seq "AB1AB2AB"))

