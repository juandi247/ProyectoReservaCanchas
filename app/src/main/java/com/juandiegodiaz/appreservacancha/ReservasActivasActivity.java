package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

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
        String canchaDeseada = "Picadelly";
        Log.d("UsuariosCancha", "cosoooo");

        db.collection("Usuarios")
                .whereEqualTo("nombre cancha reservada", canchaDeseada)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Accede a los datos de los usuarios que tienen la cancha deseada
                                String nombreUsuario = document.getString("nombre");
                                String ApellidoUsuario = document.getString("apellido");
                                String FechaReservada = document.getString("fecha reserva");

                                // Registra los datos de los usuarios en el log
                                Log.d("UsuariosCancha", "Nombre: " + nombreUsuario +"" +ApellidoUsuario +", Fecha de reserva " + FechaReservada);
                            }
                        } else {
                            Log.w("UsuariosCancha", "Error al buscar usuarios: ", task.getException());
                        }
                    }
                });
    }
}