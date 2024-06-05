package com.example.mediconnect.Utilidades;

import android.app.DatePickerDialog;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class Calendario {

    private DatePickerDialog datePickerDialog;
    private TextView fechaSeleccionada;

    public Calendario(DatePickerDialog datePickerDialog, TextView fechaSelececcionada) {
        this.datePickerDialog = datePickerDialog;
        this.fechaSeleccionada = fechaSelececcionada;
    }

    public void obtenerCalendario(){
        datePickerDialog.show();
    }

    public void listenerCalendario(ICalendario operacion){
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                operacion.ejecutar(year, month, dayOfMonth);
            }
        });
    }
}
