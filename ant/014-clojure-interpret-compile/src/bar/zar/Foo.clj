(ns bar.zar.Foo
  (:use [clojure.set :only (union)])
  (:use [ clojure.math.combinatorics :only (combinations)])
  (:use [clojure.tools.cli :only (cli)])
  (:use [mutil :only (unless alltrue in? not-in? not-nil? strct1lvlFlatten zippy not-empty? maxSameElements
                           third fourth orderGraph coll-sub coll-sub-abs map-graph)])
  (:gen-class :main true)) ; it quriously appears that ':main true' is not necessary

(println "this S-expression is evaluated during compilation")

(unless false
    (if true
        (if true
           (println "this S-expression too, is evaluated during compilation although it's not at the top-level"))))

(defn foo[]
    (println "evaluating a `defn` form doesn't mean evaluating internal S-expressions, so this one doesn't get printed"))


(defn -main
  "The application's main function"
  [& args]
  (println args)
  (println "boo")
  (println (count (union #{:a :b} #{:c})))
  (println (count (combinations (range 10) 2)))
  (println (fourth (range 10)))
  (let [ [opts args banner]
         (cli args
              ["-h" "--help" "Show help" :flag true :default false]
              ["-n" "--number-of-printout" "number of times to print messages" :default 1]
              ["-m" "--message" "REQUIRED: message to print"]
              ["-v" "--verbose" "verbose"]
              )]
    (println (str "Options:\n" opts "\n\n"))
    (println (str "Arguments:\n" args "\n\n"))
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (doall
        (for [_ (range (Integer/valueOf (:number-of-printout opts)))]
          (println "message is: " (:message opts))))))



