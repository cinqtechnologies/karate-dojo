FROM maven:3.5.2-jdk-8-alpine

COPY ci-entrypoint.sh /ci-entrypoint.sh

ENV KARATE_ENVIRONMENT=${KARATE_ENVIRONMENT:-docker}

ENTRYPOINT ["/ci-entrypoint.sh"]