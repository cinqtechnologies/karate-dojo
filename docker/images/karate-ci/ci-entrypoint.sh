#!/usr/bin/env bash

until false; do

  if [ ! -f "/tmp/app/lock" ]; then

    if [ -d "/tmp/app/ci" ]; then
      echo "==> Running mvn clean package"
      mvn clean package -f /tmp/app/pom.xml
      echo "==> Running mvn clean test"
      echo "==> KARATE_ENVIRONMENT = ${KARATE_ENVIRONMENT}"
      mvn clean test -f /tmp/app/ci/pom.xml -Dtest=GreetingRunner -Dkarate.env=${KARATE_ENVIRONMENT} -DargLine="-Dkarate.env=${KARATE_ENVIRONMENT}"
      cp -rfv /tmp/app/ci/target/surefire-reports/greeting.greeting.html /external/nginx/home/index.html
      cp -rfv /tmp/app/ci/target/surefire-reports/*.html /external/nginx/home/
    else
      echo "==> Directory /tmp/app/ci not found!"
    fi
  else
    echo "==> I'm locked, remove the lock file from the root project..."
  fi

  for ((i=0;i<10;i++)); do echo -n "."; sleep 1; done
  echo "."
#  sleep 10
done
