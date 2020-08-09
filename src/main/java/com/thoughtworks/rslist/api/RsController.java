package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RsController{

    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    public RsController(RsEventRepository rsEventRepository, UserRepository userRepository){
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/rs/all")
    public List<RsEventEntity> getRsAllList(){

        return rsEventRepository.findAll();
    }

    @PostMapping("/rs/event")
    public ResponseEntity addOneRs(@RequestBody RsEvent rsEvent) {
        if(! userRepository.existsById(rsEvent.getUserId())){
            return ResponseEntity.badRequest().build();
        }

        RsEventEntity eventEntity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .category(rsEvent.getCategory())
                .userId(rsEvent.getUserId())
                .build();
        rsEventRepository.save(eventEntity);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/rs/list/{rsEventId}")
    public ResponseEntity updateOneRs(@PathVariable Integer rsEventId, @RequestBody RsEventEntity rsEvent){
        RsEventEntity event = rsEventRepository.findById(rsEventId).get();
        Integer userId = event.getUserId();

        if (userId == rsEvent.getUserId()) {
            event.setEventName(rsEvent.getEventName());
            event.setCategory(rsEvent.getCategory());
        } else {
            return ResponseEntity.badRequest().build();
        }
        rsEventRepository.save(event);
        return new ResponseEntity(null, HttpStatus.OK);
    }

//    @GetMapping("/rs/list/{index}")
//    public RsEvent getOneRsEvent(@PathVariable int index){
//
//    }

//    @DeleteMapping("/rs/list/{index}")
//    public void deleteOnRsByIndex(@PathVariable int index){
//
//    }
//

}
