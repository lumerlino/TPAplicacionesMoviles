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

public class CasasFavoritosActivity extends AppCompatActivity {
    Toolbar mi_toolbar;
    RecyclerView rvCasasFavoritos;
    CasaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casas_favoritos);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Casas Favoritos");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);
        setUpAdapter();
    }

    private void setUpAdapter() {
        rvCasasFavoritos = findViewById(R.id.rvCasasFavoritos);
        adapter = new CasaAdapter(getCasasFavoritos(), new CasaAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Casa casa) {
                Intent casas_favoritos_activity = new Intent(CasasFavoritosActivity.this, PersonajeActivity.class);
                casas_favoritos_activity.putExtra("name",casa.getNombre());
                startActivity(casas_favoritos_activity);
            }
        });
        rvCasasFavoritos.setAdapter(adapter);
    }

    private List<Casa> getCasasFavoritos() {
        try{
            return CasaManager.getInstancia(CasasFavoritosActivity.this).getCasasFavoritos();
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
            Intent casa_activity = new Intent(CasasFavoritosActivity.this, CasaActivity.class);
            startActivity(casa_activity);
        }
        return super.onOptionsItemSelected(item);
    }
}