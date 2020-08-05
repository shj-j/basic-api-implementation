package com.thoughtworks.rslist.domain;

import lombok.Data;

@Data
public class RsEvent {
    private String eventName;
    private String category;
    private User user;

    public RsEvent(String name, String category,User user){
        this.eventName = name;
        this.category = category;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getEventName(){
            return eventName;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
