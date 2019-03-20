#!/usr/bin/env bash


BASEDIR=$(dirname "$0")
echo "==> BASEDIR = $BASEDIR"

echo '==> building the docker images'
mvn clean package -Pbuild-docker-image -U -f $BASEDIR/../pom.xml

echo '==> removing cinqtech stack'
echo `docker stack rm cinqtech`
sleep 3
echo '==> removing cinqtech-dojo network'
echo `docker network rm cinqtech-dojo`
sleep 13

SKIP_PRUNE="y"
while getopts "s:" arg; do
  case $arg in
    s)
      SKIP_PRUNE=$OPTARG
      echo "-- SKIPPING DOCKER PRUNE: ${SKIP_PRUNE}"
      ;;
  esac
done

if [ "x${SKIP_PRUNE}" != "xy" ]; then

  echo "==> aplying prune on images, containers and volumes"
  for command in {image,container,volume} ;do echo `docker $command prune -f`; done
fi

sleep 3


echo '==> creating network'
${BASEDIR}/create-network.sh
sleep 3

echo '==> deploying wtropen stack'
echo `docker stack deploy --compose-file ${BASEDIR}/cinqtechdojo-compose.yml cinqtech`
sleep 3

echo "==> listing docker services (docker service ls | grep cinqtech)";
docker service ls | grep cinqtech