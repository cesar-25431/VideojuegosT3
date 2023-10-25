package com.example.redsocial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.redsocial.adapters.ComentarioAdapter;
import com.example.redsocial.models.Comentario;
import com.example.redsocial.models.ListPublicationResponse;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.retrofitapp.RetrofitSingleton;
import com.example.redsocial.services.ComentarioService;
import com.example.redsocial.services.PublicacionSerivce;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ComentariosActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Publicacion publicacion;
    Comentario comentario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        String publicacionJson = getIntent().getStringExtra("Publicacion");
        publicacion = new Gson().fromJson(publicacionJson, Publicacion.class);
        sharedPreferences = getSharedPreferences("RedSocial", MODE_PRIVATE);
        ImageView ic_publicacion_usuario = findViewById(R.id.ic_publicacion_usuario);
        TextView tv_publicacion_usuario = findViewById(R.id.tv_publicacion_usuario);
        TextView tv_publicacion_date = findViewById(R.id.tv_publicacion_date);
        TextView tv_publicacion_descripcion = findViewById(R.id.tv_publicacion_descripcion);
        ImageView iv_publicacion_image = findViewById(R.id.iv_publicacion_image);

        RecyclerView rv_comentarios = findViewById(R.id.rv_comentarios);
        Picasso.get().load("http://146.190.60.137:8090/api/files/users_red_social/"+publicacion.expand.usuario.id+"/"+publicacion.expand.usuario.avatar).into(ic_publicacion_usuario);
        if(!publicacion.imagen.equals("")) {
            Picasso.get().load("http://146.190.60.137:8090/api/files/publicacion/"+publicacion.id+"/"+publicacion.imagen).into(iv_publicacion_image);
        } else {
            // CardView cv_publication_image = view.findViewById(R.id.cv_publication_image);
            iv_publicacion_image.setVisibility(View.GONE);
        }
        tv_publicacion_descripcion.setText(publicacion.descripcion);

        tv_publicacion_usuario.setText(publicacion.expand.usuario.username);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");

        LocalDateTime localDateTime = LocalDateTime.parse(publicacion.created, inputFormatter);
        tv_publicacion_date.setText(localDateTime.format(outputFormatter));
        if(publicacion.expand.comentarios != null) {
            ComentarioAdapter adapter=new ComentarioAdapter(publicacion.expand.comentarios);
            rv_comentarios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv_comentarios.setHasFixedSize(true);
            rv_comentarios.setAdapter(adapter);
        } else {
            ComentarioAdapter adapter=new ComentarioAdapter(new ArrayList<Comentario>());
            rv_comentarios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv_comentarios.setHasFixedSize(true);
            rv_comentarios.setAdapter(adapter);
        }

        EditText et_nuevo_comentario = findViewById(R.id.et_nuevo_comentario);
        ImageButton btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(view -> {

            //Crear comentario
            comentario = new Comentario();
            comentario.comentario = String.valueOf(et_nuevo_comentario.getText());
            comentario.pub = publicacion.id;
            comentario.user = sharedPreferences.getString("userid","");
            Retrofit retrofit = RetrofitSingleton.getInstance();
            ComentarioService services = retrofit.create(ComentarioService.class);
            Call<Comentario> call=services.crearComentario(comentario);

            call.enqueue(new Callback<Comentario>() {
                @Override
                public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                    if (response.isSuccessful()) {

                        Retrofit retrofit = RetrofitSingleton.getInstance();

                        PublicacionSerivce services = retrofit.create(PublicacionSerivce.class);
                        Call<Publicacion> call2=services.viewPublicacion(publicacion.id);

                        call2.enqueue(new Callback<Publicacion>() {
                            @Override
                            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                                if (!response.isSuccessful()){
                                    Log.e("asd1234", "error");
                                }else {

                                    et_nuevo_comentario.setText("");


                                    Publicacion publicacion2 = response.body();

                                    if(publicacion2.expand.comentarios != null) {
                                        ComentarioAdapter adapter=new ComentarioAdapter(publicacion2.expand.comentarios);
                                        rv_comentarios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        rv_comentarios.setHasFixedSize(true);
                                        rv_comentarios.setAdapter(adapter);
                                    } else {
                                        ComentarioAdapter adapter=new ComentarioAdapter(new ArrayList<Comentario>());
                                        rv_comentarios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        rv_comentarios.setHasFixedSize(true);
                                        rv_comentarios.setAdapter(adapter);
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<Publicacion> call, Throwable t) {

                            }
                        });

                    }else{
                        Log.e("Error ", response.toString());
                        Log.i("Error: ", response.message());
                        Log.i("Error: ", response.errorBody().contentType().toString());

                    }
                }

                @Override
                public void onFailure(Call<Comentario> call, Throwable t) {
                    Log.e("Error on failure", "no hay conexion: " + t.toString() + " " + call.request());
                    Log.e("Error on failure", "no hay conexion: " + call.request().method());
                }
            });
        });

    }

    public void recargarInfo() {

    }
}