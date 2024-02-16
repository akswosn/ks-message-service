# Dockerfile
FROM openjdk:21-jdk
EXPOSE 18081

ARG JAR_FILE=./build/libs/ks-message-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]