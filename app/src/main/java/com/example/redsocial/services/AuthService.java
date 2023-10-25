package com.example.redsocial.services;

import com.example.redsocial.models.User;
import com.example.redsocial.models.UserCredentials;
import com.example.redsocial.services.responses.LoginResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthService {
    @POST("/api/collections/users_red_social/auth-with-password")
    Call<LoginResponse> loginUserAndPassword(@Body UserCredentials userCredentials);


    @Multipart
    @POST("/api/collections/users_red_social/records")
    Call<User> createUser(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("passwordConfirm") RequestBody passwordConfirm,
            @Part MultipartBody.Part avatar
    );

}
