package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Button btnFutbolera = findViewById(R.id.btn_reservaFutbolera);
        Button btnNoviesota = findViewById(R.id.btn_reservaNoviesota);
       Button btnPicadelly = findViewById(R.id.btn_reservaPicadelly);

       //agregacion de botones para el menu de abajo
       ImageButton btnHorario=findViewById(R.id.btn_verCalendario_desdeInicio);
       ImageButton btnPerfil=findViewById(R.id.btn_verPerfil_desdeInicio);


        TextView txtReservaActiva = findViewById(R.id.tv_reservaActiva);
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
        String hora=sharedPreferences.getString("hora reserva","horaPre");
        DocumentReference userDocRef = db.collection("Usuarios").document(usuario);


        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Boolean reservaActiva = documentSnapshot.getBoolean("reserva activa");
                        if (reservaActiva != null && reservaActiva) {
                            // El usuario tiene una reserva activa, muestra el mensaje
                            txtReservaActiva.setVisibility(View.VISIBLE);
                        } else {
                            // No hay reserva activa, oculta el mensaje
                            txtReservaActiva.setVisibility(View.GONE);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("error mostrar texto", "no muestra texto de reserva "+ usuario);

                });



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


        btnHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InicioActivity.this, CalendarioActivity.class);
                startActivity(intent);
            }
        });


        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InicioActivity.this, PerfilActivity.class);
                startActivity(intent);
            }
        });
    }
}