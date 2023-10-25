package com.example.redsocial.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MultipartBody;

public class Publication {
    public String id;
    public String descripcion;
    public String ubicacion;
    public String image;
    public String created;
    @SerializedName("usuario")
    public User usuario;

    public class Expand {
        public User usuario;
    }

    public Publication() {
    }

    public Publication(String id, String descripcion, String ubicacion, String image,String created, Expand expand) {
        Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","Aqui");
        this.id = id;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.image = image != null ? "http://146.190.60.137:8090/api/files/publicacion/" + id + "/" +image : "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(created);
            this.created = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            this.created = "";
        }

        this.usuario = expand.usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
