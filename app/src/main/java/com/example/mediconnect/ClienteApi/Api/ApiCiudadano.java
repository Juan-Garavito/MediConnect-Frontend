package com.example.mediconnect.ClienteApi.Api;


import com.example.mediconnect.Modelos.Ciudadano;
import com.example.mediconnect.Modelos.Login;
import com.example.mediconnect.Modelos.CitaDTO;

import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCiudadano {

    @POST("/ciudadano/login")
    Call<Ciudadano> login(@Body Login loginData);

    @GET("/cita/buscar/paciente/{idPaciente}")
    Call<List<CitaDTO>> citasPorPaciente(@Path("idPaciente") String idPaciente);

    @GET("/cita/buscar/paciente/{idPaciente}/{maxLimit}")
    Call<List<CitaDTO>> citasPorPacienteConLimite(@Path("idPaciente") String idPaciente, @Path("maxLimit") int maxLimit);

    @GET("/cita/buscar/medico/{idMedico}/{fechaCita}")
    Call<List<CitaDTO>> citasPorIdMedicoFecha(@Path("idMedico") String idMedico, @Path("fechaCita") LocalDate fechaCita);
}
