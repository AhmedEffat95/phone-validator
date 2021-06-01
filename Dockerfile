FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY sample.db sample.db
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]