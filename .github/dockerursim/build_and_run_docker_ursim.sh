#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Make directory to store the URCap
mkdir ${DIR}/urcaps 2>/dev/null
cp target/externalcontrol-*.jar ${DIR}/urcaps/externalcontrol.jar

docker run --rm --name="mydockerursim" -d \
  -e ROBOT_MODEL=UR5 \
  --network host \
  -v "${DIR}/.vol":/ursim/programs \
  -v "${DIR}/urcaps":/urcaps \
  --privileged \
  --cpus=1 \
  universalrobots/ursim_e-series
