(ns sudoku.rules
  (:require [clojure.pprint :refer [pprint]]))

(def all (set (map inc (range 9))))
(def all-coords (for [i (range 9) j (range 9)] [i j]))

(def board
  [[5 3 nil nil 7 nil nil nil nil]
   [6 nil nil 1 9 5 nil nil nil]
   [nil 9 8 nil nil nil nil 6 nil]
   [8 nil nil nil 6 nil nil nil 3]
   [4 nil nil 8 nil 3 nil nil 1]
   [7 nil nil nil 2 nil nil nil 6]
   [nil 6 nil nil nil nil 2 8 nil]
   [nil nil nil 4 1 9 nil nil 5]
   [nil nil nil nil 8 nil nil 7 9]])

;https://github.com/awilhelm/brutal-puzzle-solving/blob/master/sudoku.dat
(def board
  [[1 nil nil nil nil 8 6 nil nil]
   [nil 3 4 nil nil nil nil nil nil]
   [nil nil nil nil 5 nil 1 3 2]
   [nil nil nil nil nil nil 3 nil nil]
   [9 nil nil 6 nil nil 4 nil nil]
   [7 nil nil nil 9 3 nil 6 nil]
   [8 2 7 nil nil nil nil nil nil]
   [nil nil 6 nil 7 nil nil nil 4]
   [nil nil nil nil 1 nil 2 nil nil]])

(def board
  [[6 nil 8 nil 4 nil nil nil nil]
   [1 7 nil nil nil 5 nil 2 nil]
   [nil nil 5 nil nil 3 nil nil nil]
   [nil 2 nil 7 nil nil nil 1 9]
   [nil nil nil nil nil nil nil nil nil]
   [9 1 nil nil nil 6 nil 4 nil]
   [nil nil nil 2 nil nil 3 nil nil]
   [nil 5 nil 4 nil nil nil 6 7]
   [nil nil nil nil 3 nil 9 nil 1]])


(defn row [[_ j]] (mapv (fn [i] [i j]) (range 9)))
(defn col [[i _]] (mapv (fn [j] [i j]) (range 9)))
(defn sector [[i j]]
  (let [s [(* 3 (quot i 3)) (* 3 (quot j 3))]]
    (for [a (range 3) b (range 3)]
      (mapv + s [a b]))))

(defn sphere-of-influence [c]
  (reduce into #{} ((juxt row col sector) c)))

(defn remove-value [v b c]
  (if (number? (get-in b c)) b (update-in b c disj v)))

(defn simplify [board c]
  (let [v (get-in board c)]
    (cond
      (number? v)
      (reduce (partial remove-value v) board (sphere-of-influence c))
      (== 1 (count v)) (simplify (assoc-in board c (first v)) c)
      :else board)))

(defn full-simpify [board] (reduce simplify board all-coords))

(defn solve [board]
  (let [guesses (mapv (fn [row] (mapv #(or % all) row)) board)]
    (some (fn [[a b]] (when (= a b) a)) (partition 2 (iterate full-simpify guesses)))))

(defn solved? [board] (every? #(number? (get-in board %)) all-coords))
(defn ok [board] (not-any? #(= #{} (get-in board %)) all-coords))

(defn neighbors [board]
  (let [most-constrained (apply min-key #(count (get-in board %))
                                 (filter #(set? (get-in board %)) all-coords))
        new-values (get-in board most-constrained)]
    (filter ok (map #(solve (assoc-in board most-constrained %)) new-values))))

(def n (neighbors (solve board)))
(def nn (some (fn [s] (some #(when (solved? %) %) s))
              (iterate (partial mapcat neighbors) n)))
(pprint nn)