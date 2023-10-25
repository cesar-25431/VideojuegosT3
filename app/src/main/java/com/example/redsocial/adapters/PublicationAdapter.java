package com.example.redsocial.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.ComentariosActivity;
import com.example.redsocial.R;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.retrofitapp.RetrofitSingleton;
import com.example.redsocial.services.PublicacionSerivce;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PublicationAdapter extends RecyclerView.Adapter {
    public List<Publicacion> publications;
    public PublicationAdapter(List<Publicacion> publications){
        this.publications = publications;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_publication, parent, false);
        PublicacionViewHolder viewHolder = new PublicacionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Publicacion item = publications.get(position);
        Log.i("Validar Contacto", "Information message: " + item.toString());
        View view = holder.itemView;

        TextView tv_username = view.findViewById(R.id.tv_publicacion_usuario);
        TextView tv_date = view.findViewById(R.id.tv_publicacion_date);
        TextView tv_item_description = view.findViewById(R.id.tv_publicacion_descripcion);
        ImageView iv_avatar_user = view.findViewById(R.id.ic_publicacion_usuario);
        ImageView iv_publication_image = view.findViewById(R.id.iv_publicacion_image);

        ImageButton btn_comentarios = view.findViewById(R.id.btn_comentarios);

        tv_item_description.setText(item.descripcion);
        tv_username.setText(item.expand.usuario.username);
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");

        // Convertir la cadena de fecha y hora a LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(item.created, inputFormatter);
        tv_date.setText(localDateTime.format(outputFormatter));

        if(!item.imagen.equals("")) {
            Picasso.get().load("http://146.190.60.137:8090/api/files/publicacion/"+item.id+"/"+item.imagen).into(iv_publication_image);
        } else {
            // CardView cv_publication_image = view.findViewById(R.id.cv_publication_image);
            iv_publication_image.setVisibility(View.GONE);
        }
        if(!item.expand.usuario.avatar.equals("")) {
            Picasso.get().load("http://146.190.60.137:8090/api/files/users_red_social/"+item.expand.usuario.id+"/"+item.expand.usuario.avatar).into(iv_avatar_user);
        } else {
            Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngwing.com%2Fes%2Ffree-png-dlglg&psig=AOvVaw21wNv0ncA6nTpha9TdBvQP&ust=1686849066198000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJjpnM2gw_8CFQAAAAAdAAAAABAE").into(iv_avatar_user);
        }

        btn_comentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitSingleton.getInstance();

                PublicacionSerivce services = retrofit.create(PublicacionSerivce.class);
                Call<Publicacion> call=services.viewPublicacion(item.id);

                call.enqueue(new Callback<Publicacion>() {
                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        if (!response.isSuccessful()){
                            Log.e("asd1234", "error");
                        }else {



                            Intent intent= new Intent(view.getContext(), ComentariosActivity.class);
                            Publicacion publicacion = response.body();
                            String publicacionJson = new Gson().toJson(publicacion);
                            intent.putExtra("Publicacion",publicacionJson);

                            view.getContext().startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<Publicacion> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    public class  PublicacionViewHolder extends RecyclerView.ViewHolder {

        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
