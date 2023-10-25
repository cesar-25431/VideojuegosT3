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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.redsocial.repositories.AuthRepository;
import com.example.redsocial.utils.Redireccionamiento;

import java.io.File;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    EditText etusernamecreate;
    EditText etpasswordcreate;
    EditText etpasswordconfirmcreate;
    Bitmap  selectedImage;
    Button btnregistrar;
    Button btnImage;
    ImageView ivpreview;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etusernamecreate = findViewById(R.id.etusernamecreate);
        etpasswordcreate = findViewById(R.id.etpasswordcreate);
        etpasswordconfirmcreate = findViewById(R.id.etpasswordconfirmcreate);
        btnregistrar = findViewById(R.id.btnregistrar);
        btnImage = findViewById(R.id.btnImage);
        ivpreview = findViewById(R.id.ivpreview);
        sharedPreferences = getSharedPreferences("RedSocial", MODE_PRIVATE);
        AuthRepository authRepository = new AuthRepository(sharedPreferences);


        btnImage.setOnClickListener(view -> {
            showImagePickerDialog();
        });

        btnregistrar.setOnClickListener(view -> {
            authRepository.register(
                    String.valueOf(etusernamecreate.getText()),
                    String.valueOf(etpasswordcreate.getText()),
                    String.valueOf(etpasswordconfirmcreate.getText()),
                    selectedImage,
                    () -> {
                        new Redireccionamiento(this).goToHomeWithoutBack();
                    });
        });

    }


    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar imagen");
        String[] options = {"Cámara", "Galería"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
        ivpreview.setImageBitmap(selectedImage);
    }
}