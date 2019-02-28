#!/usr/bin/env bash


function wait_for_mysql() {
sleep 1
  echo "=> waitting mysql to come up..."
  SERVICEID=
  STATUS=
  until false; do
    sleep 1
    SERVICEID=$(docker service ls | grep wtropen | grep mysql | awk '{print $1}')
    STATUS=$(docker service ls | grep wtropen | grep mysql | awk '{print $4}')
    RUNNING=""
    [[ "1/1" == "$STATUS" ]] && RUNNING="Y"

    STATUS_RUNNING="No"
    [[ "x$RUNNING" != "x" ]] && STATUS_RUNNING="Yes"
    echo "=> SERVICEID=$SERVICEID | STATUS=$STATUS | RUNNING=$STATUS_RUNNING"
    [[ "x$RUNNING" != "x" ]] && break
  done

  unset SERVICEID
  unset STATUS
  unset RUNNING
  unset STATUS_RUNNING
}

echo '==> removing wtropen stack'
echo `docker stack rm wtropen`
sleep 3
echo '==> removing sita-wtropen network'
echo `docker network rm sita-wtropen`
sleep 13

SKIP_PRUNE=""
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
  for command in {image,container,volume} ;do echo `docker volume prune -f`; done
fi

sleep 3

BASEDIR=$(dirname "$0")
echo "==> BASEDIR = $BASEDIR"

echo '==> creating network'
${BASEDIR}/images/sita-karate-hw/create-network.sh
sleep 3

echo '==> deplying wtropen stack'
echo `docker stack deploy --compose-file ${BASEDIR}/karate-compose.yml wtropen`
sleep 3

wait_for_mysql

CONTAINERID=$(docker ps | grep wtropen | grep mysql | awk '{print $1}')
sleep 30
docker exec -i $CONTAINERID mysql -uroot -padmin << EOF
DELIMITER //
CREATE database karate;
    //
DELIMITER ;
EOF

echo "==> creating sql schema on container ${CONTAINERID}";
${BASEDIR}/images/mysql/create-schema.sh "$CONTAINERID"
echo "==> listing docker services (docker service ls)";
docker service ls | grep wtropen