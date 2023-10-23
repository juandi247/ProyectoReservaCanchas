package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PerfilActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button btnGoToLogin = findViewById(R.id.cerrar_sesion_btn);
        ImageButton btnGoToInicio= findViewById(R.id.btn_home_desdePerfil);

        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


        btnGoToInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, InicioActivity.class);
                startActivity(intent);

            }
        });

       /* btnGoToCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, CalendarioActivity.class);
                startActivity(intent);

            }
        });

*/

    }

}