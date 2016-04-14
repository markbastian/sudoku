(ns sudoku.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.reader :refer [read-string]]
            [sudoku.examples :as ex]
            [sudoku.rules :as rules]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

(def puzzle-options
  {:easy ex/easy
   :hard ex/hard
   :hardest ex/hardest
   :absurd ex/absurd
   :brutal ex/brutal
   :al-escargot ex/al-escargot
   :broken ex/broken})

(defn render [state]
  (let [{:keys [puzzle]} @state]
    [:div
     [:h2 "Sudoku Solver"]
     [:table {:border "1" :style {:border "1px solid black"
                                  :border-collapse :collapse}}
      (doall
        (for [row (range 9)]
          [:tr {:key (str "row:" row)}
           (doall
             (for [col (range 9)
                   :let [n (get-in puzzle [row col])]]
               [:td {:key (str "cell:" row ":" col)
                     :style {:width :2em :height :2em
                             :border "1px solid black"}}
                [:input {:type :number
                         :min 1 :max 9 :value n
                         :on-change #(swap! state assoc-in [:puzzle row col]
                                            (-> % .-target .-value read-string))}]]))]))]
     [:div
      [:button {:on-click #(if-let [solution (rules/solve puzzle)]
                            (swap! state assoc :puzzle solution)
                            (js/alert "Current puzzle is unsolvable.")) }
       "Solve"]]
     [:select#current-puzzle {:on-change
               #(swap! state assoc :puzzle
                       (puzzle-options (-> % .-target .-value keyword)))}
      [:option {:value :easy} "Easy"]
      [:option {:value :hard} "Hard"]
      [:option {:value :hardest} "Hardest"]
      [:option {:value :absurd} "Absurd"]
      [:option {:value :brutal} "Brutal"]
      [:option {:value :al-escargot} "Al Escargot"]
      [:option {:value :broken} "Broken"]]
     [:div
      [:button {:on-click #(let [element (.getElementById js/document "current-puzzle")]
                            (swap! state assoc :puzzle
                                   (puzzle-options (-> element .-value keyword)))) }
       "Reset"]]
     [:div
      [:button {:on-click #(swap! state assoc :puzzle
                                  (vec (take 10 (repeat (vec (take 10 (repeat nil))))))) }
       "Clear"]]]))

(when-let [app-context (.getElementById js/document "sudoku-solver")]
  (let [initial-value ex/easy
        state (atom {:puzzle initial-value})]
  (reagent/render-component [render state] app-context)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )