package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PersonajesActivity extends AppCompatActivity {

    Button btnAtras;
    Toolbar mi_toolbar;
    RecyclerView rvPersonajes;
    PersonajeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Personajes");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        btnAtras = findViewById(R.id.btnAtras);
        setSupportActionBar(mi_toolbar);
        setUpAdapter();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home_activity = new Intent(PersonajesActivity.this, MainActivity.class);
                startActivity(home_activity);
                finish();
            }
        });

    }

    private void setUpAdapter() {
        rvPersonajes = findViewById(R.id.rvPersonajes);
        adapter = new PersonajeAdapter(getPersonajes(), new PersonajeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Personaje personaje) {
                //Toast.makeText(PersonajesActivity.this, personaje.getNombreCompleto(), Toast.LENGTH_SHORT).show();
                Intent personaje_activity = new Intent(PersonajesActivity.this, PersonajeActivity.class);
                personaje_activity.putExtra("name",personaje.getNombreCompleto());
                startActivity(personaje_activity);
            }
        });
        rvPersonajes.setAdapter(adapter);
    }

    private List<Personaje> getPersonajes() {
        return new ArrayList<Personaje>(){{
            add(new Personaje(1,"Daenerys Targaryen"));
            add(new Personaje(2,"Jon Snow"));
            add(new Personaje(3,"Sansa Stark"));
            add(new Personaje(4,"Arya Stark"));
            add(new Personaje(5,"Cersei Lannister"));
            add(new Personaje(6,"Tyron Lannister"));
            add(new Personaje(7,"Khal Drogo"));
        }};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_personajes){
            Intent personajes_activity = new Intent(PersonajesActivity.this, PersonajesActivity.class);
            startActivity(personajes_activity);

        }

        if (item.getItemId() == R.id.item_casas){
            Intent casas_activity = new Intent(PersonajesActivity.this, CasasActivity.class);
            startActivity(casas_activity);

        }

        if (item.getItemId() == R.id.item_libros){
            Intent libros_activity = new Intent(PersonajesActivity.this, LibrosActivity.class);
            startActivity(libros_activity);

        }
        return super.onOptionsItemSelected(item);
    }
}