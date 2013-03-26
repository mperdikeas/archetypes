(ns db-metadata
  (:use [clojure.java.jdbc :as sql :only ()])
  (:use [clojure.repl])
  (:use [clojure.set])
  (:use [jdbc-types :only (code->str)])
  (:use [mutil :only (in? not-empty? strct1lvlFlatten only reorder group-by-only third)]))
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

(defn _table-views [mtdt catalog schema]
  (let
      [rs (.getTables mtdt catalog schema nil (into-array  String (list "TABLE" "VIEW")))
       rv (atom [])]
    (while (.next rs)
      (swap! rv conj [(.getString rs "TABLE_NAME")
                      (.getString rs "TABLE_TYPE")]))
    @rv))

(defrecord Column           [tble ordn name dtyp ndec nlbl])
(defrecord UniqueConstraint [tble cnst ordn name])

    
(defn _columns [mtdt catalog schema]
  (let
      [rs (.getColumns mtdt catalog schema nil nil)
       rv (atom [])]
    (while (.next rs)
      (swap! rv conj (->Column (.getString rs "TABLE_NAME")
                               (.getInt    rs "ORDINAL_POSITION")
                               (.getString rs "COLUMN_NAME")
                               (code->str (.getInt    rs "DATA_TYPE"))
                               (.getInt    rs "DECIMAL_DIGITS")
                               (if (= (.getString rs "IS_NULLABLE") "YES")
                                 true
                                 false))))
    (filter #(in? (map first (_table-views mtdt catalog schema)) (:tble %))
            @rv)))

(defn _primaryKeys [mtdt catalog schema]
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

(defn _uniqueIndices
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
              (map #(_uniqueIndices mtdt catalog schema %)
                   (map first (_table-views mtdt catalog schema)))))))

(defrecord ForeignKeyColumn [fktable fkname pktable ordn fkcolumn pkcolumn])

(defn _exportedKeys
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
              (map #(_exportedKeys mtdt catalog schema %)
                   (map first (_table-views mtdt catalog schema)))))))

(defn metadata [conn catalog schema]
  (let [mtdt (.getMetaData conn)]
    {:tables  (_table-views   mtdt catalog schema)
     :columns (_columns      mtdt catalog schema)
     :pKeys   (_primaryKeys   mtdt catalog schema)
     :uIndcs  (_uniqueIndices mtdt catalog schema)
     :fKeys   (_exportedKeys  mtdt catalog schema)}))


;;; all the below functions accept a metadata object and produce various views:

(defn tables
  "returns a list of table (not view) names"
  [mdata]
  (map first (filter #(= (second %) "TABLE") (:tables mdata))))

(defn columns
  "returns a quadruple: (column name, data type , number of decimal, is nullable)
   of the columns of a tabley (table or view) in the correct order"
  [mdata tabley]
  (map #(vector (:name %) (:dtyp %) (:ndec %) (:nlbl %))
       (sort-by :ordn (filter #(= (:tble %) tabley)
                              (:columns mdata)))))

(defn pkColumns
  "returns a list of the (single) PK constraint columns of that table
   with the ordering found in the table definition (not the ordering
   given in the constraint definition)"
  [mdata table]
  (let
      [
       columnsInPK (map :name (only (vals (group-by :cnst (filter #(= (:tble %) table) (:pKeys mdata))))))]
    (reorder columnsInPK (map first
                              (columns mdata table)))))

(defn ukConstraints
  "returns a list of the lists of UK constraint columns of that table"
  [mdata table]
  (let
      [columnsInUK (map #(map :name %) (vals (group-by :cnst (filter #(= (:tble %) table)
                                                            (:uIndcs mdata)))))]
    (map #(reorder % (map first (columns mdata table))) columnsInUK)))
  
(defn fkConstraints
  "returns a list of the FKs pointing to this table
   as a map: foreign table and foreign key name ->
   a map of (ord -> pairs (fkcolumn, local column)"
  [mdata table]
  (let
      [fkColsForTbl (filter #(= (:pktable %) table)
                              (:fKeys mdata))
       __fkColsMap (group-by #(vector (:fktable %) (:fkname %)) fkColsForTbl)
       _ (comment (println "****************" __fkColsMap))
       _fkColsMap (into {} (for [[k v] __fkColsMap] [k (map #(vector
                                                              (:ordn     %)
                                                              (:fkcolumn %)
                                                              (:pkcolumn %))
                                                           v)]))
       fkColsMap (into {} (for [ [k v] _fkColsMap] [k (group-by-only first v)]))
       fkColsMap_ (into {} (for [ [k v] fkColsMap] [k (into {} (for [ [_k _v] v]
                                                                 [_k (vector
                                                                      (second _v)
                                                                      (third _v))]))]))
       ]
    fkColsMap_))
    

