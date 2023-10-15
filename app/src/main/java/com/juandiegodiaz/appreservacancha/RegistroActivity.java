package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {


private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button btnVolver = findViewById(R.id.btn_VolverInicioSesion);
        FirebaseApp.initializeApp(this);
        Button btnRegistro = findViewById(R.id.btn_terminarRegistro);
        EditText usuarioEditText = findViewById(R.id.et_usuarioRegistro);
        EditText contrasenaET = findViewById(R.id.et_contrasenaRegistro);
        EditText nombreEditText = findViewById(R.id.et_nombre);
        EditText apellidoEditText = findViewById(R.id.et_apellido);

        db= FirebaseFirestore.getInstance();

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String usuario = usuarioEditText.getText().toString();
                String contraseña = contrasenaET.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String apellido = apellidoEditText.getText().toString();


                if (usuario.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ) {

                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.length() < 5) {
                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {


                        db.collection("Usuarios")
                        .whereEqualTo("usuario", usuario)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                      if (queryDocumentSnapshots.isEmpty()) {
                        // Crear un nuevo documento en la colección "usuarios"
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("usuario", usuario);
                        userData.put("contraseña", contraseña);
                        userData.put("nombre", nombre);
                        userData.put("apellido", apellido);
                        userData.put("fecha",0);
                        userData.put("hora",0);


                        db.collection("Usuarios")
                                .add(userData)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito, Inicia sesion ahora", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(RegistroActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                });
                                    } else {
                                        // El nombre de usuario ya está en uso
                                        Toast.makeText(RegistroActivity.this, "El nombre de usuario ya está en uso. Por favor, elige otro.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } catch (Exception e) {
                        Toast.makeText(RegistroActivity.this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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