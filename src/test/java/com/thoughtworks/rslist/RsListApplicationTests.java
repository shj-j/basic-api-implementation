package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.api.UserController;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void cleanup(){
      userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        User user = new User("name 0", 20,"female", "name0@gmail.com","18888888888");
        String request = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/users").content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();
        assertEquals(1,users.size());
        assertEquals("name 0", users.get(0).getUserName());
    }

    @Test
    void shouldDeleteUserByUserId() throws Exception{
        UserEntity entity = UserEntity.builder()
                .userName("name 0")
                .gender("female")
                .age(20)
                .email("name0@gmail.com")
                .phone("18888888888")
                .build();
        userRepository.save(entity);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();
        assertEquals(0,users.size());
    }

    @Test
    void shouldGetUserByUserId() throws Exception{
        UserEntity entity = UserEntity.builder()
                .userName("name 0")
                .gender("female")
                .age(20)
                .email("name0@gmail.com")
                .phone("18888888888")
                .build();
        userRepository.save(entity);

        mockMvc.perform(get("/users/1"))
                .andExpect(jsonPath("$.userName",is("name 0")))
                .andExpect(status().isOk());
    }
}
