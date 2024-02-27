package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PruebaSuperAdmin_Activity extends AppCompatActivity {

    private DiasPagerAdapter adapter;
    private HorariosViewModel horariosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_super_admin);

        // Inicializa el ViewModel
        horariosViewModel = new ViewModelProvider(this).get(HorariosViewModel.class);

        ViewPager viewPager = findViewById(R.id.viewPager);
        adapter = new DiasPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Obtén referencia al botón "Guardar Horarios"
        Button btnFinalizar = findViewById(R.id.btnGuardar_Horarios_Super);

        // Configura el OnClickListener para el botón
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama al método para gestionar el evento de guardar horarios
                onGuardarHorariosClick(view);
                String nombreCancha = obtenerUsuarioCancha();

                // Utiliza el nombre de usuario en tu log
                Log.d("PruebaSuperAdmin", "Usuario de la Cancha: " + nombreCancha);
                Intent intent = new Intent(PruebaSuperAdmin_Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Método llamado al presionar el botón "Guardar Horarios"
    public void onGuardarHorariosClick(View view) {
        // Repite estos pasos para los demás días según sea necesario
        ArrayList<String> horariosLunes = horariosViewModel.getHorariosLunes();
        Collections.sort(horariosLunes);
        Log.d("HorariosLunes", "Horarios Lunes: " + horariosLunes.toString());

        ArrayList<String> horariosMartes = horariosViewModel.getHorariosMartes();
        Collections.sort(horariosMartes);
        Log.d("HorariosMartes", "Horarios Martes: " + horariosMartes.toString());

        ArrayList<String> horariosMiercoles = horariosViewModel.getHorariosMiercoles();
        Collections.sort(horariosMiercoles);
        Log.d("HorariosMiercoles", "Horarios Miércoles: " + horariosMiercoles.toString());

        ArrayList<String> horariosJueves = horariosViewModel.getHorariosJueves();
        Collections.sort(horariosJueves);
        Log.d("HorariosJueves", "Horarios Jueves: " + horariosJueves.toString());

        ArrayList<String> horariosViernes = horariosViewModel.getHorariosViernes();
        Collections.sort(horariosViernes);
        Log.d("HorariosViernes", "Horarios Viernes: " + horariosViernes.toString());

        ArrayList<String> horariosSabado = horariosViewModel.getHorariosSabado();
        Collections.sort(horariosSabado);
        Log.d("HorariosSabado", "Horarios Sábado: " + horariosSabado.toString());

        ArrayList<String> horariosDomingo = horariosViewModel.getHorariosDomingo();
        Collections.sort(horariosDomingo);
        Log.d("HorariosDomingo", "Horarios Domingo: " + horariosDomingo.toString());

        String usuarioCancha = obtenerUsuarioCancha();

        // Repite estos pasos para los demás días según sea necesario
        guardarHorariosEnFirestore(usuarioCancha, "Lunes", horariosViewModel.getHorariosLunes());
        guardarHorariosEnFirestore(usuarioCancha, "Martes", horariosViewModel.getHorariosMartes());
        guardarHorariosEnFirestore(usuarioCancha, "Miércoles", horariosViewModel.getHorariosMiercoles());
        guardarHorariosEnFirestore(usuarioCancha, "Jueves", horariosViewModel.getHorariosJueves());
        guardarHorariosEnFirestore(usuarioCancha, "Viernes", horariosViewModel.getHorariosViernes());
        guardarHorariosEnFirestore(usuarioCancha, "Sábado", horariosViewModel.getHorariosSabado());
        guardarHorariosEnFirestore(usuarioCancha, "Domingo", horariosViewModel.getHorariosDomingo());
    }

    // Método para guardar horarios en Firestore
    // Método para guardar horarios en Firestore
    private void guardarHorariosEnFirestore(String nombreCancha, String dia, ArrayList<String> horarios) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtener la referencia a la colección de horarios del usuario
        DocumentReference horariosDocument = db.collection("Administradores")
                .document(nombreCancha)
                .collection("Horarios")
                .document(dia);

        // Crear un mapa para los campos de horarios
        Map<String, Object> horariosMap = new HashMap<>();

        // Iterar sobre los horarios y agregar campos individuales
        for (String horario : horarios) {
            horariosMap.put(horario, "Disponible");
        }

        // Actualizar o crear el documento para el día con los nuevos horarios
        ((DocumentReference) horariosDocument)
                .set(horariosMap, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Éxito al guardar los horarios en Firestore
                        Toast.makeText(PruebaSuperAdmin_Activity.this, "-----Horarios guardados en Firestore-----", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al guardar los horarios en Firestore
                        Toast.makeText(PruebaSuperAdmin_Activity.this, "Error al guardar horarios de " + dia + " en Firestore", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String obtenerUsuarioCancha() {
        SharedPreferences preferences = getSharedPreferences("USUARIO_CANCHAS", MODE_PRIVATE);
        return preferences.getString("nombrecancha", null);
    }
}