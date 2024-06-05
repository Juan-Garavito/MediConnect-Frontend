package com.example.mediconnect.Modelos;


import java.util.Objects;

public class Cita {

    private String idCita;
    private String fechaCita;
    private Integer idFranjaHoraria;
    private Integer idEspecialidad;
    private Integer idModalidadCita;
    private String idPaciente;
    private String idMedico;
    private Integer idIps;


    public Cita() {
    }

    public Cita(String idCita, String fechaCita, Integer idFranjaHoraria, Integer idEspecialidad, Integer idModalidadCita, String idPaciente, String idMedico, Integer idIps) {
        this.idCita = idCita;
        this.fechaCita = fechaCita;
        this.idFranjaHoraria = idFranjaHoraria;
        this.idEspecialidad = idEspecialidad;
        this.idModalidadCita = idModalidadCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.idIps = idIps;
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

    public Integer getIdFranjaHoraria() {
        return idFranjaHoraria;
    }

    public void setIdFranjaHoraria(Integer idFranjaHoraria) {
        this.idFranjaHoraria = idFranjaHoraria;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Integer getIdModalidadCita() {
        return idModalidadCita;
    }

    public void setIdModalidadCita(Integer idModalidadCita) {
        this.idModalidadCita = idModalidadCita;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdIps() {
        return idIps;
    }

    public void setIdIps(Integer idIps) {
        this.idIps = idIps;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "idCita='" + idCita + '\'' +
                ", fechaCita=" + fechaCita +
                ", idFranjaHoraria=" + idFranjaHoraria +
                ", idEspecialidad=" + idEspecialidad +
                ", idModalidadCita=" + idModalidadCita +
                ", idPaciente='" + idPaciente + '\'' +
                ", idMedico='" + idMedico + '\'' +
                ", idIps=" + idIps +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cita cita = (Cita) o;
        return Objects.equals(idCita, cita.idCita) && Objects.equals(fechaCita, cita.fechaCita) && Objects.equals(idFranjaHoraria, cita.idFranjaHoraria) && Objects.equals(idEspecialidad, cita.idEspecialidad) && Objects.equals(idModalidadCita, cita.idModalidadCita) && Objects.equals(idPaciente, cita.idPaciente) && Objects.equals(idMedico, cita.idMedico) && Objects.equals(idIps, cita.idIps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCita, fechaCita, idFranjaHoraria, idEspecialidad, idModalidadCita, idPaciente, idMedico, idIps);
    }
}
