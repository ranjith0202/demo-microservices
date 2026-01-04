#!/bin/sh
echo "Waiting for Eureka to be ready..."

# Wait until Eureka is up
until curl -s $EUREKA_URL/actuator/health | grep UP > /dev/null; do
  echo -n "."
  sleep 2
done

echo "Eureka is up! Starting Auth Service..."
java -jar /app/auth-Service-0.0.1-SNAPSHOT.jar
