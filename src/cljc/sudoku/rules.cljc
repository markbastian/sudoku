(ns sudoku.rules)

(def valid-values (apply hash-set (range 1 10)))

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

(defn lock-cell [[board unknowns] cell v]
  [(assoc-in board cell v)
   (reduce (fn [u n] (cond-> u (u n) (update n disj v)))
           (dissoc unknowns cell) (neighbors cell))])

(defn initialize [b]
  (reduce
    (fn [u c] (if-let[v (valid-values (get-in b c))] (lock-cell u c v) u))
    [b (zipmap all-cells (repeat valid-values))] all-cells))

(defn solve-step [[_ unknowns :as soln]]
  (let [[best-cell best-values] (apply min-key (comp count val) unknowns)]
    (for [v best-values] (lock-cell soln best-cell v))))

(defn solve [board]
  (loop [[[board unknowns :as f] & r] [(initialize board)]]
    (cond
      (and (empty? unknowns) (every? #(every? integer? %) board)) board
      (nil? f) f
      :default (recur (into r (solve-step f))))))

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
