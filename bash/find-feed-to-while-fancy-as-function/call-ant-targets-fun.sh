#!/usr/bin/env bash

call-ant-targets() {
TARGET=$1
DIRS=$2

EXPR=/usr/bin/expr
START=$(/bin/date +%s)
RESULT=0
COUNTER=0

BLACK=$(tput setaf 0)
RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
YELLOW=$(tput setaf 3)
LIME_YELLOW=$(tput setaf 190)
POWDER_BLUE=$(tput setaf 153)
BLUE=$(tput setaf 4)
MAGENTA=$(tput setaf 5)
CYAN=$(tput setaf 6)
WHITE=$(tput setaf 7)
BRIGHT=$(tput bold)
NORMAL=$(tput sgr0)
BLINK=$(tput blink)
REVERSE=$(tput smso)
UNDERLINE=$(tput smul)

printf '%s%s%s%s\n' "$CYAN" "$UNDERLINE" "calling target '$TARGET' on all build.xml files in $DIRS" "$NORMAL"
COL=80 # $(tput cols) # you can hardcode a value here, e.g. 80 or use $(tput cols) to get the full width

while IFS= read -r -u3 -d $'\0' file; do
        INTRO="$TARGET on: $file ... "
           echo -n $INTRO
           ant -f "$file" $TARGET > "$file.build.log" 2>&1 &&
             printf '%s%*s%s\n' "$GREEN" $((COL - ${#INTRO})) "[OK]"   "$NORMAL" ||
           { printf '%s%*s%s\n' "$RED"   $((COL - ${#INTRO})) "[FAIL]" "$NORMAL" && cat "$file.build.log" ; RESULT=1 ; break; }
        COUNTER=$((COUNTER + 1))
done 3< <(find $DIRS -iname build.xml -print0)


END=$(/bin/date +%s)
DIFF=$($EXPR $END - $START)

if [ $RESULT -eq 0 ]
then
    printf '%s%s%s%s\n' "$GREEN" "$UNDERLINE" "builds (target: '$TARGET') successfully concluded for all $COUNTER projects in $DIFF seconds" "$NORMAL"
else
    printf '%s%s%s%s\n' "$RED" "$UNDERLINE" "! build failed after $COUNTER projects" "$NORMAL"
fi
}


