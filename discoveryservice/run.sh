#!/bin/sh
echo "********************************************************"
echo "Starting Eureka Discovery Server ON PORT: $SERVER_PORT"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
-Dspring.profiles.active=$PROFILE -jar /usr/local/eurekadiscoveryservice/app.jar
