#!/bin/sh

echo "********************************************************"
echo "Starting Configuration Server ON PORT: $SERVER_PORT"
echo "********************************************************"
java -Dserver.port=$SERVER_PORT -Dspring.profiles.active=$PROFILE -jar /usr/local/configurationserver/app.jar
