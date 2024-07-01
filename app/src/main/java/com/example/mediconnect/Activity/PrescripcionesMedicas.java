package com.example.mediconnect.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediconnect.R;


import java.io.Serializable;

public class PrescripcionesMedicas extends AppCompatActivity {
    private RelativeLayout relativeLayoutFM;
    private RelativeLayout relativeLayoutOM;
    private RelativeLayout relativeLayoutHM;
    private RelativeLayout relativeLayoutHMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescripciones_medicas);

        relativeLayoutFM = findViewById(R.id.relativeLayout);
        relativeLayoutOM = findViewById(R.id.relativeLayout2);
        relativeLayoutHM = findViewById(R.id.relativeLayout3);
        relativeLayoutHMP = findViewById(R.id.relativeLayaut4);

        ImageButton IBFM = findViewById(R.id.imageButton5);
        ImageButton IBOM= findViewById(R.id.imageButton6);
        ImageButton IBHM = findViewById(R.id.imageButton7);
        ImageButton IBHMP = findViewById(R.id.imageButton8);

        relativeLayoutFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, GenerarFormulaMedica.class);
                startActivity(intent);
            }
        });

        IBFM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, GenerarFormulaMedica.class);
                startActivity(intent);
            }
        });

        relativeLayoutOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, GenerarOrdenTratamiento.class);
                startActivity(intent);
            }
        });

        IBOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, GenerarOrdenTratamiento.class);
                startActivity(intent);
            }
        });


        relativeLayoutHM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, GenerarHistoriaClinica.class);
                startActivity(intent);
            }
        });

        IBHM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, GenerarHistoriaClinica.class);
                startActivity(intent);
            }
        });



        relativeLayoutHMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, HistorialMedico.class);
                startActivity(intent);
            }
        });

        IBHMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescripcionesMedicas.this, HistorialMedico.class);
                startActivity(intent);
            }
        });
    }
}