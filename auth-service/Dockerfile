FROM openjdk:17-alpine
WORKDIR /usr/src/app

COPY target/auth-service-0.0.1-SNAPSHOT.jar /usr/src/app/auth-service-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties /usr/src/app/application.properties

EXPOSE 8081
CMD ["java", "-jar", "/usr/src/app/auth-service-0.0.1-SNAPSHOT.jar"]
