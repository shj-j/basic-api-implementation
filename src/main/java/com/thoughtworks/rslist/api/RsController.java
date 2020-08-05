package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RsController {

    public static List<User> userList;

    User user1 = new User("user1",19,"male","user1@gmail.com","11111111111");
    User user2 = new User("user2",20,"male","user2@gmail.com","12222222222");
    User user3 = new User("user3",20,"male","user3@gmail.com","13333333333");

    public List<RsEvent> rsList = Stream.of(
            new RsEvent("firstEvent", "unCategory",user1),
            new RsEvent("secondEvent", "unCategory",user2),
            new RsEvent("thirdEvent", "unCategory",user3)).collect(Collectors.toList());;


    @GetMapping("/rs/list/all")
    public List<RsEvent> getRsAllList(){
      return rsList;
    }

    @GetMapping("/rs/list/{index}")
    public RsEvent getOneRsEvent(@PathVariable int index){
        return rsList.get(index);
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getRsSubList(@RequestParam(required = false) int start, @RequestParam(required = false) int end){

        return rsList.subList(start, end+1);
    }

    @DeleteMapping("/rs/list/{index}")
    public void deleteOneRsByIndex(@PathVariable int index){
        rsList.remove(index);
    }

    @PutMapping("/rs/list")
    public void updateOneRs(@RequestParam(required = true) int index, @RequestBody RsEvent rsEvent){
        RsEvent needChangeRs = rsList.get(index);
        String newName = rsEvent.getEventName();
        String newCategory = rsEvent.getCategory();

        if (! newName.isEmpty()){
            needChangeRs.setEventName(newName);
        }
        if (! newCategory.isEmpty()){
            needChangeRs.setCategory(newCategory);
        }

    }

    @PostMapping("/rs/list/event")
    public void addOneRs(@RequestBody RsEvent rsEvent){
//        User user = rsEvent.getUser();
        rsList.add(rsEvent);

    }


}
