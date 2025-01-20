package com.metarash.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.metarash.backend.dto.TaskDto;
import com.metarash.backend.dto.UserCredentialsDto;
import com.metarash.backend.model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.time.ZonedDateTime;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateTask() throws Exception {
        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("lnebylovskij@bk.ru");
        userCredentialsDto.setPassword("12345");

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCredentialsDto)))
                .andExpect(status().isOk())
                .andReturn();

        ZonedDateTime time = ZonedDateTime.now();

        String responseBody = loginResult.getResponse().getContentAsString();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<>() {});
        String jwtToken = (String) responseMap.get("token");

        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test Task");
        taskDto.setDescription("This is a test task");
        taskDto.setStatus(TaskStatus.PENDING);
        taskDto.setUsername("metarash");

        // Выполнение запроса
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskDto))
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Task created successfully"));
    }
}
