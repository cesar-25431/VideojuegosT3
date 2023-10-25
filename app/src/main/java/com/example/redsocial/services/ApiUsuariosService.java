package com.example.redsocial.services;



import com.example.redsocial.entidades.Usuario;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiUsuariosService {

    @POST("/usuarios")
    Call<Usuario> login(@Body Map<String, String> requestBody);

    @POST()
    Call<Usuario> registrarUsuario(@Body Usuario usuario);

}
