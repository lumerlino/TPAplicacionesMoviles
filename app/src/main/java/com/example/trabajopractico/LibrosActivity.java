package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LibrosActivity extends AppCompatActivity {

    RecyclerView rvLibros;
    LibroAdapter adapter;
    Toolbar mi_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Libros");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);
        //getSupportActionBar().setTitle("Libros");

        setUpAdapter();
    }

    private void setUpAdapter() {
        rvLibros = findViewById(R.id.rvLibros);

        adapter = new LibroAdapter(getLibros(), new LibroAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Libro libro) {
                //Toast.makeText(LibrosActivity.this, libro.getNombre(), Toast.LENGTH_SHORT).show();
                Intent libro_activity = new Intent(LibrosActivity.this, LibroActivity.class);
                libro_activity.putExtra("name",libro.getNombre());
                libro_activity.putExtra("id",libro.getId().toString());
                startActivity(libro_activity);
            }
        });
        rvLibros.setAdapter(adapter);
    }

    private List<Libro> getLibros() {
        List<Libro> libros = new ArrayList<Libro>();
        libros.add(new Libro(1,"A Game of Thrones"));
        libros.add(new Libro(2,"A Clash of Kings"));
        libros.add(new Libro(3,"A Storm of Swords"));
        libros.add(new Libro(4,"The Hedge Knight"));
        libros.add(new Libro(5,"A Feast for Crows"));
        libros.add(new Libro(6,"The Sworn Sword"));
        libros.add(new Libro(7,"The Mystery Knight"));
        return libros;
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