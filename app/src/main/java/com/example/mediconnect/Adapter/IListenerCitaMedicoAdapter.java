package com.example.mediconnect.Adapter;

import com.example.mediconnect.Modelos.CitaDTO;

public interface IListenerCitaMedicoAdapter {
    void cancelarCitaMedico(CitaDTO cita);
    void ingresarChatMedico(CitaDTO citaDTO);
}
