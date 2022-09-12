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
import android.widget.Toast;

public class CasaActivity extends AppCompatActivity {

    Toolbar mi_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casa);

        String toolbar_title = "nombre casa";

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            toolbar_title = bundle.getString("name");
        }

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
            Intent casas_activity = new Intent(CasaActivity.this, CasasActivity.class);
            startActivity(casas_activity);
        }
        if (item.getItemId() == R.id.item_favorito){
            Toast.makeText(this, "La Casa fue registrada correctamente en sus Favoritos", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}