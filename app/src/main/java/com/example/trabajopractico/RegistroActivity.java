package com.example.trabajopractico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    Toolbar mi_toolbar;
    Button btnCancelar, btnRegistroUsuario;
    EditText etNombreCompleto, etFechaNacimiento, etEmail, etUsuario, etContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mi_toolbar = findViewById(R.id.mi_toolbar);
        mi_toolbar.setTitle("Registro");
        mi_toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mi_toolbar);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnRegistroUsuario = findViewById(R.id.btnRegistroUsuario);

        etNombreCompleto = findViewById(R.id.etNombreCompleto);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etEmail = findViewById(R.id.etEmail);
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_activity = new Intent(RegistroActivity.this, LoginActivity.class);
                startActivity(login_activity);
                finish();
            }
        });

        btnRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreCompleto = etNombreCompleto.getText().toString();
                String fechaNacimiento = etFechaNacimiento.getText().toString();
                String email = etEmail.getText().toString();
                String usuario = etUsuario.getText().toString();
                String contrasena = etContrasena.getText().toString();

                List<Usuario> usuarios = getUsuarios();

                if (nombreCompleto.isEmpty() || fechaNacimiento.isEmpty() || email.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()){
                    Toast.makeText(RegistroActivity.this, "Por favor, complete los datos.", Toast.LENGTH_SHORT).show();
                }else if (existeUsuario(usuarios, usuario)){
                    Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                }else {
                    agregarUsuario();
                    Intent login_activity = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(login_activity);
                    finish();
                }
            }
        });
    }

    private List<Usuario> getUsuarios(){
        try{
            return UsuarioManager.getInstancia(RegistroActivity.this).getUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existeUsuario(List<Usuario> usuarios, String usuario){
        boolean existe = false;
        for(Usuario u : usuarios){
            String usuarioLista = u.getUsuario();
            if (usuarioLista.equals(usuario)){
                existe = true;
            }
        }
        return existe;
    }

    public void agregarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setNombreCompleto(etNombreCompleto.getText().toString());
        usuario.setFechaNacimiento(etFechaNacimiento.getText().toString());
        usuario.setEmail(etEmail.getText().toString());
        usuario.setUsuario(etUsuario.getText().toString());
        usuario.setPassword(etContrasena.getText().toString());
        try{
            UsuarioManager.getInstancia(RegistroActivity.this).agregarUsuario(usuario);
            Toast.makeText(RegistroActivity.this, "Usuario Agregado!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}