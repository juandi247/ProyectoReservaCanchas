package com.juandiegodiaz.appreservacancha;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.widget.Button;

import java.util.ArrayList;

public class SabadoFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> horarioSabado = new ArrayList<>();
    private HorariosViewModel horariosViewModel;

    public SabadoFragment() {
        // Constructor público vacío requerido por la documentación de Fragment.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sabado, container, false);

        // Obtén referencias a los botones
        Button[] botones = new Button[]{
                view.findViewById(R.id.btn_6am_Sabado),
                view.findViewById(R.id.btn_7am_Sabado),
                view.findViewById(R.id.btn_8am_Sabado),
                view.findViewById(R.id.btn_9am_Sabado),
                view.findViewById(R.id.btn_10am_Sabado),
                view.findViewById(R.id.btn_11am_Sabado),
                view.findViewById(R.id.btn_12pm_Sabado),
                view.findViewById(R.id.btn_1pm_Sabado),
                view.findViewById(R.id.btn_2pm_Sabado),
                view.findViewById(R.id.btn_3pm_Sabado),
                view.findViewById(R.id.btn_4pm_Sabado),
                view.findViewById(R.id.btn_5pm_Sabado),
                view.findViewById(R.id.btn_6pm_Sabado),
                view.findViewById(R.id.btn_7pm_Sabado),
                view.findViewById(R.id.btn_8pm_Sabado),
                view.findViewById(R.id.btn_9pm_Sabado),
                view.findViewById(R.id.btn_10pm_Sabado),
                view.findViewById(R.id.btn_11pm_Sabado)
                // Agrega más botones según sea necesario...
        };

        for (Button boton : botones) {
            boton.setOnClickListener(this);
        }

        Button btnGuardarSabado = view.findViewById(R.id.btn_GuardarSabado);
        btnGuardarSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHorarios();
            }
        });

        // Obtiene una instancia del ViewModel
        horariosViewModel = new ViewModelProvider(requireActivity()).get(HorariosViewModel.class);

        // Cargar los horarios guardados, si existen
        cargarHorariosGuardados(botones);

        return view;
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        toggleButtonSelection(clickedButton);
    }

    private void toggleButtonSelection(Button button) {
        if (button.isSelected()) {
            button.setSelected(false);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6200EE")));
            String buttonText = button.getText().toString();
            horarioSabado.remove(buttonText);
        } else {
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            String buttonText = button.getText().toString();
            horarioSabado.add(buttonText);
        }
    }

    private void guardarHorarios() {
        // Actualiza los horarios en el ViewModel
        horariosViewModel.setHorariosSabado(new ArrayList<>(horarioSabado));

        // Puedes realizar alguna acción aquí con los horarios guardados
        ArrayList<String> horariosGuardados = getHorariosSabado();
        Log.d("HorariosGuardados", "Horarios Sábado Guardados: " + horariosGuardados.toString());
    }

    private void cargarHorariosGuardados(Button[] botones) {
        // Obtén los horarios del ViewModel y configura el estado de los botones
        ArrayList<String> horariosGuardados = horariosViewModel.getHorariosSabado();
        for (String horario : horariosGuardados) {
            for (Button boton : botones) {
                if (boton.getText().toString().equals(horario)) {
                    boton.setSelected(true);
                    boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                }
            }
        }
        // Actualiza el array original con los horarios guardados
        horarioSabado.clear();
        horarioSabado.addAll(horariosGuardados);
    }

    public ArrayList<String> getHorariosSabado() {
        return horarioSabado;
    }
}




