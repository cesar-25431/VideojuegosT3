package com.example.redsocial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.redsocial.models.ApiResponse;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.models.User;
import com.example.redsocial.models.Usuario;
import com.example.redsocial.retrofitapp.RetrofitSingleton;
import com.example.redsocial.services.PublicacionSerivce;
import com.example.redsocial.services.UserService;
import com.example.redsocial.utils.Redireccionamiento;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    Usuario usuario;
    Retrofit retrofit;
    ImageView ic_publicacion_usuario;
    TextView tv_user_name;
    Bitmap  selectedImage;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        retrofit = RetrofitSingleton.getInstance();

        ic_publicacion_usuario = findViewById(R.id.ic_publicacion_usuario);
        tv_user_name = findViewById(R.id.tv_user_name);
        sharedPreferences = getSharedPreferences("RedSocial", MODE_PRIVATE);

        cargarInfo();

        Button btn_cambiar_imagen = findViewById(R.id.btn_cambiar_imagen);
        btn_cambiar_imagen.setOnClickListener(view -> {
            showImagePickerDialog();
        });
    }
    public void cargarInfo() {

        UserService services = retrofit.create(UserService.class);
        Call<Usuario> call=services.verUsuario(sharedPreferences.getString("userid",""));
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {

                    usuario = response.body();
                    Picasso.get().load("http://146.190.60.137:8090/api/files/users_red_social/"+usuario.id+"/"+usuario.avatar).into(ic_publicacion_usuario);
                    tv_user_name.setText(usuario.username);

                }else{
                    Log.e("Error ", response.toString());
                    Log.e("Error ", response.raw().toString());

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Error on failure", t.toString() );
            }
        });
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar imagen");
        String[] options = {"Cámara", "Galería"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.i("My app","El permiso de la cámara ya se ha otorgado, puedes realizar la acción deseada aquí");
                        openCamera();
                    } else {
                        // El permiso de la cámara no se ha otorgado, solicítalo al usuario
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1000);
                        Log.i("My app","No tienes permiso");
                    }
                } else if (which == 1) {
                    openGallery();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE );
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedImage = photo;
            submitNewImage();
            // Realiza acciones con la foto capturada desde la cámara
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Uri imageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedImage = imageBitmap;
                submitNewImage();
                // Procesar la imagen seleccionada aquí
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Realiza acciones con la imagen seleccionada desde la galería
        }
    }

    public void submitNewImage() {
        Log.i("Imagen", "Cargando");
        UserService services = retrofit.create(UserService.class);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        // Crea el objeto MultipartBody.Part con el RequestBody
        MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar", "avatar.jpeg", requestBody);
        Call<Usuario> call=services.actualizarImagen(sharedPreferences.getString("userid",""),avatar);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {

                    usuario = response.body();
                    Picasso.get().load("http://146.190.60.137:8090/api/files/users_red_social/"+usuario.id+"/"+response.body().avatar).into(ic_publicacion_usuario);
                    tv_user_name.setText(usuario.username);

                }else{
                    Log.e("Error ", response.toString());
                    Log.e("Error ", response.body().toString());
                }
                Log.i("Imagen", response.toString());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Error on failure", t.toString() );
                Log.i("Imagen", "failure response");
            }
        });
    }
}