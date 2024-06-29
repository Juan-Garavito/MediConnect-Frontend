package com.example.mediconnect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediconnect.ClienteApi.Api.ApiCiudadano;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.CitaDTO;
import com.example.mediconnect.Adapter.ProximaCitaAdapter;
import com.example.mediconnect.Modelos.Ciudadano;
import com.example.mediconnect.Modelos.CiudadanoDTO;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.CargaDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioPaciente extends AppCompatActivity {

    Bundle bundle;
    AlertDialog dialog;
    TextView tachadoCitasProximas;
    TextView citasProximasVacio;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_paciente);

        bundle = getIntent().getExtras();
        CiudadanoDTO ciudadano = (CiudadanoDTO) bundle.getSerializable("ciudadano");
        String nombre = ciudadano.getNombres().split(" ")[0] + " " + ciudadano.getApellidos().split(" ")[0];
        String idDocumento = ciudadano.getNumerodocumento();
        String imagen = ciudadano.getUrl();

        dialog =  new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();

        TextView textNombre = findViewById(R.id.idTextNombre);
        textNombre.setText(nombre);
        ImageView imageView = findViewById(R.id.idImagen);
        Glide.with(this).load(imagen).into(imageView);

        tachadoCitasProximas = findViewById(R.id.idTextoProximasTachado);
        citasProximasVacio = findViewById(R.id.idCitasDisponiblesVacio);

        recyclerView = findViewById(R.id.idRecyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiCiudadano clienteRetrofit = ClienteRetrofit.getClient().create(ApiCiudadano.class);
        Call<List<CitaDTO>> callCitas = clienteRetrofit.citasPorPacienteConLimite(idDocumento, 5);

        callCitas.enqueue(new Callback<List<CitaDTO>>() {
            @Override
            public void onResponse(Call<List<CitaDTO>> call, Response<List<CitaDTO>> response) {
                dialog.dismiss();

                if(response.body() != null){
                    ProximaCitaAdapter adapter = new ProximaCitaAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                    return;
                }

                recyclerView.setVisibility(View.GONE);
                tachadoCitasProximas.setVisibility(View.VISIBLE);
                citasProximasVacio.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<CitaDTO>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Error",Toast.LENGTH_SHORT).show();
                Log.i("Error Citas",t.toString());
            }
        });


        ImageView btnCitas = findViewById(R.id.idBtnCitas);
        ImageView btnPerfil = findViewById(R.id.idBtnPerfil);

        btnCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioPaciente.this, CitasPaciente.class);
                intent.putExtra("ciudadano", ciudadano);
                startActivity(intent);
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioPaciente.this, PerfilPaciente.class);
                intent.putExtra("ciudadano", ciudadano);
                intent.putExtra("tipoPerfil", "1");
                startActivity(intent);
            }
        });

    }
}