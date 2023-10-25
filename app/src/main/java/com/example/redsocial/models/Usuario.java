package com.example.redsocial.models;

import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("avatar")
    public String avatar;

    @SerializedName("collectionId")
    public String collectionId;

    @SerializedName("collectionName")
    public String collectionName;

    @SerializedName("created")
    public String created;

    @SerializedName("emailVisibility")
    public boolean emailVisibility;

    @SerializedName("id")
    public String id;

    @SerializedName("updated")
    public String updated;

    @SerializedName("username")
    public String username;

    @SerializedName("verified")
    public boolean verified;
}
