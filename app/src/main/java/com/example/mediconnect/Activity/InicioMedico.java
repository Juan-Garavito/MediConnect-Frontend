package com.example.mediconnect.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.Adapter.CitaAdapter;
import com.example.mediconnect.Adapter.CitaMedicoAdapter;
import com.example.mediconnect.Adapter.IListenerCitaMedicoAdapter;
import com.example.mediconnect.ClienteApi.Api.ApiCita;
import com.example.mediconnect.ClienteApi.Api.ApiCiudadano;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.Cita;
import com.example.mediconnect.Modelos.CitaDTO;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.CargaDialog;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioMedico extends AppCompatActivity implements IListenerCitaMedicoAdapter {

    Bundle bundle;
    String idDocumentoMedico;
    LocalDate  fechaCita;

    IListenerCitaMedicoAdapter listenerCitaMedicoAdapter = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_medico);

        bundle = getIntent().getExtras();
        idDocumentoMedico = bundle.getString("idDocumento");
        fechaCita = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy", new Locale("es"));
        String fechaFormateada = fechaCita.format(formatter);

        TextView textViewFecha = findViewById(R.id.textView16);
        textViewFecha.setText(fechaFormateada);

        obtenerCitasMedico(idDocumentoMedico, fechaCita);
    }

    @Override
    public void cancelarCitaMedico(CitaDTO cita) {
        AlertDialog dialog = new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();
        ApiCita apiCita = ClienteRetrofit.getClient().create(ApiCita.class);
        Call<Cita> callCita = apiCita.eliminarCita(cita.getIdCita());
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                dialog.dismiss();
                if(response.body() != null){
                    Toast.makeText(getBaseContext(), "Se ha cancelado con exito", Toast.LENGTH_SHORT).show();
                    obtenerCitasMedico(idDocumentoMedico,fechaCita);
                    return;
                }
                Toast.makeText(getBaseContext(), "No existe esta cita", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getBaseContext(), "Hay un erro en la conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void ingresarChatMedico(CitaDTO citaDTO) {
        Map<String,String> fechaYHoraAhora =  obtenerFechaYHoraAhora();
        String fecha = fechaYHoraAhora.get("fecha");
        String hora = fechaYHoraAhora.get("hora");


        String[] franjaCita = citaDTO.getFranjaHoraria().split(" ");
        String[] tiempoCita = franjaCita[0].split(":");
        String[] franjaAhora = hora.split(" ");
        String[] tiempoAhora = franjaAhora[0].split(":");


        if(!fecha.equals(citaDTO.getFechaCita()) ||
                !franjaCita[1].equals(franjaAhora[1]) ||
                Integer.parseInt(tiempoCita[0]) != Integer.parseInt(tiempoAhora[0]) ||
                !(Integer.parseInt(tiempoCita[1]) <= Integer.parseInt(tiempoAhora[1]) &&
                        Integer.parseInt(tiempoCita[1])+30 > Integer.parseInt(tiempoAhora[1]))
        ){
            Toast.makeText(this, "No puedes entrar al chat. Tu cita es "+ citaDTO.getFechaCita() + " a las "+ franjaCita[0] + " " + franjaCita[1], Toast.LENGTH_SHORT).show();
            return;
        }

        ApiCita apiCita = ClienteRetrofit.getClient().create(ApiCita.class);
        Call<Cita> callCita = apiCita.buscarCitaPorId(citaDTO.getIdCita());
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                if(response.body() != null){
                    try {
                        Cita cita = response.body();
                        Intent intent = new Intent(getBaseContext(), ChatCita.class);
                        intent.putExtra("cita", cita);
                        intent.putExtra("idCiudadano", idDocumentoMedico);
                        intent.putExtra("idChat", citaDTO.getIdChat());
                        startActivity(intent);
                    }catch (Throwable e){
                        Log.e("error eligiendo cita:", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {

            }
        });
    }

    public void obtenerCitasMedico(String idDocumento, LocalDate fechaCita){
        AlertDialog dialog = new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();
        RecyclerView recyclerView = findViewById(R.id.idListCitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ApiCiudadano clienteRetrofitMedico = ClienteRetrofit.getClient().create(ApiCiudadano.class);
        Call<List<CitaDTO>> citasMedicoCall = clienteRetrofitMedico.citasPorIdMedicoFecha(idDocumento, fechaCita);
        Log.d("RespuestaCM", citasMedicoCall.toString());

        citasMedicoCall.enqueue(new Callback<List<CitaDTO>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CitaDTO>> call, Response<List<CitaDTO>> response) {
                CitaMedicoAdapter citaMedicoAdapter = new CitaMedicoAdapter(response.body(), listenerCitaMedicoAdapter);
                recyclerView.setAdapter(citaMedicoAdapter);
                dialog.dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<List<CitaDTO>> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Map<String, String> obtenerFechaYHoraAhora(){
        int hora = LocalTime.now(ZoneId.of("America/Bogota")).getHour();
        int minutos = LocalTime.now(ZoneId.of("America/Bogota")).getMinute();
        String fecha = LocalDate.now(ZoneId.of("America/Bogota")).toString();
        String horaString = String.valueOf(hora);
        String minutosString= String.valueOf(minutos);
        String abreviatura = "a.m.";
        Map<String,String> fechaYHoraAhora = new HashMap<>();

        if(hora > 12 ){
            hora -= 12;
            horaString = String.valueOf(hora);
            abreviatura = "p.m.";
        }

        if(minutos < 10){
            minutosString = "0"+minutos;
        }

        fechaYHoraAhora.put("fecha", fecha);
        fechaYHoraAhora.put("hora", horaString+":"+minutosString+" "+abreviatura);

        return fechaYHoraAhora;
    }
}