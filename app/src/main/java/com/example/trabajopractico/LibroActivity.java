package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

public class LibroActivity extends AppCompatActivity {

    Toolbar mi_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro);

        String toolbar_title = "nombre libro";

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            toolbar_title = bundle.getString("name");
        }

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle(toolbar_title);
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);
    }
}