package com.example.gimnasio.logica;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio.activities.ClientesActivity;
import com.example.gimnasio.activities.EstablecimientosActivity;
import com.example.gimnasio.activities.MainActivity;
import com.example.gimnasio.activities.MenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class LogicaLoginYLogout {

    private Activity activity;

    public LogicaLoginYLogout(Activity activity) {
        this.activity = activity;
    }






//método para el login de la app
    public void login(String nick, String password) {

        String url = "https://quintobackendgym-production.up.railway.app/login";

        // Crear el objeto LoginDTO para enviar los datos
        JSONObject loginData = new JSONObject();
        String contraseñaSegura=CifradoContraseña.hashPassword(password);
        try {
            loginData.put("nick", nick);
            loginData.put("password", contraseñaSegura);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear la solicitud StringRequest con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Login Response", response);

                if (response.equals("Login exitoso")) {

                    Toast.makeText(activity, "Login exitoso", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(activity, MenuActivity.class);
                    intent.putExtra("usuarioNick", nick);
                    boolean cambiarFechaLogin=true;
                    intent.putExtra("cambiar fecha de login",cambiarFechaLogin);
                    activity.startActivity(intent);
                    activity.finish();

                } else if (response.equals("Credenciales incorrectas")) {

                    Toast.makeText(activity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    // Si el código de estado es 401, las credenciales son incorrectas
                    Toast.makeText(activity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                } else {
                    // Si hay otros errores (por ejemplo, problemas de red)
                    Log.e("Login Error", error.toString());
                    Toast.makeText(activity, "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                // Convierte el objeto JSONObject a una cadena en UTF-8 para el cuerpo de la solicitud
                return loginData.toString().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                // Especificamos que el cuerpo de la solicitud es de tipo JSON
                return "application/json";
            }
        };

        // Agregar la solicitud a la cola de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }



    //método para salir de la app
    public void salirDeLaApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("¿Estás seguro que deseas salir de la aplicación?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        activity.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Toast.makeText(activity, "No se ha cerrado la app", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }



    //método para cerrar sesión
    public void cerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("¿Estás seguro que deseas cerrar sesión?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        activity.startActivity(intent);
                        activity.finish();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Toast.makeText(activity, "No se ha cerrado sesión", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
