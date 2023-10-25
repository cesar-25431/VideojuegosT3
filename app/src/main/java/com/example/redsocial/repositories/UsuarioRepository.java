package com.example.redsocial.repositories;

import android.graphics.Bitmap;

import com.example.redsocial.entidades.Usuario;
import com.example.redsocial.retrofitapp.RetrofitSingleton;
import com.example.redsocial.services.ApiUsuariosService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class UsuarioRepository {

    Retrofit retrofit;

    UsuarioRepository() {
        retrofit = RetrofitSingleton.getInstance();
    }

    public Usuario login(String usuario,String contrasenia) {
        return new Usuario();
    }

    public List<Usuario> listar() {
        return new ArrayList<Usuario>();
    }

    public Usuario ver(int id) {
        return new Usuario();
    }

    public Usuario registrar(String nombre, String contrasenia, String confirmarContrasenia, Bitmap avatar) {
        if(!isNetworkConnected()) {

        } else {
            ApiUsuariosService service = retrofit.create(ApiUsuariosService.class);


            Call<Usuario> call= service.registrarUsuario();
        }
        return new Usuario();
    }

    public Usuario actualizar(int id, Usuario usuario) {
        return new Usuario();
    }

    public void eliminar(int id) {

    }


}
