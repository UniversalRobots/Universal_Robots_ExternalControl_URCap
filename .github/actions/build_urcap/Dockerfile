# Container image that runs your code
FROM ubuntu:bionic

# Copies your code file from your action repository to the filesystem path `/` of the container
COPY entrypoint.sh /entrypoint.sh

RUN apt-get update && apt-get install -y \
 default-jdk\
 maven\
 unzip\
 dialog\
 sshpass\
 curl

# Code file to execute when the docker container starts up (`entrypoint.sh`)
ENTRYPOINT ["/entrypoint.sh"]
