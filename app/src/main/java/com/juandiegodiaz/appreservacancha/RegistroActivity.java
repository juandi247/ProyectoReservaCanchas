package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button btnVolver = findViewById(R.id.btn_VolverInicioSesion);

        Button btnRegistro = findViewById(R.id.btn_terminarRegistro);
        EditText usuarioEditText = findViewById(R.id.et_usuarioRegistro);
        EditText contrasenaET = findViewById(R.id.et_contrasenaRegistro);
        EditText nombreEditText = findViewById(R.id.et_nombre);
        EditText apellidoEditText = findViewById(R.id.et_apellido);









        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = usuarioEditText.getText().toString();
                String contraseña = contrasenaET.getText().toString();
                String nombre = nombreEditText.getText().toString();
                String apellido = apellidoEditText.getText().toString();
                if (usuario.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ) {
                    // Al menos uno de los campos está en blanco, muestra una alerta
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Ambos campos tienen valores, procede con el inicio de sesión
                    // Agrega aquí el código para iniciar sesión
                }
            }
        });
    }
}