#!/bin/bash

expected="Connected: Universal Robots Dashboard Server
Disconnected"
response=$(echo "quit" | netcat -q 1 127.0.0.1 29999)
while [ "$response" != "$expected" ]
do
  echo "Dashboard server not accepting connections..."
  sleep 3
  response=$(echo "quit" | netcat -q 1 127.0.0.1 29999)
done
echo "Dashboard server connections are possible."
