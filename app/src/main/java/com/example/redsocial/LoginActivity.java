package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.redsocial.repositories.AuthRepository;
import com.example.redsocial.utils.Redireccionamiento;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText etusernamelogin;
    EditText etpasswordlogin;
    Button btnlogin;
    TextView tvRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("RedSocial", MODE_PRIVATE);
        AuthRepository authRepository = new AuthRepository(sharedPreferences);
        // inputs
        etusernamelogin = findViewById(R.id.etusernamecreate);
        etpasswordlogin = findViewById(R.id.etpasswordlogin);

        //botones
        btnlogin = findViewById(R.id.btnregistrar);

        btnlogin.setOnClickListener(view -> {
            authRepository.login(String.valueOf(etusernamelogin.getText()),String.valueOf(etpasswordlogin.getText()), () -> {
                new Redireccionamiento(this).goToHomeWithoutBack();
            },() -> {});
        });

        tvRegistrarse = findViewById(R.id.tvRegistrarse);

        tvRegistrarse.setOnClickListener(view -> {
            new Redireccionamiento(this).goToRegister();
        });
    }



}