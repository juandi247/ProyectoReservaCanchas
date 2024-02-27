package com.juandiegodiaz.appreservacancha;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiasPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 7; // Número total de días
    private List<Boolean> botonesSeleccionados;

    public DiasPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        botonesSeleccionados = new ArrayList<>(Collections.nCopies(NUM_PAGES, false));
    }
    @Override
    public Fragment getItem(int position) {
        // Devuelve el Fragment correspondiente al día en la posición actual
        switch (position) {
            case 0:
                return new LunesFragment();
            case 1:
                return new MartesFragment();
            case 2:
                return new MiercolesFragment();
            case 3:
                return new JuevesFragment();
            case 4:
                return new ViernesFragment();
            case 5:
                return new SabadoFragment();
            case 6:
                return new DomingoFragment();
            // Agrega más casos según sea necesario
            default:
                Log.d("mal", "no sirve: ");
                return null;


        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
