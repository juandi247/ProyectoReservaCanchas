package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Verificar si el usuario ya ha iniciado sesión
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null && currentUser.isEmailVerified()) {
                    // Usuario autenticado y correo electrónico verificado
                    Intent intent = new Intent(SplashActivity.this, InicioActivity.class);
                    startActivity(intent);
                } else {
                    // Usuario no autenticado o correo electrónico no verificado
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 3000);
    }
}
