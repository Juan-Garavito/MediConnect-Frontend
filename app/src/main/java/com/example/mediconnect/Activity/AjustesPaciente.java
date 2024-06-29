package com.example.mediconnect.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediconnect.R;
import com.example.mediconnect.databinding.ActivityAjustesPacienteBinding;

public class AjustesPaciente extends AppCompatActivity {

    ActivityAjustesPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAjustesPacienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idBtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(AjustesPaciente.this, PerfilPaciente.class);
                intent.putExtra("idDocumento", "1005546504");
                intent.putExtra("tipoPerfil", "0");
                startActivity(intent);
            }
        });
    }
}