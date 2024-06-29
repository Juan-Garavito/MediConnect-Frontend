package com.example.mediconnect.Modelos;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Ciudadano {
    private String numerodocumento;
    private String nombres;
    private String apellidos;
    private String fechanacimiento;
    private Integer idgenero;
    private Integer idtiposangre;
    private String direccion;
    private String numerocelular;
    private String correo;
    private String password;
    private String urlimagenperfil;


    public Ciudadano() {

    }

    public Ciudadano(String numerodocumento, String nombres, String apellidos, String fechanacimiento, Integer idgenero, Integer idtiposangre, String direccion, String numerocelular, String correo, String password, String urlimagenperfil) {
        this.numerodocumento = numerodocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechanacimiento = fechanacimiento;
        this.idgenero = idgenero;
        this.idtiposangre = idtiposangre;
        this.direccion = direccion;
        this.numerocelular = numerocelular;
        this.correo = correo;
        this.password = password;
        this.urlimagenperfil = urlimagenperfil;
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

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public Integer getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(Integer idgenero) {
        this.idgenero = idgenero;
    }

    public Integer getIdtiposangre() {
        return idtiposangre;
    }

    public void setIdtiposangre(Integer idtiposangre) {
        this.idtiposangre = idtiposangre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumerocelular() {
        return numerocelular;
    }

    public void setNumerocelular(String numerocelular) {
        this.numerocelular = numerocelular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlimagenperfil() {
        return urlimagenperfil;
    }

    public void setUrlimagenperfil(String urlimagenperfil) {
        this.urlimagenperfil = urlimagenperfil;
    }

    @Override
    public String toString() {
        return "Ciudadano{" +
                "numerodocumento='" + numerodocumento + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechanacimiento=" + fechanacimiento +
                ", idgenero=" + idgenero +
                ", idtiposangre=" + idtiposangre +
                ", direccion='" + direccion + '\'' +
                ", numerocelular='" + numerocelular + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", urlimagenperfil='" + urlimagenperfil + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudadano ciudadano = (Ciudadano) o;
        return Objects.equals(numerodocumento, ciudadano.numerodocumento) && Objects.equals(nombres, ciudadano.nombres) && Objects.equals(apellidos, ciudadano.apellidos) && Objects.equals(fechanacimiento, ciudadano.fechanacimiento) && Objects.equals(idgenero, ciudadano.idgenero) && Objects.equals(idtiposangre, ciudadano.idtiposangre) && Objects.equals(direccion, ciudadano.direccion) && Objects.equals(numerocelular, ciudadano.numerocelular) && Objects.equals(correo, ciudadano.correo) && Objects.equals(password, ciudadano.password) && Objects.equals(urlimagenperfil, ciudadano.urlimagenperfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerodocumento, nombres, apellidos, fechanacimiento, idgenero, idtiposangre, direccion, numerocelular, correo, password, urlimagenperfil);
    }
}
