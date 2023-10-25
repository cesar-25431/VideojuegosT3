package com.example.redsocial.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "publicaciones",
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "usuario_id"))
public class Publicacion {
    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "contenido")
    private String contenido;

    @ColumnInfo(name = "imagen")
    private String imagen;

    @ColumnInfo(name = "usuario_id")
    private int usuarioId;

    @ColumnInfo(name = "fecha_creacion")
    private String fechaCreacion;

    private int sincronizado;





    //Constructor vacio
    public Publicacion() {
    }

    // Constructor
    public Publicacion(int id, String contenido, String imagen, int usuarioId, String fechaCreacion) {
        this.id = id;
        this.contenido = contenido;
        this.imagen = imagen;
        this.usuarioId = usuarioId;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
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

