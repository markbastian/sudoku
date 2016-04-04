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
  (let [cell-dim 20
        {:keys [puzzle]} @state]
    [:div
     [:h1 "sudoku"]
     [:svg {:width (* cell-dim 9) :height (* cell-dim 9)}
      (doall (for [i (range 9) j (range 9)]
               [:rect { :key (str "box:" i ":" j) :x (* i cell-dim) :y (* j cell-dim)
                       :width cell-dim :height cell-dim :stroke :black :fill :white }]))
      (doall (for [row (range 9) col (range 9)
                   :let [n (get-in puzzle [row col])]
                   :when (number? n)]
               [:text { :key (str row "t:" col)
                       :x (+ (* col cell-dim) (/ cell-dim 2))
                       :y (- (* (inc row) cell-dim) (/ cell-dim 4))
                       :text-anchor :middle
                       :fill :blue } n]))]
     [:div]
     [:div                                                  ; {#js :style { :width :100% }}
      [:button {:on-click #(time (swap! state update :puzzle rules/solve)) }
      "solve"]]
     [:table
      (doall
        (for [row (range 9)]
          [:tr {:key (str "row:" row)}
           (doall
             (for [col (range 9)
                   :let [n (get-in puzzle [row col])]]
               [:td {:key (str "cell:" row ":" col)
                     :style
                          {:width :2em
                           :height :2em}}
                (cond (number? n) n
                      (empty? n) "X"
                  :default [:select {:on-change
                            (fn [e]
                              (let [s (-> e .-target .-value read-string)]
                                (pprint (swap!
                                          state
                                          #(-> %
                                               (update :history conj puzzle)
                                               (assoc-in [:puzzle row col] s)
                                               (update :puzzle rules/simplify))))))
                             :style
                            {:width :100%
                             :height :100%}}
                   [:option {:value :nil}]
                   (for [c n]
                     [:option {:key (str "choice:" row ":" col ":" c)
                               :value c} c])])]))]))]
     [:div
      [:button {:on-click #(swap! state
                                  (fn [{:keys [history] :as s}]
                                    (cond-> (assoc s :puzzle (peek history))
                                            (> (count history) 1)
                                            (update :history pop)))) }
       "undo"]]
     ]))

(when-let [app-context (. js/document (getElementById "app"))]
  (let [initial-value ex/hard
        state (atom {:puzzle initial-value
                     :history [initial-value] })]
  (reagent/render-component [render state] app-context)))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )