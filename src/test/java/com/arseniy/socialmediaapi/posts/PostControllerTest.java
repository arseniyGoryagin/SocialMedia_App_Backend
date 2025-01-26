package com.arseniy.socialmediaapi.posts;


import com.arseniy.socialmediaapi.auth.domain.RegisterRequest;
import com.arseniy.socialmediaapi.auth.domain.TokenResponse;
import com.arseniy.socialmediaapi.posts.domain.Post;
import com.arseniy.socialmediaapi.posts.domain.PostRequest;
import com.arseniy.socialmediaapi.user.UserRepository;
import com.arseniy.socialmediaapi.user.domain.User;
import com.arseniy.socialmediaapi.user.exceptions.NoSuchUserException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class PostControllerTest {


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
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    private String jwtToken;
    private User testUser;



    private void registerUser() throws Exception {

        userRepository.deleteAll();

        RegisterRequest registerRequest = RegisterRequest.builder()
                .name("Will")
                .email("Will@email.com")
                .username("Will12345")
                .password("12345Password")
                .build();


       MvcResult result = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(registerRequest))
                )
                .andExpect(status().isOk()).andReturn();

       System.out.println(result.getResponse().getContentAsString());

       TokenResponse tokenResponse = objectMapper.readValue(result.getResponse().getContentAsString(), TokenResponse.class);
       jwtToken =tokenResponse.getToken();

       testUser = userRepository.findByUsername("Will12345").orElseThrow(() -> new NoSuchUserException("No such user exception"));
    }

    private void insertPosts(){

        postRepository.deleteAll();

        Post post = Post
                .builder()
                .id(1L)
                .edited(false)
                .body("Post 1")
                .user(testUser)
                .timePosted(LocalDateTime.now())
                .build();

        Post post2 = Post
                .builder()
                .id(2L)
                .edited(false)
                .body("Post 2")
                .user(testUser)
                .timePosted(LocalDateTime.now())
                .build();


        postRepository.save(post);
        postRepository.save(post2);


    }


    @BeforeEach
    public void setUp() throws Exception {
        registerUser();
        insertPosts();
    }


    @Test
    void testMakePost() throws Exception {

        PostRequest postRequest = PostRequest.builder()
                .body("Hello test post")
                .build();

        mockMvc.perform(post("/posts")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postRequest))
                )
                .andExpect(status().isOk());

    }

    @Test
    void testGetFeed() throws Exception {


        MvcResult mvcResult = mockMvc.perform(get("/posts").header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk()).andReturn();


    }

}