package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void getOneRs() throws Exception{
        mockMvc.perform(get("/rs/list/1"))
                .andExpect(jsonPath("$.eventName", is("firstEvent")))
                .andExpect(jsonPath("$.category", is("unCategory")))
                .andExpect(status().isOk());
    }

    @Test
    void addOneRs() throws Exception{

        RsEvent rsEvent = new RsEvent("fourthEvent", "unCategory");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        getAllRs();
    }

    @Test
    void getRsBetween() throws Exception{
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName").value("firstEvent"))
                .andExpect(jsonPath("$[0].category").value("unCategory"))
                .andExpect(jsonPath("$[1].eventName").value("secondEvent"))
                .andExpect(jsonPath("$[1].category").value("unCategory"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOneRs() throws Exception {
        mockMvc.perform(delete("/rs/list/delete/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/all"))
                .andExpect(jsonPath("$[0].eventName", is("secondEvent")))
                .andExpect(jsonPath("$[0].category", is("unCategory")))
                .andExpect(jsonPath("$[1].eventName", is("thirdEvent")))
                .andExpect(jsonPath("$[1].category", is("unCategory")))
                .andExpect(status().isOk());

    }

    @Test
    void getAllRs() throws Exception {
        mockMvc.perform(get("/rs/all"))
                .andExpect(jsonPath("$[0].eventName", is("firstEvent")))
                .andExpect(jsonPath("$[0].category", is("unCategory")))
                .andExpect(jsonPath("$[1].eventName", is("secondEvent")))
                .andExpect(jsonPath("$[1].category", is("unCategory")))
                .andExpect(jsonPath("$[2].eventName", is("thirdEvent")))
                .andExpect(jsonPath("$[2].category", is("unCategory")))
                .andExpect(status().isOk());
    }

    @Test
    void changeOneRsEventName() throws Exception {

        RsEvent rsEvent = new RsEvent("changeFirstEvent","");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/change/1")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventName").value("changeFirstEvent"))
                .andExpect(status().isOk());
    }

    @Test
    void changeOneRsEventCategory() throws Exception {

        RsEvent rsEvent = new RsEvent("","category1");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/change/1")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.category").value("category1"))
                .andExpect(status().isOk());
    }

    @Test
    void changeOneRs() throws Exception {

        RsEvent rsEvent = new RsEvent("newName","category1");

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/change/1")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.eventName").value("newName"))
                .andExpect(jsonPath("$.category").value("category1"))
                .andExpect(status().isOk());
    }



}
