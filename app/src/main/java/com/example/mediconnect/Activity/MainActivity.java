package com.example.mediconnect.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mediconnect.ClienteApi.Api.ApiCiudadano;
import com.example.mediconnect.ClienteApi.Model.Ciudadano;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.ClienteApi.Model.Login;
import com.example.mediconnect.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInciarSesion = findViewById(R.id.btnIniciarSesion);
        AlertDialog dialog = crearDialogCarga();

        btnInciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView correo = findViewById(R.id.textCorreo);
                TextView contraseña = findViewById(R.id.textContraseña);

                if(!Pattern.matches("\\w+@\\w+.com", correo.getText().toString())){
                    Log.e("correo", "No esta bien");
                    Toast.makeText(getBaseContext(), "El correo es incorrecto", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(contraseña.getText().toString().equals("")){
                    Log.e("contraseña", "No esta bien");
                    Toast.makeText(getBaseContext(), "La contraseña no puede estar vacia", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();
                Login login = new Login(correo.getText().toString(), contraseña.getText().toString());
                Log.i("datos login",login.toString());

                ApiCiudadano clienteRetrofit = ClienteRetrofit.getClient().create(ApiCiudadano.class);
                Call<Ciudadano> ciudadanoCall = clienteRetrofit.login(login);

                ciudadanoCall.enqueue(new Callback<Ciudadano>() {
                    @Override
                    public void onResponse(Call<Ciudadano> call, Response<Ciudadano> response) {
                        dialog.dismiss();
                        if(response.isSuccessful() && !response.body().equals(null)){
                            Log.i("respuesta Login", response.body().toString());
                            Toast.makeText(getBaseContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                            if(response.body().getIdRol() == 1){
                                Intent intent = new Intent(MainActivity.this, InicioPaciente.class);
                                intent.putExtra("nombre", response.body().getNombres().split(" ")[0] + " " + response.body().getApellidos().split(" ")[0]);
                                intent.putExtra("idDocumento", response.body().getId());
                                intent.putExtra("imagen", response.body().getUrl());
                                startActivity(intent);
                            }
                            return;
                        }
                        Log.e("respuesta errada", "No existe este usuario");
                        Toast.makeText(getBaseContext(), "El usuario no existe, revisa el correo y la contraseña", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Ciudadano> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Hay un error de conexion", Toast.LENGTH_SHORT).show();
                        Log.e("error login",t.toString());
                    }
                });
            }
        });
    }


    public AlertDialog crearDialogCarga(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_carga, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}