#!/bin/bash

set -e

SDK_MAJOR=$(echo ${SDK_VERSION} | awk -F. '{print $1}')
SDK_MINOR=$(echo ${SDK_VERSION} | awk -F. '{print $2}')
SDK_BUILD=$(echo ${SDK_VERSION} | awk -F. '{print $3}')

download_folder=$(mktemp -d)
pushd ${download_folder}
curl -o sdk-${SDK_VERSION}.zip https://s3-eu-west-1.amazonaws.com/urplus-developer-site/sdk/sdk-${SDK_VERSION}.zip
mkdir sdk
unzip -q sdk-${SDK_VERSION}.zip -d sdk
if [ "$SDK_MAJOR" -eq 1 ] && [ "$SDK_MINOR" -eq 10 ]; then
  cd sdk/URCap_SDK-${SDK_VERSION}
else
  cd sdk
fi
./install.sh

popd

mvn install
