package com.example.mediconnect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.Adapter.CitaAdapter;
import com.example.mediconnect.Adapter.IListenerCitaAdapter;
import com.example.mediconnect.ClienteApi.Api.ApiCita;
import com.example.mediconnect.ClienteApi.Api.ApiCiudadano;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.Cita;
import com.example.mediconnect.Modelos.CitaDTO;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.CargaDialog;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitasPaciente extends AppCompatActivity implements IListenerCitaAdapter {

    Bundle bundle;
    String idDocumento;
    IListenerCitaAdapter listenerCitaAdapter = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_paciente);

        bundle = getIntent().getExtras();
        idDocumento = bundle.getString("idDocumento");


        obtenerCitas(idDocumento);

        Button idBtnAgendar = findViewById(R.id.idBtnAgendar);
        idBtnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AgendarCitaPaciente.class);
                intent.putExtra("idDocumento", idDocumento);
                intent.putExtra("cita", (Serializable) null);
                startActivity(intent);
            }
        });
    }

    @Override
    public void cancelarCita(CitaDTO cita) {
        AlertDialog dialog = new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();
        ApiCita apiCita = ClienteRetrofit.getClient().create(ApiCita.class);
        Call<Cita> callCita = apiCita.eliminarCita(cita.getIdCita());
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                dialog.dismiss();
                if(response.body() != null){
                    Toast.makeText(getBaseContext(), "Se ha cancelado con exito", Toast.LENGTH_SHORT).show();
                    obtenerCitas(idDocumento);
                    return;
                }
                Toast.makeText(getBaseContext(), "No existe esta cita", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Hay un erro en la conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void reprogramarCita(CitaDTO cita) {
        Intent intent = new Intent(getBaseContext(), AgendarCitaPaciente.class);
        intent.putExtra("idDocumento", idDocumento);
        intent.putExtra("cita", cita);
        startActivity(intent);
    }

    public void obtenerCitas(String idDocumento){
        AlertDialog dialog = new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();
        RecyclerView recyclerView = findViewById(R.id.idRecyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiCiudadano clienteRetrofit = ClienteRetrofit.getClient().create(ApiCiudadano.class);
        Call<List<CitaDTO>> citasCall = clienteRetrofit.citasPorPaciente(idDocumento);

        citasCall.enqueue(new Callback<List<CitaDTO>>() {
            @Override
            public void onResponse(Call<List<CitaDTO>> call, Response<List<CitaDTO>> response) {
                CitaAdapter citaAdapter = new CitaAdapter(response.body(), listenerCitaAdapter);
                recyclerView.setAdapter(citaAdapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CitaDTO>> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}