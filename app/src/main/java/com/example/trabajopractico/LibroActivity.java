package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class LibroActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    TextView tvNombre;

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

        tvNombre = findViewById(R.id.tvNombre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_favorito, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_volver){
            finish();
        }
        if (item.getItemId() == R.id.item_favorito){
            LibroManager manager = LibroManager.getInstancia(LibroActivity.this);
            Libro libro = null;
            Bundle bundle = getIntent().getExtras();
            if (bundle!=null) {
                libro = new Libro( Integer.parseInt(bundle.getString("id")) ,bundle.getString("name")) ;
            }
            try{
                if(libro!=null){
                    Libro libroGuardado = manager.getLibroFavorito(libro.getId());
                    if(libroGuardado == null){
                        manager.agregarLibroFavorito(libro);
                        Toast.makeText(LibroActivity.this, "Libro Agregado a Favoritos", Toast.LENGTH_SHORT).show();
                    }else{
                        manager.deleteLibroFavorito(libro.getId());
                        Toast.makeText(LibroActivity.this, "Libro Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}