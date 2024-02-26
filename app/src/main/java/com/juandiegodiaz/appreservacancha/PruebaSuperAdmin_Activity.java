package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class PruebaSuperAdmin_Activity extends AppCompatActivity {

    private DiasPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_super_admin);

        ViewPager viewPager = findViewById(R.id.viewPager);
        adapter = new DiasPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // Obtén referencia al botón "Guardar Horarios"
        Button btnGuardarHorarios = findViewById(R.id.btnGuardar_Horarios_Super);

        // Configura el OnClickListener para el botón
        btnGuardarHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama al método para gestionar el evento de guardar horarios
                onGuardarHorariosClick(view);
            }
        });
    }
    // Método llamado al presionar el botón "Guardar Horarios"
    public void onGuardarHorariosClick(View view) {
        // Obtén una referencia al ViewPager
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Obtén una referencia al fragmento actual en la posición 0 (Lunes)
        LunesFragment lunesFragment = (LunesFragment) adapter.instantiateItem(viewPager, 0);
        // Obtén los horarios del fragmento
        ArrayList<String> horariosLunes = lunesFragment.getHorarioLunes();
        // Imprime o gestiona estos horarios como desees
        Log.d("HorariosLunes", "Horarios Lunes: " + horariosLunes.toString());

        // Repite estos pasos para los demás días según sea necesario
        MartesFragment martesFragment = (MartesFragment) adapter.instantiateItem(viewPager, 1);
        ArrayList<String> horariosMartes = martesFragment.getHorariosMartes();
        Log.d("HorariosMartes", "Horarios Martes: " + horariosMartes.toString());

        MiercolesFragment miercolesFragment = (MiercolesFragment) adapter.instantiateItem(viewPager, 2);
        ArrayList<String> horariosMiercoles = miercolesFragment.getHorariosMiercoles();
        Log.d("HorariosMiercoles", "Horarios Miércoles: " + horariosMiercoles.toString());

        JuevesFragment juevesFragment = (JuevesFragment) adapter.instantiateItem(viewPager, 3);
        ArrayList<String> horariosJueves = juevesFragment.getHorariosJueves();
        Log.d("HorariosJueves", "Horarios Jueves: " + horariosJueves.toString());

        ViernesFragment viernesFragment = (ViernesFragment) adapter.instantiateItem(viewPager, 4);
        ArrayList<String> horariosViernes = viernesFragment.getHorariosViernes();
        Log.d("HorariosViernes", "Horarios Viernes: " + horariosViernes.toString());

        SabadoFragment sabadoFragment = (SabadoFragment) adapter.instantiateItem(viewPager, 5);
        ArrayList<String> horariosSabado = sabadoFragment.getHorariosSabado();
        Log.d("HorariosSabado", "Horarios Sábado: " + horariosSabado.toString());

        DomingoFragment domingoFragment = (DomingoFragment) adapter.instantiateItem(viewPager, 6);
        ArrayList<String> horariosDomingo = domingoFragment.getHorariosDomingo();
        Log.d("HorariosDomingo", "Horarios Domingo: " + horariosDomingo.toString());
    }

}