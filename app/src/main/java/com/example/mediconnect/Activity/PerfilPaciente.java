package com.example.mediconnect.Activity;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mediconnect.ClienteApi.Api.ApiCiudadano;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.Ciudadano;
import com.example.mediconnect.Modelos.CiudadanoDTO;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.AESEncryption;
import com.example.mediconnect.Utilidades.CargaDialog;
import com.example.mediconnect.databinding.ActivityPerfilPacienteBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilPaciente extends AppCompatActivity {

    Bundle bundle;
    Map<Integer, String> genero = Map.of(1,"Masculino",2,"Femenino" );

    Map<Integer, String> tipoSangre = Map.of(1, "O+", 2, "O-",
            3, "A+", 4, "A-", 5, "B+", 6, "B-",
            7, "AB+", 8, "AB-");

    ActivityPerfilPacienteBinding binding;
    Boolean ocultarContraseña = true;
    ApiCiudadano apiCiudadano;
    Ciudadano ciudadano;
    AlertDialog cargaDialog;

    String idCiudadano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilPacienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        bundle = getIntent().getExtras();
        CiudadanoDTO ciudadanoDTO = (CiudadanoDTO) bundle.getSerializable("ciudadano");
        idCiudadano  = ciudadanoDTO.getNumerodocumento();
        Integer tipo = Integer.parseInt(bundle.getString("tipoPerfil"));
        apiCiudadano = ClienteRetrofit.getClient().create(ApiCiudadano.class);
        Call<Ciudadano> ciudadanoCall = apiCiudadano.buscarCiudadano(idCiudadano);
        this.listenerInputs();

        cargaDialog = new CargaDialog(this, this.getWindow()).crearDialogCarga();
        cargaDialog.show();

        ciudadanoCall.enqueue(new Callback<Ciudadano>() {
            @Override
            public void onResponse(Call<Ciudadano> call, Response<Ciudadano> response) {
                ciudadano = response.body();
                cargaDialog.dismiss();
                if(tipo == 1){
                    dataTipoEstatico(ciudadano);
                    return;
                }

                dataTipoModificable(ciudadano);
            }

            @Override
            public void onFailure(Call<Ciudadano> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Hubo un error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


        binding.ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilPaciente.this, AjustesPaciente.class);
                intent.putExtra("ciudadano", ciudadanoDTO);
                intent.putExtra("tipoPerfil", "0");
                startActivity(intent);
            }
        });

        binding.idBtnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilPaciente.this, InicioPaciente.class);
                intent.putExtra("ciudadano", ciudadanoDTO);
                startActivity(intent);
            }
        });

        binding.idBtnCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilPaciente.this, CitasPaciente.class);
                intent.putExtra("ciudadano", ciudadanoDTO);
                startActivity(intent);
            }
        });
    }

    public void dataTipoEstatico(Ciudadano ciudadano) {
        binding.tvName.setText(ciudadano.getNombres());
        binding.tvSurname.setText(ciudadano.getApellidos());
        binding.etId.setText(ciudadano.getNumerodocumento());
        binding.etYear.setText(ciudadano.getFechanacimiento().toString().split("-")[0]);
        binding.etMonth.setText(ciudadano.getFechanacimiento().toString().split("-")[1]);
        binding.etDay.setText(ciudadano.getFechanacimiento().toString().split("-")[2]);
        binding.tvGender.setText(genero.get(ciudadano.getIdgenero()));
        binding.tvRh.setText(tipoSangre.get(ciudadano.getIdtiposangre()));
        binding.etAddress.setText(ciudadano.getDireccion());
        binding.etEmail.setText(ciudadano.getCorreo());
        binding.etTelefono.setText(ciudadano.getNumerocelular());
        Glide.with(getBaseContext()).load(ciudadano.getUrlimagenperfil()).into(binding.ivProfilePicture);

        String contraseñaDesencriptada = null;
        try {
            contraseñaDesencriptada = AESEncryption.getInstance(getBaseContext()).descifrar(ciudadano.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        binding.etPassword.setText(contraseñaDesencriptada);

        this.habilitarInput(false);
    }

    @SuppressLint("ResourceAsColor")
    public void dataTipoModificable(Ciudadano ciudadano) {
        binding.tvName.setText(ciudadano.getNombres());
        binding.tvSurname.setText(ciudadano.getApellidos());
        binding.etId.setText(ciudadano.getNumerodocumento());
        binding.etYear.setText(ciudadano.getFechanacimiento().toString().split("-")[0]);
        binding.etMonth.setText(ciudadano.getFechanacimiento().toString().split("-")[1]);
        binding.etDay.setText(ciudadano.getFechanacimiento().toString().split("-")[2]);
        binding.etAddress.setText(ciudadano.getDireccion());
        binding.etEmail.setText(ciudadano.getCorreo());
        binding.etTelefono.setText(ciudadano.getNumerocelular());
        binding.tvMasculino.setBackgroundTintMode(ciudadano.getIdgenero() == 1 ? PorterDuff.Mode.DST_OVER : PorterDuff.Mode.MULTIPLY);
        binding.tvFemenino.setBackgroundTintMode(ciudadano.getIdgenero() == 2 ? PorterDuff.Mode.DST_OVER : PorterDuff.Mode.MULTIPLY);
        binding.etListTipoSangre.setText(tipoSangre.get(ciudadano.getIdtiposangre()));

        String contraseñaDesencriptada = null;
        try {
            contraseñaDesencriptada = AESEncryption.getInstance(getBaseContext()).descifrar(ciudadano.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        binding.etPassword.setText(contraseñaDesencriptada);

        String[] tiposSangre = tipoSangre.values().toArray(new String[tipoSangre.size()]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.list_item_drop_down, tiposSangre);
        binding.etListTipoSangre.setAdapter(arrayAdapter);

        binding.labelRh.setVisibility(View.GONE);
        binding.tvRh.setVisibility(View.GONE);
        binding.tvGender.setVisibility(View.GONE);
        binding.ivProfilePicture.setVisibility(View.GONE);
        binding.ivSettings.setVisibility(View.GONE);
        binding.idBtnCitas.setVisibility(View.GONE);
        binding.idBtnInicio.setVisibility(View.GONE);
        binding.idBtnPerfil.setVisibility(View.GONE);
        binding.textBtnCita.setVisibility(View.GONE);
        binding.textBtnInicio.setVisibility(View.GONE);
        binding.textBtnPerfil.setVisibility(View.GONE);
        binding.tvTitle.setText("Cambiando tú información");
        binding.tvMasculino.setVisibility(View.VISIBLE);
        binding.tvFemenino.setVisibility(View.VISIBLE);
        binding.inputListTipoSangre.setVisibility(View.VISIBLE);
        binding.idBtnModificar.setVisibility(View.VISIBLE);

        this.habilitarInput(true);
    }

    public void habilitarInput(boolean enable){
        for(int i= 0; i<binding.getRoot().getChildCount(); i++){
                binding.getRoot().getChildAt(i).setFocusable(enable);
        }

        binding.etDay.setFocusable(enable);
        binding.etMonth.setFocusable(enable);
        binding.etYear.setFocusable(enable);
    }

    public void listenerInputs(){
         binding.tvShowPassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (!ocultarContraseña){
                     binding.tvShowPassword.setChecked(false);
                     binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                 } else {
                     binding.tvShowPassword.setChecked(true);
                     binding.etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                 }

                 ocultarContraseña = !ocultarContraseña;
                 binding.etPassword.setSelection(binding.etPassword.getText().length());
             }
         });

         binding.tvMasculino.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(PorterDuff.Mode.DST_OVER == binding.tvMasculino.getBackgroundTintMode()){
                     binding.tvMasculino.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                 }

                 if(PorterDuff.Mode.MULTIPLY == binding.tvMasculino.getBackgroundTintMode()){
                     binding.tvMasculino.setBackgroundTintMode(PorterDuff.Mode.DST_OVER);
                     binding.tvFemenino.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                 }
             }
         });

        binding.tvFemenino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PorterDuff.Mode.DST_OVER == binding.tvFemenino.getBackgroundTintMode()){
                    binding.tvFemenino.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }

                if(PorterDuff.Mode.MULTIPLY == binding.tvFemenino.getBackgroundTintMode()){
                    binding.tvFemenino.setBackgroundTintMode(PorterDuff.Mode.DST_OVER);
                    binding.tvMasculino.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
                }
            }
        });

        binding.idBtnModificar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                modificarInformación(ciudadano);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void modificarInformación(Ciudadano ciudadano){
        String nombres = binding.tvName.getText().toString().trim();
        String apellidos = binding.tvSurname.getText().toString().trim();
        String numeroDocumento = binding.etId.getText().toString().trim();
        String direccion = binding.etAddress.getText().toString().trim();
        String correo = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String numerocelular = binding.etTelefono.getText().toString().trim();

        String year = binding.etYear.getText().toString().trim();
        String month = binding.etMonth.getText().toString().trim();
        String day = binding.etDay.getText().toString().trim();
        String fechanacimiento = year+"-"+month+"-"+day;

        if(nombres.equals("") | apellidos.equals("") | numeroDocumento.equals("") |
                direccion.equals("") | correo.equals("") | password.equals("") | numerocelular.equals("")
                | year.equals("") | month.equals("") | day.equals("")){
            Toast.makeText(this, "No has llenado todos los datos", Toast.LENGTH_SHORT).show();
            return;
        }


        if(Integer.parseInt(year) < 1800 | Integer.parseInt(year) > LocalDate.now().getYear()){
            Toast.makeText(this, "El formato del año es incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!month.matches("\\d{2}")  | Integer.parseInt(month) < 1 | Integer.parseInt(month) > 12){
            Toast.makeText(this, "El formato del mes es incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!day.matches("\\d{2}") | Integer.parseInt(day) < 1 | Integer.parseInt(day) > 31){
            Toast.makeText(this, "El formato del dia es incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer idgenero = ciudadano.getIdgenero();
        if(binding.tvMasculino.getBackgroundTintMode() == PorterDuff.Mode.DST_OVER){
            idgenero = 1;
        }

        if(binding.tvFemenino.getBackgroundTintMode() == PorterDuff.Mode.DST_OVER){
            idgenero = 2;
        }

        String tipoSangreText = binding.etListTipoSangre.getText().toString();
        ArrayList<String> tipoSangreValores = new ArrayList(tipoSangre.values());
        Integer idtiposangre = -1;
        for(int i = 0; i<tipoSangreValores.size(); i++){
            if(tipoSangreValores.get(i).equals(tipoSangreText)){
                idtiposangre = i+1;
                break;
            }
        }


        Ciudadano nuevoCiudadano = new Ciudadano( numeroDocumento,  nombres,  apellidos,  fechanacimiento,  idgenero,  idtiposangre,  direccion,  numerocelular,  correo,  password,  ciudadano.getUrlimagenperfil());

        if (ciudadano.equals(nuevoCiudadano)){
            Toast.makeText(this, "No has hecho ningun cambio", Toast.LENGTH_SHORT).show();
            return;
        }

        cargaDialog.show();
        Call<Ciudadano> callModificar = apiCiudadano.modificarCiudadano(nuevoCiudadano);
        callModificar.enqueue(new Callback<Ciudadano>() {
            @Override
            public void onResponse(Call<Ciudadano> call, Response<Ciudadano> response) {
                if(response.body() != null){
                    cargaDialog.dismiss();
                    Toast.makeText(getBaseContext(), "Se ha modificado con exito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PerfilPaciente.this, PerfilPaciente.class);
                    intent.putExtra("idDocumento", response.body().getNumerodocumento());
                    intent.putExtra("tipoPerfil", 1);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Ciudadano> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Hay un error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
}