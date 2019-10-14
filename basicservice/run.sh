#!/bin/sh

echo "********************************************************"
echo "Waiting for the discovery service to start on port $DISCOVERYSERVICE_PORT"
echo "********************************************************"
while ! `nc -z eurekadiscoveryservice $DISCOVERYSERVICE_PORT`; do sleep 3; done
echo "******* Discovery Service has started"

echo "********************************************************"
echo "Waiting for the basicdb server to start on port $DATABASESERVER_PORT"
echo "********************************************************"
while ! `nc -z basicdb $DATABASESERVER_PORT`; do sleep 3; done
echo "******** BasicDB Server has started "

echo "********************************************************"
echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z configurationserver $CONFIGSERVER_PORT`; do sleep 3; done
echo "*******  Configuration Server has started"

echo "********************************************************"
echo "Starting Basic Service with Configuration Service via Eureka :  $DISCOVERYSERVICE_URI ON PORT: $SERVER_PORT"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
-Deureka.client.serviceUrl.defaultZone=$DISCOVERYSERVICE_URI             \
-Dspring.cloud.config.uri=$CONFIGSERVER_URI                          \
-Dspring.profiles.active=$PROFILE -jar /usr/local/basicservice/app.jar
