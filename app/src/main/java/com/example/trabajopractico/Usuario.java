package com.example.trabajopractico;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuario")
public class Usuario {

    @DatabaseField(id=true)
    private Integer id;

    @DatabaseField
    private String usuario;

    @DatabaseField
    private String password;

    @DatabaseField
    private String nombreCompleto;

    @DatabaseField
    private String fechaNacimiento;

    @DatabaseField
    private String email;

    public Usuario() {}

    public Usuario(Integer id, String usuario, String password, String nombreCompleto, String fechaNacimiento, String email) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
