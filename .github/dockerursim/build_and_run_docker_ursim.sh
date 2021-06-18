#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

cp target/externalcontrol-*.jar ${DIR}/externalcontrol.jar

docker network create --subnet=192.168.0.0/16 static_test_net

docker build ${DIR} -t mydockerursim
docker volume create dockerursim
docker run --rm --name="mydockerursim" -d \
  -e ROBOT_MODEL=UR5 \
  --net static_test_net \
  --ip 192.168.56.101 \
  -v "${DIR}/.vol":/ursim/programs \
  --privileged \
  --cpus=1 \
  mydockerursim

# This is needed in order to install the URCap properly
sleep 30
docker exec -t mydockerursim killall java