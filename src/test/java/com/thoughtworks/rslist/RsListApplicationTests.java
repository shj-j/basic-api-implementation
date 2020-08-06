package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RsListApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    void shouldAddUser() throws Exception {
        User user = new User("name 0", 20,"female", "name1@gmail.com","18888888888");
        String request = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/users").content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<UserEntity> users = userRepository.findAll();
        assertEquals(1,users.size());
        assertEquals("name 0", users.get(0).getUserName());
    }

}
