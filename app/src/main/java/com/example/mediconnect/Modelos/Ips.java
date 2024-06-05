package com.example.mediconnect.Modelos;

public class Ips {

    private Integer id;
    private String descripcion;
    private String url;
    private String direccion;

    public Ips() {
    }

    public Ips(Integer id, String descripcion, String url, String direccion) {
        this.id = id;
        this.descripcion = descripcion;
        this.url = url;
        this.direccion = direccion;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Ips{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", url='" + url + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
