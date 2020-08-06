package com.thoughtworks.rslist.domain;

<<<<<<< HEAD
<<<<<<< HEAD
public class RsEvent {
    private String eventName;
    private String category;

    public RsEvent(String name, String category){
        this.eventName = name;
        this.category = category;
    }

    public String getEventName(){
        return eventName;
    }
=======
=======
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
>>>>>>> customize-response
import lombok.Data;

import javax.validation.Valid;

@Data
public class RsEvent {
    private String eventName;
    private String category;
    private @Valid User user;

    public RsEvent(String name, String category,User user){
        this.eventName = name;
        this.category = category;
        this.user = user;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(User user){
        this.user = user;
    }

    public String getEventName(){
        return eventName;
    }

>>>>>>> validation
    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
<<<<<<< HEAD
=======

>>>>>>> validation
}
