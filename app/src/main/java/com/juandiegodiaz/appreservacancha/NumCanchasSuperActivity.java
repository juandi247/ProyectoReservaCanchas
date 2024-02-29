package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumCanchasSuperActivity extends AppCompatActivity {

    private LinearLayout containerLayout;
    private NumberPicker numberPicker;
    private List<Spinner> categorySpinners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_canchas_super);

        containerLayout = findViewById(R.id.containerLayout);
        numberPicker = findViewById(R.id.numberPicker);
        categorySpinners = new ArrayList<>();

        // Configura los valores mínimo, máximo e inicial del NumberPicker
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setValue(1);
        String nombreCancha = obtenerUsuarioCancha();
        agregarSubcoleccionCancha(nombreCancha);

        Button btnGuardarNumCanchas = findViewById(R.id.btn_GuardarNumCanchas);

        // Configura el listener para el botón de guardar
        btnGuardarNumCanchas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Guarda los datos en Firestore al hacer clic en el botón
                Intent intent = new Intent(NumCanchasSuperActivity.this, PruebaSuperAdmin_Activity.class);
                startActivity(intent);
                guardarDatosCanchaEnFirestore(nombreCancha);
                finish();
            }
        });

        // Setea el listener para cambios en el valor del NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // Actualiza la interfaz de usuario basándote en el número seleccionado de canchas
                updateCanchasViews(newVal);
            }
        });

        // Inicializa la interfaz de usuario basándote en el valor predeterminado
        updateCanchasViews(numberPicker.getValue());
    }

    private void updateCanchasViews(int numCanchas) {
        // Limpia las vistas existentes
        containerLayout.removeAllViews();
        categorySpinners.clear();

        // Agrega nuevos EditText y Spinner basándote en el número seleccionado de canchas
        for (int i = 1; i <= numCanchas; i++) {
            TextView textView = new TextView(this);
            textView.setText("Cancha " + i + " Nombre:");

            // Agrega TextView al contenedor
            containerLayout.addView(textView);

            // Agrega EditText al contenedor
            EditText editText = new EditText(this);

            // Configura los parámetros de diseño
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Agrega EditText al contenedor con los parámetros de diseño
            containerLayout.addView(editText, layoutParams);

            // Agrega un nuevo Spinner al contenedor para seleccionar categorías
            Spinner categorySpinner = new Spinner(this);

            // Configura los márgenes para agregar espacio debajo del Spinner
            LinearLayout.LayoutParams spinnerLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            spinnerLayoutParams.setMargins(0, 0, 0, 100);  // Ajusta el valor según sea necesario

            // Agrega el nuevo Spinner al contenedor con los márgenes
            containerLayout.addView(categorySpinner, spinnerLayoutParams);
            categorySpinners.add(categorySpinner);

            // Configura el adapter para el Spinner con las categorías
            setupCategorySpinner(categorySpinner);
        }
    }

    private void setupCategorySpinner(Spinner spinner) {
        // Lista de categorías
        List<String> categories = new ArrayList<>();
        categories.add("5vs5");
        categories.add("6vs6");
        categories.add("8vs8");
        categories.add("11vs11");

        // Configura el adapter para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplica el adapter al Spinner
        spinner.setAdapter(adapter);
    }

    private void guardarDatosCanchaEnFirestore(String nombreCancha) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Itera sobre los Spinners y EditText para crear subcolecciones
        for (int i = 0; i < categorySpinners.size(); i++) {
            Spinner spinner = categorySpinners.get(i);
            View childView = containerLayout.getChildAt(i * 3 + 1);

            if (childView instanceof EditText) {
                EditText editText = (EditText) childView;
                String nombreCanchaIndividual = editText.getText().toString();
                String categoriaCancha = spinner.getSelectedItem().toString();

                // Crea una nueva subcolección para cada EditText
                CollectionReference subcanchaCollection = db.collection("Canchas")
                        .document(nombreCancha)
                        .collection(nombreCanchaIndividual);

                // Crea un documento con nombre "TipoCancha" y agrega los datos
                DocumentReference tipoCanchaDocument = subcanchaCollection.document("TipoCancha");

                // Crea un mapa para los datos de la subcolección
                Map<String, Object> datosSubcancha = new HashMap<>();
                datosSubcancha.put("Categoria", categoriaCancha);

                // Guarda los datos en el documento "TipoCancha"
                tipoCanchaDocument.set(datosSubcancha)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Éxito al agregar los datos en la subcolección
                                Toast.makeText(NumCanchasSuperActivity.this, "Datos guardados en Firestore", Toast.LENGTH_SHORT).show();
                                guardarNombresSubcanchas(nombreCancha);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error al agregar los datos en la subcolección
                                Toast.makeText(NumCanchasSuperActivity.this, "Error al guardar datos en Firestore", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }




    private void agregarSubcoleccionCancha(String nombreCancha) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Obtén la referencia a la colección "Canchas"
        DocumentReference canchasDocument = db.collection("Canchas").document(nombreCancha);

        // Crear un mapa vacío, ya que no parece haber datos específicos que desees almacenar aquí
        Map<String, Object> subcoleccionMap = new HashMap<>();

        // Agregar la subcolección con el nombre de la cancha
        canchasDocument.set(subcoleccionMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Éxito al agregar la subcolección en "Canchas"
                        Log.d("coleccion CANCHA", "Subcolección agregada en Canchas: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al agregar la subcolección en "Canchas"
                        Log.d("coleccion CANCHA", "Error al agregadar en Canchas: ");
                    }
                });
    }

    private String obtenerUsuarioCancha() {
        SharedPreferences preferences = getSharedPreferences("USUARIO_CANCHAS", MODE_PRIVATE);
        return preferences.getString("nombrecancha", null);
    }




    private void guardarNombresSubcanchas(String nombreCancha) {
        SharedPreferences preferences = getSharedPreferences("SUBCANCHAS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        List<String> nombresSubcanchas = new ArrayList<>();

        // Itera sobre los Spinners y EditText para obtener los nombres y almacenarlos en el array
        for (int i = 0; i < categorySpinners.size(); i++) {
            View childView = containerLayout.getChildAt(i * 3 + 1);

            if (childView instanceof EditText) {
                EditText editText = (EditText) childView;
                String nombreSubcancha = editText.getText().toString();

                // Agrega el nombre de la subcancha al array
                nombresSubcanchas.add(nombreSubcancha);
            }
        }

        // Convierte el array a una cadena JSON y almacénalo en SharedPreferences
        String arrayString = new Gson().toJson(nombresSubcanchas);
        editor.putString(nombreCancha, arrayString);

        // Aplica los cambios
        editor.apply();
    }

}

