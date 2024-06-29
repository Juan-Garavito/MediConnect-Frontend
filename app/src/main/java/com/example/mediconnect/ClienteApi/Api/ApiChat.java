package com.example.mediconnect.ClienteApi.Api;

import com.example.mediconnect.Modelos.Mensaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiChat {

    @GET("/chat/{idChat}/{pagina}")
    Call<List<Mensaje>> obtenerMensajesConIdChat(@Path("idChat") String idChat, @Path("pagina") int pagina);
}
