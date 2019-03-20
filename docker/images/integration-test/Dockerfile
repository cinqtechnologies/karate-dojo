FROM maven:3.5.2-jdk-8-alpine

COPY entrypoint.sh /entrypoint.sh

ENV KARATE_ENVIRONMENT=${KARATE_ENVIRONMENT:-docker}

ENTRYPOINT ["/entrypoint.sh"]