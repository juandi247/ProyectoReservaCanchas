package com.juandiegodiaz.appreservacancha;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PerfilActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private static final int GALLERY_REQUEST_CODE = 123;
    private ImageView imagenPerfil;
    private StorageReference storageRef;
    private DocumentReference userDocRef;
    private static final String REMEMBER_ME_KEY = "rememberMe";
    private static final String EMAIL_KEY = "email";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        imagenPerfil = findViewById(R.id.im_mostrarImagen);
        Button btnSeleccionarImagen = findViewById(R.id.btn_cambiarImagen);

        Button btnGoToLogin = findViewById(R.id.cerrar_sesion_btn);
        ImageButton btnGoToInicio= findViewById(R.id.btn_home_desdePerfil);
        ImageButton btnGoToCalendar= findViewById(R.id.btn_calendarPerfil);
        db = FirebaseFirestore.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
        userDocRef = db.collection("Usuarios").document(usuario);
        TextView nombreTextView = findViewById(R.id.tv_nombreUsuario);
        TextView UserTextView = findViewById(R.id.tv_usuario);

        obtenerYMostrarImagenDesdeFirestore();

        btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });




        userDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String nombreUsuario = documentSnapshot.getString("nombre");
         String User= documentSnapshot.getString("usuario");
                nombreTextView.setText("¡Hola! "+nombreUsuario);
                UserTextView.setText("Tu usuario es: "+User);


            }
        });






        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión en Firebase
                FirebaseAuth.getInstance().signOut();

                // Redirigir al LoginActivity
                Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finalizar todas las actividades actuales y la aplicación
                finishAffinity();

                Toast.makeText(PerfilActivity.this, "Cerraste Sesión", Toast.LENGTH_SHORT).show();
            }
        });


        btnGoToInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, InicioActivity.class);
                startActivity(intent);

            }
        });

        btnGoToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, CalendarioActivity.class);
                startActivity(intent);

            }
        });



    }

    private void abrirGaleria() {
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent, GALLERY_REQUEST_CODE);
    }


    @Override




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imagenUri = data.getData();

            // Define la referencia en Firebase Storage donde se guardará la imagen
            String usuario = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE).getString("usuario", "UsuarioPredeterminado");
            StorageReference imagenRef = storageRef.child("imagenes_perfil/" + usuario + ".jpg");

            // Sube la imagen a Firebase Storage
            UploadTask uploadTask = imagenRef.putFile(imagenUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // La imagen se ha subido correctamente, puedes realizar acciones adicionales aquí
                // Ahora, obtén la URL de la imagen y guárdala en Firestore
                guardarUrlEnFirestore(imagenRef);
            }).addOnFailureListener(e -> {
                // Manejar errores en caso de que la carga falle
                Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
            });
        }
    }
    private void obtenerYMostrarImagenDesdeFirestore() {
        if (userDocRef != null) {
            userDocRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String imageUrl = documentSnapshot.getString("link imagen");

                    // Verifica que la URL de la imagen no sea nula o vacía antes de cargarla
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Carga la imagen en el ImageView usando Picasso
                        Picasso.get()
                                .load(imageUrl)
                                .into(imagenPerfil);
                    }
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "userDocRef es nulo", Toast.LENGTH_SHORT).show();
        }
    }




    private void guardarUrlEnFirestore(StorageReference imagenRef) {
        if (userDocRef != null) {
            imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();

                // Guarda la URL en Firestore en el documento del usuario
                userDocRef.update("link imagen", imageUrl)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(PerfilActivity.this, "Espera unos segundos a que se actualice la imagen :)", Toast.LENGTH_SHORT).show();
                            obtenerYMostrarImagenDesdeFirestore();

                            // Actualiza la imagen en el ImageView si es necesario
                            imagenPerfil.setImageURI(Uri.parse(imageUrl));
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(PerfilActivity.this, "Error al actualizar la imagen de perfil", Toast.LENGTH_LONG).show();
                        });
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error al obtener la URL de la imagen", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "userDocRef es nulo", Toast.LENGTH_SHORT).show();
        }
    }
}