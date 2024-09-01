FROM openjdk:17-alpine
WORKDIR /usr/src/app

COPY target/mysql-service-0.0.1-SNAPSHOT.jar /usr/src/app/mysql-service-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application.properties /usr/src/app/application.properties

EXPOSE 8084
CMD ["java", "-jar", "/usr/src/app/mysql-service-0.0.1-SNAPSHOT.jar"]
