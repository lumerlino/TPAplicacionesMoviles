package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistroActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    Button btnCancelar, btnRegistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Registro");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnRegistroUsuario = findViewById(R.id.btnRegistroUsuario);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_activity = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(login_activity);
                finish();
            }
        });

        btnRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_activity = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(login_activity);
                finish();
            }
        });
    }
}