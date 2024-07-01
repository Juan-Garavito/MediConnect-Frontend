package com.example.mediconnect.Modelos;

public class Medicamento {

    private Long id;

    private String nombreMedicamento;

    private String concentracion;

    private String formaFarmaceutica;


    public Medicamento(){

    }

    public Medicamento(Long id, String nombreMedicamento, String concentracion, String formaFarmaceutica) {
        this.id = id;
        this.nombreMedicamento = nombreMedicamento;
        this.concentracion = concentracion;
        this.formaFarmaceutica = formaFarmaceutica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getFormaFarmaceutica() {
        return formaFarmaceutica;
    }

    public void setFormaFarmaceutica(String formaFarmaceutica) {
        this.formaFarmaceutica = formaFarmaceutica;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nombreMedicamento='" + nombreMedicamento + '\'' +
                ", concentracion='" + concentracion + '\'' +
                ", formaFarmaceutica='" + formaFarmaceutica + '\'' +
                '}';
    }
}
