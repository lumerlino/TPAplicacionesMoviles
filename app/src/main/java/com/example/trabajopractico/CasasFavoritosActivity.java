package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.j256.ormlite.android.apptools.OpenHelperManager;

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
                Intent casas_favoritos_activity = new Intent(CasasFavoritosActivity.this, CasaActivity.class);
                casas_favoritos_activity.putExtra("name",casa.getNombre());
                casas_favoritos_activity.putExtra("id",casa.getId().toString());
                startActivity(casas_favoritos_activity);
            }
        });
        rvCasasFavoritos.setAdapter(adapter);
    }

    private List<Casa> getCasasFavoritos() {
        try{
            CasaManager ins = CasaManager.getInstancia(CasasFavoritosActivity.this);
            List<Casa> res = ins.getCasasFavoritos();
            return res;
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
    public  void onDestroy() {

        super.onDestroy();
        OpenHelperManager.releaseHelper();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpAdapter();
    }
}