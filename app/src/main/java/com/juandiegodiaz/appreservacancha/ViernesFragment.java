package com.juandiegodiaz.appreservacancha;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.ColorStateList;
import android.graphics.Color;

import android.widget.Button;

import java.util.ArrayList;



public class ViernesFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> HorarioViernes = new ArrayList<>();

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
            HorarioViernes.remove(button.getText().toString());
        } else {
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            HorarioViernes.add(button.getText().toString());
        }
    }

    // Este método se llama al hacer clic en el botón de guardar horarios en la actividad principal


    public ArrayList<String> getHorariosViernes() {
        return HorarioViernes;


    }
}
