package com.example.redsocial.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "comentarios",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "usuario_id"),
                @ForeignKey(entity = Publicacion.class, parentColumns = "id", childColumns = "publicacion_id")
        })
public class Comentario {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contenido")
    private String contenido;

    @ColumnInfo(name = "usuario_id")
    private int usuarioId;

    @ColumnInfo(name = "publicacion_id")
    private int publicacionId;

    @ColumnInfo(name = "fecha_creacion")
    private String fechaCreacion;

    private int sincronizado;

    // Constructor vacío
    public Comentario() {
    }


    // Constructor
    public Comentario(int id, String contenido, int usuarioId, int publicacionId, String fechaCreacion) {
        this.id = id;
        this.contenido = contenido;
        this.usuarioId = usuarioId;
        this.publicacionId = publicacionId;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(int sincronizado) {
        this.sincronizado = sincronizado;
    }
}
