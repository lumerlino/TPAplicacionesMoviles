package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constantes.SP_CREDENCIALES, MODE_PRIVATE);
        /*String usuarioGuardado = prefs.getString(Constantes.USUARIO, null);
        String passGuardada = prefs.getString(Constantes.PASSWORD, null);*/

        String usuarioGuardado = null;
        String passGuardada = null;

        if(usuarioGuardado != null && passGuardada != null){
            iniciarMainActivity(usuarioGuardado);
        }

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TODO","Se apretó el botón Iniciar Sesión");

                String usuario = etUsuario.getText().toString();
                String password = etPassword.getText().toString();

                List<Usuario> usuarios = getUsuarios();

                if(usuario.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Por favor, completar datos", Toast.LENGTH_SHORT).show();
                }else if (!encontrarUsuario(usuarios, usuario)){
                    Toast.makeText(LoginActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                }else if (!passwordVerificada(usuarios, usuario, password)){
                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }else{
                    login(usuario, password);
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

    private List<Usuario> getUsuarios(){
        try{
            return UsuarioManager.getInstancia(LoginActivity.this).getUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean encontrarUsuario(List<Usuario> usuarios, String usuario){
        boolean encontrado = false;
        for(Usuario u : usuarios){
            String usuarioLista = u.getUsuario();
            if (usuarioLista.equals(usuario)){
                encontrado = true;
            }
        }
        return encontrado;
    }

    private boolean passwordVerificada(List<Usuario> usuarios, String usuario, String password){
        boolean verificada = false;
        for(Usuario u : usuarios){
            String usuarioLista = u.getUsuario();
            String passwordLista = u.getPassword();
            if (usuarioLista.equals(usuario)){
                if(passwordLista.equals(password)){
                    verificada = true;
                }
            }
        }
        return verificada;
    }

    private void login(String usuario, String password){
        if(cbRecordarUsuario.isChecked()){
            SharedPreferences prefs = getApplicationContext().getSharedPreferences(Constantes.SP_CREDENCIALES, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constantes.USUARIO, usuario);
            editor.putString(Constantes.PASSWORD, password);
            editor.apply();
        }
        iniciarMainActivity(usuario);
    }

    private void iniciarMainActivity(String usuarioGuardado) {
        Intent main_activity = new Intent(LoginActivity.this, MainActivity.class);
        main_activity.putExtra(Constantes.USUARIO,usuarioGuardado);
        startActivity(main_activity);
        finish();
    }
}