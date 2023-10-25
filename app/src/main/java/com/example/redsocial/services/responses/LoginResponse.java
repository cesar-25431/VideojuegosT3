package com.example.redsocial.services.responses;

import com.example.redsocial.models.User;

public class LoginResponse {

    private String token;
    private User record;

    public LoginResponse(String token, User record) {
        this.token = token;
        this.record = record;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getRecord() {
        return record;
    }

    public void setRecord(User record) {
        this.record = record;
    }
}
