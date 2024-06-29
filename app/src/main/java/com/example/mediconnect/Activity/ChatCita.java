package com.example.mediconnect.Activity;


import static android.util.Log.i;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediconnect.Adapter.MensajeAdapter;
import com.example.mediconnect.ClienteApi.Api.ApiChat;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.Cita;
import com.example.mediconnect.Modelos.Mensaje;
import com.example.mediconnect.R;
import com.example.mediconnect.Utilidades.CargaDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;


public class ChatCita extends AppCompatActivity {
    List<Mensaje> mensajesChat = new ArrayList<>();;
    MensajeAdapter mensajeAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    int pagina = 0;
    boolean contieneMensajes = false;
    boolean primerRender;
    Bundle bundle;
    String remitente;
    String destinatario;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_cita);

        Gson mGson = new GsonBuilder().create();

        bundle = getIntent().getExtras();
        Cita cita = (Cita) bundle.getSerializable("cita");
        String idCiudadano =  bundle.getString("idCiudadano");
        String idChat = bundle.getString("idChat");

        recyclerView = findViewById(R.id.idRecyclerChat);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mensajeAdapter = new MensajeAdapter(mensajesChat, idCiudadano);
        recyclerView.setAdapter(mensajeAdapter);

        String destino = "/chat/"+idChat;
        Toast.makeText(this, destino, Toast.LENGTH_SHORT).show();

        obtenerMensajes(idChat, pagina);


        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
                        & mensajesChat.size() >= 20 & contieneMensajes){
                    pagina += 1;
                    obtenerMensajes(idChat, pagina);
                }

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == mensajesChat.size()-1
                        & mensajesChat.size() >= 20 & !primerRender ){
                    contieneMensajes = true;
                    primerRender = true;
                }

            }
        });

        StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.1.16:8080/chats");
        stompClient.connect();


        TextView textMensajeContenido = findViewById(R.id.idMensajeChat);
        ImageView btnEnviarMensaje = findViewById(R.id.idBtnEnviar);

        Mensaje mensaje = new Mensaje();
        if(cita.getIdPaciente().equals(idCiudadano)){
            remitente = cita.getIdPaciente();
            destinatario = cita.getIdMedico();
        }else{
            remitente = cita.getIdMedico();
            destinatario = cita.getIdPaciente();
        }

        mensaje.setIdChat(idChat);
        mensaje.setIdRemitente(remitente);
        mensaje.setIdDestinatario(destinatario);

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje.setContenido(textMensajeContenido.getText().toString());
                mensaje.setFechaMensaje(LocalDate.now(ZoneId.of("America/Bogota")).toString()
                + "T" + LocalTime.now(ZoneId.of("America/Bogota")));
                mensaje.setIdMensaje(UUID.randomUUID().toString().substring(0,9));
                textMensajeContenido.setText("");
                stompClient.send("/chat/"+idChat, mGson.toJson(mensaje)).subscribe();
            }
        });


        stompClient.topic("/chat/"+idChat).subscribe((message)->{
            mensajesChat.add(mGson.fromJson(message.getPayload(), Mensaje.class));
            runOnUiThread(()-> mensajeAdapter.notifyItemInserted(mensajesChat.size()-1));
            recyclerView.smoothScrollToPosition(mensajesChat.size()-1);
        });

    }



    public void obtenerMensajes(String idChat, int pagina){
        AlertDialog dialog = new CargaDialog(this, getWindow()).crearDialogCarga();
        dialog.show();
        Call<List<Mensaje>> callMensajes = ClienteRetrofit.getClient().create(ApiChat.class).obtenerMensajesConIdChat(idChat, pagina);
        callMensajes.enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if(response.body() != null){
                    List<Mensaje> mensajesList = response.body();
                    Collections.reverse(mensajesList);
                    mensajesChat.addAll(0, mensajesList);
                    mensajeAdapter.notifyDataSetChanged();
                    if(!contieneMensajes){
                        recyclerView.smoothScrollToPosition(mensajesChat.size()-1);
                    }
                }

                if(response.body() == null && contieneMensajes){
                    contieneMensajes = false;
                    Toast.makeText(getBaseContext(), "No tienes mas mensajes", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable t) {

            }
        });
    }
}