package com.example.redsocial.retrofitapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit instance;

    private RetrofitSingleton() {
        // Constructor privado para evitar que se creen instancias de esta clase
    }

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitSingleton.class) {
                if (instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl("http://146.190.60.137:8090")  // Reemplaza con tu URL base
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }
}
