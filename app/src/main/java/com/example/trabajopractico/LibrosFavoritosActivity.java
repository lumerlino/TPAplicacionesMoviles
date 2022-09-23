package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class LibrosFavoritosActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    RecyclerView rvLibrosFavoritos;
    LibroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_favoritos);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Libros Favoritos");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);
        setUpAdapter();
    }

    private void setUpAdapter() {
        rvLibrosFavoritos = findViewById(R.id.rvLibrosFavoritos);
        adapter = new LibroAdapter(getLibrosFavoritos(), new LibroAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Libro libro) {
                Intent libros_favoritos_activity = new Intent(LibrosFavoritosActivity.this, LibroActivity.class);
                libros_favoritos_activity.putExtra("name",libro.getNombre());
                libros_favoritos_activity.putExtra("id",libro.getId().toString());
                startActivity(libros_favoritos_activity);
            }
        });
        rvLibrosFavoritos.setAdapter(adapter);
    }

    private List<Libro> getLibrosFavoritos() {
        try{
            return LibroManager.getInstancia(LibrosFavoritosActivity.this).getLibrosFavoritos();
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
}