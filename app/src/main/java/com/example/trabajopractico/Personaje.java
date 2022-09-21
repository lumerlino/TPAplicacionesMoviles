package com.example.trabajopractico;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "personaje")
public class Personaje {

    @DatabaseField(id=true)
    private Integer id;

    @DatabaseField
    private String nombreCompleto;

    public Personaje(){}

    public Personaje(Integer id, String nombreCompleto){
        this.id = id;
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}




