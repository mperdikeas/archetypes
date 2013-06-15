(import '(java.io File FileInputStream FileOutputStream))
(import '(org.apache.commons.io FileUtils))
(use '[clojure.string :only (join split capitalize)])
(println (seq (.getURLs (java.lang.ClassLoader/getSystemClassLoader))))
(use '[mutil])

(use '[clojure.xml :only (parse)])


(let
    [parsed-xml (parse (File. "a.xml"))]
  (println (:tag parsed-xml))
  (println (:attrs parsed-xml))
  (println (:content parsed-xml)))
