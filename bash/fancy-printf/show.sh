#!/usr/bin/env bash
echo "source: http://stackoverflow.com/a/5506264/274677"
RED=$(tput setaf 1)
GREEN=$(tput setaf 2)
NORMAL=$(tput sgr0)
COL=80 #$(tput cols) # you can hardcode a value here, e.g. 80
printf '%s%*s%s\n' "$GREEN" $COL "[OK]" "$NORMAL"
printf '%s%*s%s\n' "$RED"   $COL "[FAIL]" "$NORMAL"
