package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PersonajeActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
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
           PersonajeManager manager = PersonajeManager.getInstancia(PersonajeActivity.this);
           Personaje personaje = null;
           Bundle bundle = getIntent().getExtras();
           if (bundle!=null) {
               personaje = new Personaje( Integer.parseInt(bundle.getString("id")) ,bundle.getString("name")) ;
           }
           try{
               if(personaje!=null){
                   Personaje personajeGuardado = manager.getPersonajeFavorito(personaje.getId());
                   if(personajeGuardado == null){
                       manager.agregarPersonajeFavorito(personaje);
                       Toast.makeText(PersonajeActivity.this, "Personaje Agregado a Favoritos", Toast.LENGTH_SHORT).show();
                   }else{
                       manager.deletePersonajeFavorito(personaje.getId());
                       Toast.makeText(PersonajeActivity.this, "Personaje Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                   }

               }
           }catch (Exception e){
               e.printStackTrace();
           }
        }
        return super.onOptionsItemSelected(item);
    }
}