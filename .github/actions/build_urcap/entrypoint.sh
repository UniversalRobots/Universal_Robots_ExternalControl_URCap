#!/bin/bash

set -e

pushd ${HOME}
curl -o sdk-${SDK_VERSION}.zip https://s3-eu-west-1.amazonaws.com/urplus-developer-site/sdk/sdk-${SDK_VERSION}.zip
mkdir sdk
unzip sdk-${SDK_VERSION}.zip -d sdk
cd sdk
./install.sh

popd

mvn install
