(ns sudoku.rules-test
  (:require [clojure.test :refer :all]
            [sudoku.rules :refer :all]
            [sudoku.examples :as ex]))

(deftest broken
  (testing "Broken board should not solve."
    (is (not (valid-board? (solve ex/broken))))))

(deftest easy
  (testing "Easy board."
    (is (valid-board? (solve ex/easy)))))

(deftest hard
  (testing "Hard board."
    (is (valid-board? (solve ex/hard)))))

(deftest hardest
  (testing "A harder board."
    (is (valid-board? (solve ex/hardest)))))

(deftest brutal
  (testing "Another harder board."
    (is (valid-board? (solve ex/brutal)))))

(deftest absurd
  (testing "An absurdly hard board."
    (is (valid-board? (solve ex/absurd)))))

(deftest al-escargot
  (testing "The Al Escargot board."
    (is (valid-board? (solve ex/al-escargot)))))

(deftest broken-solution
  (testing "Broken board should not solve."
    (is (nil? (solve ex/broken)))))

(deftest easy-solution
  (testing "Easy board."
    (is (= (solve ex/easy)
           [[5 3 4 6 7 8 9 1 2]
            [6 7 2 1 9 5 3 4 8]
            [1 9 8 3 4 2 5 6 7]
            [8 5 9 7 6 1 4 2 3]
            [4 2 6 8 5 3 7 9 1]
            [7 1 3 9 2 4 8 5 6]
            [9 6 1 5 3 7 2 8 4]
            [2 8 7 4 1 9 6 3 5]
            [3 4 5 2 8 6 1 7 9]]))))

(deftest hard-solution
  (testing "Hard board."
    (is (= (solve ex/hard)
           [[6 3 8 9 4 2 1 7 5]
            [1 7 9 8 6 5 4 2 3]
            [2 4 5 1 7 3 8 9 6]
            [5 2 3 7 8 4 6 1 9]
            [8 6 4 5 1 9 7 3 2]
            [9 1 7 3 2 6 5 4 8]
            [7 9 6 2 5 1 3 8 4]
            [3 5 1 4 9 8 2 6 7]
            [4 8 2 6 3 7 9 5 1]]))))

(deftest hardest-solution
  (testing "A harder board."
    (is (= (solve ex/hardest)
           [[8 1 2 7 5 3 6 4 9]
            [9 4 3 6 8 2 1 7 5]
            [6 7 5 4 9 1 2 8 3]
            [1 5 4 2 3 7 8 9 6]
            [3 6 9 8 4 5 7 2 1]
            [2 8 7 1 6 9 5 3 4]
            [5 2 1 9 7 4 3 6 8]
            [4 3 8 5 2 6 9 1 7]
            [7 9 6 3 1 8 4 5 2]]))))

(deftest brutal-solution
  (testing "Another harder board."
    (is (= (solve ex/brutal)
           [[1 7 5 2 3 8 6 4 9]
            [2 3 4 1 6 9 7 8 5]
            [6 9 8 7 5 4 1 3 2]
            [4 6 1 5 2 7 3 9 8]
            [9 5 3 6 8 1 4 2 7]
            [7 8 2 4 9 3 5 6 1]
            [8 2 7 3 4 5 9 1 6]
            [3 1 6 9 7 2 8 5 4]
            [5 4 9 8 1 6 2 7 3]]))))

(deftest absurd-solution
  (testing "An absurdly hard board."
    (is (= (solve ex/absurd)
           [[1 2 8 3 7 6 5 9 4]
            [3 4 5 1 9 2 7 8 6]
            [6 7 9 4 8 5 2 3 1]
            [2 6 4 5 1 9 8 7 3]
            [9 8 3 7 2 4 6 1 5]
            [7 5 1 6 3 8 9 4 2]
            [4 3 6 9 5 7 1 2 8]
            [8 1 7 2 6 3 4 5 9]
            [5 9 2 8 4 1 3 6 7]]))))

(deftest al-escargot-solution
  (testing "The Al Escargot board."
    (is (= (solve ex/al-escargot)
           [[1 6 2 8 5 7 4 9 3]
            [5 3 4 1 2 9 6 7 8]
            [7 8 9 6 4 3 5 2 1]
            [4 7 5 3 1 2 9 8 6]
            [9 1 3 5 8 6 7 4 2]
            [6 2 8 7 9 4 1 3 5]
            [3 5 6 4 7 8 2 1 9]
            [2 4 1 9 3 5 8 6 7]
            [8 9 7 2 6 1 3 5 4]]))))
