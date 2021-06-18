#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

docker build ${DIR} -t myurclient

docker run --rm --name="myurclient" -d \
  --net static_test_net \
  --ip 192.168.56.102 \
  myurclient

# Execute the test
docker exec -t myurclient ./build/test_external_control
