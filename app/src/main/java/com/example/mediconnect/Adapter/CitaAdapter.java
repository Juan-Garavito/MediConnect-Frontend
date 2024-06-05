package com.example.mediconnect.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediconnect.Modelos.CitaDTO;
import com.example.mediconnect.R;

import java.util.List;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.ViewHolder> {

    private List<CitaDTO> citas;
    private IListenerCitaAdapter listenerCitaAdapter;

    public CitaAdapter(List<CitaDTO> citas, IListenerCitaAdapter listenerCitaAdapter) {
        this.citas = citas;
        this.listenerCitaAdapter = listenerCitaAdapter;
    }

    @NonNull
    @Override
    public CitaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaAdapter.ViewHolder holder, int position) {
        CitaDTO cita = citas.get(position);
        holder.bind(cita);
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textCita;
        public TextView textMedico;
        public TextView textFecha;
        public TextView textHora;
        public TextView textTipoCita;
        public ImageView imgIps;
        public TextView textIps;
        public Button btnCancelarCita;
        public Button btnReprogramarCita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textCita = itemView.findViewById(R.id.idEspecialidadCita);
            this.textMedico = itemView.findViewById(R.id.idDoctorCita);
            this.textFecha = itemView.findViewById(R.id.idCitaFecha);
            this.textHora = itemView.findViewById(R.id.idHoraCita);
            this.textTipoCita = itemView.findViewById(R.id.idModaCita);
            this.imgIps = itemView.findViewById(R.id.idImagenIps);
            this.textIps = itemView.findViewById(R.id.idIpsCita);
            this.btnCancelarCita = itemView.findViewById(R.id.idCancelarCIta);
            this.btnReprogramarCita = itemView.findViewById(R.id.idReprogramarCita);
        }

        public void bind(CitaDTO cita) {
            textCita.setText(cita.getEspecialidad());
            textMedico.setText(cita.getMedico().split(" ")[0] + " " + cita.getMedico().split(" ")[2]);
            textFecha.setText(cita.getFechaCita());
            textHora.setText(cita.getFranjaHoraria().split("-")[0]);
            textTipoCita.setText(cita.getModalidadCita());
            Glide.with(itemView.getContext()).load(cita.getUrlIps()).into(imgIps);
            textIps.setText(cita.getIps());

            btnReprogramarCita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerCitaAdapter.reprogramarCita(cita);
                }
            });

            btnCancelarCita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerCitaAdapter.cancelarCita(cita);
                }
            });
        }
    }
}
