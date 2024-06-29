package com.example.mediconnect.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.Modelos.Mensaje;
import com.example.mediconnect.R;

import java.util.List;


public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {

    private List<Mensaje> mensajes;
    private String idCiudadano;

    public MensajeAdapter(List<Mensaje> mensajes, String idCiudadano) {
        this.mensajes = mensajes;
        this.idCiudadano = idCiudadano;
    }

    @NonNull
    @Override
    public MensajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensaje_chat, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeAdapter.ViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        holder.bind(mensaje, this.idCiudadano);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textDestinatario;
        public TextView textRemitente;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textDestinatario = itemView.findViewById(R.id.idDestinatario);
            this.textRemitente = itemView.findViewById(R.id.idRemitente);
        }

        public void bind(Mensaje mensaje, String idCiudadano) {
            if(mensaje.getIdDestinatario().equals(idCiudadano)){
                textDestinatario.setText(mensaje.getContenido());
                textDestinatario.setVisibility(View.VISIBLE);
                textRemitente.setVisibility(View.GONE);
                return;
            }

            textRemitente.setText(mensaje.getContenido());
            textRemitente.setVisibility(View.VISIBLE);
            textDestinatario.setVisibility(View.GONE);
        }
    }
}
