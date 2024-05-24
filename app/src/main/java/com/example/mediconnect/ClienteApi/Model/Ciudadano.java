package com.example.mediconnect.ClienteApi.Model;

public class Ciudadano {

    private String id;
    private String nombres;
    private String apellidos;
    private String url;
    private Integer idRol;

    public Ciudadano() {
    }

    public Ciudadano(String id, String nombres, String apellidos, String url, Integer idRol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.url = url;
        this.idRol = idRol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "id='" + id + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", url='" + url + '\'' +
                ", idRol=" + idRol +
                '}';
    }
}
