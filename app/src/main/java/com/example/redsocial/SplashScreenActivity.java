package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.redsocial.repositories.AuthRepository;
import com.example.redsocial.utils.Redireccionamiento;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("RedSocial",MODE_PRIVATE);
        AuthRepository authRepository = new AuthRepository(sharedPreferences);

        Log.i("identity ", sharedPreferences.getString("identity",""));
        Log.i("pass: ",sharedPreferences.getString("password", ""));
        if(!sharedPreferences.getString("identity", "").equals("") && !sharedPreferences.getString("password", "").equals("")) {
            Log.i("Usuario", "ingresando con el usuario logueado");
            authRepository.login(sharedPreferences.getString("identity",""),sharedPreferences.getString("password", ""),() -> {
                new Redireccionamiento(this).goToHomeWithoutBack();
            },() ->{
                new Redireccionamiento(this).goToLoginWithoutBack();
            });
        } else {
            new Redireccionamiento(this).goToLoginWithoutBack();
        }
    }

}