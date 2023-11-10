package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class ReservasActivasActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<String> reservasList;
    private ReservasAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_activas);
        db = FirebaseFirestore.getInstance();

        db = FirebaseFirestore.getInstance();
        reservasList = new ArrayList<>(); // Aquí debes cargar la lista con los datos de tu base de datos

        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservasAdapter(reservasList);
        recyclerView.setAdapter(adapter);

        // Obtener datos de Firebase y actualizar el adaptador
        obtenerDatosDeFirebase();
    }






    private void obtenerDatosDeFirebase() {
        SharedPreferences sharedPreferences = getSharedPreferences("AdminPreferencias", Context.MODE_PRIVATE);
        String canchaDeseada = sharedPreferences.getString("cancha", "UsuarioPredeterminado");

        Log.d("Cancha del admin", "cosoooo " + canchaDeseada);

        db.collection("Usuarios")
                .whereEqualTo("nombre cancha reservada", canchaDeseada)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            reservasList.clear(); // Limpiar la lista antes de agregar nuevos elementos

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String nombreUsuario = document.getString("nombre");
                                String ApellidoUsuario = document.getString("apellido");
                                String FechaReservada = document.getString("fecha reserva");
                                String HoraReservada = document.getString("hora reserva");
                                String Usuario=document.getString("usuario");

                                // Crear una cadena con la información de la reserva y agregarla a la lista
                                String NombreAppelidoInfo = nombreUsuario + " " + ApellidoUsuario;
                                String FechaHoraInfo=" Fecha: " + FechaReservada + " Hora: " + HoraReservada;
                                String NombreUsuario=Usuario;
                                reservasList.add(NombreAppelidoInfo + ";" + FechaHoraInfo+";"+NombreUsuario);                            }

                            // Notificar al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("UsuariosCancha", "Error al buscar usuarios: ", task.getException());
                        }
                    }
                });


}
}





































/*protected void onCreate(Bundle savedInstanceState) {
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
} */

