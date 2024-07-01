package com.example.mediconnect.Activity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mediconnect.ClienteApi.Api.ApiMedicamento;
import com.example.mediconnect.ClienteApi.Config.ClienteRetrofit;
import com.example.mediconnect.Modelos.Medicamento;
import com.example.mediconnect.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerarFormulaMedica extends AppCompatActivity {
    Button btnGenerarFormula;
    String txtTituloFM = "Fórmula médica";
    String txtMedicamentoFM = "";
    String txtCantidadFM = "";
    String txtViaAdministracionFM = "";
    String txtFrecuencia = "";
    String txtObservaciones = "";

    String txtTituloApp = "MediConnect";

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generar_formula_medica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Validamos los permisos al iniciar la actividad
        if (!checkPermission()) {
            requestPermissions();
        }

        ApiMedicamento apiMedicamento = ClienteRetrofit.getClient().create(ApiMedicamento.class);
        Call<List<Medicamento>> callMedicamento = apiMedicamento.cargarMedicamentos();

        callMedicamento.enqueue(new Callback<List<Medicamento>>() {
            @Override
            public void onResponse(Call<List<Medicamento>> call, Response<List<Medicamento>> response) {
                if (response.isSuccessful()) {
                    List<Medicamento> medicamentos = response.body();
                    List<String> nombresMedicamentos = new ArrayList<>();
                    for (Medicamento medicamento : medicamentos) {
                        nombresMedicamentos.add(medicamento.getNombreMedicamento());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(GenerarFormulaMedica.this, R.layout.spinner_item_activity_formula_medica, nombresMedicamentos);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinnerMedicamentos = findViewById(R.id.spinnerMedicamentos);
                    spinnerMedicamentos.setAdapter(adapter);

                    spinnerMedicamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Obtener el valor seleccionado
                            txtMedicamentoFM = parent.getItemAtPosition(position).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    // Maneja la respuesta no exitosa aquí
                }
            }

            @Override
            public void onFailure(Call<List<Medicamento>> call, Throwable t) {
                // Maneja el error de la llamada aquí
            }
        });

        //Generación del pdf
        btnGenerarFormula = findViewById(R.id.btnGenerarFM);

        btnGenerarFormula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editCantidadFM = findViewById(R.id.editTextCantidad);
                EditText editViaAdministracionFM = findViewById(R.id.editVia);
                EditText editFrecuencia = findViewById(R.id.editTextFrecuencia);
                EditText editObservaciones = findViewById(R.id.editTextObservaciones);

                txtCantidadFM = editCantidadFM.getText().toString();
                txtViaAdministracionFM = editViaAdministracionFM.getText().toString();
                txtFrecuencia = editFrecuencia.getText().toString();
                txtObservaciones = editObservaciones.getText().toString();

                Log.d("txtMedicamentoFM", txtMedicamentoFM);
                Log.d("txtCantidadFM", txtCantidadFM);
                Log.d("txtViaAdministracionFM", txtViaAdministracionFM);
                Log.d("txtFrecuencia", txtFrecuencia);
                Log.d("txtObservaciones", txtObservaciones);

                generarPDFFM();
            }
        });
    }

    //Métodos para los permisos para crear el pdf
    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, procede con la generación del PDF
                generarPDFFM();
            } else {
                // Permiso denegado, muestra un mensaje al usuario o solicita los permisos nuevamente
                Toast.makeText(this, "Permiso de almacenamiento denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void generarPDFFM() {
        if (checkPermission()) {
            PdfDocument pdfDocument = new PdfDocument();
            Paint paint = new Paint();
            TextPaint tituloApp = new TextPaint();
            TextPaint tituloFM = new TextPaint();
            TextPaint medicamentoFM = new TextPaint();
            TextPaint CantidadFM = new TextPaint();
            TextPaint viaAdministracionFM = new TextPaint();
            TextPaint frecuencia = new TextPaint();
            TextPaint observaciones = new TextPaint();

            Bitmap bitmap, bitmapEscala;

            PdfDocument.PageInfo paginaInfo = new PdfDocument.PageInfo.Builder(816, 1054, 1).create();
            PdfDocument.Page pagina1 = pdfDocument.startPage(paginaInfo);

            Canvas canvas = pagina1.getCanvas();

            //Para pintar la imagen
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_redondeado);
            bitmapEscala = Bitmap.createScaledBitmap(bitmap, 80, 80, false);

            canvas.drawBitmap(bitmapEscala, 368, 20, paint);

            //Para pintar el titulo de la app
            tituloApp.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tituloApp.setTextSize(20);

            canvas.drawText(txtTituloApp, 10, 150, tituloApp);

            //Para pintar titulo fórmula médica
            tituloFM.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            tituloFM.setTextSize(18);

            canvas.drawText(txtTituloFM, 10, 180, tituloFM);

            //Para pintar texto del medicamento
            medicamentoFM.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            medicamentoFM.setTextSize(14);

            canvas.drawText(txtMedicamentoFM, 10, 210, medicamentoFM);

            //Para pintar la cantidad
            CantidadFM.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            CantidadFM.setTextSize(14);

            canvas.drawText(txtCantidadFM, 80, 210, CantidadFM);

            //Para pintar la via de administracion
            viaAdministracionFM.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            viaAdministracionFM.setTextSize(14);

            canvas.drawText(txtViaAdministracionFM, 110, 210, viaAdministracionFM);

            //Para pintar la frecuencia
            frecuencia.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            frecuencia.setTextSize(14);

            canvas.drawText(txtFrecuencia, 110, 230, frecuencia);

            //Para pintar las observaciones
            observaciones.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            observaciones.setTextSize(14);

            canvas.drawText(txtObservaciones, 10, 250, observaciones);

            pdfDocument.finishPage(pagina1);

            File file = new File(Environment.getExternalStorageDirectory(), "Archivo.pdf");
            Log.d("GenerarFormulaMedica", "Intentando escribir en archivo PDF en la ruta: " + file.getAbsolutePath());
            try {
                pdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "Se creó el PDF correctamente", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar el PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            pdfDocument.close();
        } else {
            // Si los permisos no están concedidos, solicítalos nuevamente o maneja la situación
            Toast.makeText(this, "Permisos no concedidos", Toast.LENGTH_LONG).show();
            requestPermissions();
        }
    }
}
