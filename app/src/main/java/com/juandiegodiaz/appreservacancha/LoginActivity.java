package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnRegistro = findViewById(R.id.btn_registro);
        Button btnInicioSesion= findViewById(R.id.btn_inicioSesion);
        EditText usuarioEditText = findViewById(R.id.et_usuario);
        EditText contraseñaET = findViewById(R.id.et_contrasenaInicio);
        db= FirebaseFirestore.getInstance();

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });


        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = usuarioEditText.getText().toString();
                String contraseña = contraseñaET.getText().toString();



                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    // Al menos uno de los campos está en blanco, muestra una alerta
                    Toast.makeText(LoginActivity.this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
                } else {


                    db.collection("Usuarios")
                            .whereEqualTo("usuario", usuario)
                            .whereEqualTo("contraseña", contraseña)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                    String horaReserva = "";
                                    //llamar el campo de hora reserva de la base de datos!! godd
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        horaReserva = document.getString("hora reserva");
                                    }
                                    SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("usuario", usuario); // Donde "usuario" es el nombre del usuario

                                    editor.apply();


                                        Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                                        startActivity(intent);






                                } else {
                                    // Usuario y/o contraseña incorrectos
                                    Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                // Error en la consulta
                                Toast.makeText(LoginActivity.this, "Error en la consulta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });


    }
}




/*

 //   CollectionReference canchasCollection = db.collection("Canchas");
    //DocumentReference cancha1Doc = canchasCollection.document("Cancha Picadelly");
    DocumentReference cancha2Doc = canchasCollection.document("Cancha Picadelly");
                                    for (int i = 15; i <= 17; i++) {
                                            // Crea una fecha en formato "yyyy-MM-dd"
                                            String fecha = "2023-10-" + i;

                                            // Crea un nuevo documento con la fecha como ID en la colección "Fechas"
                                            DocumentReference fechaDoc = cancha2Doc.collection("Fechas").document(fecha);

                                            // Agrega los horarios como campos en este documento
                                            Map<String, Object> horarios = new HashMap<>();
        horarios.put("7pm", "Disponible");
        horarios.put("8pm", "Ocupado");
        horarios.put("9pm", "Disponible");

        // Actualiza los horarios en el documento de fecha
        fechaDoc.set(horarios, SetOptions.merge());
        }

 */