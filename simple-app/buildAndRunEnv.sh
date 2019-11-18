#!/bin/sh
sh ./build/maven.sh user-ms

cp ./build/docker-compose.yml docker-compose.yml

docker-compose up --build
rm ./docker-compose.yml
