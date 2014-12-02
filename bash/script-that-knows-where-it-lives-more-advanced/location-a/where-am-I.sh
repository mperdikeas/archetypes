#!/usr/bin/env bash

#---%<--- first method ---------------------------------
# doesn't work when the script itself is symlinked
ME=`basename $0`
WHERE_I_LIVE=${BASH_SOURCE[0]/%${ME}/.}
#------ first method ------------------------------>%---

#---%<---- second method -------------------------------
# works even if the script itself is symlinked
SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
  DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
#------------------------------------------------>%-----


echo "[where-I-live simple     ] says: I live in ${WHERE_I_LIVE}"
echo "[where-I-live on steroids] says: I live in ${DIR}"
printf "\n\n"
echo "I am able to cat the contents of the following file no matter where you call me from (even if the script itself is multiply symlinked, it will follow the link(s) to the ultimate target):"
cat ${DIR}/a/b/c/d.txt

printf "\n\n"
echo "I am able to cat the contents of the following file no matter where you call me from but only if you don't call me via a *final* symlink (i.e. the script itself cannot be symlinked although dirs may be):"
cat ${WHERE_I_LIVE}/a/b/c/d.txt

