FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=roman-calculator-rs-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /roman-calculator-rs.jar
COPY entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
