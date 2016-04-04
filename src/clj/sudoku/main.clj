(ns sudoku.main
  (:require [sudoku.examples :as ex]
            [sudoku.rules :refer [solve valid-board?]]
            [clojure.pprint :refer [pprint]]))

(pprint (time (solve ex/broken)))
(pprint (time (solve ex/easy)))
(pprint (time (solve ex/hard)))
(pprint (time (solve ex/hardest)))
(pprint (time (solve ex/brutal)))
(pprint (time (solve ex/absurd)))
(pprint (time (solve ex/al-escargot)))