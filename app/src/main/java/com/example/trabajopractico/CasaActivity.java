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
import android.widget.TextView;
import android.widget.Toast;

public class CasaActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    TextView tvNombre;

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
            CasaManager manager = CasaManager.getInstancia(CasaActivity.this);
            Casa casa = null;
            Bundle bundle = getIntent().getExtras();
            if (bundle!=null) {
                casa = new Casa( Integer.parseInt(bundle.getString("id")) ,bundle.getString("name")) ;
            }
            try{
                if(casa!=null){
                    Casa casaGuardado = manager.getCasaFavorito(casa.getId());
                    if(casaGuardado == null){
                        manager.agregarCasaFavorito(casa);
                        ExtensionesKt.mensajeCorto(CasaActivity.this, "Casa Agregada a Favoritos");
                    }else{
                        manager.deleteCasaFavorito(casa.getId());
                        Toast.makeText(CasaActivity.this, "Casa Eliminada de Favoritos", Toast.LENGTH_SHORT).show();
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}