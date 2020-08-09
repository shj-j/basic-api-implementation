package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class VoteController {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    public VoteController(VoteRepository voteRepository,
                          UserRepository userRepository,
                          RsEventRepository rsEventRepository ) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @PostMapping("/rs/vote/{rsEventId}")
    void voteForRsEvent(@PathVariable int rsEventId, @RequestBody VoteEntity voteEntity){
        Integer userId = voteEntity.getUserId();

        Optional<RsEventEntity> event = rsEventRepository.findById(rsEventId);

        Optional<UserEntity> user = userRepository.findById(voteEntity.getUserId());
        int id = user.get().getId();

        if (user.get().getVoteNum() >= voteEntity.getVoteNum()){
            VoteEntity vote = VoteEntity.builder()
                    .localDateTime(LocalDateTime.now())
                    .voteNum(voteEntity.getVoteNum())
                    .userId(user.get().getId())
                    .eventId(event.get().getId())
                    .build();
            voteRepository.save(vote);
        }


    }

}
