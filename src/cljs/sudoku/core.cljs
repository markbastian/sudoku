(ns sudoku.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [put! chan <! >! timeout close!]]
            [cljs.pprint :refer [pprint]]
            [cljs.reader :refer [read-string]]
            [sudoku.examples :as ex]
            [sudoku.rules :as rules]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

(defn render [state]
  (let [{:keys [puzzle]} @state]
    [:div
     [:h1 "Sudoku Solver"]
     [:table
      (doall
        (for [row (range 9)]
          [:tr {:key (str "row:" row)}
           (doall
             (for [col (range 9)
                   :let [n (get-in puzzle [row col])]]
               [:td {:key (str "cell:" row ":" col)
                     :style {:width :2em :height :2em}}
                [:input {:type :number
                         :min 1 :max 9 :value n
                         :on-change #(swap! state assoc-in [:puzzle row col]
                                            (-> % .-target .-value read-string))}]]))]))]
     [:div
      [:button {:on-click #(if-let [solution (rules/solve puzzle)]
                            (swap! state assoc :puzzle solution)
                            (js/alert "Current puzzle is unsolvable.")) }
       "Solve"]]
     [:div
      [:button {:on-click #(swap! state assoc :puzzle ex/easy) }
       "Reset"]]
     [:div
      [:button {:on-click #(swap! state assoc :puzzle
                                  (vec (take 10 (repeat (vec (take 10 (repeat nil))))))) }
       "Clear"]]
     ]))

(when-let [app-context (. js/document (getElementById "app"))]
  (let [initial-value ex/hardest
        state (atom {:puzzle initial-value
                     :history [initial-value] })]
  (reagent/render-component [render state] app-context)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )