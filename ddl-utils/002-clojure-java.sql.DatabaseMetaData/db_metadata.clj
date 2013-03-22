(ns db-metadata
  (:use [clojure.java.jdbc :as sql :only ()])
  (:use [clojure.repl])
  (:use [clojure.set])
  (:use [jdbc-types :only (code->str)])
  (:use [mutil :only (in? not-empty? strct1lvlFlatten)]))
(import '(java.io File))
(import '(java.sql ResultSet))

(comment ; TODO: remove that
(defn db-read [db query & flags]
  (when (contains? (set flags) :debug)
      (print (format "executing:\n%s\n" query)))
  (sql/with-connection db
    (sql/with-query-results res
      [query]
      (doall res)))))



(defn schemas [mtdt]
  (let
      [rs (.getSchemas mtdt)
       rv (atom [])]
    (while (.next rs)
      (swap! rv conj [(.getString rs "TABLE_SCHEM")
                      (.getString rs "TABLE_CATALOG")]))
    @rv))

(defn table-views [mtdt catalog schema]
  (let
      [rs (.getTables mtdt catalog schema nil (into-array  String (list "TABLE" "VIEW")))
       rv (atom [])]
    (while (.next rs)
      (swap! rv conj [(.getString rs "TABLE_NAME")
                      (.getString rs "TABLE_TYPE")]))
    @rv))

(defrecord Column           [tble ordn name dtyp ndec])
(defrecord UniqueConstraint [tble cnst ordn name])

    
(defn columns [mtdt catalog schema]
  (let
      [rs (.getColumns mtdt catalog schema nil nil)
       rv (atom [])]
    (while (.next rs)
      (swap! rv conj (->Column (.getString rs "TABLE_NAME")
                               (.getInt    rs "ORDINAL_POSITION")
                               (.getString rs "COLUMN_NAME")
                               (code->str (.getInt    rs "DATA_TYPE"))
                               (.getInt    rs "DECIMAL_DIGITS"))))
                      
    (filter #(in? (map first (table-views mtdt catalog schema)) (:tble %))
            @rv)))

(defn primaryKeys [mtdt catalog schema]
  (let
      [rs (.getPrimaryKeys mtdt catalog schema nil)
       rv (atom [])]
    (while (.next rs)
      (swap! rv conj (->UniqueConstraint
                      (.getString rs "TABLE_NAME")
                      (.getString rs "PK_NAME")
                      (.getInt    rs "KEY_SEQ")
                      (.getString rs "COLUMN_NAME"))))
    @rv))

(defn uniqueIndices
  ([mtdt catalog schema table]
     (let [rs (.getIndexInfo mtdt catalog schema table true false)
           rv (atom [])]
       (while (.next rs)
         (swap! rv conj (->UniqueConstraint
                         (.getString rs "TABLE_NAME")
                         (.getString rs "INDEX_NAME")
                         (.getInt    rs "ORDINAL_POSITION")
                         (.getString rs "COLUMN_NAME"))))
       @rv))
  ([mtdt catalog schema]
     (strct1lvlFlatten
      (filter not-empty?
              (map #(uniqueIndices mtdt catalog schema %)
                   (map first (table-views mtdt catalog schema)))))))

(defrecord ForeignKeyColumn [fktable fkname pktable ordn fkcolumn pkcolumn])

(defn exportedKeys
  ([mtdt catalog schema table]
     (let [rs (.getExportedKeys mtdt catalog schema table)
           rv (atom [])]
       (while (.next rs)
         (swap! rv conj (->ForeignKeyColumn
                         (.getString rs "FKTABLE_NAME")
                         (.getString rs "FK_NAME")
                         (.getString rs "PKTABLE_NAME")
                         (.getInt    rs "KEY_SEQ")
                         (.getString rs "FKCOLUMN_NAME")
                         (.getString rs "PKCOLUMN_NAME"))))
       @rv))
  ([mtdt catalog schema]
     (strct1lvlFlatten
      (filter not-empty?
              (map #(exportedKeys mtdt catalog schema %)
                   (map first (table-views mtdt catalog schema)))))))

(defn metadata [conn catalog schema]
  (let [mtdt (.getMetaData conn)]
    {:tables  (table-views   mtdt catalog schema)
     :columns (columns       mtdt catalog schema)
     :pKeys   (primaryKeys   mtdt catalog schema)
     :uIndcs  (uniqueIndices mtdt catalog schema)
     :fKeys   (exportedKeys  mtdt catalog schema)}))