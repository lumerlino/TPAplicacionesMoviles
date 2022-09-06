package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonajeActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    Button btnAtras;
    TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje);

        String toolbar_title = "nombre personaje";
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            toolbar_title = bundle.getString("name");
        }

        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText(toolbar_title);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle(toolbar_title);
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);

        btnAtras = findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personajes_activity = new Intent(PersonajeActivity.this, PersonajesActivity.class);
                startActivity(personajes_activity);
                finish();
            }
        });


    }
}