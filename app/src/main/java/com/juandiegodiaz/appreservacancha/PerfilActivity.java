package com.juandiegodiaz.appreservacancha;

import static android.widget.Toast.makeText;

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
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Button btnGoToLogin = findViewById(R.id.cerrar_sesion_btn);
        ImageButton btnGoToInicio= findViewById(R.id.btn_home_desdePerfil);
        ImageButton btnGoToCalendar= findViewById(R.id.btn_calendarPerfil);
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "UsuarioPredeterminado");
        DocumentReference userDocRef = db.collection("Usuarios").document(usuario);
        TextView nombreTextView = findViewById(R.id.tv_nombreUsuario);
        TextView UserTextView = findViewById(R.id.tv_usuario);

        userDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String nombreUsuario = documentSnapshot.getString("nombre");
         String User= documentSnapshot.getString("usuario");
                nombreTextView.setText("¡Hola! "+nombreUsuario);
                UserTextView.setText("Tu usuario es: "+User);


            }
        });





        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                startActivity(intent);
               Toast.makeText(PerfilActivity.this, "Cerraste Sesion", Toast.LENGTH_SHORT).show();

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

        btnGoToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar la actividad de inicio
                Intent intent = new Intent(PerfilActivity.this, CalendarioActivity.class);
                startActivity(intent);

            }
        });



    }

}