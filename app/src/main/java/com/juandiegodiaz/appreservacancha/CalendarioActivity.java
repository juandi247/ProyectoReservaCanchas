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

public class CalendarioActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        db = FirebaseFirestore.getInstance();

        Button btncancelarReserva = findViewById(R.id.btn_cancelarReserva);
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
        String hora=sharedPreferences.getString("hora reserva","horaPre");
        DocumentReference userDocRef = db.collection("Usuarios").document(usuario);



        ImageButton btnHome=findViewById(R.id.btn_Home_desdeCalendario2);


        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Boolean reservaActiva = documentSnapshot.getBoolean("reserva activa");
                        if (reservaActiva != null && !reservaActiva) {
                            // El usuario tiene una reserva activa, muestra el mensaje
                            btncancelarReserva.setVisibility(View.GONE);
                        } else {
                            // No hay reserva activa, oculta el boton
                            btncancelarReserva.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("error mostrar texto", "no muestra texto de reserva "+ usuario);

                });




        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CalendarioActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });


    }
}