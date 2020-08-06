package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
=======
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
>>>>>>> validation
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
<<<<<<< HEAD
public class RsController<e> {

    public List<RsEvent> initialRsList() {
        List<RsEvent> initialList = new ArrayList<>();
        initialList.add(new RsEvent("firstEvent", "unCategory"));
        initialList.add(new RsEvent("secondEvent", "unCategory"));
        initialList.add(new RsEvent("thirdEvent", "unCategory"));
        return  initialList;
    }

    List<RsEvent> rsList = initialRsList();

    @GetMapping("/rs/all")
    public List<RsEvent> getRsAllList(){
        return rsList;
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getRsSubList(@RequestParam(required = false) int start, @RequestParam(required = false) int end){
        return rsList.subList(start-1, end);
=======
public class RsController {

    public static List<User> userList;
    public List<RsEvent> rsList;

    public RsController(){
        User user1 = new User("user1",19,"male","user1@gmail.com","11111111111");
        User user2 = new User("user2",20,"male","user2@gmail.com","12222222222");
        User user3 = new User("user3",20,"male","user3@gmail.com","13333333333");

        rsList = Stream.of(
                new RsEvent("firstEvent", "unCategory",user1),
                new RsEvent("secondEvent", "unCategory",user2),
                new RsEvent("thirdEvent", "unCategory",user3)).collect(Collectors.toList());

        userList = Stream.of(user1, user2,user3).collect(Collectors.toList());
    }


    @GetMapping("/rs/list/all")
    public List<RsEvent> getRsAllList(){
      return rsList;
>>>>>>> validation
    }

    @GetMapping("/rs/list/{index}")
    public RsEvent getOneRsEvent(@PathVariable int index){
<<<<<<< HEAD
        return rsList.get(index-1);
    }

    @PostMapping("/rs/event")
    public void addOneRs( @RequestBody RsEvent rsEvent) {
        rsList.add(rsEvent);
    }

    @DeleteMapping("/rs/list/delete/{index}")
    public void deleteOnRsByIndex(@PathVariable int index){
        rsList.remove(index-1);
    }

    @PostMapping("/rs/change/{index}")
    public RsEvent changeOneRs(@PathVariable int index, @RequestBody RsEvent rsEvent){
        RsEvent needChangeRs = rsList.get(index-1);
=======
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
>>>>>>> validation
        String newName = rsEvent.getEventName();
        String newCategory = rsEvent.getCategory();

        if (! newName.isEmpty()){
            needChangeRs.setEventName(newName);
        }
        if (! newCategory.isEmpty()){
            needChangeRs.setCategory(newCategory);
        }
<<<<<<< HEAD
        return needChangeRs;
=======

    }

    @PostMapping("/rs/list/event")
    public void addOneRs(@RequestBody @Valid RsEvent rsEvent){
        User user = rsEvent.getUser();
        String category = rsEvent.getCategory();
        String eventName = rsEvent.getEventName();
        long count = rsList.stream().filter(c->c.getUser().getUserName().equals(user.getUserName())).count();
        if(count == 0) {
            userList.add(user);
        }
        rsList.add(rsEvent);
>>>>>>> validation
    }
}
