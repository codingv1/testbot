FROM openjdk:8-jdk-alpine

ARG JAR_FILE=history.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-.jar"]jar", "application