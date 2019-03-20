#!/usr/bin/env sh
echo "===> STARTING OFF..."

java -Dspring.profiles.active=prod -Duser.timezone=America/Sao_Paulo -jar /roman-calculator-rs.jar