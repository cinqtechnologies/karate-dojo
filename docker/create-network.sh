#!/usr/bin/env bash
#docker network create --driver overlay --subnet=192.168.1.0/25 --gateway=192.168.1.100 --attachable cinqtech-dojo
docker network create --driver overlay --attachable cinqtech-dojo