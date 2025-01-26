package com.arseniy.socialmediaapi.auth;

import com.arseniy.socialmediaapi.TestConfig;
import com.arseniy.socialmediaapi.auth.domain.LoginRequest;
import com.arseniy.socialmediaapi.auth.domain.RegisterRequest;
import com.arseniy.socialmediaapi.jwt.config.JwtConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class AuthControllerTest {


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("jwt.secret", () -> "L8vJh5TDoBFOG7QXWRh3zqf3Ib0BdsFt5SHXSaOkq90=");
    }

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("testDb")
            .withUsername("postgres")
            .withPassword("password");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Optionally, perform any setup actions here, such as clearing the database or seeding data
        // Verify that JwtConfig properties are set correctly
        System.out.println("JWT Token Secret: " + jwtConfig.getSecret());
        System.out.println("JWT Exp Access: " + jwtConfig.getExpAccess());
        System.out.println("JWT Exp Refresh: " + jwtConfig.getExpRefresh());
        System.out.println("JWT Exp Reset: " + jwtConfig.getExpReset());
    }


    @Test
    void contextLoads() {
    }


    @Test
    void registerUserTest() throws Exception {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Arseniy")
                .email("email@email.com")
                .username("User12345")
                .password("12345Password")
                .build();


        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(registerRequest))
                )
                .andExpect(status().isOk());

    }



    @BeforeEach
    public void registerUser() throws Exception {

        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Will")
                .email("Will@email.com")
                .username("Will12345")
                .password("12345Password")
                .build();


        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(registerRequest))
                )
                .andExpect(status().isOk());

    }




    @Test
    void loginUserTest() throws Exception {


        LoginRequest loginRequest= LoginRequest.builder()
                .username("Will12345")
                .password("12345Password")
                .build();

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(loginRequest))
                )
                .andExpect(status().isOk());

    }




}