package com.example.redsocial.models;

import com.google.gson.annotations.SerializedName;

public class ExpandComentario {
    @SerializedName("user")
    public Usuario user;

    public ExpandComentario() {

    }

    public ExpandComentario(Usuario user) {
        this.user = user;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }


}
