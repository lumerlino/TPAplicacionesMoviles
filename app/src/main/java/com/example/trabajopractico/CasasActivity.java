package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CasasActivity extends AppCompatActivity {

    RecyclerView rvCasas;
    CasaAdapter adapter;
    Toolbar mi_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casas);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Casas");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);
        //getSupportActionBar().setTitle("Casas");
        setUpAdapter();
    }

    private void setUpAdapter() {
        rvCasas = findViewById(R.id.rvCasas);

        adapter = new CasaAdapter(getCasas(), new CasaAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(Casa casa) {
                Toast.makeText(CasasActivity.this, casa.getNombre(), Toast.LENGTH_SHORT).show();
                //redirigir a detalle de casa
            }
        });
        rvCasas.setAdapter(adapter);
    }

    private List<Casa> getCasas() {
        List<Casa> casas = new ArrayList<Casa>();
        casas.add(new Casa(1,"casa1"));
        casas.add(new Casa(2,"casa2"));
        casas.add(new Casa(3,"casa3"));
        casas.add(new Casa(4,"casa4"));
        casas.add(new Casa(5,"casa5"));
        casas.add(new Casa(6,"casa6"));
        casas.add(new Casa(7,"casa7"));
        return casas;
    }


}