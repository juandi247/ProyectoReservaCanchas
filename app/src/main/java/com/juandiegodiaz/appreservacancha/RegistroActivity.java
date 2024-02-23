package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.StorageReference;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private StorageReference storageRef;
    private DocumentReference userDocRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button btnVolver = findViewById(R.id.btn_VolverInicioSesion);
        FirebaseApp.initializeApp(this);

        Button btnRegistro = findViewById(R.id.btn_terminarRegistro);
        EditText emailEditText = findViewById(R.id.et_emailRegistro);
        EditText contrasenaET = findViewById(R.id.et_contrasenaRegistro);
        EditText nombreEditText = findViewById(R.id.et_nombre);
        EditText telefonoEditText = findViewById(R.id.et_telefono);


        db = FirebaseFirestore.getInstance();


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String contraseña = contrasenaET.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String telefono = telefonoEditText.getText().toString();
                String fecha = "";
                String cancha = "";
                String hora = "";
                String Imagen = "";

                if (email.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (contraseña.length() < 5) {
                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                    // Iniciar autenticación con el email y contraseña
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contraseña)
                            .addOnCompleteListener(RegistroActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = task.getResult().getUser();

                                    // Enviar el correo de verificación
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(taskEmail -> {
                                                if (taskEmail.isSuccessful()) {
                                                    // El correo de verificación fue enviado exitosamente
                                                    Toast.makeText(RegistroActivity.this, "Se ha enviado un correo de verificación a " + email, Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                                    Log.d("error cambio de pag", "deberia cambiar a la activity de login ");

                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    // Error al enviar el correo de verificación
                                                    Toast.makeText(RegistroActivity.this, "Error al enviar el correo de verificación: " + taskEmail.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                } else {
                                    // La autenticación falló, muestra un mensaje de error
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(RegistroActivity.this, "El usuario ya existe, elige otro email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegistroActivity.this, "Error en la autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}