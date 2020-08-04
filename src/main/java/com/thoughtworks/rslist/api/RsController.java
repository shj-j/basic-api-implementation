package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController<e> {

    public List<RsEvent> initialRsList() {
        List<RsEvent> initialList = new ArrayList<>();
        initialList.add(new RsEvent("firstEvent", "unCategory"));
        initialList.add(new RsEvent("secondEvent", "unCategory"));
        initialList.add(new RsEvent("thirdEvent", "unCategory"));
        return  initialList;
    }

    List<RsEvent> rsList = initialRsList();

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
        rsList.add(rsEvent);
    }
}
