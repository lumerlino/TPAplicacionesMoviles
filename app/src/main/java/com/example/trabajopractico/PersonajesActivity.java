package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PersonajesActivity extends AppCompatActivity {

    Button btnDaenerys, btnJon, btnArya, btnTyron, btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);

        btnDaenerys = findViewById(R.id.btnDaenerys);
        btnJon = findViewById(R.id.btnJon);
        btnArya = findViewById(R.id.btnArya);
        btnTyron = findViewById(R.id.btnTyron);
        btnAtras = findViewById(R.id.btnAtras);

        btnDaenerys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personaje_activity = new Intent(PersonajesActivity.this, PersonajeActivity.class);
                startActivity(personaje_activity);
                finish();
            }
        });
        btnJon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personaje_activity = new Intent(PersonajesActivity.this, PersonajeActivity.class);
                startActivity(personaje_activity);
                finish();
            }
        });
        btnArya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personaje_activity = new Intent(PersonajesActivity.this, PersonajeActivity.class);
                startActivity(personaje_activity);
                finish();
            }
        });
        btnTyron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personaje_activity = new Intent(PersonajesActivity.this, PersonajeActivity.class);
                startActivity(personaje_activity);
                finish();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_activity = new Intent(PersonajesActivity.this, MainActivity.class);
                startActivity(main_activity);
                finish();
            }
        });
    }
}