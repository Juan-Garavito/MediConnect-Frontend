package com.example.mediconnect.Activity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mediconnect.ClienteApi.Api.ApiCita;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.Cita;
import com.example.mediconnect.Modelos.CitaDTO;
import com.example.mediconnect.Utilidades.Calendario;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.DataFormularioAgendar;
import com.example.mediconnect.Utilidades.IBuscarCita;
import com.example.mediconnect.Utilidades.PeticionesFormularioCita;
import com.example.mediconnect.Utilidades.TipoAutocompleteView;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AgendarCitaPaciente extends AppCompatActivity {
    String idDocumento;
    Calendario calendario;
    PeticionesFormularioCita formularioAgendar;
    AutoCompleteTextView autoCompleteEspecialidades;
    AutoCompleteTextView autoCompleteMedico;
    AutoCompleteTextView autoCompleteFranjaHoraria;
    AutoCompleteTextView autoCompleteModalidad;
    AutoCompleteTextView autoCompleteIps;
    DataFormularioAgendar dataFormularioAgendar;
    HashMap<TipoAutocompleteView, String> opcionesHash;
    List<TextInputLayout> textInputLayouts;
    Button btnAgendar;
    TextView textCalendario;
    Cita citaReagendar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita_paciente);
        initActivity();
        listenerFormulario();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void initActivity(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.color_calendario);
        calendario = new Calendario(datePickerDialog, textCalendario);

        ApiCita apiCita = ClienteRetrofit.getClient().create(ApiCita.class);
        formularioAgendar = new PeticionesFormularioCita(apiCita, this, getWindow());
        dataFormularioAgendar = DataFormularioAgendar.getInstance();

        opcionesHash = new HashMap<>();

        autoCompleteEspecialidades = findViewById(R.id.idAutoEspecialidad);
        autoCompleteMedico = findViewById(R.id.idAutoMedico);
        autoCompleteFranjaHoraria =  findViewById(R.id.idAutoFranjaHoraria);
        autoCompleteModalidad = findViewById(R.id.idAutoModalidad);
        autoCompleteIps = findViewById(R.id.idAutoIps);
        textCalendario = findViewById(R.id.idCalendario);

        btnAgendar = findViewById(R.id.idBtnAgendar);

        TextInputLayout inputModalidad = findViewById(R.id.inputLayoutModalidad);
        TextInputLayout inputEspecialidad = findViewById(R.id.inputLayoutEspecialidad);
        TextInputLayout inputMedico = findViewById(R.id.inputLayoutMedico);
        TextInputLayout inputHora = findViewById(R.id.inputLayoutHora);
        TextInputLayout inputIps = findViewById(R.id.inputLayoutIps);
        textInputLayouts = new ArrayList<>();
        textInputLayouts.add(inputEspecialidad);
        textInputLayouts.add(inputMedico);
        textInputLayouts.add(inputHora);
        textInputLayouts.add(inputModalidad);
        textInputLayouts.add(inputIps);

        formularioAgendar.buscarEspecialidades(autoCompleteEspecialidades);
        formularioAgendar.buscarModalidadCita(autoCompleteModalidad);
        formularioAgendar.buscarIps(autoCompleteIps);


        Bundle bundle = getIntent().getExtras();
        idDocumento = bundle.getString("idDocumento");
        CitaDTO citaDTO = (CitaDTO) bundle.getSerializable("cita");
        if(citaDTO != null){
            autoCompleteEspecialidades.setText(citaDTO.getEspecialidad());
            autoCompleteMedico.setText(citaDTO.getMedico().split(" ")[0] + " " + citaDTO.getMedico().split(" ")[2]);
            autoCompleteFranjaHoraria.setText((citaDTO.getFranjaHoraria()));
            autoCompleteModalidad.setText(citaDTO.getModalidadCita());
            textCalendario.setText(citaDTO.getFechaCita());
            formularioAgendar.buscarCitaPorId(citaDTO.getIdCita(), new IBuscarCita() {
                @Override
                public void onResponse(Cita cita) {
                    citaReagendar = cita;
                    opcionesHash.put(TipoAutocompleteView.ESPECIALIDAD, String.valueOf(cita.getIdEspecialidad()));
                    opcionesHash.put(TipoAutocompleteView.MEDICO, cita.getIdMedico());
                    opcionesHash.put(TipoAutocompleteView.HORA, String.valueOf(cita.getIdFranjaHoraria()));
                    opcionesHash.put(TipoAutocompleteView.MODALIDAD,  String.valueOf(cita.getIdModalidadCita()));
                    opcionesHash.put(TipoAutocompleteView.FECHA, cita.getFechaCita());
                    if (cita.getIdModalidadCita() == 1){
                        opcionesHash.put(TipoAutocompleteView.IPS, String.valueOf(cita.getIdIps()));
                    }
                    formularioAgendar.buscarMedico(LocalDate.parse(cita.getFechaCita()), cita.getIdEspecialidad(), autoCompleteMedico);
                    formularioAgendar.buscarFranjaHoraria(cita.getIdMedico(), autoCompleteFranjaHoraria);
                }
            });

            if (citaDTO.getModalidadCita().equals("Presencial")){
                autoCompleteIps.setText(citaDTO.getIps());
                inputIps.setVisibility(View.VISIBLE);
            }
        }else{
            desactivarVistas(textInputLayouts, 0 , false);
        }
    }

    public void listenerFormulario(){
        textCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendario.obtenerCalendario();
                calendario.listenerCalendario((int year, int month, int dayOfMonth) -> {
                    String fechaSinFormato = year + "-" + month + "-" + dayOfMonth;
                    String fechaConFormato = fechaSinFormato.replaceAll("-(\\d(?!\\d))", "-0$1");

                    if (!opcionesHash.containsKey(TipoAutocompleteView.FECHA)){
                        opcionesHash.put(TipoAutocompleteView.FECHA, fechaConFormato.toString());
                        textCalendario.setText(fechaConFormato.toString());
                        activarVista(textInputLayouts.get(0));
                        return;
                    }

                    if(!opcionesHash.get(TipoAutocompleteView.FECHA).equals(fechaConFormato.toString())){
                        desactivarVistas(textInputLayouts, 1, true);
                        opcionesHash.remove(TipoAutocompleteView.ESPECIALIDAD);
                        opcionesHash.put(TipoAutocompleteView.FECHA, fechaConFormato.toString());
                        textCalendario.setText(fechaConFormato.toString());
                    }
                });
            }
        });

        autoCompleteEspecialidades.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!opcionesHash.containsKey(TipoAutocompleteView.ESPECIALIDAD)){
                    activarVista(textInputLayouts.get(1));
                    formularioAgendar.buscarMedico(LocalDate.parse(opcionesHash.get(TipoAutocompleteView.FECHA)), position+1, autoCompleteMedico);
                    opcionesHash.put(TipoAutocompleteView.ESPECIALIDAD, String.valueOf(position+1));
                    opcionesHash.remove(TipoAutocompleteView.MEDICO);
                    return;
                }

                if(!opcionesHash.get(TipoAutocompleteView.ESPECIALIDAD).equals(String.valueOf(position+1))){
                    formularioAgendar.buscarMedico(LocalDate.parse(opcionesHash.get(TipoAutocompleteView.FECHA)), position+1, autoCompleteMedico);
                    opcionesHash.put(TipoAutocompleteView.ESPECIALIDAD, String.valueOf(position+1));
                    opcionesHash.remove(TipoAutocompleteView.MEDICO);
                    desactivarVistas(textInputLayouts, 2, true);
                }

            }

        });

        autoCompleteMedico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!opcionesHash.containsKey(TipoAutocompleteView.MEDICO)){
                    activarVista(textInputLayouts.get(2));
                    opcionesHash.put(TipoAutocompleteView.MEDICO, dataFormularioAgendar.getMedicos().get(position).getNumerodocumento());
                    opcionesHash.remove(TipoAutocompleteView.HORA);
                    formularioAgendar.buscarFranjaHoraria(dataFormularioAgendar.getMedicos().get(position).getNumerodocumento(), autoCompleteFranjaHoraria);
                    return;
                }

                if(!opcionesHash.get(TipoAutocompleteView.MEDICO).equals(dataFormularioAgendar.getMedicos().get(position).getNumerodocumento())){
                    opcionesHash.put(TipoAutocompleteView.MEDICO, dataFormularioAgendar.getMedicos().get(position).getNumerodocumento());
                    formularioAgendar.buscarFranjaHoraria(dataFormularioAgendar.getMedicos().get(position).getNumerodocumento(), autoCompleteFranjaHoraria);
                    opcionesHash.remove(TipoAutocompleteView.HORA);
                    desactivarVistas(textInputLayouts, 3, true);
                }
            }
        });

        autoCompleteFranjaHoraria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!opcionesHash.containsKey(TipoAutocompleteView.HORA)){
                    activarVista(textInputLayouts.get(3));
                    opcionesHash.put(TipoAutocompleteView.HORA, dataFormularioAgendar.getFranjaHorarias().get(position).getId().toString());
                    opcionesHash.remove(TipoAutocompleteView.MODALIDAD);
                    return;
                }

                if(!opcionesHash.get(TipoAutocompleteView.HORA).equals(dataFormularioAgendar.getFranjaHorarias().get(position).getId().toString())){
                    opcionesHash.put(TipoAutocompleteView.HORA, dataFormularioAgendar.getFranjaHorarias().get(position).getId().toString());
                    opcionesHash.remove(TipoAutocompleteView.MODALIDAD);
                    desactivarVistas(textInputLayouts, 4, true);
                }
            }
        });

        autoCompleteModalidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                opcionesHash.put(TipoAutocompleteView.MODALIDAD, String.valueOf(position+1));
                Toast.makeText(getBaseContext(), String.valueOf(position+1),Toast.LENGTH_SHORT).show();
                if(position == 0){
                    activarVista(textInputLayouts.get(4));
                    return;
                }
                desactivarVistas(textInputLayouts, 4, false);
                opcionesHash.remove(TipoAutocompleteView.IPS);
            }
        });

        autoCompleteIps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                opcionesHash.put(TipoAutocompleteView.IPS, dataFormularioAgendar.getIpsList().get(position).getId().toString());
            }
        });

        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcionesHash.size() < 5){
                    Toast.makeText(getBaseContext(), "No estan llenados todos los campos",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(opcionesHash.get(TipoAutocompleteView.MODALIDAD).equals("1")
                        && !opcionesHash.containsKey(TipoAutocompleteView.IPS)){
                    Toast.makeText(getBaseContext(), "No estan llenados todos los campos",Toast.LENGTH_SHORT).show();
                    return;
                }

                Cita cita = obetenerCita();

                if(cita.equals(citaReagendar)){
                    Toast.makeText(getBaseContext(), "No has cambiado nada en la cita", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(cita.getIdCita().equals(citaReagendar.getIdCita())){
                    formularioAgendar.reagendarCita(cita);
                    return;
                }

                formularioAgendar.agendarCita(cita);
            }
        });
    }

    public Cita obetenerCita(){
        String idCita = UUID.randomUUID().toString().substring(0, 9);
        if(citaReagendar != null){
            idCita = citaReagendar.getIdCita();
        }

        if(opcionesHash.get(TipoAutocompleteView.MODALIDAD).equals("1")){
            Cita cita = new Cita(idCita,
                    opcionesHash.get(TipoAutocompleteView.FECHA),
                    Integer.parseInt(opcionesHash.get(TipoAutocompleteView.HORA)),
                    Integer.parseInt(opcionesHash.get(TipoAutocompleteView.ESPECIALIDAD)),
                    Integer.parseInt(opcionesHash.get(TipoAutocompleteView.MODALIDAD)),
                    idDocumento,
                    opcionesHash.get(TipoAutocompleteView.MEDICO),
                    Integer.parseInt(opcionesHash.get(TipoAutocompleteView.IPS)));
            return cita;
        }

        Cita cita = new Cita(idCita,
                opcionesHash.get(TipoAutocompleteView.FECHA),
                Integer.parseInt(opcionesHash.get(TipoAutocompleteView.HORA)),
                Integer.parseInt(opcionesHash.get(TipoAutocompleteView.ESPECIALIDAD)),
                Integer.parseInt(opcionesHash.get(TipoAutocompleteView.MODALIDAD)),
                idDocumento,
                opcionesHash.get(TipoAutocompleteView.MEDICO),
                dataFormularioAgendar.getIpsList().size());

        return cita;
    }

    public void desactivarVistas(List<TextInputLayout> textInputLayouts, int index, boolean cleaInputAnterior){
        for (int i = index;i<textInputLayouts.size();i++){
            textInputLayouts.get(i).setVisibility(View.GONE);
            if (cleaInputAnterior){
                textInputLayouts.get(i-1).getEditText().setText("");
                continue;
            }
            textInputLayouts.get(i).getEditText().setText("");
        }
    }

    public void activarVista(TextInputLayout textInputLayout){
        textInputLayout.setVisibility(View.VISIBLE);
    }

}