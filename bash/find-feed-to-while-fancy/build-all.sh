#!/usr/bin/env bash
EXPR=/usr/bin/expr
START=$(/bin/date +%s)
RESULT=0
COUNTER=0

RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
NORMAL=$(tput sgr0)
COL=80 # $(tput cols) # you can hardcode a value here, e.g. 80 or use $(tput cols) to get the full width

while IFS= read -r -u3 -d $'\0' file; do
        INTRO="building $file ... "
        echo -n "$INTRO"
           ant -f "$file" build > "$file.build.log" 2>&1 &&
             printf '%s%*s%s\n' "$GREEN" $((COL - ${#INTRO})) "[OK]"   "$NORMAL" ||
           { printf '%s%*s%s\n' "$RED"   $((COL - ${#INTRO})) "[FAIL]" "$NORMAL" && cat "$file.build.log" ; RESULT=1 ; break; }
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


