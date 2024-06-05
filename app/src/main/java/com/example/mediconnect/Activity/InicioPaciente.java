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
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.CargaDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioPaciente extends AppCompatActivity {

    Bundle bundle;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_paciente);

        bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        String idDocumento = bundle.getString("idDocumento");
        String imagen = bundle.getString("imagen");

        dialog =  new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();
        Toast.makeText(getBaseContext(), nombre,Toast.LENGTH_SHORT).show();

        TextView textNombre = findViewById(R.id.idTextNombre);
        textNombre.setText(nombre);
        ImageView imageView = findViewById(R.id.idImagen);
        Glide.with(this).load(imagen).into(imageView);


        RecyclerView recyclerView = findViewById(R.id.idRecyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiCiudadano clienteRetrofit = ClienteRetrofit.getClient().create(ApiCiudadano.class);
        Call<List<CitaDTO>> callCitas = clienteRetrofit.citasPorPacienteConLimite(idDocumento, 5);

        callCitas.enqueue(new Callback<List<CitaDTO>>() {
            @Override
            public void onResponse(Call<List<CitaDTO>> call, Response<List<CitaDTO>> response) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Repuesta",Toast.LENGTH_SHORT).show();
                ProximaCitaAdapter adapter = new ProximaCitaAdapter(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CitaDTO>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Error",Toast.LENGTH_SHORT).show();
                Log.i("Error Citas",t.toString());
            }
        });


        ImageView btnCitas = findViewById(R.id.idBtnCitas);
        btnCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioPaciente.this, CitasPaciente.class);
                intent.putExtra("idDocumento", idDocumento);
                startActivity(intent);
            }
        });

    }
}