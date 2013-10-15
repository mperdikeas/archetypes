(ns not_used
  (:use [clojure.tools.cli :only (cli)])
  (:use [bar.zar.Foo :only (-main)]))


(def opts
  (let [opts-args-banner
        (cli *command-line-args*
             ["-h" "--help" "Show help" :flag true :default false]
             ["-n" "--number-of-printout" "number of times to print messages" :default 1]
             ["-m" "--message" "REQUIRED: message to print"]
             ["-v" "--verbose" "verbose"])]
    (first opts-args-banner)))


(def simpleWay false)

(if simpleWay
  (-main "-m" "this is the simple interpreter message (only three times)" "-n" 3)
  (-main "-m" (:message opts) "-n" (Integer/parseInt (:number-of-printout opts))))
  