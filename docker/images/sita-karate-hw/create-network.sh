#!/usr/bin/env bash
docker network create --driver overlay --subnet=192.168.1.0/25 --gateway=192.168.1.100 --attachable sita-wtropen