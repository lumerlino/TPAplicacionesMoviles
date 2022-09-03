package com.example.trabajopractico;

public class Personaje {
    private Integer id;
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




