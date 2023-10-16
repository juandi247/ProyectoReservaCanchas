package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Button btnFutbolera = findViewById(R.id.btn_reservaFutbolera);
        Button btnNoviesota = findViewById(R.id.btn_reservaNoviesota);
       Button btnPicadelly = findViewById(R.id.btn_reservaPicadelly);

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
        String hora=sharedPreferences.getString("hora reserva","horaPre");


        Log.d("Coso usuario", "el usuario de la sesion es "+ usuario);
        Log.d("hora reserva","hora: "+hora);

        btnFutbolera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InicioActivity.this, FutboleraReservaActivity.class);
                startActivity(intent);
            }
        });



        btnNoviesota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InicioActivity.this,NoviesotaReservaActivity.class);
                startActivity(intent);
            }
        });


        btnPicadelly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InicioActivity.this, PicadellyReservaActivity.class);
                startActivity(intent);
            }
        });
    }
}