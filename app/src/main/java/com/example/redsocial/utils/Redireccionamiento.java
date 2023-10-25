package com.example.redsocial.utils;

import android.content.Context;
import android.content.Intent;

import com.example.redsocial.LoginActivity;
import com.example.redsocial.MainActivity;
import com.example.redsocial.SignUpActivity;
import com.example.redsocial.SplashScreenActivity;

public class Redireccionamiento {

    Context context;

    public Redireccionamiento(Context context) {
        this.context = context;
    }

    public void goToHomeWithoutBack() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goToLoginWithoutBack() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goToRegister() {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }
}
