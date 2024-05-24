package com.example.mediconnect.Adapter;

import static android.media.CamcorderProfile.get;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.ClienteApi.Model.Cita;
import com.example.mediconnect.R;

import java.util.List;

public class ItemCitaAdapter extends RecyclerView.Adapter<ItemCitaAdapter.ViewHolder>{

    private List<Cita> itemCitas;

    public ItemCitaAdapter(List<Cita> itemCitas) {
        this.itemCitas = itemCitas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_proximas_citas,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cita item = itemCitas.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemCitas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textCita;
        public TextView textMedico;
        public TextView textFecha;
        public TextView textTipoCita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textCita = itemView.findViewById(R.id.idCita);
            this.textMedico = itemView.findViewById(R.id.idDoctor);
            this.textFecha = itemView.findViewById(R.id.idFechaCita);
            this.textTipoCita = itemView.findViewById(R.id.idTipoCita);
        }


        public void bind(Cita item) {

            String franja = item.getFranjaHoraria().split("-")[0];
            String medico = item.getMedico().split(" ")[0] + " " + item.getMedico().split(" ")[2];


            textCita.setText(item.getEspecialidad());
            textMedico.setText(medico);
            textFecha.setText(franja + " " + item.getFechaCita());
            textTipoCita.setText(item.getModalidadCita());

        }
    }
}
