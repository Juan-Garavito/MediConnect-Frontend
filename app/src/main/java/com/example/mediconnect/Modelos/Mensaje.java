package com.example.mediconnect.Modelos;

import java.util.Date;

public class Mensaje {

    private String idMensaje;
    private String contenido;
    private String fechaMensaje;
    private String idChat;
    private String idRemitente;
    private String idDestinatario;

    public Mensaje() {
    }

    public Mensaje(String idMensaje, String contenido, String fechaMensaje, String idChat, String idRemitente, String idDestinatario) {
        this.idMensaje = idMensaje;
        this.contenido = contenido;
        this.fechaMensaje = fechaMensaje;
        this.idChat = idChat;
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
    }

    public String getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getFechaMensaje() {
        return fechaMensaje;
    }

    public void setFechaMensaje(String fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    public String getIdDestinatario() {
        return idDestinatario;
    }

    public void setIdDestinatario(String idDestinatario) {
        this.idDestinatario = idDestinatario;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "idMensaje='" + idMensaje + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fechaMensaje='" + fechaMensaje + '\'' +
                ", idChat='" + idChat + '\'' +
                ", idRemitente='" + idRemitente + '\'' +
                ", idDestinatario='" + idDestinatario + '\'' +
                '}';
    }
}
