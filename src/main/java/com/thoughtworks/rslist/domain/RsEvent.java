package com.thoughtworks.rslist.domain;

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
