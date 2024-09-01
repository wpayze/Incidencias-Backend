FROM openjdk:17-alpine
WORKDIR /usr/src/app

COPY target/issue-service-0.0.1-SNAPSHOT.jar /usr/src/app/issue-service-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties /usr/src/app/application.properties

EXPOSE 8080
CMD ["java", "-jar", "/usr/src/app/issue-service-0.0.1-SNAPSHOT.jar"]
