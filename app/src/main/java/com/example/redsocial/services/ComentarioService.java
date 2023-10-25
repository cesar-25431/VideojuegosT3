package com.example.redsocial.services;

import com.example.redsocial.models.Comentario;
import com.example.redsocial.models.ListPublicationResponse;
import com.example.redsocial.models.Publicacion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ComentarioService {

    @POST("/api/collections/comentarios/records")
    Call<Comentario>crearComentario(@Body Comentario libro);
}
