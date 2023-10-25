package com.example.redsocial.models;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;


import kotlin.jvm.Transient;

@Entity(tableName = "publicaciones")
public class Publicacion {
    @SerializedName("collectionId")
    public String collectionId;

    @SerializedName("collectionName")
    public String collectionName;

    @SerializedName("created")
    public String created;

    @SerializedName("descripcion")
    public String descripcion;

    @Transient
    @SerializedName("expand")
    public ExpandPublicaciones expand;

    @SerializedName("id")
    public String id;

    @SerializedName("imagen")
    public String imagen;

    @SerializedName("ubicacion")
    public String ubicacion;

    @SerializedName("updated")
    public String updated;

    @SerializedName("usuario")
    public String usuario;

    // Getters y setters


    public Publicacion() {
    }

    public Publicacion(String collectionId, String collectionName, String created, String descripcion, ExpandPublicaciones expand, String id, String imagen, String ubicacion, String updated, String usuario) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.created = created;
        this.descripcion = descripcion;
        this.expand = expand;
        this.id = id;
        this.imagen = imagen;
        this.ubicacion = ubicacion;
        this.updated = updated;
        this.usuario = usuario;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ExpandPublicaciones getExpand() {
        return expand;
    }

    public void setExpand(ExpandPublicaciones expand) {
        this.expand = expand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
