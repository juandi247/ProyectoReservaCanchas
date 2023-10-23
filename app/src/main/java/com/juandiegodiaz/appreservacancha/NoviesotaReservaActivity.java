package com.juandiegodiaz.appreservacancha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class NoviesotaReservaActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String horaSeleccionada ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noviesota_reserva);

        db = FirebaseFirestore.getInstance();
        ImageButton boton_horario=findViewById(R.id.boton_CalendarioNovia);
        ImageButton boton_inicio=findViewById(R.id.boton_inicioNovia);
        ImageButton boton_perfil=findViewById(R.id.boton_perfilNovia);


        CalendarView calendarView = findViewById(R.id.cv_picadelly);
        final String[] selectedDate = {""}; // Variable para guardar la fecha seleccionada

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Convierte la fecha seleccionada en un formato de String deseado
                selectedDate[0] = year + "-" + (month + 1) + "-" + dayOfMonth; // El mes se indexa desde 0
                Log.d("FechaSeleccionada", selectedDate[0]);

            }
        });





        Button btn7pm = findViewById(R.id.btn_reserva_picadelly_7pm);
        Button btn8pm = findViewById(R.id.btn_reserva_picadelly_8pm);
        Button btn9pm = findViewById(R.id.btn_reserva_picadelly_9pm);


        btn7pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaSeleccionada = "7pm";
                verificarDisponibilidad(selectedDate[0], horaSeleccionada);

            }
        });

        // Listener para el botón 8pm
        btn8pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaSeleccionada = "8pm";
                verificarDisponibilidad(selectedDate[0], horaSeleccionada);
            }
        });

        btn9pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaSeleccionada = "9pm";
                verificarDisponibilidad(selectedDate[0], horaSeleccionada);
            }
        });




        boton_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoviesotaReservaActivity.this,PerfilActivity.class);
                startActivity(intent);
            }
        });


        boton_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoviesotaReservaActivity.this,CalendarioActivity.class);
                startActivity(intent);
            }
        });

        boton_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoviesotaReservaActivity.this,InicioActivity.class);
                startActivity(intent);
            }
        });
        // hacemos esto para que los 3 botones pues depende de cada boton de la hora cambia el string de la hora seleccionad
    }


    private void verificarDisponibilidad(String fecha, String hora) {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
        DocumentReference userDocRef = db.collection("Usuarios").document(usuario);
        CollectionReference fechasCollection = db.collection("Canchas").document("Cancha Noviesota").collection("Fechas");

        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Boolean reservaActiva = documentSnapshot.getBoolean("reserva activa");
                        if (reservaActiva != null && reservaActiva) {
                            // El usuario ya tiene una reserva activa, muestra un mensaje y no continúes
                            Toast.makeText(NoviesotaReservaActivity.this, "Ya tienes una reserva activa. No puedes reservar otra.", Toast.LENGTH_SHORT).show();
                        } else {



        DocumentReference fechaDoc = fechasCollection.document(fecha);
        fechaDoc.get()
                .addOnSuccessListener(documentSnapshot1 -> {
                    if (documentSnapshot1.exists()) {

                        String disponibilidad = documentSnapshot1.getString(hora);

                        if (disponibilidad != null) {
                            if (disponibilidad.equals("Disponible")) {
                                // La hora está disponible
                                Log.d("Disponibilidad", "La hora " + hora + " está disponible en la fecha " + fecha);

                                new AlertDialog.Builder(NoviesotaReservaActivity.this)
                                        .setTitle("Confirmar Reserva")
                                        .setMessage("¿Deseas confirmar la reserva para las " + hora + " en la fecha " + fecha + "?")
                                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                DocumentReference fechaDoc = fechasCollection.document(fecha);
                                                Map<String, Object> updateData = new HashMap<>();
                                                updateData.put(hora, "Ocupado");
                                                Intent intent = new Intent(NoviesotaReservaActivity.this, InicioActivity.class);
                                                startActivity(intent);
                                                SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                                String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
                                                //actualizacion de la persona que reservo la cancha!!!
                                                DocumentReference userDocRef = db.collection("Usuarios").document(usuario);
                                                userDocRef.update("hora reserva", hora);
                                                userDocRef.update("nombre cancha reservada", "Noviesota");
                                                userDocRef.update("reserva activa", true);
                                                userDocRef.update("fecha reserva",fecha )

                                                        .addOnSuccessListener(aVoid -> {
                                                            // Éxito al actualizar el campo
                                                            Log.d("Actualización", "Campo 'hora reserva' actualizado con éxito");
                                                        })
                                                        .addOnFailureListener(e -> {
                                                            // Error al actualizar el campo
                                                            Log.e("Actualización", "Error al actualizar campo 'hora reserva': " + e.getMessage());
                                                        });

                                                fechaDoc.set(updateData, SetOptions.merge())
                                                        .addOnSuccessListener(aVoid -> {
                                                            Toast.makeText(NoviesotaReservaActivity.this, "Reserva confirmada", Toast.LENGTH_SHORT).show();

                                                        })
                                                        .addOnFailureListener(e -> {
                                                            Toast.makeText(NoviesotaReservaActivity.this, "Error al confirmar la reserva", Toast.LENGTH_SHORT).show();

                                                        });


                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(NoviesotaReservaActivity.this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();







                            } else {
                                // La hora está ocupada
                                Log.d("Disponibilidad", "La hora " + hora + " está ocupada en la fecha " + fecha);
                                Toast.makeText(NoviesotaReservaActivity.this, "La cancha esta ocupada en esta fehca, elige otra", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        // El documento de la fecha no existe
                        Log.d("Disponibilidad", "La fecha " + fecha + " no se puede reservar todavia");
                        Toast.makeText(NoviesotaReservaActivity.this, "La fecha que elegiste, todavia no esta disponible para reserva :)", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento
                    Log.e("Disponibilidad", "Error al obtener la disponibilidad: " + e.getMessage());
                });
                        }
                    }
                });}}