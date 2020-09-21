(ns notespace.renderers.gorilla-notes
  (:require [gorilla-notes.core :as gn]
            [cljfx.api :as fx]
            [notespace.view :as view]
            [notespace.note :as note]
            [notespace.actions :as actions]
            [notespace.util :as u]))

(defonce server (atom nil))

(defn init []
  (when-not @server
    (reset! server true)
    (gn/start-server!))
  (gn/reset-notes!)
  (gn/merge-new-options! {:notes-in-cards? false
                          :header?         false
                          :reverse-notes?  false
                          :custom-header [:div [:big "Notespace"] [:hr]]
                          :custom-footer [:div [:hr]]})
  (gn/watch-inputs! actions/assoc-input!))

(defn browse []
  (gn/browse-default-url))

(defn rendering [ctx namespace idx]
  (view/note->hiccup
   (fx/sub-val ctx get-in [:ns->notes namespace idx])))

(defn renderer [old-ctx new-ctx]
  (when-let [namespace (fx/sub-val new-ctx :last-ns-handled)]
    (if (not= (fx/sub-val old-ctx :inputs)
              (fx/sub-val new-ctx :inputs))
      (do
        (dotimes [idx (-> new-ctx
                          (fx/sub-val get-in [:ns->notes namespace])
                          count)]
          (actions/rerender-note! namespace idx)))
      (let [[old-notes new-notes] (->> [old-ctx new-ctx]
                                       (map (fn [ctx]
                                              (fx/sub-val
                                               ctx
                                               get-in [:ns->notes namespace]))))
            new-things            (->> (map vector
                                            (range)
                                            (concat old-notes (repeat nil))
                                            new-notes)
                                       (filter
                                        (fn [[_ old-note new-note]]
                                          (not (= old-note new-note)))))
            [old-n new-n]         (->> [old-notes new-notes]
                                       (map count))]
        (->> new-things
             (run!
              (fn [[idx _ new-note]]
                (gn/assoc-note!
                 idx
                 (fx/sub-ctx new-ctx rendering namespace idx)
                 :broadcast? false))))
        (when (> old-n new-n)
          (gn/drop-tail! (- old-n new-n)
                         :broadcast? false))
        (Thread/sleep 100)
        (gn/broadcast-content-ids!)))))