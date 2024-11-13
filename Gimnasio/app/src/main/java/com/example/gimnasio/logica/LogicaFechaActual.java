package com.example.gimnasio.logica;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LogicaFechaActual {

    private Activity activity;

    public LogicaFechaActual(Activity activity) {
        this.activity = activity;
    }

    public String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        return dateFormat.format(new Date());
    }
    /*
    public void actualizarFechaUltimoLogin(String nickUser) {
        String url = "https://sextobackendgym-production.up.railway.app/entrenadores/nick/" + nickUser;
        String fechaActual = obtenerFechaActual();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("fechaUltimoLogin", fechaActual);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear la solicitud PATCH
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH, url, jsonBody,
                response -> {
                    // Manejo de la respuesta
                    Toast.makeText(activity, "Fecha de último login actualizada", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Manejo del error
                    Toast.makeText(activity, "Error al actualizar fecha de último login", Toast.LENGTH_SHORT).show();
                }
        );

    }

     */

}
