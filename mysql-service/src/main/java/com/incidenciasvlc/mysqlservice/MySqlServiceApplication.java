package com.incidenciasvlc.mysqlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class MySqlServiceApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MySqlServiceApplication.class, args);

        ConfigurableEnvironment  env = context.getEnvironment();

        System.out.println("SPRING_DATASOURCE_URL: " + env.getProperty("spring.datasource.url"));
        System.out.println("SPRING_DATASOURCE_USERNAME: " + env.getProperty("spring.datasource.username"));
        System.out.println("SPRING_DATASOURCE_PASSWORD: " + env.getProperty("spring.datasource.password"));
        System.out.println("SPRING_DATASOURCE_DRIVER_CLASS_NAME: " + env.getProperty("spring.datasource.driver-class-name"));
        System.out.println("SPRING_JPA_HIBERNATE_DDL_AUTO: " + env.getProperty("spring.jpa.hibernate.ddl-auto"));
        System.out.println("SERVER_PORT: " + env.getProperty("server.port"));
    }

}
