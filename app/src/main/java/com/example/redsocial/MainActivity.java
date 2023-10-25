package com.example.redsocial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;

import com.example.redsocial.adapters.PublicationAdapter;
import com.example.redsocial.models.ListPublicationResponse;
import com.example.redsocial.models.Publicacion;
import com.example.redsocial.models.Publication;
import com.example.redsocial.models.User;
import com.example.redsocial.repositories.AuthRepository;
import com.example.redsocial.repositories.PublicacionesRepository;
import com.example.redsocial.retrofitapp.RetrofitSingleton;
import com.example.redsocial.services.AuthService;
import com.example.redsocial.services.PublicacionSerivce;
import com.example.redsocial.utils.Redireccionamiento;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView iv_publication_preview;
    Switch sw_location;
    EditText et_description;
    Button btn_pick_image;
    RecyclerView recyclerView;


    Bitmap  selectedImage;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    PublicacionesRepository publicacionesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btnactions = findViewById(R.id.btnactions);
        recyclerView = findViewById(R.id.rvPublicaciones);
        sharedPreferences = getSharedPreferences("RedSocial", MODE_PRIVATE);
        editor= sharedPreferences.edit();
        publicacionesRepository = new PublicacionesRepository(sharedPreferences);

        publicacionesRepository.listarPublicaciones(recyclerView);
        PublicationAdapter publicationAdapter = new PublicationAdapter(new ArrayList<Publicacion>());
        recyclerView= findViewById(R.id.rvPublicaciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(publicationAdapter);

        btnactions.setOnClickListener(view -> {
            showPopupMenu(view);
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                publicacionesRepository.listarPublicaciones(recyclerView);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);

        // Agrega los elementos del menú dinámicamente
        Menu menu = popupMenu.getMenu();
        menu.add(Menu.NONE, 1, Menu.NONE, "Nueva publicacion").setIcon(R.drawable.add_icon);
        menu.add(Menu.NONE, 2, Menu.NONE, "Mi perfil").setIcon(R.drawable.user_icon);
        menu.add(Menu.NONE, 3, Menu.NONE, "Salir").setIcon(R.drawable.exit_icon);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle item clicks here
                switch (item.getItemId()) {
                    case 1:
                        // Acción para la opción 1
                        if (!isFinishing()) {
                            showFormDialog();
                        }
                        return true;
                    case 2:
                        // Acción para la opción 2
                        Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case 3:
                        editor.remove("identity"); // Eliminar la preferencia "identity"
                        editor.remove("password"); // Eliminar la preferencia "password"
                        editor.apply(); // Aplicar los cambios
                        new Redireccionamiento(MainActivity.this).goToLoginWithoutBack();
                        // Acción para la opción 2
                        return true;
                }
                return false;
            }
        });

        popupMenu.show();
    }

    private void showFormDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("NUEVA PUBLICACION");

        // Inflate the layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.publication_create_dialog, null);
        builder.setView(dialogView);
        iv_publication_preview = dialogView.findViewById(R.id.iv_publication_preview);
        et_description = dialogView.findViewById(R.id.et_description);
        btn_pick_image = dialogView.findViewById(R.id.btn_pick_image);

        btn_pick_image.setOnClickListener(view -> {
            showImagePickerDialog();
        });

        // Add form fields, buttons, etc., and handle their interactions

        builder.setPositiveButton("Publicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle save button click
                MultipartBody.Part imagen = null;
                if(selectedImage != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();

                    // Crea el objeto RequestBody con el array de bytes
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

                    // Crea el objeto MultipartBody.Part con el RequestBody
                    imagen = MultipartBody.Part.createFormData("imagen", "avatar.jpeg", requestBody);
                }

                RequestBody descripcion = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(et_description.getText()));
                RequestBody ubicacion = RequestBody.create(MediaType.parse("text/plain"), "");

                RequestBody usuario = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("userid",""));

                Retrofit retrofit = RetrofitSingleton.getInstance();
                PublicacionSerivce services = retrofit.create(PublicacionSerivce.class);
                Call<ListPublicationResponse.Publication> call=services.createpublication(descripcion,ubicacion,usuario,imagen);

                call.enqueue(new Callback<ListPublicationResponse.Publication>() {
                    @Override
                    public void onResponse(Call<ListPublicationResponse.Publication> call, Response<ListPublicationResponse.Publication> response) {
                        if (response.isSuccessful()) {

                            publicacionesRepository.listarPublicaciones(recyclerView);

                        }else{
                            Log.e("Error ", response.toString());
                            Log.i("Error: ", response.message());
                            Log.i("Error: ", response.errorBody().contentType().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<ListPublicationResponse.Publication> call, Throwable t) {
                        Log.e("Error on failure", "no hay conexion: " + t.toString() + " " + call.request());
                        Log.e("Error on failure", "no hay conexion: " + call.request().method());
                    }
                });
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle cancel button click
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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

            // Realiza acciones con la foto capturada desde la cámara
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Uri imageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedImage = imageBitmap;
                // Procesar la imagen seleccionada aquí
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Realiza acciones con la imagen seleccionada desde la galería
        }
        iv_publication_preview.setImageBitmap(selectedImage);
    }
}