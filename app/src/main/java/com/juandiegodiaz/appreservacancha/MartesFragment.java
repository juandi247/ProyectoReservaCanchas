package com.juandiegodiaz.appreservacancha;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;



public class MartesFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> HorarioMartes = new ArrayList<>();

    public MartesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_martes, container, false);

        // Obtén referencias a los botones
        Button[] botones = new Button[]{
                view.findViewById(R.id.btn_6am_Martes),
                view.findViewById(R.id.btn_7am_Martes),
                view.findViewById(R.id.btn_8am_Martes),
                view.findViewById(R.id.btn_9am_Martes),
                view.findViewById(R.id.btn_10am_Martes),
                view.findViewById(R.id.btn_11am_Martes),
                view.findViewById(R.id.btn_12pm_Martes),
                view.findViewById(R.id.btn_1pm_Martes),
                view.findViewById(R.id.btn_2pm_Martes),
                view.findViewById(R.id.btn_3pm_Martes),
                view.findViewById(R.id.btn_4pm_Martes),
                view.findViewById(R.id.btn_5pm_Martes),
                view.findViewById(R.id.btn_6pm_Martes),
                view.findViewById(R.id.btn_7pm_Martes),
                view.findViewById(R.id.btn_8pm_Martes),
                view.findViewById(R.id.btn_9pm_Martes),
                view.findViewById(R.id.btn_10pm_Martes),
                view.findViewById(R.id.btn_11pm_Martes)
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
            HorarioMartes.remove(button.getText().toString());
        } else {
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            HorarioMartes.add(button.getText().toString());
        }
    }

    // Este método se llama al hacer clic en el botón de guardar horarios en la actividad principal


    public ArrayList<String> getHorariosMartes() {
        return HorarioMartes;
    }
}