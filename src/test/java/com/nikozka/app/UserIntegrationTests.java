package com.nikozka.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikozka.app.dtos.UserDto;
import com.nikozka.app.model.ErrorResponse;
import com.nikozka.app.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TaskManagerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:test.properties")
class UserIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenValidUserRegisteredThanReturn201() throws Exception {
        UserDto userDto = new UserDto("testUser", "testPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenExistingValidUserRegisteredThanReturn409() throws Exception {
        UserDto userDto = new UserDto("testUser", "testPassword");
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isConflict());
    }

    @Test
    void whenInvalidUserRegisteredThanReturn400() throws Exception {
        UserDto userDto = new UserDto("t", "t");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(
                        "Password must be between 8 and 255 characters, User name must be between 5 and 255 characters"));
    }
}
