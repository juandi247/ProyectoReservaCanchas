package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        //BOTONES DE REINICIO Y VOLVER ATRAS

        ImageButton btnVolver = findViewById(R.id.btn_volverMostrarAdmin);
        ImageButton btnRefresh = findViewById(R.id.btn_reiniciarPagReservas);


        // Configurar RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReservas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservasAdapter(reservasList);
        recyclerView.setAdapter(adapter);

        // Obtener datos de Firebase y actualizar el adaptador
        obtenerDatosDeFirebase();


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ReservasActivasActivity.this, InformacionAdminsActivity.class);
                startActivity(intent);
            }
        });


      btnRefresh.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
            obtenerDatosDeFirebase();
            Toast.makeText(ReservasActivasActivity.this, "Pagina Refrescada", Toast.LENGTH_SHORT).show();

        }
    });
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
                                String Usuario = document.getString("usuario");

                                // Crear una cadena con la información de la reserva y agregarla a la lista
                                String NombreAppelidoInfo = nombreUsuario + " " + ApellidoUsuario;
                                String FechaHoraInfo = " Fecha: " + FechaReservada + " Hora: " + HoraReservada;
                                String NombreUsuario = Usuario;
                                reservasList.add(NombreAppelidoInfo + ";" + FechaHoraInfo + ";" + NombreUsuario);
                            }
                            ordenarReservasPorFecha();

                            // Notificar al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.w("UsuariosCancha", "Error al buscar usuarios: ", task.getException());
                        }
                    }
                });


    }



    private void ordenarReservasPorFecha() {
        Collections.sort(reservasList, new Comparator<String>() {
            @Override
            public int compare(String reserva1, String reserva2) {
                String[] partesReserva1 = reserva1.split(";");
                String[] partesReserva2 = reserva2.split(";");

                String fechaStr1 = partesReserva1[1]; // La fecha está en la segunda parte
                String fechaStr2 = partesReserva2[1];

                // Verificar si alguna de las fechas es nula o está vacía
                if (fechaStr1.isEmpty() && fechaStr2.isEmpty()) {
                    return 0; // Ambas fechas son nulas o vacías, considerarlas iguales
                } else if (fechaStr1.isEmpty()) {
                    return 1; // La primera fecha es nula o vacía, considerar la segunda mayor
                } else if (fechaStr2.isEmpty()) {
                    return -1; // La segunda fecha es nula o vacía, considerar la primera mayor
                }

                try {
                    return fechaStr1.compareTo(fechaStr2);
                } catch (NullPointerException e) {
                    // Imprimir la pila de llamadas en caso de excepción
                    e.printStackTrace();
                    return 0; // Manejar la excepción y considerar las fechas iguales
                }
            }
        });
    }

}













/*

    private void ordenarReservasPorFecha() {
        Collections.sort(reservasList, new Comparator<String>() {
            @Override
            public int compare(String reserva1, String reserva2) {
                Date fecha1 = parsearFecha(reserva1);
                Date fecha2 = parsearFecha(reserva2);

                return fecha1.compareTo(fecha2);
            }

            private Date parsearFecha(String fechaString) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    return sdf.parse(fechaString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
}
 */


































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

