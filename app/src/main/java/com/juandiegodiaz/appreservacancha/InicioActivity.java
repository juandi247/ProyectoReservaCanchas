package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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