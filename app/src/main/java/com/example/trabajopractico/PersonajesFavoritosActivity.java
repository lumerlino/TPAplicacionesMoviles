package com.example.trabajopractico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PersonajesFavoritosActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    RecyclerView rvPersonajesFavoritos;
    PersonajeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajes_favoritos);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Personajes Favoritos");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);
        setUpAdapter();
    }

    private void setUpAdapter() {
        rvPersonajesFavoritos = findViewById(R.id.rvPersonajesFavoritos);
        adapter = new PersonajeAdapter(getPersonajesFavoritos(), new PersonajeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Personaje personaje) {
                Intent personajes_favoritos_activity = new Intent(PersonajesFavoritosActivity.this, PersonajeActivity.class);
                personajes_favoritos_activity.putExtra("name",personaje.getNombreCompleto());
                personajes_favoritos_activity.putExtra("id",personaje.getId().toString());
                startActivity(personajes_favoritos_activity);
            }
        });
        rvPersonajesFavoritos.setAdapter(adapter);
    }

    private List<Personaje> getPersonajesFavoritos() {
        try{
            return PersonajeManager.getInstancia(PersonajesFavoritosActivity.this).getPersonajesFavoritos();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_volver){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpAdapter();
    }
}
