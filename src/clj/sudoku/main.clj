(ns sudoku.main
  (:require [clojure.pprint :refer [pprint]]
            [sudoku.rules :refer [solve unknowns]]
            [sudoku.examples :as ex]))

(pprint (time (solve ex/easy)))
(pprint (time (solve ex/hard)))
(pprint (time (solve ex/brutal)))
