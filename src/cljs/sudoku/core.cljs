(ns sudoku.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [put! chan <! >! timeout close!]]
            [cljs.pprint :refer [pprint]]
            [cljsjs.hammer]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

(def possibilities (map inc (range 9)))

(def board
  [[5 3 nil nil 7 nil nil nil nil]
   [6 nil nil 1 9 5 nil nil nil]
   [nil 9 8 nil nil nil nil 6 nil]
   [8 nil nil nil 6 nil nil nil 3]
   [4 nil nil 8 nil 3 nil nil 1]
   [7 nil nil nil 2 nil nil nil 6]
   [nil 6 nil nil nil nil 2 8 nil]
   [nil nil nil 4 1 9 nil nil 5]
   [nil nil nil nil 8 nil nil 7 9]])

(defn render [state]
  (let [cell-dim 20
        {:keys [locked score high-score]} @state]
    [:div
     [:h1 "sudoku"]
     [:svg {:width (* cell-dim 9) :height (* cell-dim 9)}
      (doall (for [i (range 9) j (range 9)]
        [:rect { :key (str i ":" j) :x (* i cell-dim) :y (* j cell-dim)
                :width cell-dim :height cell-dim
                :stroke :red :fill :blue }]))]
     [:h4 (str "Score: " score)]
     [:h4 (str "High Score: " high-score)]

     [:button #(prn "Hi") "solve"]
     ]))


(when-let [app-context (. js/document (getElementById "app"))]
  (let [state (atom {})]
  (reagent/render-component
    [render state]
    (do
      (set! (.-onkeydown js/window)
            (fn [e] (when (and (#{32 37 38 39 40} (.-keyCode e))
                               (= (.-target e) (.-body js/document)))
                      (.preventDefault e))))
      ;(js/setInterval #(swap! state rules/game-step) 10)
      app-context))))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )