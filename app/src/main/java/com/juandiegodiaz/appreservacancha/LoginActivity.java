package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;




public class LoginActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Button btnRegistro = findViewById(R.id.btn_registro);
        Button btnInicioSesion = findViewById(R.id.btn_inicioSesion);

        Button btnSuper = findViewById(R.id.btn_superadmin);

        EditText EmailEditText = findViewById(R.id.et_email);
        ImageButton btnGoToAdmin = findViewById(R.id.btn_GoToInicioSesionAdmins);
        EditText contraseñaET = findViewById(R.id.et_contrasenaInicio);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        btnSuper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SuperAdminActivity.class);
                startActivity(intent);
            }
        });

        btnGoToAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailEditText.getText().toString();
                String contraseña = contraseñaET.getText().toString();

                if (email.isEmpty() || contraseña.isEmpty()) {
                    // Al menos uno de los campos está en blanco, muestra una alerta
                    Toast.makeText(LoginActivity.this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Iniciar sesión con Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, contraseña)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Verificar si el correo electrónico está verificado
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null && user.isEmailVerified()) {
                                            // Inicio de sesión exitoso y correo electrónico verificado
                                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                                            // Resto de tu código...
                                            // Aquí puedes realizar otras acciones después de un inicio de sesión exitoso.

                                            // Ejemplo: Guardar el email en SharedPreferences
                                            SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("email", email); // Donde "email" es la clave para recuperar el valor
                                            editor.apply();

                                            Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                                            startActivity(intent);
                                        } else {
                                            // El correo electrónico no está verificado
                                            Toast.makeText(LoginActivity.this, "Por favor, verifica tu dirección de correo electrónico", Toast.LENGTH_LONG).show();

                                            // Reenviar el correo de verificación
                                            if (user != null) {
                                                user.sendEmailVerification();
                                                Toast.makeText(LoginActivity.this, "Se ha enviado un nuevo correo de verificación", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else {
                                        // Error en el inicio de sesión
                                        Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }


}





/* Para actualizar los horarios!!!

  CollectionReference canchasCollection = db.collection("Canchas");
    DocumentReference cancha1Doc = canchasCollection.document("Cancha Picadelly");
    DocumentReference cancha2Doc = canchasCollection.document("Cancha La Futbolera");

                                    for (int i = 15; i <= 17; i++) {
                                            // Crea una fecha en formato "yyyy-MM-dd"
                                            String fecha = "2023-10-" + i;

                                            // Crea un nuevo documento con la fecha como ID en la colección "Fechas"
                                            DocumentReference fechaDoc = cancha2Doc.collection("Fechas").document(fecha);

                                            // Agrega los horarios como campos en este documento
                                            Map<String, Object> horarios = new HashMap<>();
        horarios.put("7pm", "Disponible");
        horarios.put("8pm", "Disponible");
        horarios.put("9pm", "Disponible");

        // Actualiza los horarios en el documento de fecha
        fechaDoc.set(horarios, SetOptions.merge());
        }

 */