package com.example.mediconnect.Modelos;

import java.io.Serializable;

public class CiudadanoDTO implements Serializable {

    private String numerodocumento;
    private String nombres;
    private String apellidos;
    private String url;
    private Integer idRol;

    public CiudadanoDTO() {
    }

    public CiudadanoDTO(String numerodocumento, String nombres, String apellidos, String url, Integer idRol) {
        this.numerodocumento = numerodocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.url = url;
        this.idRol = idRol;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
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
                "numerodocumento='" + numerodocumento + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", url='" + url + '\'' +
                ", idRol=" + idRol +
                '}';
    }
}
