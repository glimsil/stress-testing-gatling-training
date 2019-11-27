#!/bin/sh
sh ./build/gradle.sh user-ms
sh ./build/gradle.sh pet-ms

cp ./build/docker-compose.yml docker-compose.yml

docker-compose up --build
rm ./docker-compose.yml
