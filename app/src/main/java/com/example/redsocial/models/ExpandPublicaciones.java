package com.example.redsocial.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpandPublicaciones {
    @SerializedName("usuario")
    public Usuario usuario;

    @SerializedName("comentarios(pub)")
    public List<Comentario> comentarios;

    public ExpandPublicaciones() {
    }

    public ExpandPublicaciones(Usuario usuario, List<Comentario> comentarios) {
        this.usuario = usuario;
        this.comentarios = comentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
