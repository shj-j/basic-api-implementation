package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
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
                .userID(rsEvent.getUserId())
                .build();
        rsEventRepository.save(eventEntity);
        return ResponseEntity.ok().build();
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
//    @PostMapping("/rs/change/{index}")
//    public RsEvent updateOneRs(@PathVariable int index, @RequestBody RsEvent rsEvent){
//
//    }
}
