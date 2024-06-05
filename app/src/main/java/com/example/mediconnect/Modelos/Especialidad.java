package com.example.mediconnect.Modelos;

import java.util.List;

public class Especialidad {

    private Integer id;

    private String descripcion;


    public Especialidad() {
    }

    public Especialidad(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Especialidad{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
