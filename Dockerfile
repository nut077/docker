#FROM openjdk:8-jdk-alpine
#ADD target/docker-school.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]

FROM openjdk:8-slim
RUN apt-get update && \
 apt-get install -y netcat;
COPY wait-for.sh /opt/wait-for.sh
COPY target/docker-school.jar /opt/app.jar