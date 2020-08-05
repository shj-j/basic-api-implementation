package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllRs() throws Exception {
        mockMvc.perform(get("/rs/list/all"))
                .andExpect(jsonPath("$[0].eventName", is("firstEvent")))
                .andExpect(jsonPath("$[0].category", is("unCategory")))
                .andExpect(jsonPath("$[0].user.userName", is("user1")))
                .andExpect(jsonPath("$[1].eventName", is("secondEvent")))
                .andExpect(jsonPath("$[1].category", is("unCategory")))
                .andExpect(jsonPath("$[1].user.userName", is("user2")))
                .andExpect(jsonPath("$[2].eventName", is("thirdEvent")))
                .andExpect(jsonPath("$[2].category", is("unCategory")))
                .andExpect(jsonPath("$[2].user.userName", is("user3")))
                .andExpect(status().isOk());
    }

    @Test
    void getOneRs() throws Exception{
        mockMvc.perform(get("/rs/list/0"))
                .andExpect(jsonPath("$.eventName", is("firstEvent")))
                .andExpect(jsonPath("$.category", is("unCategory")))
                .andExpect(jsonPath("$.user.userName", is("user1")))
                .andExpect(status().isOk());
    }

    @Test
    void getRsBetween() throws Exception{
        mockMvc.perform(get("/rs/list?start=0&end=1"))
                .andExpect(jsonPath("$[0].eventName").value("firstEvent"))
                .andExpect(jsonPath("$[0].category").value("unCategory"))
                .andExpect(jsonPath("$[0].user.userName", is("user1")))
                .andExpect(jsonPath("$[1].eventName").value("secondEvent"))
                .andExpect(jsonPath("$[1].category").value("unCategory"))
                .andExpect(jsonPath("$[1].user.userName", is("user2")))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneRs() throws Exception {
        mockMvc.perform(delete("/rs/list/0"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("secondEvent")))
                .andExpect(jsonPath("$[0].category", is("unCategory")))
                .andExpect(jsonPath("$[1].eventName", is("thirdEvent")))
                .andExpect(jsonPath("$[1].category", is("unCategory")))
                .andExpect(status().isOk());
    }

    @Test
    void updateOneRs() throws Exception {

        RsEvent rsEvent = new RsEvent("newName","category1", null);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(put("/rs/list?index=0")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/0"))
                .andExpect(jsonPath("$.eventName", is("newName")))
                .andExpect(jsonPath("$.category", is("category1")))
                .andExpect(status().isOk());
    }

    @Test
    void addOneRs()throws Exception{

        User newUser = new User("xiaowang", 19,"female", "a@thoughtworks.com","18888888888");
        RsEvent rsEvent = new RsEvent("fourthEvent", "entertainment", newUser);

        String requestJson = new ObjectMapper().writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/list/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list/3"))
                .andExpect(jsonPath("$.eventName", is("fourthEvent")))
                .andExpect(jsonPath("$.category", is("entertainment")))
                .andExpect(jsonPath("$.user.userName", is("xiaowang")))
                .andExpect(jsonPath("$.user.age", is(19)))
                .andExpect(status().isOk());
        assertEquals(4, RsController.userList.size());
    }

    @Test
    void should_not_user_to_userList_but_add_rs()throws Exception{

        User newUser = new User("user1", 19,"female", "a@thoughtworks.com","18888888888");
        RsEvent rsEvent = new RsEvent("newEvent", "science", newUser);

        String requestJson = new ObjectMapper().writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/list/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(3, RsController.userList.size());
    }

    @Test
    void ageShouldValid() throws Exception{
        User user = new User("validName", 0,"female","a@gmail.com", "11234567890");
        String userJson = new ObjectMapper().writeValueAsString(user);

        RsEvent rsEvent = new RsEvent("newEvent", "science", user);
        String requestJson = new ObjectMapper().writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/list/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
