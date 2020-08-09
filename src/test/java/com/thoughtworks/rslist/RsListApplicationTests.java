package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    VoteRepository voteRepository;

    @BeforeEach
    void cleanup(){
        rsEventRepository.deleteAll();
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
        int userId = entity.getId();

        mockMvc.perform(get("/users/"+userId))
                .andExpect(jsonPath("$.userName",is("name 0")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddRsEventWhenUserExist() throws Exception {
        UserEntity entity = UserEntity.builder()
                .userName("name 0")
                .gender("female")
                .age(20)
                .email("name0@gmail.com")
                .phone("18888888888")
                .build();
        entity = userRepository.save(entity);
        Integer userId = entity.getId();

        RsEvent rsEvent = new RsEvent("event 0","category1", userId );

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<RsEventEntity> rsEvents = rsEventRepository.findAll();

        assertEquals("event 0", rsEvents.get(0).getEventName());
        assertEquals(1, rsEvents.size());
        assertEquals(userId, rsEvents.get(0).getId());

    }

    @Test
    void shouldNotAddRsEventWhenUserNotExist() throws Exception{
        Integer userId = 1000;
        RsEvent rsEvent = new RsEvent("event 0","category1", userId );

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteRsEventsWhenDeletedUser() throws Exception{
        UserEntity user = UserEntity.builder()
                .userName("name 0")
                .gender("female")
                .age(20)
                .email("name0@gmail.com")
                .phone("18888888888")
                .build();
        user = userRepository.save(user);
        int userId = user.getId();

        RsEventEntity rsEvent = RsEventEntity.builder()
                .eventName("event 0")
                .category("category1")
                .userId(userId)
                .build();
        rsEventRepository.save(rsEvent);

        mockMvc.perform(delete("/users/"+userId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<RsEventEntity> rsEvents = rsEventRepository.findAll();
        assertEquals(0, rsEvents.size());
    }

    @Test
    void shouldUpdateRsEventWhenMatchUserId() throws Exception{
        UserEntity user = UserEntity.builder()
                .userName("name 0")
                .gender("female")
                .age(20)
                .email("name0@gmail.com")
                .phone("18888888888")
                .build();
        user = userRepository.save(user);
        int userId = user.getId();


        RsEventEntity rsEvent = RsEventEntity.builder()
                .eventName("event 0")
                .category("category 0")
                .userId(userId)
                .build();
        rsEventRepository.save(rsEvent);
        int eventId = rsEvent.getId();

        RsEventEntity ReplaceRsEvent = RsEventEntity.builder()
                .eventName("new event")
                .category("category 1")
                .userId(userId)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(ReplaceRsEvent);

        mockMvc.perform(patch("/rs/list/"+eventId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<RsEventEntity> updateEvent = rsEventRepository.findById(eventId);

        assertEquals("new event", updateEvent.get().getEventName());
        assertEquals("category 1", updateEvent.get().getCategory());
    }
    @Test
    void shouldGive400WhenUserIdNotMatch() throws Exception{

        RsEventEntity rsEvent = RsEventEntity.builder()
                .eventName("event 0")
                .category("category 0")
                .userId(100)
                .build();
        rsEventRepository.save(rsEvent);
        int eventId = rsEvent.getId();

        RsEventEntity ReplaceRsEvent = RsEventEntity.builder()
                .eventName("new event")
                .category("category 1")
                .userId(1)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(ReplaceRsEvent);

        mockMvc.perform(patch("/rs/list/"+eventId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldVoteWhenUserVoteNumEnough() throws Exception{
        UserEntity user = UserEntity.builder()
                .userName("user 0")
                .gender("male")
                .age(20)
                .email("12@34.com")
                .phone("13579246810")
                .voteNum(3)
                .build();
        user = userRepository.save(user);

        RsEventEntity event = RsEventEntity.builder()
                .eventName("new event")
                .category("category 1")
                .userId(user.getId())
                .build();
        event = rsEventRepository.save(event);
        Integer rsEventId = event.getId();

        VoteEntity vote = VoteEntity.builder()
                .localDateTime(LocalDateTime.now())
                .voteNum(1)
                .userId(user.getId())
                .eventId(rsEventId)
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(vote);

        mockMvc.perform(post("rs/vote"+rsEventId).content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<VoteEntity> updateVote = voteRepository.findByUserIdAndRsEventId(user.getId(), event.getId());

        assertEquals("1", updateVote.size());
    }
}
