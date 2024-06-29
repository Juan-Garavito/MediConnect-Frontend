package com.example.mediconnect.Utilidades;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.mediconnect.Activity.CitasPaciente;
import com.example.mediconnect.ClienteApi.Api.ApiCita;
import com.example.mediconnect.Modelos.Cita;
import com.example.mediconnect.Modelos.CiudadanoDTO;
import com.example.mediconnect.Modelos.Especialidad;
import com.example.mediconnect.Modelos.FranjaHoraria;
import com.example.mediconnect.Modelos.Ips;
import com.example.mediconnect.Modelos.ModalidadCita;
import com.example.mediconnect.R;

import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeticionesFormularioCita {

    private ApiCita apiCita;
    private Context context;
    private DataFormularioAgendar dataFormularioAgendar;
    private CargaDialog cargaDialog;

    public PeticionesFormularioCita() {
    }

    public PeticionesFormularioCita(ApiCita apiCita, Context context, Window window) {
        this.apiCita = apiCita;
        this.context = context;
        this.cargaDialog = new CargaDialog(context, window);
        this.dataFormularioAgendar = DataFormularioAgendar.getInstance();
    }

    public void buscarEspecialidades(AutoCompleteTextView autoCompleteEspecialidades){
        Call<List<Especialidad>> callEspecialidades = apiCita.buscarTodasEspecialidades();
        callEspecialidades.enqueue(new Callback<List<Especialidad>>() {
            @Override
            public void onResponse(Call<List<Especialidad>> call, Response<List<Especialidad>> response) {
                List<Especialidad> especialidades =  response.body();
                dataFormularioAgendar.setEspecialidads(especialidades);
                String[] especialidadesText = new String[especialidades.size()];
                for (int i = 0; i<especialidades.size(); i++){
                    especialidadesText[i] = especialidades.get(i).getDescripcion();
                    Log.i("especialidad", especialidades.get(i).getDescripcion());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.list_item_drop_down, especialidadesText);
                autoCompleteEspecialidades.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Especialidad>> call, Throwable t) {

            }
        });
    }

    public void buscarMedico(LocalDate fecha, int idEspecialidad, AutoCompleteTextView autoCompleteMedico){
        Call<List<CiudadanoDTO>> callCiudadano = apiCita.buscarMedicosConFechaYEspecialidad(fecha, idEspecialidad);
        callCiudadano.enqueue(new Callback<List<CiudadanoDTO>>() {
            @Override
            public void onResponse(Call<List<CiudadanoDTO>> call, Response<List<CiudadanoDTO>> response) {
                ArrayAdapter<String> arrayAdapter;
                if(response.body() != null){
                    List<CiudadanoDTO> medicos = response.body();
                    dataFormularioAgendar.setMedicos(medicos);
                    String[] medicosText = new String[medicos.size()];
                    for (int i = 0; i<medicos.size(); i++){
                        medicosText[i] = medicos.get(i).getNombres().split(" ")[0] + " " + medicos.get(i).getApellidos().split(" ")[0];
                    }
                    arrayAdapter = new ArrayAdapter<>(context, R.layout.list_item_drop_down, medicosText);
                    autoCompleteMedico.setAdapter(arrayAdapter);
                    return;
                }

                String[] medicoText = {};
                arrayAdapter = new ArrayAdapter<>(context, R.layout.list_item_drop_down, medicoText);
                autoCompleteMedico.setAdapter(arrayAdapter);
                Toast.makeText(context, "No hay medicos disponibles", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<CiudadanoDTO>> call, Throwable t) {
                Log.i("errorCiudadano", t.toString());
            }
        });
    }



    public void buscarFranjaHoraria(String idMedico, LocalDate fecha, AutoCompleteTextView autoCompleteFranjaHoraria){
        Call<List<FranjaHoraria>> callFranjaHoraria = apiCita.buscarTodasFranjaHoraria(idMedico, fecha);
        callFranjaHoraria.enqueue(new Callback<List<FranjaHoraria>>() {
            @Override
            public void onResponse(Call<List<FranjaHoraria>> call, Response<List<FranjaHoraria>> response) {
                List<FranjaHoraria> franjaHorarias =  response.body();
                dataFormularioAgendar.setFranjaHorarias(franjaHorarias);
                String[] franjaHorariaText = new String[franjaHorarias.size()];
                for (int i = 0; i<franjaHorarias.size(); i++){
                    franjaHorariaText[i] = franjaHorarias.get(i).getDescripcion();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.list_item_drop_down, franjaHorariaText);
                autoCompleteFranjaHoraria.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<FranjaHoraria>> call, Throwable t) {
                Log.e("error-franja", t.toString());
            }
        });
    }

    public void buscarModalidadCita(AutoCompleteTextView autoCompleteFranjaHoraria) {
        Call<List<ModalidadCita>> callModalidadCita = apiCita.buscarTodasModalidaCita();
        callModalidadCita.enqueue(new Callback<List<ModalidadCita>>() {
            @Override
            public void onResponse(Call<List<ModalidadCita>> call, Response<List<ModalidadCita>> response) {
                List<ModalidadCita> modalidadCitas =  response.body();
                dataFormularioAgendar.setModalidadCitas(modalidadCitas);
                String[] modalidadCitasText = new String[modalidadCitas.size()];
                for (int i = 0; i<modalidadCitas.size(); i++){
                    modalidadCitasText[i] = modalidadCitas.get(i).getDescripcion();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.list_item_drop_down, modalidadCitasText);
                autoCompleteFranjaHoraria.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<ModalidadCita>> call, Throwable t) {
                Log.e("error-modalidad", t.toString());
            }
        });

    }

    public void buscarIps(AutoCompleteTextView autoCompleteIps ){
        Call<List<Ips>> callIps = apiCita.buscarTodasIps();
        callIps.enqueue(new Callback<List<Ips>>() {
            @Override
            public void onResponse(Call<List<Ips>> call, Response<List<Ips>> response) {
                List<Ips> ipsList =  response.body();
                dataFormularioAgendar.setIpsList(ipsList);
                String[] ipsListText = new String[ipsList.size()-1];
                for (int i = 0; i<ipsList.size()-1; i++){
                    ipsListText[i] = ipsList.get(i).getDescripcion();
                    Log.i("Ips", ipsList.get(i).getDescripcion());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.list_item_drop_down, ipsListText);
                autoCompleteIps.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Ips>> call, Throwable t) {

            }
        });

    }

    public void agendarCita(Cita cita){
        Call<Cita> callCita = apiCita.enviarCita(cita);
        AlertDialog dialog = cargaDialog.crearDialogCarga();
        dialog.show();
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                dialog.dismiss();
                if (response.body() != null){
                    Toast.makeText(context, "Se ha Agendado tu cita", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CitasPaciente.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "Hubo un error en la Conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void reagendarCita(Cita cita){
        Call<Cita> callCita = apiCita.editarCita(cita);
        AlertDialog dialog = cargaDialog.crearDialogCarga();
        dialog.show();
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                dialog.dismiss();
                if(response.body() != null){
                    Toast.makeText(context, "Se ha editado tu cita correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CitasPaciente.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public void buscarCitaPorId(String idCita, IBuscarCita iBuscarCita){
        Call<Cita> callCita = apiCita.buscarCitaPorId(idCita);
        AlertDialog dialog = cargaDialog.crearDialogCarga();
        dialog.show();
        callCita.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {
                dialog.dismiss();
                if (response.body() != null){
                    iBuscarCita.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }
}
