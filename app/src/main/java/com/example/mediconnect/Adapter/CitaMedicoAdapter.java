package com.example.mediconnect.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mediconnect.Modelos.CitaDTO;
import com.example.mediconnect.R;

import java.util.List;

public class CitaMedicoAdapter extends RecyclerView.Adapter<CitaMedicoAdapter.ViewHolder>  {
    private List<CitaDTO> citas;
    private IListenerCitaMedicoAdapter listenerCitaMedicoAdapterAdapter;

    public CitaMedicoAdapter(List<CitaDTO> citas, IListenerCitaMedicoAdapter listenerCitaMedicoAdapterAdapter) {
        this.citas = citas;
        this.listenerCitaMedicoAdapterAdapter = listenerCitaMedicoAdapterAdapter;
    }

    @NonNull
    @Override
    public CitaMedicoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita_medico, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaMedicoAdapter.ViewHolder holder, int position) {
        CitaDTO cita = citas.get(position);
        holder.bind(cita);
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textCitaMedico;
        public TextView textPacienteAM;
        public TextView textHoraMedico;
        public TextView textTipoCitaMedico;
        public ImageView imgIpsMedico;
        public TextView textIpsMedico;
        public Button btnCancelarCitaMedico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textCitaMedico = itemView.findViewById(R.id.idEspecialidadCitaMedico);
            this.textPacienteAM = itemView.findViewById(R.id.idPacienteAM);
            this.textHoraMedico = itemView.findViewById(R.id.idHoraCitaAM);
            this.textTipoCitaMedico = itemView.findViewById(R.id.idModaCitaMedico);
            this.imgIpsMedico = itemView.findViewById(R.id.idImagenIpsAM);
            this.btnCancelarCitaMedico = itemView.findViewById(R.id.idCancelarCItaAM);
        }

        public void bind(CitaDTO cita) {
            textCitaMedico.setText(cita.getEspecialidad());
            textPacienteAM.setText(cita.getIdPaciente());
            textHoraMedico.setText(cita.getFranjaHoraria());
            textTipoCitaMedico.setText(cita.getModalidadCita());
            Glide.with(itemView.getContext()).load(cita.getUrlIps()).into(imgIpsMedico);

            btnCancelarCitaMedico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerCitaMedicoAdapterAdapter.cancelarCitaMedico(cita);
                }
            });
        }
    }
}