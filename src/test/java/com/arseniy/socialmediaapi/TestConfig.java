package com.arseniy.socialmediaapi;


import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class TestConfig {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);

        // JWT Properties
        registry.add("jwt.token.secret", () -> "your-secret-key"); // Replace with your actual secret key
        registry.add("jwt.exp.access", () -> 7 * 24 * 60 * 60 * 1000);
        registry.add("jwt.exp.refresh", () -> 15 * 60 * 1000);
        registry.add("jwt.exp.reset", () -> 15 * 60 * 1000);

        // Mail Properties
        registry.add("spring.mail.properties.mail.smtp.auth", () -> "true");
        registry.add("spring.mail.port", () -> 587);
        registry.add("spring.mail.properties.mail.smtp.starttls.enable", () -> "true");
        registry.add("mail.domain", () -> "your-mail-domain.com"); // Replace with your actual domain
        registry.add("spring.mail.host", () -> "smtp.your-mail-host.com"); // Replace with your actual host
        registry.add("spring.mail.username", () -> "your-mail-username"); // Replace with your actual username
        registry.add("spring.mail.password", () -> "your-mail-password"); // Replace with your actual password

        // DB Properties
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("springdoc.override-with-generic-response", () -> "false");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.properties.hibernate.boot.allow_jdbc_metadata_access", () -> "false");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");

        // Kafka Properties
        registry.add("spring.kafka.bootstrap-servers", () -> "broker:9092");
    }


    }
