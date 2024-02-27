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


public class ViernesFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> horarioViernes = new ArrayList<>();
    private HorariosViewModel horariosViewModel;

    public ViernesFragment() {
        // Constructor público vacío requerido por la documentación de Fragment.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viernes, container, false);

        // Obtén referencias a los botones
        Button[] botones = new Button[]{
                view.findViewById(R.id.btn_6am_Viernes),
                view.findViewById(R.id.btn_7am_Viernes),
                view.findViewById(R.id.btn_8am_Viernes),
                view.findViewById(R.id.btn_9am_Viernes),
                view.findViewById(R.id.btn_10am_Viernes),
                view.findViewById(R.id.btn_11am_Viernes),
                view.findViewById(R.id.btn_12pm_Viernes),
                view.findViewById(R.id.btn_1pm_Viernes),
                view.findViewById(R.id.btn_2pm_Viernes),
                view.findViewById(R.id.btn_3pm_Viernes),
                view.findViewById(R.id.btn_4pm_Viernes),
                view.findViewById(R.id.btn_5pm_Viernes),
                view.findViewById(R.id.btn_6pm_Viernes),
                view.findViewById(R.id.btn_7pm_Viernes),
                view.findViewById(R.id.btn_8pm_Viernes),
                view.findViewById(R.id.btn_9pm_Viernes),
                view.findViewById(R.id.btn_10pm_Viernes),
                view.findViewById(R.id.btn_11pm_Viernes)
                // Agrega más botones según sea necesario...
        };

        for (Button boton : botones) {
            boton.setOnClickListener(this);
        }

        Button btnGuardarViernes = view.findViewById(R.id.btn_guardarViernes);
        btnGuardarViernes.setOnClickListener(new View.OnClickListener() {
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
            horarioViernes.remove(button.getText().toString());
        } else {
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            horarioViernes.add(button.getText().toString());
        }
    }

    private void guardarHorarios() {
        // Actualiza los horarios en el ViewModel
        horariosViewModel.setHorariosViernes(new ArrayList<>(horarioViernes));

        // Puedes realizar alguna acción aquí con los horarios guardados
        // Por ejemplo, puedes enviarlos a la actividad principal o realizar alguna otra lógica
        ArrayList<String> horariosGuardados = getHorariosViernes();
        Log.d("HorariosGuardados", "Horarios Viernes Guardados: " + horariosGuardados.toString());
    }

    private void cargarHorariosGuardados(Button[] botones) {
        // Obtén los horarios del ViewModel y configura el estado de los botones
        ArrayList<String> horariosGuardados = horariosViewModel.getHorariosViernes();
        for (String horario : horariosGuardados) {
            for (Button boton : botones) {
                if (boton.getText().toString().equals(horario)) {
                    boton.setSelected(true);
                    boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                }
            }
        }
        // Actualiza el array original con los horarios guardados
        horarioViernes.clear();
        horarioViernes.addAll(horariosGuardados);
    }

    public ArrayList<String> getHorariosViernes() {
        return horarioViernes;
    }
}
