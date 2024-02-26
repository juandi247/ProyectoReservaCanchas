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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LunesFragment extends Fragment implements View.OnClickListener {

    private ArrayList<String> HorarioLunes = new ArrayList<>();

    public LunesFragment() {
        // Required empty public constructor
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
            String buttonText = button.getText().toString();
            HorarioLunes.remove(buttonText);
            Log.d("Boton", "Deseleccionado: " + buttonText + " - HorarioLunes: " + HorarioLunes.toString());
        } else {
            button.setSelected(true);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF00")));
            String buttonText = button.getText().toString();
            HorarioLunes.add(buttonText);
            Log.d("Boton", "Seleccionado: " + buttonText + " - HorarioLunes: " + HorarioLunes.toString());
        }
    }



    // Este método se llama al hacer clic en el botón de guardar horarios en la actividad principal
    public ArrayList<String> getHorarioLunes() {
        return HorarioLunes;
    }


}