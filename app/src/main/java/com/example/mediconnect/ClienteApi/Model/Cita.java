package com.example.mediconnect.ClienteApi.Model;

import java.time.LocalDate;

public class Cita {
    private String idCita;
    private String fechaCita;
    private String franjaHoraria;
    private String especialidad;
    private String modalidadCita;
    private String idPaciente;
    private String medico;
    private String ips;
    private String idChat;


    public Cita() {
    }

    public Cita(String idCita, String fechaCita, String franjaHoraria, String especialidad, String modalidadCita, String idPaciente, String medico, String ips, String idChat) {
        this.idCita = idCita;
        this.fechaCita = fechaCita;
        this.franjaHoraria = franjaHoraria;
        this.especialidad = especialidad;
        this.modalidadCita = modalidadCita;
        this.idPaciente = idPaciente;
        this.medico = medico;
        this.ips = ips;
        this.idChat = idChat;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getFranjaHoraria() {
        return franjaHoraria;
    }

    public void setFranjaHoraria(String franjaHoraria) {
        this.franjaHoraria = franjaHoraria;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getModalidadCita() {
        return modalidadCita;
    }

    public void setModalidadCita(String modalidadCita) {
        this.modalidadCita = modalidadCita;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita='" + idCita + '\'' +
                ", fechaCita=" + fechaCita +
                ", franjaHoraria='" + franjaHoraria + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", modalidadCita='" + modalidadCita + '\'' +
                ", idPaciente='" + idPaciente + '\'' +
                ", medico='" + medico + '\'' +
                ", ips='" + ips + '\'' +
                ", idChat='" + idChat + '\'' +
                '}';
    }
}
