package com.example.mediconnect.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediconnect.ClienteApi.Api.ApiCiudadano;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.ClienteApi.Model.Cita;
import com.example.mediconnect.Adapter.ItemCitaAdapter;
import com.example.mediconnect.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioPaciente extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_paciente);

        bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        String idDocumento = bundle.getString("idDocumento");
        String imagen = bundle.getString("imagen");


        Toast.makeText(getBaseContext(), nombre,Toast.LENGTH_SHORT).show();

        TextView textNombre = findViewById(R.id.idTextNombre);
        textNombre.setText(nombre);
        ImageView imageView = findViewById(R.id.idImagen);
        Glide.with(this).load(imagen).into(imageView);


        RecyclerView recyclerView = findViewById(R.id.idRecyclerList);

        ApiCiudadano clienteRetrofit = ClienteRetrofit.getClient().create(ApiCiudadano.class);
        Call<List<Cita>> callCitas = clienteRetrofit.citasPorPaciente(idDocumento);

        callCitas.enqueue(new Callback<List<Cita>>() {
            @Override
            public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {
                Toast.makeText(getBaseContext(), "Repuesta",Toast.LENGTH_SHORT).show();
                ItemCitaAdapter adapter = new ItemCitaAdapter(response.body());
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Cita>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error",Toast.LENGTH_SHORT).show();
                Log.i("Error Citas",t.toString());
            }
        });


    }
}