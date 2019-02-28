FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=web-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} sita-karate-hw.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Duser.timezone=America/Sao_Paulo","-Djava.security.egd=file:/dev/./urandom","-jar","/sita-karate-hw.jar"]
