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

(doall (map println (_table-views mtdt CATALOG SCHEMA)))

(println (message "columns"))

(doall (map println (_columns mtdt CATALOG SCHEMA)))

(println (message "primary keys"))

(doall (map println (_primaryKeys mtdt CATALOG SCHEMA)))

(println (message "unique indices"))

(doall (map println (_uniqueIndices mtdt CATALOG SCHEMA)))

(println (message "unique except primary keys"))
(doall (map println (coll-sub (_uniqueIndices mtdt CATALOG SCHEMA)
                              (_primaryKeys   mtdt CATALOG SCHEMA))))

(println (message "foreign keys columns of troolean table"))
(doall (map println (_exportedKeys mtdt CATALOG SCHEMA "troolean")))

(println (message "all foreign key columns"))
(doall (map println (_exportedKeys mtdt CATALOG SCHEMA)))

(println "****** SECOND FETCHING *******")

(let
    [mtdtrslts (metadata conn CATALOG SCHEMA)]
  (doall (map #(do
                 (println (format "\n\nCOLLECTION: %s" %))
                 (doall (map println (% mtdtrslts))))
              (keys mtdtrslts))))

(println "\n\n\n\n\n****** THIRD FETCHING ********\n\n\n\n\n")
(let [mdata (metadata conn CATALOG SCHEMA)
      tablenames (tables mdata)]
  (println "tables are: " tablenames)
  (println "\n")
  (doseq [tablename tablenames]
    (println "columns of table: " tablename "are: " (columns mdata tablename)))
  (println "\n")
  (doseq [tablename tablenames]
    (println "PK columns of table: " tablename "are: " (pkColumns mdata tablename)))
  (println "\n")  
  (doseq [tablename tablenames]
    (println "UK constraints of table: " tablename "are: " (ukConstraints mdata tablename)))
  (println "\n")
  (doseq [tablename tablenames]
    (println "FK cols lists of table: " tablename "are: " (fkConstraints mdata tablename)))
  (println "\n")
  (doseq [tablename tablenames]
    (println "FK OUT cols lists of table: " tablename "are: " (fkConstraintsOut mdata tablename))))
  
        