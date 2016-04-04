(ns sudoku.rules)

(defonce all-cells (for [i (range 9) j (range 9)] [i j]))

(defonce neighbors
  (let [cells
        (juxt
          #(map (fn [i] [i (second %)]) (range 9))
          #(map (fn [j] [(first %) j]) (range 9))
          #(for [a (range 3) b (range 3)]
            (mapv + [(* 3 (quot (first %) 3)) (* 3 (quot (second %) 3))]
                  [a b])))]
    (zipmap all-cells (map #(disj (reduce into #{} (cells %)) %) all-cells))))

(defn constraints [[board unsolved-cells]]
  (letfn [(solved [cell] (apply hash-set (map (partial get-in board) (neighbors cell))))]
    (->> unsolved-cells
       (map (fn [c] [c (remove (solved c) (map inc (range 9)))]))
       (group-by (comp count second)))))

(defn lock-cells [[board unsolved-cells] [cell values]]
  (map (fn [value] [(assoc-in board cell value) (disj unsolved-cells cell)]) values))

(defn solve [initial-board]
  (let [all-unknowns (apply hash-set (remove (partial get-in initial-board) all-cells))]
    (loop [[[board unsolved-cells :as f] & r] [[initial-board all-unknowns]]]
    (if-not (empty? unsolved-cells)
      (recur (into r (lock-cells f (first (some (constraints f) (range))))))
      board))))

(defn valid-cell? [board cell]
  (not-any? #{(get-in board cell)}
            (map (partial get-in board)
                 (neighbors cell))))

(defn bad-cells [board]
  (remove (partial valid-cell? board) all-cells))

(defn valid-board? [board]
  (and
    (every? (partial get-in board) all-cells)
    (empty? (bad-cells board))))
