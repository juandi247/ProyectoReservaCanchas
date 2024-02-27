package com.juandiegodiaz.appreservacancha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuperAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_admin);

       Button btnseleccionHorario=findViewById(R.id.btn_seleccionar_horarios);





     btnseleccionHorario.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SuperAdminActivity.this, PruebaSuperAdmin_Activity.class);
            startActivity(intent);
          }
    });
}


}