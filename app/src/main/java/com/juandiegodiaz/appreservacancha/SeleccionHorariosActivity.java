package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SeleccionHorariosActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> horariosLunes = new ArrayList<>();
    private ArrayList<String> horariosMartes = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_horarios);


        // Obtén referencias a los botones
        Button[] botonesmartes = new Button[]{
                findViewById(R.id.btn_6am_Martes),
                findViewById(R.id.btn_7am_Martes),
                findViewById(R.id.btn_8am_Martes),
                findViewById(R.id.btn_9am_Martes),
                findViewById(R.id.btn_10am_Martes),
                findViewById(R.id.btn_11am_Martes),
                findViewById(R.id.btn_12pm_Martes),
                findViewById(R.id.btn_1pm_Martes),
                findViewById(R.id.btn_2pm_Martes),
                findViewById(R.id.btn_3pm_Martes),
                findViewById(R.id.btn_4pm_Martes),
                findViewById(R.id.btn_5pm_Martes),
                findViewById(R.id.btn_6pm_Martes),
                findViewById(R.id.btn_7pm_Martes),
                findViewById(R.id.btn_8pm_Martes),
                findViewById(R.id.btn_9pm_Martes),
                findViewById(R.id.btn_10pm_Martes),
                findViewById(R.id.btn_11pm_Martes)
                // Agrega más botones según sea necesario...
        };
        // Obtén referencias a los botones del lunes
        Button[] botonesLunes = new Button[]{
                findViewById(R.id.btn_6am),
                findViewById(R.id.btn_7am),
                findViewById(R.id.btn_8am),
                findViewById(R.id.btn_9am),
                findViewById(R.id.btn_10am),
                findViewById(R.id.btn_11am),
                findViewById(R.id.btn_12pm),
                findViewById(R.id.btn_1pm),
                findViewById(R.id.btn_2pm),
                findViewById(R.id.btn_3pm),
                findViewById(R.id.btn_4pm),
                findViewById(R.id.btn_5pm),
                findViewById(R.id.btn_6pm),
                findViewById(R.id.btn_7pm),
                findViewById(R.id.btn_8pm),
                findViewById(R.id.btn_9pm),
                findViewById(R.id.btn_10pm),
                findViewById(R.id.btn_11pm)
                // Agrega más botones según sea necesario...
        };


        for (Button botonMartes : botonesmartes) {
            botonMartes.setOnClickListener(this);
        }

        // Configura el mismo OnClickListener para todos los botones del lunes
        for (Button botonLunes : botonesLunes) {
            botonLunes.setOnClickListener(this);
        }
        Button btnGuardarHorarios = findViewById(R.id.btn_guardar_horarios);
        btnGuardarHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama a la función para guardar los horarios
                guardarHorarios();
                // Repite el proceso para otros días...
            }
            private void guardarHorarios() {
                // Recorre los botones del lunes y guarda los textos de los botones seleccionados
                for (Button button : botonesLunes) {
                    if (button.isSelected()) {
                        horariosLunes.add(button.getText().toString());
                    }
                }
                // Puedes imprimir los horarios del lunes guardados para verificar
                Log.d("HorariosLunes","Horario Lunes"+ horariosLunes.toString());

                // Recorre los botones del martes y guarda los textos de los botones seleccionados
                for (Button button : botonesmartes) {
                    if (button.isSelected()) {
                        horariosMartes.add(button.getText().toString());
                    }
                }
                // Puedes imprimir los horarios del martes guardados para verificar
                Log.d("HorariosMartes","Horario Martes" +horariosMartes.toString());
            }
        });








    }


    @Override
    public void onClick(View view) {
        // Identifica el botón presionado por su referencia
        Button clickedButton = (Button) view;
        Log.d("BotonClic", "Botón clicado con ID: " + clickedButton.getId());

        // Aplica la lógica según el botón presionado
        toggleButtonSelection(clickedButton);
    }

    // Función para alternar la selección/deselección de un botón
    private void toggleButtonSelection(Button button) {
        if (button.isSelected()) {
            // Deseleccionar
            button.setSelected(false);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCCCCC")));
        } else {
            // Seleccionar
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00"))); // Cambia el color a tu preferencia
        }
    }



}