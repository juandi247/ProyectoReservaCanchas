package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CalendarioActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        db = FirebaseFirestore.getInstance();

        Button btncancelarReserva = findViewById(R.id.btn_cancelarReserva);
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");

        DocumentReference userDocRef = db.collection("Usuarios").document(usuario);



        ImageButton btnHome=findViewById(R.id.btn_Home_desdeCalendario2);


        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Boolean reservaActiva = documentSnapshot.getBoolean("reserva activa");
                        if (reservaActiva != null && reservaActiva) {
                            // El usuario tiene una reserva activa, muestra el mensaje
                            btncancelarReserva.setVisibility(View.VISIBLE);

                            String horaReserva = documentSnapshot.getString("hora reserva");
                            String fechaReserva = documentSnapshot.getString("fecha reserva");
                            String canchaReservada = documentSnapshot.getString("nombre cancha reservada");


                            Log.d("DatosUsuario", "Hora de Reserva: " + horaReserva);
                            Log.d("DatosUsuario", "Fecha de Reserva: " + fechaReserva);
                            Log.d("DatosUsuario", "Cancha Reservada: " + canchaReservada);

                            btncancelarReserva.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    new AlertDialog.Builder(CalendarioActivity.this)
                                            .setTitle("Confirmar Cancelacion")

                                   .setMessage("¿Seguro que deseas cancelar tu reserva?")

                                  .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //BASE DE DATOS!
                                            SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                            String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
                                            DocumentReference userDocRef = db.collection("Usuarios").document(usuario);




                                            userDocRef.get()
                                                    .addOnSuccessListener(documentSnapshot -> {
                                                        if (documentSnapshot.exists()) {
                                                            String fechaReserva = documentSnapshot.getString("fecha reserva");
                                                            String horaReserva = documentSnapshot.getString("hora reserva");
                                                            String canchaReservada = documentSnapshot.getString("nombre cancha reservada");
                                                            canchaReservada="Cancha "+canchaReservada;
                                                            // Actualiza el horario correspondiente en la cancha
                                                            DocumentReference canchaDocRef = db.collection("Canchas").document(canchaReservada).collection("Fechas").document(fechaReserva);
                                                            Map<String, Object> horarioUpdate = new HashMap<>();
                                                            horarioUpdate.put(horaReserva, "Disponible");

                                                            canchaDocRef.set(horarioUpdate, SetOptions.merge());

                                                            // Elimina los campos relacionados con la reserva en el documento del usuario
                                                            Map<String, Object> reservaData = new HashMap<>();
                                                            reservaData.put("hora reserva", "");
                                                            reservaData.put("fecha reserva", "");
                                                            reservaData.put("nombre cancha reservada", "");
                                                            reservaData.put("reserva activa", false);

                                                            userDocRef.update(reservaData)
                                                                    .addOnSuccessListener(aVoid -> {
                                                                        Toast.makeText(CalendarioActivity.this, "Reserva cancelada con éxito", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(CalendarioActivity.this, InicioActivity.class);
                                                                        startActivity(intent);
                                                                    });
                                                        }

                                            dialog.dismiss();
                                        });}
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                   .show();
                                }
                            });

                        } else {
                            // No hay reserva activa, oculta el boton
                            btncancelarReserva.setVisibility(View.GONE);


                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("error mostrar texto", "no muestra texto de reserva "+ usuario);

                });




        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CalendarioActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });


    }
}








