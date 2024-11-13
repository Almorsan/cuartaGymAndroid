package com.example.gimnasio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio.Modelos.Actividad;
import com.example.gimnasio.activities.MenuActivity;
import com.example.gimnasio.logica.ActividadesAdapter;
import com.example.gimnasio.logica.LogicaMenuLateral;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetalleEntrenadorActivity extends AppCompatActivity {
    private Spinner spinnerClientes, spinnerEntrenadoresCreados, spinnerSalas, spinnerActividades;
    private String nick;
    private String nickEntrenadorAConsultar, idEntrenadorAConsultar;

    private RequestQueue requestQueue;  // Asegúrate de declarar el requestQueue

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private RecyclerView recyclerView;
    private ActividadesAdapter adapter;
    private List<Actividad> listaActividades;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrenador);

        Button btnVolver;
        btnVolver=findViewById(R.id.btnVolver);

        // Inicializar el requestQueue
        requestQueue = Volley.newRequestQueue(this);  // Esto inicializa el requestQueue

        // Obtener el nombre del entrenador desde el Intent
        nick = getIntent().getStringExtra("usuarioNick");
        nickEntrenadorAConsultar =getIntent().getStringExtra("nickEntrenadorAConsultar");

        Log.d("Estamos en DetalleEntrenadorActivity, nick del entrenador a consultar", nickEntrenadorAConsultar);

        LogicaMenuLateral logicaMenuLateral=new LogicaMenuLateral(DetalleEntrenadorActivity.this,nick);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        logicaMenuLateral = new LogicaMenuLateral(DetalleEntrenadorActivity.this, nick);
        logicaMenuLateral.mostrarDatosMenuLateral(toolbar, drawerLayout, navigationView);

        // Inicializar los Spinners
        spinnerClientes = findViewById(R.id.spinnerClientes);
        spinnerEntrenadoresCreados = findViewById(R.id.spinnerEntrenadoresCreados);
        spinnerSalas = findViewById(R.id.spinnerSalas);
        //spinnerActividades=findViewById(R.id.spinnerActividades);

        recyclerView = findViewById(R.id.recyclerViewActividades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // LayoutManager
        listaActividades = new ArrayList<>();

        cargarActividades(nickEntrenadorAConsultar);

        adapter = new ActividadesAdapter(listaActividades);
        recyclerView.setAdapter(adapter);

        // Llamar a un método para cargar la información del entrenador
        fetchEntrenadorDetails(nickEntrenadorAConsultar);


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleEntrenadorActivity.this, EntrenadoresActivity.class);
                startActivity(intent);

                intent.putExtra("usuarioNick", nick);
                startActivity(intent);
                finish();



            }
        });

    }

    private void fetchEntrenadorDetails(String nick) {
        String url = "https://sextobackendgym-production.up.railway.app/entrenadores/api/" + nick;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject entrenadorDetails = new JSONObject(response);


                            // Cargar los detalles del entrenador
                            String nombre = entrenadorDetails.getString("nombre");
                            String nick = entrenadorDetails.getString("nick");
                            String fechaNacimiento = entrenadorDetails.getString("fechaNacimiento");
                            String fechaAlta = entrenadorDetails.getString("fechaAlta");
                            String fechaUltimoLogin = entrenadorDetails.getString("fechaUltimoLogin");


                            // Entrenador Creador: es un objeto
                            JSONObject entrenadorCreadorObj = entrenadorDetails.getJSONObject("creador");
                            String entrenadorCreadorNombre = entrenadorCreadorObj.getString("nombre");
                            String entrenadorCreadorNick = entrenadorCreadorObj.getString("nick");

                            // Asignar los valores a los TextViews
                            TextView textNombre = findViewById(R.id.textNombre);
                            TextView textNick = findViewById(R.id.textNick);
                            TextView textFechaNacimiento = findViewById(R.id.textFechaNacimiento);
                            TextView textFechaAlta = findViewById(R.id.textFechaAlta);
                            TextView textFechaUltimoLogin = findViewById(R.id.textFechaUltimoLogin);
                            TextView textEntrenadorCreador = findViewById(R.id.textEntrenadorCreador);




                            SimpleDateFormat inputFormatWithTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                            SimpleDateFormat inputFormatNoTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            SimpleDateFormat outputFormatNoTime = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
                            SimpleDateFormat outputFormatWithTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es", "ES"));

                            try {
                                // Fecha de nacimiento (sin hora)
                                Date fechaNacimientoDate = inputFormatNoTime.parse(fechaNacimiento);
                                String fechaNacimientoFormatted = outputFormatNoTime.format(fechaNacimientoDate);

                                // Fecha de alta (con hora)
                                Date fechaAltaDate = inputFormatWithTime.parse(fechaAlta);
                                String fechaAltaFormatted = outputFormatWithTime.format(fechaAltaDate);

                                // Fecha de último login (con hora)
                                Date fechaUltimoLoginDate = inputFormatWithTime.parse(fechaUltimoLogin);
                                String fechaUltimoLoginFormatted = outputFormatWithTime.format(fechaUltimoLoginDate);

                                // Asignar los valores formateados a los TextViews
                                textNombre.setText("Nombre: " + nombre);
                                textNick.setText("Nick: " + nick);
                                textFechaNacimiento.setText("Fecha de nacimiento: " + fechaNacimientoFormatted);
                                textFechaAlta.setText("Fecha de alta: " + fechaAltaFormatted);
                                textFechaUltimoLogin.setText("Fecha de último login: " + fechaUltimoLoginFormatted);

                            } catch (ParseException e) {
                                e.printStackTrace();
                                // Manejo de error
                                textFechaNacimiento.setText("Fecha de nacimiento: formato incorrecto");
                                textFechaAlta.setText("Fecha de alta: formato incorrecto");
                                textFechaUltimoLogin.setText("Fecha de último login: formato incorrecto");
                            }

                          // Configurar el nombre y el nick del entrenador creador en el TextView correspondiente
                            String creadorInfo = "Añadido a la BBDD por " + entrenadorCreadorNombre + " (" + entrenadorCreadorNick+")";
                            textEntrenadorCreador.setText(creadorInfo);


                            // Cargar los clientes
                            JSONArray clientes = entrenadorDetails.getJSONArray("clientes");
                            List<String> clientesList = new ArrayList<>();
                            for (int i = 0; i < clientes.length(); i++) {
                                JSONObject cliente = clientes.getJSONObject(i);
                                clientesList.add(cliente.getString("nombre"));
                            }
                            ArrayAdapter<String> clientesAdapter = new ArrayAdapter<>(DetalleEntrenadorActivity.this,
                                    android.R.layout.simple_spinner_item, clientesList);
                            spinnerClientes.setAdapter(clientesAdapter);



                            // Cargar los entrenadores creados
                            JSONArray entrenadoresCreados = entrenadorDetails.getJSONArray("entrenadoresCreados");
                            List<String> entrenadoresCreadosList = new ArrayList<>();
                            for (int i = 0; i < entrenadoresCreados.length(); i++) {
                                JSONObject entrenador = entrenadoresCreados.getJSONObject(i);
                                entrenadoresCreadosList.add(entrenador.getString("nombre"));
                            }
                            ArrayAdapter<String> entrenadoresCreadosAdapter = new ArrayAdapter<>(DetalleEntrenadorActivity.this,
                                    android.R.layout.simple_spinner_item, entrenadoresCreadosList);
                            spinnerEntrenadoresCreados.setAdapter(entrenadoresCreadosAdapter);

                            // Cargar las salas
                            JSONArray salas = entrenadorDetails.getJSONArray("salas");
                            List<String> salasList = new ArrayList<>();
                            for (int i = 0; i < salas.length(); i++) {
                                JSONObject sala = salas.getJSONObject(i);
                                salasList.add(sala.getString("nombre"));
                            }
                            ArrayAdapter<String> salasAdapter = new ArrayAdapter<>(DetalleEntrenadorActivity.this,
                                    android.R.layout.simple_spinner_item, salasList);
                            spinnerSalas.setAdapter(salasAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DetalleEntrenadorActivity.this, "Error cargando datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetalleEntrenadorActivity.this, "Error en la solicitud", Toast.LENGTH_SHORT).show();
                    }
                });

        // Añadir la solicitud a la cola de Volley
        requestQueue.add(request);  // Aquí usamos requestQueue que hemos inicializado previamente
    }

    private void cargarActividades(String nickEntrenadorAConsultar) {

        String url = "https://sextobackendgym-production.up.railway.app/entrenadores/api/" + nickEntrenadorAConsultar;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesar la respuesta de la API
                        try {
                            listaActividades.clear(); // Limpiar la lista antes de agregar los nuevos elementos


                            JSONArray actividadesArray = response.getJSONArray("actividades");


                            for (int i = 0; i < actividadesArray.length(); i++) {
                                JSONObject actividadObject = actividadesArray.getJSONObject(i);


                                String fechaInicioString = actividadObject.getString("fechaInicio");
                                String fechaFinString = actividadObject.getString("fechaFin");


                                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


                                SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


                                try {
                                    Date fechaInicioDate = originalFormat.parse(fechaInicioString);
                                    Date fechaFinDate = originalFormat.parse(fechaFinString);

                                    // Convertir las fechas a formato "día/mes/año hora:minuto"
                                    String fechaInicioFormateada = targetFormat.format(fechaInicioDate);
                                    String fechaFinFormateada = targetFormat.format(fechaFinDate);

                                    // Ahora puedes usar estas fechas formateadas
                                    //System.out.println("Fecha de Inicio: " + fechaInicioFormateada);
                                    //System.out.println("Fecha de Fin: " + fechaFinFormateada);

                                    // Puedes asignarlas a tus TextViews o a cualquier otra vista que las necesite
                                    // Ejemplo:
                                    Actividad actividad = new Actividad( fechaInicioFormateada, fechaFinFormateada);
                                    listaActividades.add(actividad);

                                } catch (Exception e) {
                                    e.printStackTrace();

                                }

                                // Crear un objeto Actividad y agregarlo a la lista

                            }

                            // Notificar al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores
                        Toast.makeText(DetalleEntrenadorActivity.this, "Error al cargar actividades", Toast.LENGTH_SHORT).show();
                    }
                });

        // Añadir la solicitud a la cola de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);


    }

}

