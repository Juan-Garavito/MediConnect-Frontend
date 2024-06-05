package com.example.mediconnect.Utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.example.mediconnect.R;

public class CargaDialog {

    private Context context;
    private Window window;

    public CargaDialog(Context context, Window window) {
        this.context = context;
        this.window = window;
    }

    public AlertDialog crearDialogCarga(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = window.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_carga, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
