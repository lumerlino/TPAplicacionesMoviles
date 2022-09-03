package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnPersonajes, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saludarUsuario();

        btnPersonajes = findViewById(R.id.btnPersonajes);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnPersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Personajes");

                Intent personajes_activity = new Intent(MainActivity.this, PersonajesActivity.class);
                startActivity(personajes_activity);
                finish();
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Cerrar Sesion");

                Intent login_activity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login_activity);
                finish();
            }
        });
    }
    private void saludarUsuario() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            String usuario = bundle.getString("usuario");

            Toast.makeText(this, "Bienvenido/a "+usuario, Toast.LENGTH_SHORT).show();
        }
    }
}