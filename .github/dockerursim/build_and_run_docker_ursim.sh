#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Make directory to store the URCap
mkdir ${DIR}/urcaps 2>/dev/null
cp target/externalcontrol-*.jar ${DIR}/urcaps/externalcontrol.jar

docker network create --subnet=192.168.0.0/16 static_test_net

docker run --rm --name="mydockerursim" -d \
  -e ROBOT_MODEL=UR5 \
  --net static_test_net \
  --ip 192.168.56.101 \
  -v "${DIR}/.vol":/ursim/programs \
  -v "${DIR}/urcaps":/urcaps \
  --privileged \
  --cpus=1 \
  universalrobots/ursim_e-series
