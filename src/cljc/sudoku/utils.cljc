(ns sudoku.utils)

(def valid? #{1 2 3 4 5 6 7 8 9})

(defn read-soln [line]
  (->> line
       (partition 9)
       (mapv #(mapv (comp valid? #?(:clj clojure.edn/read-string :cljs js/parseInt)) %))))