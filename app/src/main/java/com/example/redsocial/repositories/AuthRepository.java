package com.example.redsocial.repositories;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.redsocial.models.User;
import com.example.redsocial.models.UserCredentials;
import com.example.redsocial.retrofitapp.RetrofitSingleton;
import com.example.redsocial.services.AuthService;
import com.example.redsocial.services.responses.LoginResponse;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRepository {
    SharedPreferences sharedPreferences;
    Retrofit retrofit;

    public AuthRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        retrofit = RetrofitSingleton.getInstance();
    }
    public interface MyCallback {
        void callback();
    }

    public void login(String username, String password,MyCallback callbackOnSuccess, MyCallback callbackOnFail) {

        if(username.equals("") || password.equals("")) {
            Log.e("Error de datos","Error de datos");
            return;
        }
        UserCredentials userCredentials = new UserCredentials(username,password);
        AuthService services = retrofit.create(AuthService.class);
        Call<LoginResponse> call=services.loginUserAndPassword(userCredentials);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // falta guardar usuario y contraseña

                    editor.putString("identity",username);
                    editor.putString("password",password);





                    // guardamos el id usuario
                    editor.putString("userid",response.body().getRecord().getId());
                    editor.apply();
                    Log.i("Login success", "ingresando");
                    callbackOnSuccess.callback();
                }else{
                    Log.e("Error", response.toString());

                    callbackOnFail.callback();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("Error on failure", "no hay conexion: " + t.toString() + " " + call.request());
                Log.e("Error on failure", "no hay conexion: " + call.request().method());
            }
        });
    }


    public  void register(String username, String password, String passwordconfirm, Bitmap avatar, MyCallback callback) {

        if (avatar == null) {
            Log.e("Error image","Error debe seleccionar una imagen");
        }
        Log.i("Aqui",username+ " " + password + " " + passwordconfirm);
        if(username.equals("") || password.equals("") || passwordconfirm.equals("") || (!password.equals(passwordconfirm))) {
            Log.e("Error de datos","Error de datos");
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        // Crea el objeto RequestBody con el array de bytes
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

        // Crea el objeto MultipartBody.Part con el RequestBody
        MultipartBody.Part avatarPart = MultipartBody.Part.createFormData("avatar", "avatar.jpeg", requestBody);
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody passwordConfirmBody = RequestBody.create(MediaType.parse("text/plain"), passwordconfirm);

        AuthService services = retrofit.create(AuthService.class);
        Call<User> call=services.createUser(usernameBody,passwordBody,passwordConfirmBody,avatarPart);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // falta guardar usuario y contraseña

                    Log.i("Guardando usuario", "se guardo el usuario");
                    editor.putString("identity",username);
                    editor.putString("password",password);





                    // guardamos el id usuario
                    editor.putString("userid",response.body().getId());
                    editor.apply();
                    Log.i("Login success", "ingresando");
                    callback.callback();
                }else{
                    Log.e("Error ", response.toString());
                    Log.i("Error: ", response.message());
                    Log.i("Error: ", response.errorBody().contentType().toString());

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error on failure", "no hay conexion: " + t.toString() + " " + call.request());
                Log.e("Error on failure", "no hay conexion: " + call.request().method());
            }
        });

    }
}
