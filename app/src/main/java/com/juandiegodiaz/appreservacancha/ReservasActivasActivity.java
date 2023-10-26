package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ReservasActivasActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_activas);
        db = FirebaseFirestore.getInstance();
        TextView textViewUsuariosCancha = findViewById(R.id.tv_mostrarReservasActivas);

        SharedPreferences sharedPreferences = getSharedPreferences("AdminPreferencias", Context.MODE_PRIVATE);

        String canchaDeseada = sharedPreferences.getString("cancha", "UsuarioPredeterminado");

        Log.d("Cancha del admin", "cosoooo "+canchaDeseada);

        db.collection("Usuarios")
                .whereEqualTo("nombre cancha reservada", canchaDeseada)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            StringBuilder usuariosInfo = new StringBuilder(); // Utilizamos un StringBuilder para concatenar los resultados

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Accede a los datos de los usuarios que tienen la cancha deseada
                                String nombreUsuario = document.getString("nombre");
                                String ApellidoUsuario = document.getString("apellido");
                                String FechaReservada = document.getString("fecha reserva");
                                String HoraReservada = document.getString("hora reserva");

                                // Registra los datos de los usuarios en el log
                                usuariosInfo.append(nombreUsuario).append(" ").append(ApellidoUsuario).append(" Fecha de reserva: ").append(FechaReservada).append(" Hora: ").append(HoraReservada).append("\n \n ");

                                Log.d("UsuariosCancha", "Nombre: " + nombreUsuario +"" +ApellidoUsuario +", Fecha de reserva " + FechaReservada);
                            }
                            textViewUsuariosCancha.setText(usuariosInfo.toString());
                        } else {
                            Log.w("UsuariosCancha", "Error al buscar usuarios: ", task.getException());
                        }
                    }
                });
    }
}