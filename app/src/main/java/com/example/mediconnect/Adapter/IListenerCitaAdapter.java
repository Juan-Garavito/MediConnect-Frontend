package com.example.mediconnect.Adapter;

import com.example.mediconnect.Modelos.CitaDTO;

public interface IListenerCitaAdapter {
    void cancelarCita(CitaDTO cita);
    void reprogramarCita(CitaDTO cita);
}
