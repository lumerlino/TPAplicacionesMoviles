package com.example.trabajopractico;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "libro")
public class Libro {

    @DatabaseField(id=true)
    private Integer id;

    @DatabaseField
    private String nombre;

    public Libro() {
    }

    public Libro(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
