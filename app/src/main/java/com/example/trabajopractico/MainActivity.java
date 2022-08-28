package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saludarUsuario();
    }
    private void saludarUsuario() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            String usuario = bundle.getString("usuario");

            Toast.makeText(this, "Bienvenido/a "+usuario, Toast.LENGTH_SHORT).show();
        }
    }
}