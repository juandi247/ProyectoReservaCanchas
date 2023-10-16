package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FutboleraReservaActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String horaSeleccionada ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futbolera_reserva);

        db = FirebaseFirestore.getInstance();


        CalendarView calendarView = findViewById(R.id.cv_futbolera); // Reemplaza con el ID de tu CalendarView
        final String[] selectedDate = {""}; // Variable para guardar la fecha seleccionada

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Convierte la fecha seleccionada en un formato de String deseado
                selectedDate[0] = year + "-" + (month + 1) + "-" + dayOfMonth; // El mes se indexa desde 0
                Log.d("FechaSeleccionada", selectedDate[0]);

            }
        });





        Button btn7pm = findViewById(R.id.btn_reserva_futbolera_7pm);
        Button btn8pm = findViewById(R.id.btn_reserva_futbolera_8pm);
        Button btn9pm = findViewById(R.id.btn_reserva_futbolera_9pm);


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
        // Referencia a la colección de fechas
        CollectionReference fechasCollection = db.collection("Canchas").document("Cancha La Futbolera").collection("Fechas");

        // Obtén el documento de la fecha seleccionada
        DocumentReference fechaDoc = fechasCollection.document(fecha);

        fechaDoc.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        String disponibilidad = documentSnapshot.getString(hora);

                        if (disponibilidad != null) {
                            if (disponibilidad.equals("Disponible")) {
                                // La hora está disponible
                                Log.d("Disponibilidad", "La hora " + hora + " está disponible en la fecha " + fecha);
                            } else {
                                // La hora está ocupada
                                Log.d("Disponibilidad", "La hora " + hora + " está ocupada en la fecha " + fecha);
                                Toast.makeText(FutboleraReservaActivity.this, "La cancha esta ocupada en esta fehca, elige otra", Toast.LENGTH_SHORT).show();

                            }
                        }
                    } else {
                        // El documento de la fecha no existe
                        Log.d("Disponibilidad", "La fecha " + fecha + " no se puede reservar todavia");
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al obtener el documento
                    Log.e("Disponibilidad", "Error al obtener la disponibilidad: " + e.getMessage());
                });
    }}
