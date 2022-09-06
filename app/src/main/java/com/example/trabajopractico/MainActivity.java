package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnPersonajes, btnCerrarSesion, btnCasas, btnLibros;
    Toolbar mi_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saludarUsuario();

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Home");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);

        btnPersonajes = findViewById(R.id.btnPersonajes);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCasas = findViewById(R.id.btnCasas);
        btnLibros = findViewById(R.id.btnLibros);

        btnPersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Personajes");

                Intent personajes_activity = new Intent(MainActivity.this, PersonajesActivity.class);
                startActivity(personajes_activity);

            }
        });

        btnCasas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Casas");

                Intent casas_activity = new Intent(MainActivity.this, CasasActivity.class);
                startActivity(casas_activity);
            }
        });

        btnLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Libros");

                Intent libros_activity = new Intent(MainActivity.this, LibrosActivity.class);
                startActivity(libros_activity);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_personajes){
            Intent personajes_activity = new Intent(MainActivity.this, PersonajesActivity.class);
            startActivity(personajes_activity);
            finish();
        }

        if (item.getItemId() == R.id.item_casas){
            Intent casas_activity = new Intent(MainActivity.this, CasasActivity.class);
            startActivity(casas_activity);
            finish();
        }

        /*if (item.getItemId() == R.id.item_libros){
            Intent libros_activity = new Intent(MainActivity.this, LibrosActivity.class);
            startActivity(libros_activity);
            finish();
        }*/
        return super.onOptionsItemSelected(item);
    }
}