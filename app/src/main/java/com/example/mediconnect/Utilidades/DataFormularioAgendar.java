package com.example.mediconnect.Utilidades;

import com.example.mediconnect.Modelos.CiudadanoDTO;
import com.example.mediconnect.Modelos.Especialidad;
import com.example.mediconnect.Modelos.FranjaHoraria;
import com.example.mediconnect.Modelos.Ips;
import com.example.mediconnect.Modelos.ModalidadCita;

import java.util.List;

public class DataFormularioAgendar {
    private static DataFormularioAgendar instance;
    private  List<CiudadanoDTO> medicos;
    private  List<Especialidad> especialidads;
    private  List<FranjaHoraria> franjaHorarias;
    private List<ModalidadCita> modalidadCitas;
    private List<Ips> ipsList;

    private DataFormularioAgendar() {

    }

    public static DataFormularioAgendar getInstance() {
        if (instance == null) {
            instance = new DataFormularioAgendar();
        }
        return instance;
    }


    public List<CiudadanoDTO> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<CiudadanoDTO> medicos) {
        this.medicos = medicos;
    }

    public List<Especialidad> getEspecialidads() {
        return especialidads;
    }

    public void setEspecialidads(List<Especialidad> especialidads) {
        this.especialidads = especialidads;
    }

    public List<FranjaHoraria> getFranjaHorarias() {
        return franjaHorarias;
    }

    public void setFranjaHorarias(List<FranjaHoraria> franjaHorarias) {
        this.franjaHorarias = franjaHorarias;
    }

    public List<ModalidadCita> getModalidadCitas() {
        return modalidadCitas;
    }

    public void setModalidadCitas(List<ModalidadCita> modalidadCitas) {
        this.modalidadCitas = modalidadCitas;
    }

    public List<Ips> getIpsList() {
        return ipsList;
    }

    public void setIpsList(List<Ips> ipsList) {
        this.ipsList = ipsList;
    }

}
