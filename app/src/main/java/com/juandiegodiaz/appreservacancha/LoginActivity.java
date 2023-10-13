package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnRegistro = findViewById(R.id.btn_registro);
Button btnInicioSesion= findViewById(R.id.btn_inicioSesion);
        EditText usuarioEditText = findViewById(R.id.et_usuario);
        EditText contraseñaET = findViewById(R.id.et_contrasenaInicio);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });


        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = usuarioEditText.getText().toString();
                String contraseña = contraseñaET.getText().toString();

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    // Al menos uno de los campos está en blanco, muestra una alerta
                    Toast.makeText(LoginActivity.this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Ambos campos tienen valores, procede con el inicio de sesión
                    // Agrega aquí el código para iniciar sesión
                }
            }
        });
    }
}
