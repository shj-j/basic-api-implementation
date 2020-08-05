package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
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
    public ResponseEntity<List<RsEvent>> getRsAllList(){
      return ResponseEntity.ok(rsList);
    }

    @GetMapping("/rs/list/{index}")
    public ResponseEntity<RsEvent> getOneRsEvent(@PathVariable int index){

        return ResponseEntity.ok(rsList.get(index));
    }

    @GetMapping("/rs/list")
    public ResponseEntity<List<RsEvent>> getRsSubList(@RequestParam(required = false) int start, @RequestParam(required = false) int end){

        return ResponseEntity.ok(rsList.subList(start, end+1));
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
    public ResponseEntity<Integer> addOneRs(@RequestBody @Valid RsEvent rsEvent){
        User user = rsEvent.getUser();
        String category = rsEvent.getCategory();
        String eventName = rsEvent.getEventName();
        long count = rsList.stream().filter(c->c.getUser().getUserName().equals(user.getUserName())).count();
        if(count == 0) {
            userList.add(user);
        }
        rsList.add(rsEvent);
        int index = rsList.size() - 1;
        return ResponseEntity.created(null).header(String.valueOf(index)).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }
}
