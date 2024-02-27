package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

public class PruebaSuperAdmin_Activity extends AppCompatActivity {

    private DiasPagerAdapter adapter;
    private HorariosViewModel horariosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_super_admin);

        // Inicializa el ViewModel
        horariosViewModel = new ViewModelProvider(this).get(HorariosViewModel.class);

        ViewPager viewPager = findViewById(R.id.viewPager);
        adapter = new DiasPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Obtén referencia al botón "Guardar Horarios"
        Button btnFinalizar = findViewById(R.id.btnGuardar_Horarios_Super);

        // Configura el OnClickListener para el botón
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama al método para gestionar el evento de guardar horarios
                onGuardarHorariosClick(view);
            }
        });
    }

    // Método llamado al presionar el botón "Guardar Horarios"
    public void onGuardarHorariosClick(View view) {
        // Repite estos pasos para los demás días según sea necesario
        ArrayList<String> horariosLunes = horariosViewModel.getHorariosLunes();
        Collections.sort(horariosLunes);
        Log.d("HorariosLunes", "Horarios Lunes: " + horariosLunes.toString());

        ArrayList<String> horariosMartes = horariosViewModel.getHorariosMartes();
        Collections.sort(horariosMartes);
        Log.d("HorariosMartes", "Horarios Martes: " + horariosMartes.toString());

        ArrayList<String> horariosMiercoles = horariosViewModel.getHorariosMiercoles();
        Collections.sort(horariosMiercoles);
        Log.d("HorariosMiercoles", "Horarios Miércoles: " + horariosMiercoles.toString());

        ArrayList<String> horariosJueves = horariosViewModel.getHorariosJueves();
        Collections.sort(horariosJueves);
        Log.d("HorariosJueves", "Horarios Jueves: " + horariosJueves.toString());

        ArrayList<String> horariosViernes = horariosViewModel.getHorariosViernes();
        Collections.sort(horariosViernes);
        Log.d("HorariosViernes", "Horarios Viernes: " + horariosViernes.toString());

        ArrayList<String> horariosSabado = horariosViewModel.getHorariosSabado();
        Collections.sort(horariosSabado);
        Log.d("HorariosSabado", "Horarios Sábado: " + horariosSabado.toString());

        ArrayList<String> horariosDomingo = horariosViewModel.getHorariosDomingo();
        Collections.sort(horariosDomingo);
        Log.d("HorariosDomingo", "Horarios Domingo: " + horariosDomingo.toString());
    }
}