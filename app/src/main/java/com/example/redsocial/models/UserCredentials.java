package com.example.redsocial.models;

public class UserCredentials {

    public String identity;
    public String password;

    public UserCredentials() {
    }

    public UserCredentials(String identity, String password) {
        this.identity = identity;
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
