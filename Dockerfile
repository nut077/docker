#FROM openjdk:8-jdk-alpine
#ADD target/docker-school.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

FROM openjdk:8-jdk-alpine
FROM maven
FROM bash