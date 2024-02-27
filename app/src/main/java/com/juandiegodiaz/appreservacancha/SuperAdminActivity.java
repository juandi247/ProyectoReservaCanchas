package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuperAdminActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

        db = FirebaseFirestore.getInstance();

        Button btnSeleccionarHorarios = findViewById(R.id.btn_seleccionar_horarios);

        btnSeleccionarHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validar que los campos no estén vacíos antes de permitir la acción
                if (camposValidos()) {
                    // Iniciar la actividad para seleccionar horarios
                    Intent intent = new Intent(SuperAdminActivity.this, PruebaSuperAdmin_Activity.class);
                    startActivity(intent);
                    guardarDatosEnFirestore();
                } else {
                    Toast.makeText(SuperAdminActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void guardarDatosEnFirestore() {
        // Obtener referencias a los EditText
        EditText etNombreCancha = findViewById(R.id.et_crear_Nombre_Cancha);
        EditText etUsuarioCancha = findViewById(R.id.et_crear_usuarioCancha);
        EditText etContrasenaCancha = findViewById(R.id.et_crear_contrasñea_cancha);
        EditText etNumeroContactoCancha = findViewById(R.id.et_numero_contacto_cancha);
        EditText etNumeroNequiCancha = findViewById(R.id.et_numero_nequi_cancha);


        // Obtener los valores ingresados por el usuario
        String nombreCancha = etNombreCancha.getText().toString();
        String usuarioCancha = etUsuarioCancha.getText().toString();
        String contrasenaCancha = etContrasenaCancha.getText().toString();
        String numeroContactoCancha = etNumeroContactoCancha.getText().toString();
        String numeroNequiCancha = etNumeroNequiCancha.getText().toString();

        // SHARED PREFERENCES!
        SharedPreferences preferences = getSharedPreferences("USUARIO_CANCHAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombrecancha", nombreCancha);
        editor.apply();

        // Crear un mapa para los datos de la cancha
        Map<String, Object> datosCancha = new HashMap<>();
        datosCancha.put("nombre", nombreCancha);
        datosCancha.put("usuario", usuarioCancha);
        datosCancha.put("contrasena", contrasenaCancha);
        datosCancha.put("numeroContacto", numeroContactoCancha);
        datosCancha.put("numeroNequi", numeroNequiCancha);



        db.collection("Administradores")
                .document(nombreCancha)
                .set(datosCancha)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Éxito al guardar datos de la cancha en Firestore
                        Toast.makeText(SuperAdminActivity.this, "Datos de la cancha guardados en Firestore", Toast.LENGTH_SHORT).show();

                        // Crear los documentos para cada día de la semana dentro de la colección "Horarios"
                        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};

                        for (String dia : diasSemana) {
                            db.collection("Administradores")
                                    .document(nombreCancha)
                                    .collection("Horarios")
                                    .document(dia)
                                    .set(new HashMap<>())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Éxito al crear el documento para el día de la semana
                                            Toast.makeText(SuperAdminActivity.this, "Documento de los dias creado en Firestore", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Error al crear el documento para el día de la semana
                                            Toast.makeText(SuperAdminActivity.this, "Error al crear el documento para '" + dia + "' en Firestore", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al guardar datos de la cancha en Firestore
                        Toast.makeText(SuperAdminActivity.this, "Error al guardar datos de la cancha en Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean camposValidos() {
        // Obtener referencias a los EditText
        EditText etNombreCancha = findViewById(R.id.et_crear_Nombre_Cancha);
        EditText etUsuarioCancha = findViewById(R.id.et_crear_usuarioCancha);
        EditText etContrasenaCancha = findViewById(R.id.et_crear_contrasñea_cancha);
        EditText etNumeroContactoCancha = findViewById(R.id.et_numero_contacto_cancha);
        EditText etNumeroNequiCancha = findViewById(R.id.et_numero_nequi_cancha);

        // Obtener los valores ingresados por el usuario
        String nombreCancha = etNombreCancha.getText().toString();
        String usuarioCancha = etUsuarioCancha.getText().toString();
        String contrasenaCancha = etContrasenaCancha.getText().toString();
        String numeroContactoCancha = etNumeroContactoCancha.getText().toString();
        String numeroNequiCancha = etNumeroNequiCancha.getText().toString();

        // Verificar que ninguno de los campos esté vacío
        return !nombreCancha.isEmpty() &&
                !usuarioCancha.isEmpty() &&
                !contrasenaCancha.isEmpty() &&
                !numeroContactoCancha.isEmpty() &&
                !numeroNequiCancha.isEmpty();
    }
}
