package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

public class InformacionAdminsActivity extends AppCompatActivity {
private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_admins);
       Button btnReservasActivas=findViewById(R.id.verReservasboton);
        db = FirebaseFirestore.getInstance();
        TextView infoCanchaTextView = findViewById(R.id.Name_canchaTop);

        SharedPreferences sharedPreferences = getSharedPreferences("AdminPreferencias", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", ""); // "" es el valor predeterminado si no se encuentra
        String nombreCancha = sharedPreferences.getString("cancha", "");
        Log.d("cosisico","cosoooocisocsio" +usuario);
        Log.d("cosisico","cosoooocisocsio" +nombreCancha);

        btnReservasActivas.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(InformacionAdminsActivity.this, ReservasActivasActivity.class);
               startActivity(intent);
           }
       });


        db.collection("Administradores")
                .whereEqualTo("usuario", usuario)
                .whereEqualTo("cancha", nombreCancha)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().getDocuments().isEmpty()) {
                        DocumentSnapshot adminDoc = task.getResult().getDocuments().get(0);

                        if (adminDoc.exists()) {
                            // Obtiene el valor del campo "nombreCancha" desde el documento
                            String infoCancha = adminDoc.getString("cancha");
                            infoCanchaTextView.setText("Información Cancha: " + infoCancha);
                        } else {
                            infoCanchaTextView.setText("Información Cancha: No disponible");
                        }
                    } else {
                        infoCanchaTextView.setText("Información Cancha:No");
                    }
                });



    }
}