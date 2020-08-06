package com.thoughtworks.rslist.domain;

import lombok.Data;

@Data
public class RsEvent {
    private String eventName;
    private String category;
    private String userId;

//    public RsEvent(String name, String category){
//        this.eventName = name;
//        this.category = category;
//    }
//
//    public String getEventName(){
//        return eventName;
//    }
//    public void setEventName(String eventName){
//        this.eventName = eventName;
//    }
//
//    public String getCategory(){
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
}
