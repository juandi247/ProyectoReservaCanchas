package com.juandiegodiaz.appreservacancha;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentStatePagerAdapter;

public class DiasPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 7; // Número total de días

    public DiasPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
