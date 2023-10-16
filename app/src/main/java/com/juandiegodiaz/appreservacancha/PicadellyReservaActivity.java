package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PicadellyReservaActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private String horaSeleccionada ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picadelly_reserva);

        db = FirebaseFirestore.getInstance();


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


        // hacemos esto para que los 3 botones pues depende de cada boton de la hora cambia el string de la hora seleccionad
    }


    private void verificarDisponibilidad(String fecha, String hora) {

        CollectionReference fechasCollection = db.collection("Canchas").document("Cancha Noviesota").collection("Fechas");
        DocumentReference fechaDoc = fechasCollection.document(fecha);

        fechaDoc.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        String disponibilidad = documentSnapshot.getString(hora);

                        if (disponibilidad != null) {
                            if (disponibilidad.equals("Disponible")) {
                                // La hora está disponible
                                Log.d("Disponibilidad", "La hora " + hora + " está disponible en la fecha " + fecha);

                                new AlertDialog.Builder(PicadellyReservaActivity.this)
                                        .setTitle("Confirmar Reserva")
                                        .setMessage("¿Deseas confirmar la reserva para las " + hora + " en la fecha " + fecha + "?")
                                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                DocumentReference fechaDoc = fechasCollection.document(fecha);
                                                Map<String, Object> updateData = new HashMap<>();
                                                updateData.put(hora, "Ocupado");
                                                Intent intent = new Intent(PicadellyReservaActivity.this, InicioActivity.class);
                                                startActivity(intent);

                                                fechaDoc.set(updateData, SetOptions.merge())
                                                        .addOnSuccessListener(aVoid -> {
                                                            Toast.makeText(PicadellyReservaActivity.this, "Reserva confirmada", Toast.LENGTH_SHORT).show();

                                                        })
                                                        .addOnFailureListener(e -> {
                                                            Toast.makeText(PicadellyReservaActivity.this, "Error al confirmar la reserva", Toast.LENGTH_SHORT).show();

                                                        });


                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(PicadellyReservaActivity.this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        })
                                        .show();







                            } else {
                                // La hora está ocupada
                                Log.d("Disponibilidad", "La hora " + hora + " está ocupada en la fecha " + fecha);
                                Toast.makeText(PicadellyReservaActivity.this, "La cancha esta ocupada en esta fehca, elige otra", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        // El documento de la fecha no existe
                        Log.d("Disponibilidad", "La fecha " + fecha + " no se puede reservar todavia");
                        Toast.makeText(PicadellyReservaActivity.this, "La fecha que elegiste, todavia no esta disponible para reserva :)", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento
                    Log.e("Disponibilidad", "Error al obtener la disponibilidad: " + e.getMessage());
                });
    }}