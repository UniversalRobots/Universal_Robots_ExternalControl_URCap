FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y libgtest-dev && \
    apt-get install -y libboost-dev && \
    apt-get install -y git && \
    apt-get install -y cmake && \
    apt-get install -y g++

RUN mkdir /src

RUN git clone -b boost https://github.com/UniversalRobots/Universal_Robots_Client_Library.git /src/Universal_Robots_Client_Library

WORKDIR /src/Universal_Robots_Client_Library
RUN mkdir build && cd build && cmake ..

RUN cd build && make && make install

COPY test /src/urcap_test

WORKDIR /src/urcap_test
RUN mkdir build && cd build && cmake .. && make

ENTRYPOINT ["tail"]
CMD ["-f","/dev/null"]
