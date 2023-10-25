package com.example.redsocial.services;

import com.example.redsocial.models.Comentario;
import com.example.redsocial.models.Usuario;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @GET("/api/collections/users_red_social/records/{id}")
    Call<Usuario> verUsuario(@Path("id") String id);

    @Multipart
    @PATCH("/api/collections/users_red_social/records/{id}")
    Call<Usuario>actualizarImagen(@Path("id") String id, @Part MultipartBody.Part avatar);
}
