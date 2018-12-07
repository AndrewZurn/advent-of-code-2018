(ns idea-advent-of-code-2018.day1
  (:require [clojure.java.io :as io]))

(def data (io/resource "day1.txt"))

(def parsed-numbers
  (map #(Integer/parseInt %)
       (.split (slurp data) "\n")))

(def summed-freq (reduce #(+ %1 %2) parsed-numbers))

(def freqs
  (reduce #(conj %1 (+ (last %1) %2)) [0] parsed-numbers))

(def repeated-freq (atom nil))
(defn next-freqs [previous-freqs]
  (reduce (fn [accum elem]
            (let [next-freq (+ (last accum) elem)]
              (if (some #(= next-freq %) previous-freqs)
                (do
                  (println (str "Repeated Freq: " next-freq))
                  (swap! repeated-freq (fn [x] next-freq))))
              (conj accum next-freq)))
          previous-freqs
          parsed-numbers))

;; find the repeated frequency
(loop [previous-freqs freqs
       round 0]
  (when (nil? @repeated-freq)
    (println (str "Round: " round))
    (recur (next-freqs previous-freqs) (inc round))))

(println (str "Summed Freq is: " summed-freq))
(println (str "Repeated Freq is: " @repeated-freq))
