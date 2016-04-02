(ns sudoku.rules
  (:require #?(:clj [clojure.pprint :refer [pprint]]
               :cljs [cljs.pprint :refer [pprint]])))

(defonce all (set (map inc (range 9))))
(defonce all-coords (for [i (range 9) j (range 9)] [i j]))
(defn initialize [board] (mapv (fn [row] (mapv #(or % all) row)) board))

(defn solved? [board] (every? #(number? (get-in board %)) all-coords))
(defn unknowns [board] (filter #(set? (get-in board %)) all-coords))
(defn invalid? [board] (some #(= #{} (get-in board %)) all-coords))

(defn row [[_ j]] (mapv (fn [i] [i j]) (range 9)))
(defn col [[i _]] (mapv (fn [j] [i j]) (range 9)))
(defn sector [[i j]]
  (for [a (range 3) b (range 3)]
    (mapv + [(* 3 (quot i 3)) (* 3 (quot j 3))] [a b])))

(defn sphere-of-influence [c]
  (reduce into #{} ((juxt row col sector) c)))

(defonce sois (into {} (for [row (range 9) col (range 9) :let [c [row col]]]
                         [c (disj (sphere-of-influence c) c)])))

(defn remove-value [v b c]
  (if (number? (get-in b c)) b (update-in b c disj v)))

(defn simplify
  ([board c]
  (let [v (get-in board c)]
    (cond
      (number? v)
      (reduce (partial remove-value v) board (sois c))
      (== 1 (count v)) (simplify (assoc-in board c (first v)) c)
      :else board)))
  ([board]
   (some #(when (apply = %) (first %))
         (partition 2 (iterate #(reduce simplify % all-coords) board)))))

(defn expand [board cell]
  (map #(assoc-in board cell %) (get-in board cell)))

(defn neighbors [board]
  (let [most-constrained (apply min-key #(count (get-in board %)) (unknowns board))]
    (filter (complement invalid?) (map simplify (expand board most-constrained)))))

(defn solve [board]
  (let [start (simplify (initialize board))]
    (if (solved? start)
      start
      (some (fn [s] (if (empty? s) :no-solution
                        (some #(when (solved? %) %) s)))
            (iterate #(mapcat neighbors %) [start])))))



