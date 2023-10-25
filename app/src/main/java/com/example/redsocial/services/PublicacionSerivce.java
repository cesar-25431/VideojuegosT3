package com.example.redsocial.services;

import com.example.redsocial.models.ApiResponse;
import com.example.redsocial.models.ListPublicationResponse;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.models.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PublicacionSerivce {

    @GET("/api/collections/publicacion/records?expand=usuario,comentarios(pub).user&sort=-created")
    Call<ApiResponse<Publicacion>> getListPublicacion();

    @GET("/api/collections/publicacion/records/{id}?expand=usuario,comentarios(pub).user&sort=-created")
    Call<Publicacion> viewPublicacion(@Path("id") String id);

    @Multipart
    @POST("/api/collections/publicacion/records")
    Call<ListPublicationResponse.Publication> createpublication(
            @Part("descripcion") RequestBody descripcion,
            @Part("ubicacion") RequestBody ubicacion,
            @Part("usuario") RequestBody usuario,
            @Part MultipartBody.Part imagen
    );

}
