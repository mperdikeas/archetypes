#!/usr/bin/env bash
echo
echo
echo "source 1: http://stackoverflow.com/a/5506264/274677"
echo "source 2: http://stackoverflow.com/a/4410103/274677"
echo "source 3: http://steve-parker.org/sh/exitcodes.shtml"
echo 
echo
echo
check_errs()
{
  # Function. Parameter 1 is the return code
  # Para. 2 is text to display on failure.
  if [ "${1}" -ne "0" ]; then
    echo "ERROR # ${1} : ${2}"
    # as a bonus, make our script exit with the right error code.
    exit ${1}
  fi
}

RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
NORMAL=$(tput sgr0)
COL=120 #$(tput cols) # you can hardcode a value here, e.g. 80
INTRO="attempting alpha ... "                        && echo -n "$INTRO" && /bin/true  && printf '%s%*s%s\n' "$GREEN" $((COL - ${#INTRO})) "[OK]" "$NORMAL" || (printf '%s%*s%s\n' "$RED"   $((COL - ${#INTRO})) "[FAIL]" "$NORMAL" && /bin/false)
check_errs $? "failure during alpha"
# change the below first /bin/false to /bin/true to see the script continue ...
INTRO="attempting beta beta beta beta beta beta... (we expect the script to fail here)" && echo -n "$INTRO" && /bin/false && printf '%s%*s%s\n' "$GREEN" $((COL - ${#INTRO})) "[OK]" "$NORMAL" || (printf '%s%*s%s\n' "$RED"   $((COL - ${#INTRO})) "[FAIL]" "$NORMAL" && /bin/false)
check_errs $? "failure during beta"
INTRO="attempting g ... "                            && echo -n "$INTRO" && /bin/true  && printf '%s%*s%s\n' "$GREEN" $((COL - ${#INTRO})) "[OK]" "$NORMAL" || (printf '%s%*s%s\n' "$RED"   $((COL - ${#INTRO})) "[FAIL]" "$NORMAL" && /bin/false)
