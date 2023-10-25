package com.example.redsocial.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redsocial.R;
import com.example.redsocial.models.Comentario;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter{
    List<Comentario> comentarios;

    public ComentarioAdapter(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_comentario, parent, false);
        ComentarioAdapter.ComentarioViewHolder viewHolder = new ComentarioAdapter.ComentarioViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Comentario item = comentarios.get(position);
        Log.i("Validar Contacto", "Information message: " + item.toString());
        View view = holder.itemView;
        ImageView ic_coment_user = view.findViewById(R.id.ic_publicacion_usuario);
        TextView tv_coment_user = view.findViewById(R.id.tv_coment_user);
        TextView tv_comentario = view.findViewById(R.id.tv_comentario);

        tv_coment_user.setText(item.expand.user.username);
        tv_comentario.setText(item.comentario);

        Picasso.get().load("http://146.190.60.137:8090/api/files/users_red_social/"+item.expand.user.id+"/"+item.expand.user.avatar).into(ic_coment_user);

        // falta capturar el comentario nuevo
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }
    public class  ComentarioViewHolder extends RecyclerView.ViewHolder {

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
