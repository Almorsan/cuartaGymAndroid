package com.example.gimnasio.logica;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio.DetalleEntrenadorActivity;
import com.example.gimnasio.EntrenadoresActivity;
import com.example.gimnasio.R;
import com.example.gimnasio.activities.MenuActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicaEntrenadores {
    private Activity activity;
    private String nick;

    private Spinner spinnerEntrenadores;

    private RequestQueue requestQueue;

    private JSONArray entrenadoresArray;

    private String nickEntrenadorAConsultar;

    public LogicaEntrenadores(Activity activity, String nick) {
        this.activity = activity;
        this.nick = nick;
        this.requestQueue= Volley.newRequestQueue(activity);
    }




    public void dialogoRegistro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Añada un entrenador a a la base de datos");


        View dialogView = activity.getLayoutInflater().inflate(R.layout.crear_entrenador, null);
        final EditText editText1 = dialogView.findViewById(R.id.editText1);
        final EditText editText2 = dialogView.findViewById(R.id.editText2);
        final EditText editText3 = dialogView.findViewById(R.id.editText3);
        final EditText editText4 = dialogView.findViewById(R.id.editText4);
        final DatePicker datePicker =dialogView.findViewById(R.id.datePicker);
        final CheckBox checkBox=dialogView.findViewById(R.id.checkbox_terms);
        builder.setView(dialogView);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = editText1.getText().toString();
                String nick = editText2.getText().toString();
                String pass1 = editText3.getText().toString();
                String pass2=editText4.getText().toString();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Los meses empiezan en 0, por lo que sumamos 1
                int year = datePicker.getYear();

               // Formatear el día y el mes para que siempre tengan dos dígitos
                String formattedDay = String.format("%02d", day);
                String formattedMonth = String.format("%02d", month);

                // Construir la cadena de fecha en el formato deseado
                String fechaNacimiento = year + "-" + formattedMonth + "-" + formattedDay;

                boolean esAdmin = checkBox.isChecked();

                boolean registro=verificarRegistro(nombre,nick,pass1,pass2);

                if (registro==true) {
                    registrarUsuario(nombre,nick,pass1,fechaNacimiento,esAdmin);
                }





            }
        });


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public boolean verificarRegistro(String nombre, String nick, String pass, String pass2){
        if(nombre.length()>0&&nick.length()>0&&pass.length()>0&&pass2.length()>0) {

            if(!pass.equals(pass2)) {
                Toast.makeText(activity, "Error, las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }

        } else {
            Toast.makeText(activity, "Error, rellene todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return false;

        }




    }

    public void registrarUsuario(String nombre, String nick, String pass, String fechaNacimiento, boolean esAdmin) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", nombre);
            jsonObject.put("nick", nick);
            String contraseñaSegura=CifradoContraseña.hashPassword(pass);
            jsonObject.put("contraseña", contraseñaSegura);
            jsonObject.put("fechaNacimiento", fechaNacimiento);
            jsonObject.put("esAdmin", esAdmin);
            jsonObject.put("foto", "url_foto.jpg");
            LogicaFechaActual logicaFechaActual=new LogicaFechaActual(activity);
            String fechaActual= logicaFechaActual.obtenerFechaActual();
            jsonObject.put("fechaAlta", fechaActual);
            jsonObject.put("fechaUltimoLogin", "2024-11-08T10:00:00");
            jsonObject.put("establecimientoId", 3);
            jsonObject.put("creadorId", 3);

            Log.e("valor de entrenador",jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "https://quintobackendgym-production.up.railway.app/api/entrenadores";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    Toast.makeText(activity, "Agregado exitosamente a la base de datos", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(activity, "Error, no se ha podido añadir el entrenador", Toast.LENGTH_SHORT).show();
                });

        // Añadir la solicitud a la cola de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(jsonRequest);





    }

    public void showSpinnerDialog() {
        // Infla el layout del diálogo con el Spinner
        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogView = inflater.inflate(R.layout.spinner_dialog, null);

        // Encuentra el Spinner en el layout del diálogo
        spinnerEntrenadores = dialogView.findViewById(R.id.spinnerEntrenadoresDialog);

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Selecciona un Entrenador")
                .setView(dialogView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Obtener el elemento seleccionado
                        String seleccionado = spinnerEntrenadores.getSelectedItem().toString();
                        Toast.makeText(activity, "Seleccionaste: " + seleccionado, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        builder.create().show();

        // Realiza la solicitud POST para obtener la lista de entrenadores
        fetchEntrenadores();
    }

    public void fetchEntrenadores() {
        String url = "https://quintobackendgym-production.up.railway.app/entrenadores/all"; // Cambia esto a tu URL de API


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta y actualizar el Spinner
                        List<String> entrenadoresNombres = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject entrenador = response.getJSONObject(i);
                                String nombre = entrenador.getString("nombre");
                                String nick = entrenador.getString("nick");
                                entrenadoresNombres.add(nombre + " (" + nick + ")");
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                                    android.R.layout.simple_spinner_item, entrenadoresNombres);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerEntrenadores.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Error procesando datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, "Error en la solicitud", Toast.LENGTH_SHORT).show();
                    }
                }
        );


        requestQueue.add(jsonArrayRequest);
    }

    public void showDeleteEntrenadorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Eliminar Entrenador");

        // Crear un Spinner dentro del AlertDialog
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_delete_entrenador, null);
        Spinner spinnerEntrenadores = dialogView.findViewById(R.id.spinnerEntrenadores);


        List<String> entrenadoresNombres = new ArrayList<>();

        fetchEntrenadores(entrenadoresNombres, spinnerEntrenadores);

        builder.setView(dialogView);


        builder.setPositiveButton("Eliminar", null); // Configuraremos el onClickListener después


        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();


        dialog.setOnShowListener(d -> {
            Button deleteButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            deleteButton.setOnClickListener(v -> {
                String selectedEntrenador = (String) spinnerEntrenadores.getSelectedItem();
                if (selectedEntrenador != null) {

                    new AlertDialog.Builder(activity)
                            .setTitle("Confirmar Eliminación")
                            .setMessage("¿Estás seguro de que deseas eliminar a " + selectedEntrenador + "?")
                            .setPositiveButton("Sí", (confirmDialog, confirmWhich) -> {

                                deleteEntrenador(selectedEntrenador);
                                dialog.dismiss();
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    Toast.makeText(activity, "Selecciona un entrenador", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }




    public void deleteEntrenador(String entrenador) {
        String entrenadorId = obtenerIdDeEntrenador(entrenador); // Implementar lógica para obtener ID
        String url = "https://quintobackendgym-production.up.railway.app/entrenadores/" + entrenadorId;

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                response -> Toast.makeText(activity, "Entrenador eliminado correctamente", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(activity, "Error eliminando el entrenador", Toast.LENGTH_SHORT).show());

        requestQueue.add(deleteRequest);
    }

    // Método para cargar la lista de entrenadores en el Spinner
    public void fetchEntrenadores(List<String> entrenadoresNombres, Spinner spinnerEntrenadores) {
        String url = "https://quintobackendgym-production.up.railway.app/entrenadores/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        entrenadoresNombres.clear();
                        entrenadoresArray = response; // Guardamos el JSON de entrenadores

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject entrenador = response.getJSONObject(i);
                            String nombre = entrenador.getString("nombre");
                            String nick = entrenador.getString("nick");
                            entrenadoresNombres.add(nombre + " (" + nick + ")");
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                                android.R.layout.simple_spinner_item, entrenadoresNombres);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEntrenadores.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(activity, "Error obteniendo entrenadores", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonArrayRequest);
    }

// Método para obtener el ID del entrenador seleccionado

    public String obtenerIdDeEntrenador(String entrenadorSeleccionado) {
        if (entrenadoresArray != null) {
            try {
                for (int i = 0; i < entrenadoresArray.length(); i++) {
                    JSONObject entrenador = entrenadoresArray.getJSONObject(i);
                    String nombre = entrenador.getString("nombre");
                    String nick = entrenador.getString("nick");
                    String nombreConNick = nombre + " (" + nick + ")";

                    // Si el entrenador seleccionado coincide, devuelve el ID
                    if (nombreConNick.equals(entrenadorSeleccionado)) {
                        return entrenador.getString("id"); // Devuelve el ID como String
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null; // Si no se encuentra, devuelve null
    }

    /*
    public void mostrarConsularEntrenador() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Consultar Entrenador");

        // Crear un Spinner dentro del AlertDialog
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_delete_entrenador, null);
        Spinner spinnerEntrenadores = dialogView.findViewById(R.id.spinnerEntrenadores);


        List<String> entrenadoresNombres = new ArrayList<>();
        Map<String, String> entrenadoresMap = new HashMap<>();

        //fetchEntrenadoresDos(entrenadoresNombres, spinnerEntrenadores);
        fetchEntrenadoresTres(entrenadoresMap,spinnerEntrenadores);

        builder.setView(dialogView);


        builder.setPositiveButton("Consultar", null); // Configuraremos el onClickListener después


        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();


        dialog.setOnShowListener(d -> {
            Button deleteButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            deleteButton.setOnClickListener(v -> {
                String selectedEntrenador = (String) spinnerEntrenadores.getSelectedItem();
                if (selectedEntrenador != null) {

                    new AlertDialog.Builder(activity)
                            .setTitle("Consultar entrneador")
                            .setMessage("Consultando información de " + selectedEntrenador)
                            .setPositiveButton("Ok", (confirmDialog, confirmWhich) -> {
                                Intent intent = new Intent(activity, DetalleEntrenadorActivity.class);
                                activity.startActivity(intent);

                                intent.putExtra("usuarioNick", nick);
                                intent.putExtra("nickEntrenadorAConsultar",selectedEntrenador);
                                activity.startActivity(intent);
                                activity.finish();


                                dialog.dismiss();
                            })
                            .show();
                } else {
                    Toast.makeText(activity, "Selecciona un entrenador", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }
    */
    public void mostrarConsularEntrenador() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Consultar Entrenador");

        // Crear un Spinner dentro del AlertDialog
        View dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_delete_entrenador, null);
        Spinner spinnerEntrenadores = dialogView.findViewById(R.id.spinnerEntrenadores);

        // Mapa para almacenar los entrenadores
        Map<String, String> entrenadoresMap = new HashMap<>();

        // Obtener los entrenadores y llenar el Spinner
        fetchEntrenadoresTres(entrenadoresMap, spinnerEntrenadores);

        builder.setView(dialogView);

        builder.setPositiveButton("Consultar", null); // Configuraremos el onClickListener después

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.setOnShowListener(d -> {
            Button consultButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            consultButton.setOnClickListener(v -> {
                // Obtener el nombre seleccionado en el Spinner
                String selectedEntrenador = (String) spinnerEntrenadores.getSelectedItem();

                if (selectedEntrenador != null && entrenadoresMap.containsValue(selectedEntrenador)) {
                    // Buscar el nick correspondiente al nombre seleccionado
                    String selectedNick = null;
                    for (Map.Entry<String, String> entry : entrenadoresMap.entrySet()) {
                        if (entry.getValue().equals(selectedEntrenador)) {
                            selectedNick = entry.getKey();
                            break;
                        }
                    }

                    if (selectedNick != null) {
                        nickEntrenadorAConsultar=selectedNick;

                        new AlertDialog.Builder(activity)
                                .setTitle("Consultar Entrenador")
                                .setMessage("Consultando información de " + selectedEntrenador)
                                .setPositiveButton("Ok", (confirmDialog, confirmWhich) -> {
                                    Intent intent = new Intent(activity, DetalleEntrenadorActivity.class);
                                    intent.putExtra("usuarioNick", nick);
                                    intent.putExtra("nickEntrenadorAConsultar", nickEntrenadorAConsultar); // Pasar el nick al intent
                                    Log.d("valor del nick", nickEntrenadorAConsultar);

                                    activity.startActivity(intent);
                                    dialog.dismiss();
                                })
                                .show();
                    } else {
                        Toast.makeText(activity, "Entrenador no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "Selecciona un entrenador", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

/*
    public void fetchEntrenadoresDos(List<String> entrenadoresNombres, Spinner spinnerEntrenadores) {
        String url = "https://quintobackendgym-production.up.railway.app/entrenadores/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        entrenadoresNombres.clear();
                        entrenadoresArray = response; // Guardamos el JSON de entrenadores

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject entrenador = response.getJSONObject(i);
                            String nombre = entrenador.getString("nombre");
                            entrenadoresNombres.add(nombre);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                                android.R.layout.simple_spinner_item, entrenadoresNombres);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEntrenadores.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(activity, "Error obteniendo entrenadores", Toast.LENGTH_SHORT).show());

        requestQueue.add(jsonArrayRequest);
    }

 */

    public void fetchEntrenadoresTres(Map<String, String> entrenadoresMap, Spinner spinnerEntrenadores) {
        String url = "https://quintobackendgym-production.up.railway.app/entrenadores/all";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        entrenadoresMap.clear(); // Limpiar el mapa antes de agregar nuevos entrenadores
                        entrenadoresArray = response; // Guardamos el JSON de entrenadores

                        // Recorremos la respuesta para extraer los entrenadores
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject entrenador = response.getJSONObject(i);
                            String nombre = entrenador.getString("nombre");
                            String nick = entrenador.getString("nick");

                            // Agregar el nombre y el nick al mapa
                            entrenadoresMap.put(nick, nombre);
                        }

                        // Crear un ArrayList con los nombres para mostrarlos en el Spinner
                        List<String> nombresEntrenadores = new ArrayList<>(entrenadoresMap.values());

                        // Crear el adaptador para el Spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                                android.R.layout.simple_spinner_item, nombresEntrenadores);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerEntrenadores.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(activity, "Error obteniendo entrenadores", Toast.LENGTH_SHORT).show());

        // Agregar la solicitud a la cola de Volley
        requestQueue.add(jsonArrayRequest);
    }




}
