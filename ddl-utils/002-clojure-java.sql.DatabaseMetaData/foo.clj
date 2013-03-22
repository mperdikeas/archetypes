(ns sdfjkjlkjsd2342lkjlk61lk
  (:use [db-metadata])
  (:use [clojure.java.jdbc :as sql :only ()])
  (:use [mutil :only (coll-sub)]))
(comment (println (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader)))))

(let
    [config (read-string (slurp "./coords.clj"))
     probe (:probe config)]
  (def coords-conn (:connection config))
  (def CATALOG     (:schema probe))  
  (def SCHEMA      (:schema probe)))



(def conn (sql/get-connection coords-conn))
(def mtdt (.getMetaData conn))


(doall (map println (schemas mtdt)))

(defn message [v]
  (format "\n\nAnd here's the list of all %s in catalog %s / schema %s:" v CATALOG SCHEMA))


(println (message "tables"))

(doall (map println (table-views mtdt CATALOG SCHEMA)))

(println (message "columns"))

(doall (map println (columns mtdt CATALOG SCHEMA)))

(println (message "primary keys"))

(doall (map println (primaryKeys mtdt CATALOG SCHEMA)))

(println (message "unique indices"))

(doall (map println (uniqueIndices mtdt CATALOG SCHEMA)))

(println (message "unique except primary keys"))
(doall (map println (coll-sub (uniqueIndices mtdt CATALOG SCHEMA)
                              (primaryKeys   mtdt CATALOG SCHEMA))))

(println (message "foreign keys columns of troolean table"))
(doall (map println (exportedKeys mtdt CATALOG SCHEMA "troolean")))

(println (message "all foreign key columns"))
(doall (map println (exportedKeys mtdt CATALOG SCHEMA)))

(println "****** SECOND FETCHING *******")

(let
    [mtdtrslts (metadata conn CATALOG SCHEMA)]
  (doall (map #(do
                 (println (format "\n\nCOLLECTION: %s" %))
                 (doall (map println (% mtdtrslts))))
              (keys mtdtrslts))))