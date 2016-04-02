(ns sudoku.redux
  (:require [sudoku.rules :as rules]
            [sudoku.examples :as ex]
    #?(:clj [clojure.pprint :refer [pprint]]
       :cljs [cljs.pprint :refer [pprint]])))

(defn all-unknowns [board] (remove (partial get-in board) rules/all-coords))

(defn locked [board cell]
  (disj (apply hash-set (map #(get-in board %) (rules/sois cell))) nil))

;This can probabaly be turned into an iterate for clarity
(defn solve [board]
  (loop [[[f u] & r] [[board (apply hash-set (all-unknowns board))]]]
    (if-not (empty? u)
      (let [options (map (fn [c] [c (remove (locked f c) (map inc (range 9)))]) u)
            ;# -> cell -> opts
            constrained-map (group-by (comp count second) options)
            easies (reduce (fn [b [c v]] (assoc-in b c (first v))) f (constrained-map 1))
            ;This repeats singles
            [c maybes] (apply min-key (comp count second) options)]
        (recur (into r (mapv (fn [m] [(assoc-in easies c m) (disj u c)]) maybes))))
      f)))

(pprint (time (solve ex/brutal)))
(pprint (time (solve ex/absurd)))
;(time (rules/solve ex/absurd))