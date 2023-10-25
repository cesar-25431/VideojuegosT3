package com.example.redsocial.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.Update;

import com.example.redsocial.entidades.Comentario;
import com.example.redsocial.entidades.Publicacion;
import com.example.redsocial.entidades.Usuario;

import java.util.List;

@Dao
public interface PublicacionDao {
    @Query("SELECT * FROM publicaciones")
    List<Publicacion> getAllPublicaciones();

    @Query("SELECT * FROM publicaciones WHERE usuario_id IN (SELECT id FROM usuarios WHERE nombre = :nombreUsuario)")
    List<Publicacion> getPublicacionesByUsuario(String nombreUsuario);

    @Query("SELECT * FROM publicaciones WHERE id = :publicacionId")
    Publicacion getPublicacionById(int publicacionId);

    @Insert
    void insertPublicacion(Publicacion publicacion);

    @Update
    void updatePublicacion(Publicacion publicacion);

    @Delete
    void deletePublicacion(Publicacion publicacion);

    @Query("SELECT publicaciones.*, usuarios.* FROM publicaciones INNER JOIN usuarios ON publicaciones.usuario_id = usuarios.id WHERE publicaciones.id = :publicacionId")
    PublicacionWithUsuarioAndComentarios getPublicacionByIdWithUsuarioAndComentarios(int publicacionId);

    // ... otros métodos ...

    class PublicacionWithUsuarioAndComentarios {
        @Embedded
        public Publicacion publicacion;

        @Embedded
        public Usuario usuario;

        @Relation(parentColumn = "id", entityColumn = "publicacion_id", entity = Comentario.class)
        public List<ComentarioWithUsuario> comentarios;
    }

    class ComentarioWithUsuario {
        @Embedded
        public Comentario comentario;

        @Embedded
        public Usuario usuario;
    }
}

