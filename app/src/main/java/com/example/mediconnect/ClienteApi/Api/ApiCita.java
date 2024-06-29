package com.example.mediconnect.ClienteApi.Api;

import com.example.mediconnect.Modelos.Cita;
import com.example.mediconnect.Modelos.Ips;
import com.example.mediconnect.Modelos.ModalidadCita;
import com.example.mediconnect.Modelos.CiudadanoDTO;
import com.example.mediconnect.Modelos.Especialidad;
import com.example.mediconnect.Modelos.FranjaHoraria;

import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCita {

    @GET("cita/buscar/especialidades")
    Call<List<Especialidad>> buscarTodasEspecialidades();

    @GET("cita/buscar/medicos/{fecha}/{idEspecialidad}")
    Call<List<CiudadanoDTO>> buscarMedicosConFechaYEspecialidad(@Path("fecha") LocalDate fecha, @Path("idEspecialidad") Integer idEspecialidad);

    @GET("cita/buscar/franjahoraria/{fecha}/{idMedico}")
    Call<List<FranjaHoraria>> buscarTodasFranjaHoraria(@Path("idMedico") String idMedico, @Path("fecha") LocalDate fecha);

    @GET("cita/buscar/modalidadcita")
    Call<List<ModalidadCita>> buscarTodasModalidaCita();

    @GET("cita/buscar/listIps")
    Call<List<Ips>> buscarTodasIps();

    @POST("cita/ingresar")
    Call<Cita> enviarCita(@Body Cita cita);

    @DELETE("cita/eliminar/{idCita}")
    Call<Cita> eliminarCita(@Path("idCita") String idCita);

    @GET("cita/buscar/{idCita}")
    Call<Cita> buscarCitaPorId(@Path("idCita") String idCita);

    @PUT("cita/editar")
    Call<Cita> editarCita(@Body Cita cita);
}
