#!/usr/bin/env bash
EXPR=/usr/bin/expr
START=$(/bin/date +%s)
RESULT=0
COUNTER=0

while IFS= read -r -u3 -d $'\0' file; do
        echo -n "building $file ... "
        ant -f "$file" build > "$file.build.log" 2>&1 &&
           echo "done" ||
           { echo "failure" && cat "$file.build.log" ; RESULT=1 ; break; }
        COUNTER=$((COUNTER + 1))
done 3< <(find . -iname build.xml -print0)


END=$(/bin/date +%s)
DIFF=$($EXPR $END - $START)

if [ $RESULT -eq 0 ]
then
    echo "! build successfully concluded for all $COUNTER projects in $DIFF seconds"
else
    echo "X build failed after $COUNTER projects"
fi


