package com.example.redsocial.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.redsocial.entidades.Comentario;

import java.util.List;

@Dao
public interface ComentarioDao {
    @Query("SELECT * FROM comentarios")
    List<Comentario> getAllComentarios();

    @Query("SELECT * FROM comentarios WHERE id = :comentarioId")
    Comentario getComentarioById(int comentarioId);

    @Query("SELECT * FROM comentarios WHERE publicacion_id = :publicacionId")
    List<Comentario> getComentariosByPublicacion(int publicacionId);

    @Insert
    void insertComentario(Comentario comentario);

    @Update
    void updateComentario(Comentario comentario);

    @Delete
    void deleteComentario(Comentario comentario);
}
