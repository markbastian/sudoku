(ns sudoku.redux
  (:require [sudoku.rules :as rules]
            [sudoku.examples :as ex]
    #?(:clj [clojure.pprint :refer [pprint]]
       :cljs [cljs.pprint :refer [pprint]])))

(defn valid-cell? [board cell]
  (let [test #{(get-in board cell)}]
    (not-any? test (map (partial get-in board) (rules/sois cell)))))

(defn bad-cells [board]
  (remove (partial valid-cell? board) rules/all-coords))

(defn all-unknowns [board] (remove (partial get-in board) rules/all-coords))

(defn locked [board cell]
  (disj (apply hash-set (map #(get-in board %) (rules/sois cell))) nil))

;This can probabaly be turned into an iterate for clarity
(defn solve [initial-board]
  (loop [[[board unsolved-cells :as f] & r] [[initial-board (apply hash-set (all-unknowns initial-board))]]]
    (if-not (empty? unsolved-cells)
      (let [options (map (fn [c] [c (remove (locked board c) (map inc (range 9)))]) unsolved-cells)
            constrained-map (group-by (comp count second) options)
            [b u] (reduce (fn [[b u] [c v]] [(assoc-in b c (first v)) (disj u c)]) f (constrained-map 1))
            [[c maybes]] (some constrained-map (range))]
        (recur (into r (mapv (fn [m] [(assoc-in b c m) (disj unsolved-cells c)]) maybes))))
      board)))

;(pprint (time (solve ex/brutal)))
(pprint (time (solve ex/absurd)))
(pprint (time (rules/solve ex/absurd)))
;(pprint (time (solve ex/brutal)))
;(pprint (time (rules/solve ex/brutal)))
;(prn (= (solve ex/easy) (rules/solve ex/easy)))
;(prn (= (solve ex/hard) (rules/solve ex/hard)))
;(prn (= (solve ex/brutal) (rules/solve ex/brutal)))
(prn (= (solve ex/absurd) (rules/solve ex/absurd)))