package com.example.mediconnect.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.Modelos.Mensaje;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.AESEncryption;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;


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
        try {
            return new ViewHolder(view);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeAdapter.ViewHolder holder, int position) {
        Mensaje mensaje = mensajes.get(position);
        try {
            holder.bind(mensaje, this.idCiudadano);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textDestinatario;
        public TextView textRemitente;
        private final AESEncryption aesEncryption;

        public ViewHolder(@NonNull View itemView) throws IOException {
            super(itemView);
            this.textDestinatario = itemView.findViewById(R.id.idDestinatario);
            this.textRemitente = itemView.findViewById(R.id.idRemitente);
            this.aesEncryption =  AESEncryption.getInstance(itemView.getContext());
        }

        public void bind(Mensaje mensaje, String idCiudadano) throws Exception {
            String mensajeDesncriptado = aesEncryption.descifrar(mensaje.getContenido());
            if(mensaje.getIdDestinatario().equals(idCiudadano)){
                textDestinatario.setText(mensajeDesncriptado);
                textDestinatario.setVisibility(View.VISIBLE);
                textRemitente.setVisibility(View.GONE);
                return;
            }

            textRemitente.setText(mensajeDesncriptado);
            textRemitente.setVisibility(View.VISIBLE);
            textDestinatario.setVisibility(View.GONE);
        }

    }
}
