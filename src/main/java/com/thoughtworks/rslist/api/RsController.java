package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController<e> {

    public List<RsEvent> initialRsList() {
        List<RsEvent> initialList = new ArrayList<>();
        initialList.add(new RsEvent("firstEvent", "unCategory","user1"));
        initialList.add(new RsEvent("secondEvent", "unCategory"));
        initialList.add(new RsEvent("thirdEvent", "unCategory"));
        return  initialList;
    }

    List<RsEvent> rsList = initialRsList();

    private RsEventRepository rsEventRepository;

    @GetMapping("/rs/all")
    public List<RsEvent> getRsAllList(){
        return rsList;
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getRsSubList(@RequestParam(required = false) int start, @RequestParam(required = false) int end){
        return rsList.subList(start-1, end);
    }

    @GetMapping("/rs/list/{index}")
    public RsEvent getOneRsEvent(@PathVariable int index){
        return rsList.get(index-1);
    }

    @PostMapping("/rs/event")
    public void addOneRs( @RequestBody RsEvent rsEvent) {
        RsEventEntity event = RsEventEntity.builder()
                .eventName(rsEvent.getEventName())
                .category(rsEvent.getCategory())
                .userID(rsEvent.getUserId())
                .build();
        rsList.add(rsEvent);
    }

    @DeleteMapping("/rs/list/{index}")
    public void deleteOnRsByIndex(@PathVariable int index){
        rsList.remove(index-1);
    }

    @PostMapping("/rs/change/{index}")
    public RsEvent updateOneRs(@PathVariable int index, @RequestBody RsEvent rsEvent){
        RsEvent needChangeRs = rsList.get(index-1);
        String newName = rsEvent.getEventName();
        String newCategory = rsEvent.getCategory();

        if (! newName.isEmpty()){
            needChangeRs.setEventName(newName);
        }
        if (! newCategory.isEmpty()){
            needChangeRs.setCategory(newCategory);
        }
        return needChangeRs;
    }
}
