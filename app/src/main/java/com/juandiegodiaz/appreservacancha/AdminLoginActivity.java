package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AdminLoginActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        db= FirebaseFirestore.getInstance();
        Button btnInicioSesionAdmin=findViewById(R.id.btn_inicioSesionAdmins);
        EditText usuarioAdEditText = findViewById(R.id.et_usuarioAdmin);
        EditText contrasenaAdEditText = findViewById(R.id.et_contrasenaAdmin);

        ImageButton btnvolverLogin=findViewById(R.id.btn_volverInicioAdmin);

        btnInicioSesionAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = usuarioAdEditText.getText().toString();
                String contraseña = contrasenaAdEditText.getText().toString();




                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    // Al menos uno de los campos está en blanco, muestra una alerta
                    Toast.makeText(AdminLoginActivity.this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
                } else {


                    db.collection("Administradores")
                            .whereEqualTo("usuario", usuario)
                            .whereEqualTo("contraseña", contraseña)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    Toast.makeText(AdminLoginActivity.this, "Inicio de sesión exitoso (ADMIN)", Toast.LENGTH_SHORT).show();
                                    DocumentSnapshot adminDoc = queryDocumentSnapshots.getDocuments().get(0);


                                    String nombreCancha = adminDoc.getString("cancha");
                                    SharedPreferences sharedPreferences = getSharedPreferences("AdminPreferencias", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();


                                    editor.putString("usuario", usuario);

                                    editor.putString("cancha", nombreCancha);
                                    // Donde "usuario" es el nombre del usuario

                                    editor.apply();
                                    Log.d("inicio de sesion exitoso como admin", usuario);


                                    Intent intent = new Intent(AdminLoginActivity.this, InformacionAdminsActivity.class);
                                    startActivity(intent);


                                } else {
                                    // Usuario y/o contraseña incorrectos
                                    Toast.makeText(AdminLoginActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                // Error en la consulta
                                Toast.makeText(AdminLoginActivity.this, "Error en la consulta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });

        btnvolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}