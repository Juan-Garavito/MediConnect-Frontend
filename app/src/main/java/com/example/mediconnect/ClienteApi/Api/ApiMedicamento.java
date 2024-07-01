package com.example.mediconnect.ClienteApi.Api;

import com.example.mediconnect.Modelos.Medicamento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiMedicamento {
    @GET("/medicamento/list")
    Call<List<Medicamento>> cargarMedicamentos();

}
