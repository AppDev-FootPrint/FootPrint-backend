FROM openjdk:17-jdk-alpine
ARG JAR_FILE=./FootPrint-backend/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]