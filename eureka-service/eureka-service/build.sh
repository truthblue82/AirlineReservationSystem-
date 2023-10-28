#!/usr/bin/env bash

PREFIX=khoaledockloud
NAME=flightmanagement-eurekaservice
IMAGE_NAME=$PREFIX/$NAME

mvn -DskipTests clean package
mkdir target/extracted
java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted
#docker build -t $IMAGE_NAME .
#docker run -p 8761:8761 -e SERVER_PORT=8761 $IMAGE_NAME