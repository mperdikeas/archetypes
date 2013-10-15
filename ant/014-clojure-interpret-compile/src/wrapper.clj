(ns not_used
  (:use [bar.zar.Foo :only (-main)]))

(-main "-m" "this is the interpreter message (only three times)" "-n" 3)