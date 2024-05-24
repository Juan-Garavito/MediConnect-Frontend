package com.example.mediconnect.ClienteApi.Api;


import com.example.mediconnect.ClienteApi.Model.Ciudadano;
import com.example.mediconnect.ClienteApi.Model.Login;
import com.example.mediconnect.ClienteApi.Model.Cita;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCiudadano {

    @POST("/ciudadano/login")
    Call<Ciudadano> login(@Body Login loginData);

    @GET("/cita/buscar/paciente")
    Call<List<Cita>> citasPorPaciente(@Query("idPaciente") String idPaciente);

}
