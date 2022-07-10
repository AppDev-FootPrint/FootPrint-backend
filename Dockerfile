FROM openjdk:17-jdk-alpine
ARG JAR_FILE=./FootPrint-backend/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["echo 'jar 파일 실행!!'"]
ENTRYPOINT ["pwd"]
ENTRYPOINT ["java","-jar","/app.jar"]