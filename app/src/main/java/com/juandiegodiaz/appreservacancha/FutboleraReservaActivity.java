package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

public class FutboleraReservaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_futbolera_reserva);



        CalendarView calendarView = findViewById(R.id.cv_futbolera); // Reemplaza con el ID de tu CalendarView
        final String[] selectedDate = {""}; // Variable para guardar la fecha seleccionada

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Convierte la fecha seleccionada en un formato de String deseado
                selectedDate[0] =year+ "-"+ (month + 1) +"+"+dayOfMonth ; // El mes se indexa desde 0
                Log.d("FechaSeleccionada", selectedDate[0]);

            }
        });

    }
}