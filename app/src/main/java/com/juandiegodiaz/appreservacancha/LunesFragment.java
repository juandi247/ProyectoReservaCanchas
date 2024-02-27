package com.juandiegodiaz.appreservacancha;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */



public class LunesFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> horarioLunes = new ArrayList<>();
    private HorariosViewModel horariosViewModel;

    public LunesFragment() {
        // Constructor público vacío requerido por la documentación de Fragment.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lunes, container, false);

        // Obtén referencias a los botones
        Button[] botones = new Button[]{
                view.findViewById(R.id.btn_6am),
                view.findViewById(R.id.btn_7am),
                view.findViewById(R.id.btn_8am),
                view.findViewById(R.id.btn_9am),
                view.findViewById(R.id.btn_10am),
                view.findViewById(R.id.btn_11am),
                view.findViewById(R.id.btn_12pm),
                view.findViewById(R.id.btn_1pm),
                view.findViewById(R.id.btn_2pm),
                view.findViewById(R.id.btn_3pm),
                view.findViewById(R.id.btn_4pm),
                view.findViewById(R.id.btn_5pm),
                view.findViewById(R.id.btn_6pm),
                view.findViewById(R.id.btn_7pm),
                view.findViewById(R.id.btn_8pm),
                view.findViewById(R.id.btn_9pm),
                view.findViewById(R.id.btn_10pm),
                view.findViewById(R.id.btn_11pm)
        };

        for (Button boton : botones) {
            boton.setOnClickListener(this);
        }

        Button btnGuardarLunes = view.findViewById(R.id.btn_GuardarLunes);
        btnGuardarLunes.setOnClickListener(new View.OnClickListener() {
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
            horarioLunes.remove(buttonText);
        } else {
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            String buttonText = button.getText().toString();
            horarioLunes.add(buttonText);
        }
    }

    private void guardarHorarios() {
        // Actualiza los horarios en el ViewModel
        horariosViewModel.setHorariosLunes(new ArrayList<>(horarioLunes));

        // Puedes realizar alguna acción aquí con los horarios guardados
        ArrayList<String> horariosGuardados = getHorarioLunes();
        Log.d("HorariosGuardados", "Horarios Lunes Guardados: " + horariosGuardados.toString());
    }

    private void cargarHorariosGuardados(Button[] botones) {
        // Obtén los horarios del ViewModel y configura el estado de los botones
        ArrayList<String> horariosGuardados = horariosViewModel.getHorariosLunes();
        for (String horario : horariosGuardados) {
            for (Button boton : botones) {
                if (boton.getText().toString().equals(horario)) {
                    boton.setSelected(true);
                    boton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
                }
            }
        }
        // Actualiza el array original con los horarios guardados
        horarioLunes.clear();
        horarioLunes.addAll(horariosGuardados);
    }

    public ArrayList<String> getHorarioLunes() {
        return horarioLunes;
    }
}
