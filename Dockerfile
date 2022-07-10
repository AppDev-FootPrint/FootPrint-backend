FROM openjdk:17-jdk-alpine
WORKDIR /home/runner/work/FootPrint-backend
ARG JAR_FILE=/home/runner/work/FootPrint-backend/FootPrint-backend/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
