(ns sudoku.examples)

(def easy
  [[5 3 nil nil 7 nil nil nil nil]
   [6 nil nil 1 9 5 nil nil nil]
   [nil 9 8 nil nil nil nil 6 nil]
   [8 nil nil nil 6 nil nil nil 3]
   [4 nil nil 8 nil 3 nil nil 1]
   [7 nil nil nil 2 nil nil nil 6]
   [nil 6 nil nil nil nil 2 8 nil]
   [nil nil nil 4 1 9 nil nil 5]
   [nil nil nil nil 8 nil nil 7 9]])

(def broken
  [[1 nil nil nil nil 8 6 nil nil]
   [5 3 4 nil nil nil nil nil nil]
   [nil nil nil nil 5 nil 1 3 2]
   [nil nil nil nil nil nil 3 nil nil]
   [9 nil nil 6 nil nil 4 nil nil]
   [7 nil nil nil 9 3 nil 6 nil]
   [8 2 7 nil nil nil nil nil nil]
   [nil nil 6 nil 7 nil nil nil 4]
   [nil nil nil nil 1 nil 2 nil nil]])

(def hard
  [[6 nil 8 nil 4 nil nil nil nil]
   [1 7 nil nil nil 5 nil 2 nil]
   [nil nil 5 nil nil 3 nil nil nil]
   [nil 2 nil 7 nil nil nil 1 9]
   [nil nil nil nil nil nil nil nil nil]
   [9 1 nil nil nil 6 nil 4 nil]
   [nil nil nil 2 nil nil 3 nil nil]
   [nil 5 nil 4 nil nil nil 6 7]
   [nil nil nil nil 3 nil 9 nil 1]])

;http://www.sudokuoftheday.co.uk
(def absurd
  [[nil nil nil nil 7 nil nil nil nil]
   [nil nil 5 1 nil nil nil 8 6]
   [6 nil nil 4 nil nil nil 3 nil]
   [nil nil nil nil 1 nil 8 nil nil]
   [9 nil 3 nil 2 nil nil nil nil]
   [nil 5 nil nil nil nil 9 nil nil]
   [nil 3 nil nil nil nil 1 nil 8]
   [nil 1 7 nil nil 3 nil nil 9]
   [5 nil 2 nil nil nil nil 6 nil]])

;https://github.com/awilhelm/brutal-puzzle-solving/blob/master/sudoku.dat
(def brutal
  [[1 nil nil nil nil 8 6 nil nil]
   [nil 3 4 nil nil nil nil nil nil]
   [nil nil nil nil 5 nil 1 3 2]
   [nil nil nil nil nil nil 3 nil nil]
   [9 nil nil 6 nil nil 4 nil nil]
   [7 nil nil nil 9 3 nil 6 nil]
   [8 2 7 nil nil nil nil nil nil]
   [nil nil 6 nil 7 nil nil nil 4]
   [nil nil nil nil 1 nil 2 nil nil]])

