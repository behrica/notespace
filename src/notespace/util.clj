(ns notespace.util
  (:require [clojure.pprint :as pp]
            [clojure.java.shell :as sh]))

(defn pprint-and-return [x]
  (pp/pprint x)
  x)

(defn fmap [f m]
  (->> m
       (map (fn [[k v]]
              [k (f v)]))
       (into (empty m))))

(defn only-one [elements]
  (case (count elements)
    0 nil
    1 (first elements)
    (throw (ex-info "Expected only one element."
                    {:elements elements}))))

(defn first-when-one
  [elements]
  (when (-> elements count (= 1))
    (first elements)))

(defn pending? [v]
  (instance? clojure.lang.IPending v))

(def abcd (atom 3))

(defn value=keyword
  "A careful ad-hoc way to compare values to keywords. This was necessary just for usage with version 0.15.0 of Sicmutils, due to some [bug](https://github.com/sicmutils/sicmutils/issues/291) that is fixed in the following version."
  [v kw]
  (and (keyword? v)
       (= v kw)))

(defn ready? [v]
  (cond (value=keyword v :value/not-ready) false
        (pending? v)           (realized? v)
        :else                  true))

(defn realize [v]
  (if (instance? clojure.lang.IDeref v)
    @v
    v))

(defn notify [msg]
  (sh/sh "notify-send" msg))

(defonce *current-ids (atom {}))

(defn next-id [topic]
  (-> (swap! *current-ids
             (fn [ids]
               (assoc
                ids
                topic (-> ids
                          (get topic)
                          (or 0)
                          inc))))
      (get topic)))

(defn map-coll->key-vector-map
  [coll]
  (reduce (fn [new-map key]
            (assoc new-map key (vec (map key coll))))
          {}
          (keys (first coll))))
