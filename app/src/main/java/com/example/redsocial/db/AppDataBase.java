package com.example.redsocial.db;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.redsocial.entidades.Comentario;
import com.example.redsocial.entidades.Publicacion;
import com.example.redsocial.entidades.Usuario;


@Database(entities = {Usuario.class, Publicacion.class, Comentario.class}, version = 1)
public abstract  class AppDataBase extends RoomDatabase {

    public static AppDataBase getInstance(Context context) {
        return Room.databaseBuilder(context, AppDataBase.class, "dbrs")
                .allowMainThreadQueries()
                .build();
    }

}
