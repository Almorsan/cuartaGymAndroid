package com.example.gimnasio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio.EntrenadoresActivity;
import com.example.gimnasio.R;
import com.example.gimnasio.logica.LogicaFechaActual;
import com.example.gimnasio.logica.LogicaLoginYLogout;
import com.example.gimnasio.logica.LogicaMenuLateral;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public  class MenuActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword, txtBody;
    Button btnCerrarSesion,btnExit,btnGestionEntrenadores,btnGestionClientes,
            btnGestionActividades,btnGestionSalas,btnGestionEstablecimientos;

    String nickUser;

    boolean cambioFechaLogin=false;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LogicaLoginYLogout logicaLoginYLogout;
    private  LogicaMenuLateral logicaMenuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        nickUser = getIntent().getStringExtra("usuarioNick");
        cambioFechaLogin= getIntent().getBooleanExtra("cambiar fecha de login",false);
        LogicaFechaActual logicaFechaActual=new LogicaFechaActual(MenuActivity.this);

        if (cambioFechaLogin==true) actualizarFechaUltimoLogin(nickUser);
        if (nickUser != null &&cambioFechaLogin==true) Toast.makeText(MenuActivity.this, "Bienvenido, " + nickUser, Toast.LENGTH_SHORT).show();

        logicaLoginYLogout=new LogicaLoginYLogout(MenuActivity.this);


        //////////////////////

        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnExit=findViewById(R.id.btnSalir);
        btnGestionActividades=findViewById(R.id.btnGestionActividades);
        btnGestionEntrenadores=findViewById(R.id.btnGestionUsuarios);
        btnGestionClientes=findViewById((R.id.btnGestionClientes));
        btnGestionSalas=findViewById((R.id.btnGestionSalas));
        btnGestionEstablecimientos=findViewById((R.id.btnGestionEstablecimientos));

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));



        if (cambioFechaLogin==true){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Este código se ejecutará después de 5 segundos (5000 milisegundos)
                     logicaMenuLateral = new LogicaMenuLateral(MenuActivity.this, nickUser);
                     logicaMenuLateral.mostrarDatosMenuLateral(toolbar, drawerLayout, navigationView);
                }
            }, 2000); // 5000 milisegundos = 5 segundos

        } else {
            logicaMenuLateral = new LogicaMenuLateral(MenuActivity.this, nickUser);
            logicaMenuLateral.mostrarDatosMenuLateral(toolbar, drawerLayout, navigationView);

        }




        //////////////////////////////////////////////

        //botones



        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logicaLoginYLogout.salirDeLaApp();
            }
        });


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicaLoginYLogout.cerrarSesion();


            }
        });

        btnGestionEntrenadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuActivity.this, EntrenadoresActivity.class);
                startActivity(intent);

                intent.putExtra("usuarioNick", nickUser);
                startActivity(intent);
                finish();



            }
        });


        btnGestionClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        btnGestionEstablecimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        btnGestionSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        btnGestionActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });






    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }











    public String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        return dateFormat.format(new Date());
    }


    private void actualizarFechaUltimoLogin(String nickUser) {
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
                    Toast.makeText(this, "Fecha de último login actualizada", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Manejo del error
                    Toast.makeText(this, "Error al actualizar fecha de último login", Toast.LENGTH_SHORT).show();
                }
        );

        // Añadir la solicitud a la cola de Volley
        //List<JsonObjectRequest> requestQueue = null;
        //requestQueue.add(request);
        Volley.newRequestQueue(this).add(request);
    }




}