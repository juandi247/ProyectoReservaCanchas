package com.juandiegodiaz.appreservacancha;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





public class ReservasAdapter extends RecyclerView.Adapter<ReservasAdapter.ReservaViewHolder> {

    private List<String> reservasList;
    private FirebaseFirestore db;
    private Context context;


    public ReservasAdapter(List<String> reservasList) {
        this.reservasList = reservasList;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_reservas_activas, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        String reservaInfo = reservasList.get(position);
        // Dividir la cadena de reservaInfo según un delimitador (puedes ajustar esto según la estructura real de tus datos)
        String[] partesReserva = reservaInfo.split(";");

        // Asignar los datos a las vistas
        holder.textViewNombre.setText(partesReserva[0]); // El nombre debería estar en la primera parte
        holder.textViewFechaHora.setText(partesReserva[1]);
        String NameUsuario=partesReserva[2];
        Log.d("nombre del usuario", NameUsuario);// La fecha y hora deberían estar en la segunda parte


        holder.btnFinalizarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para eliminar la reserva usando el nombre de usuario

                eliminarReserva(NameUsuario);
            }
        });
    }


    @Override
    public int getItemCount() {
        return reservasList.size();
    }

    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewFechaHora;
        Button btnFinalizarReserva;
        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewFechaHora = itemView.findViewById(R.id.textViewFechaHora);
            btnFinalizarReserva = itemView.findViewById(R.id.btnFinalizarReserva);
        }
    }


    private void eliminarReserva(String nombreUsuario) {

        db = FirebaseFirestore.getInstance();
        DocumentReference userDocRef = db.collection("Usuarios").document(nombreUsuario);




        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fechaReserva = documentSnapshot.getString("fecha reserva");
                        String horaReserva = documentSnapshot.getString("hora reserva");
                        String canchaReservada = documentSnapshot.getString("nombre cancha reservada");
                        canchaReservada = "Cancha " + canchaReservada;
                        // Actualiza el horario correspondiente en la cancha
                        DocumentReference canchaDocRef = db.collection("Canchas").document(canchaReservada).collection("Fechas").document(fechaReserva);
                        Map<String, Object> horarioUpdate = new HashMap<>();
                        horarioUpdate.put(horaReserva, "Disponible");

                        canchaDocRef.set(horarioUpdate, SetOptions.merge());

                        // Elimina los campos relacionados con la reserva en el documento del usuario
                        Map<String, Object> reservaData = new HashMap<>();
                        reservaData.put("hora reserva", "");
                        reservaData.put("fecha reserva", "");
                        reservaData.put("nombre cancha reservada", "");
                        reservaData.put("reserva activa", false);

                        userDocRef.update(reservaData)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("ELIMINACION", "RESERVA ELIMINADAAA: ");


                                });
                    }
                });







    }
}




