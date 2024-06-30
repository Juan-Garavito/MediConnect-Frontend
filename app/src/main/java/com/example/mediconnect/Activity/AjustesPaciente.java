package com.example.mediconnect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediconnect.Modelos.CiudadanoDTO;
import com.example.mediconnect.R;
import com.example.mediconnect.databinding.ActivityAjustesPacienteBinding;

public class AjustesPaciente extends AppCompatActivity {

    ActivityAjustesPacienteBinding binding;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjustesPacienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bundle = getIntent().getExtras();
        CiudadanoDTO ciudadanoDTO = (CiudadanoDTO) bundle.getSerializable("ciudadano");
        String tipo = bundle.getString("tipoPerfil");
        binding.idBtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AjustesPaciente.this, PerfilPaciente.class);
                intent.putExtra("ciudadano", ciudadanoDTO);
                intent.putExtra("tipoPerfil", tipo);
                startActivity(intent);
            }
        });

        binding.idBtnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AjustesPaciente.this, PerfilPaciente.class);
                intent.putExtra("ciudadano", ciudadanoDTO);
                intent.putExtra("tipoPerfil", "1");
                startActivity(intent);
            }
        });
    }
}