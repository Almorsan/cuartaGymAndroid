package com.example.gimnasio.logica;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio.Modelos.Entrenador;
import com.example.gimnasio.R;
import com.example.gimnasio.Interfaces.VolleyCallback;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LogicaMenuLateral {

    private Activity activity;
    private String nickUser;

    private Entrenador entrenador;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public LogicaMenuLateral( Activity activity, String nickUser) {
        this.activity = activity;
        this.nickUser=nickUser;
        this.entrenador=new Entrenador();
        conseguirInfoEntrenador();


    }





    //método para buscar el usuario con el que "pintaremos" el menú en la base de datos
    private void encontrarUsuario(String nick, final VolleyCallback callback) {
        String url = "https://quintobackendgym-production.up.railway.app/entrenadores/api/" + nick;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parseamos la respuesta JSON
                            JSONObject usuario = new JSONObject(response);

                            // Llamamos al método onSuccess del callback con el JSON recibido
                            callback.onSuccess(usuario);

                        } catch (JSONException e) {
                            // En caso de error de parseo JSON, llamamos a onError del callback
                            callback.onError("Error de parseo JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamamos a onError del callback con el mensaje de error
                        callback.onError(error.getMessage() != null ? error.getMessage() : "Error desconocido");
                    }
                });

        // Añadimos la solicitud a la cola de Volley
        Volley.newRequestQueue(activity).add(postRequest);
    }

    //método para conseguir información de dicho usuario

    public void conseguirInfoEntrenador(){


        encontrarUsuario(nickUser, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject usuario) {
                try {


                    entrenador.setNombreUser(usuario.getString("nombre"));
                    entrenador.setNickUser(usuario.getString("nick"));
                    JSONObject creador = usuario.getJSONObject("creador");
                    entrenador.setNombreCreador(creador.getString("nombre"));
                    entrenador.setNickCreador(creador.getString("nick"));
                    JSONObject establecimiento=usuario.getJSONObject("establecimiento");
                    entrenador.setDireccionEstablecimiento(establecimiento.getString("direccion"));
                    entrenador.setFechaUltimaConexionUser(usuario.getString("fechaUltimoLogin"));
                    entrenador.setFechaNacimientoUser(usuario.getString("fechaNacimiento"));
                    entrenador.setFechaAltaUser(usuario.getString("fechaAlta"));
                    JSONArray entrenadoresCreados = usuario.getJSONArray("entrenadoresCreados");
                    JSONArray clientes =usuario.getJSONArray("clientes");
                    JSONArray salas=usuario.getJSONArray("salas");




                    // Acceder al NavigationView y al menú
                    NavigationView navigationView = activity.findViewById(R.id.nav_view);
                    Menu menu = navigationView.getMenu();

                    // Actualizar el título de los items con el nombre y el nick
                    MenuItem nameItem = menu.findItem(R.id.nav_user_name);
                    MenuItem nickItem = menu.findItem(R.id.nav_user_nick);

                    nameItem.setTitle("Nombre: " + entrenador.getNombreUser());
                    nickItem.setTitle("Nick: " + nickUser);


                    MenuItem itemEntrenadores = menu.findItem(R.id.nav_trainers);
                    SubMenu subMenu = itemEntrenadores.getSubMenu();
                    //TextView title1 = (TextView) itemEntrenadores.getActionView();
                    //title1.setTypeface(null, Typeface.BOLD);

                    subMenu.clear();

                    for (int i = 0; i < entrenadoresCreados.length(); i++) {
                        JSONObject entrenador = entrenadoresCreados.getJSONObject(i);
                        String nombreEntrenador = entrenador.getString("nombre");

                        // Crear un nuevo MenuItem para cada entrenador
                        subMenu.add(Menu.NONE, i, Menu.NONE, nombreEntrenador);
                    }

                    MenuItem itemClientes=menu.findItem(R.id.nav_clientes);
                    SubMenu subMenu2=itemClientes.getSubMenu();
                    //TextView title2 = (TextView) itemClientes.getActionView();
                    // title2.setTypeface(null, Typeface.BOLD);

                    subMenu2.clear();

                    for (int i = 0; i < clientes.length(); i++) {
                        JSONObject cliente = clientes.getJSONObject(i);
                        String nombreCliente = cliente.getString("nombre");

                        // Crear un nuevo MenuItem para cada cliente
                        subMenu2.add(Menu.NONE, i, Menu.NONE, nombreCliente);
                    }


                    MenuItem itemSalas=menu.findItem(R.id.nav_salas);
                    SubMenu subMenu3=itemSalas.getSubMenu();
                    //TextView title3 = (TextView) itemSalas.getActionView();
                    //title3.setTypeface(null, Typeface.BOLD);

                    subMenu3.clear();

                    for (int i = 0; i < salas.length(); i++) {
                        JSONObject sala = salas.getJSONObject(i);
                        String nombreSala = sala.getString("nombre");

                        // Crear un nuevo MenuItem para cada sala
                        subMenu3.add(Menu.NONE, i, Menu.NONE, nombreSala);
                    }


                    Log.d("Usuario", "Nombre: " + entrenador.getNombreUser() + ", Nick: " + entrenador.getNickUser());
                    Log.d("Creador","Creador: "+creador.toString());
                    Log.d("Entrenadores creados","Entrenadores creados: "+entrenadoresCreados.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("Error en encontrarUsuario:", error);
            }
        });

    }

    //Método para "pintar" el menu con la información del usuario con el que hacemos el login

    public void mostrarDatosMenuLateral(Toolbar toolbar, DrawerLayout drawerLayout, NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // Define las acciones para cada item
                if (id == R.id.nav_home) {
                    mostrarInfoPropia();
                } else if (id == R.id.nav_settings) {
                    // Acción para configuraciones
                } else if (id == R.id.nav_creator_info) {
                    mostrarInfoCreador();
                } else if (id == R.id.nav_trainers) {
                    Log.d("Entrenador seleccionado", "Entrenador: " + item.getTitle());
                } else if (id == R.id.nav_clientes) {
                    Log.d("Cliente seleccionado", "Cliente: " + item.getTitle());
                } else if (id == R.id.nav_salas) {
                    Log.d("Sala seleccionada", "Sala: " + item.getTitle());
                } else if (id == R.id.nav_establecimiento) {
                    mostrarInfoEstablecimiento();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    //método para mostrar info del creador
    public void mostrarInfoCreador() {


        // Crear y mostrar el AlertDialog
        new AlertDialog.Builder(activity)
                .setTitle("Información del Entrenador Creador")
                .setMessage("Nombre: " + entrenador.getNombreCreador() + "\nNick: " + entrenador.getNickCreador())
                .setPositiveButton("Cerrar", null)
                .show();
    }

    public void mostrarInfoPropia() {



        SimpleDateFormat originalFormatWithTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        SimpleDateFormat originalFormatDateOnly = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        SimpleDateFormat dateFormatsinHora = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {
            // Determina y formatea la fecha de última conexión
            Date fechaUltimaConexionDate = entrenador.getFechaUltimaConexionUser().contains("T") ?
                    originalFormatWithTime.parse(entrenador.getFechaUltimaConexionUser()) :
                    originalFormatDateOnly.parse(entrenador.getFechaUltimaConexionUser());
            String fechaUltimaConexion = dateFormat.format(fechaUltimaConexionDate);

            // Determina y formatea la fecha de nacimiento
            Date fechaNacimientoDate = originalFormatDateOnly.parse(entrenador.getFechaNacimientoUser());
            String fechaNacimiento = dateFormatsinHora.format(fechaNacimientoDate);

            // Determina y formatea la fecha de alta
            Date fechaAltaDate = entrenador.getFechaAltaUser().contains("T") ?
                    originalFormatWithTime.parse(entrenador.getFechaAltaUser()) :
                    originalFormatDateOnly.parse(entrenador.getFechaAltaUser());
            String fechaAlta = dateFormat.format(fechaAltaDate);

            new AlertDialog.Builder(activity)
                    .setTitle("Mis datos")
                    .setMessage("Nombre: " + entrenador.getNombreUser() + "\nNick: " + entrenador.getNickUser()
                            + "\nFecha última conexión: " + fechaUltimaConexion
                            + "\nFecha nacimiento: " + fechaNacimiento
                            + "\nFecha alta: " + fechaAlta)
                    .setPositiveButton("Cerrar", null)
                    .show();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Error", "Error al parsear las fechas", e);
        }
    }


    public void mostrarInfoEstablecimiento() {



        new AlertDialog.Builder(activity)
                .setTitle("Establecimiento asignado")
                .setMessage("Dirección: " + entrenador.getDireccionEstablecimiento())
                .setPositiveButton("Cerrar", null)
                .show();
    }






}
