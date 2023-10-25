package com.example.redsocial.db.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.redsocial.entidades.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsuarios();

    @Query("SELECT * FROM usuarios WHERE id = :usuarioId")
    Usuario getUsuarioById(int usuarioId);

    @Query("SELECT * FROM usuarios WHERE nombre = :nombreUsuario")
    List<Usuario> getUsuariosByNombre(String nombreUsuario);

    @Insert
    void insertUsuario(Usuario usuario);

    @Update
    void updateUsuario(Usuario usuario);

    @Delete
    void deleteUsuario(Usuario usuario);
}
