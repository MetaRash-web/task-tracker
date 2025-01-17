package com.metarash.backend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metarash.backend.dto.UserCredentialsDto;
import com.metarash.backend.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BackendApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterAndGetUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test3@example.com");
        userDto.setPassword("password123");
        userDto.setUsername("test3");

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        userCredentialsDto.setEmail("test2@example.com");
        userCredentialsDto.setPassword("password123");

        MvcResult registerResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCredentialsDto)))
                .andExpect(status().isOk())
                .andReturn();

//        MvcResult registerResult = mockMvc.perform(post("/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userDto)))
//                .andExpect(status().isOk())
//                .andReturn();

        // Extract JWT token from the response header
        String responseBody = registerResult.getResponse().getContentAsString();
        System.out.println("Response Body: " + responseBody);

        Map<String, Object> responseMap = objectMapper.readValue(responseBody, new TypeReference<>() {});
        String jwtToken = (String) responseMap.get("token");
        System.out.println("Extracted JWT Token: " + jwtToken);

        mockMvc.perform(get("/user")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test2@example.com"));
    }
}
