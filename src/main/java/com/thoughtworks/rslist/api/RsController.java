package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController{


    private final RsEventRepository rsEventRepository;
    public RsController(RsEventRepository rsEventRepository){
        this.rsEventRepository = rsEventRepository;
    }

    @GetMapping("/rs/all")
    public List<RsEventEntity> getRsAllList(){

        return rsEventRepository.findAll();
    }

    @PostMapping("/rs/event")
    public void addOneRs( @RequestBody RsEvent rsEvent) {
        RsEventEntity eventEntity = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .category(rsEvent.getCategory())
                .userID(rsEvent.getUserId())
                .build();
        rsEventRepository.save(eventEntity);
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
