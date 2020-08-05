package com.thoughtworks.rslist.exception;

import lombok.Data;

@Data
public class CommonError {
    private String error;

    public void setError(String message) {
        this.error = message;
    }
    public String getError(){
        return error;
    }
}
