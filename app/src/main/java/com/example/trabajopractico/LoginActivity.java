package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUsuario, etPassword;
    Button btnIniciarSesion, btnRegistrarUsuario;
    CheckBox cbRecordarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        cbRecordarUsuario = findViewById(R.id.cbRecordarUsuario);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Iniciar Sesión");

                String usuario = etUsuario.getText().toString();
                String password = etPassword.getText().toString();

                if(usuario.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Por favor, completar datos", Toast.LENGTH_SHORT).show();
                }else{
                    Intent main_activity = new Intent(LoginActivity.this, MainActivity.class);
                    main_activity.putExtra("usuario",usuario);
                    startActivity(main_activity);
                    finish();
                }
            }
        });

        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Registrar Usuario");

                Intent registro_activity = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(registro_activity);
            }
        });


    }
}