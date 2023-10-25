package com.example.redsocial.models;

import com.google.gson.annotations.SerializedName;

public class Comentario {
    @SerializedName("collectionId")
    public String collectionId;

    @SerializedName("collectionName")
    public String collectionName;

    @SerializedName("comentario")
    public String comentario;

    @SerializedName("created")
    public String created;

    @SerializedName("id")
    public String id;

    @SerializedName("pub")
    public String pub;

    @SerializedName("updated")
    public String updated;

    @SerializedName("user")
    public String user;

    @SerializedName("expand")
    public ExpandComentario expand;

    public Comentario() {
    }

    public Comentario(String collectionId, String collectionName, String comentario, String created, String id, String pub, String updated, String user, ExpandComentario expand) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.comentario = comentario;
        this.created = created;
        this.id = id;
        this.pub = pub;
        this.updated = updated;
        this.user = user;
        this.expand = expand;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ExpandComentario getExpand() {
        return expand;
    }

    public void setExpand(ExpandComentario expand) {
        this.expand = expand;
    }
}
