package com.example.gimnasio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gimnasio.Interfaces.VolleyCallback;
import com.example.gimnasio.activities.ClientesActivity;
import com.example.gimnasio.activities.EstablecimientosActivity;
import com.example.gimnasio.activities.MenuActivity;
import com.example.gimnasio.logica.LogicaEntrenadores;
import com.example.gimnasio.logica.LogicaLoginYLogout;
import com.example.gimnasio.logica.LogicaMenuLateral;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EntrenadoresActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword, txtBody;
    Button btnCerrarSesion,btnExit, btnVolver, btnAnadirEntrenador, btnModifcarEntrenador,
    btnConsultarEntrenador,btnBorrarEntrenador,btnListarEntrenador;

    private JSONArray entrenadoresArray;

    String nickUser;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Spinner spinnerEntrenadores;
    private RequestQueue requestQueue;

   // private List<String> entrenadoresNombres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenadores);



        nickUser= getIntent().getStringExtra("usuarioNick");;


        /*if (nickUser != null) {
            Toast.makeText(EntrenadoresActivity.this, "Bienvenido, " + nickUser, Toast.LENGTH_SHORT).show();

        }*/

        LogicaLoginYLogout logicaLoginYLogout=new LogicaLoginYLogout(EntrenadoresActivity.this);

        LogicaMenuLateral logicaMenuLateral=new LogicaMenuLateral(EntrenadoresActivity.this,nickUser);

        LogicaEntrenadores logicaEntrenadores=new LogicaEntrenadores(EntrenadoresActivity.this,nickUser);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Pasa drawerLayout, navigationView y toolbar a la clase auxiliar
        logicaMenuLateral.mostrarDatosMenuLateral(toolbar, drawerLayout, navigationView);


        requestQueue = Volley.newRequestQueue(this);



        //////////////////////

        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnExit=findViewById(R.id.btnSalir);
        btnVolver=findViewById(R.id.btnVolver);
        btnAnadirEntrenador=findViewById(R.id.btnAnadirEntrenador);
        btnBorrarEntrenador=findViewById(R.id.btnBorrarEntrenador);
        btnConsultarEntrenador=findViewById(R.id.btnConsultarEntrnenador);
        btnListarEntrenador=findViewById(R.id.btnListarEntrenadores);
        btnModifcarEntrenador=findViewById(R.id.btnModificarEntrenador);




        //Bototnes
        //////////////////////////////////////////////


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              logicaLoginYLogout.salirDeLaApp();
            }
        });


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               logicaLoginYLogout.salirDeLaApp();
            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(EntrenadoresActivity.this, MenuActivity.class);
                startActivity(intent);

                intent.putExtra("usuarioNick", nickUser);
                startActivity(intent);
                finish();
                ;
            }
        });

        btnAnadirEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logicaEntrenadores.dialogoRegistro();

            }
        });

        btnListarEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logicaEntrenadores.showSpinnerDialog();


            }
        });

        btnBorrarEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                logicaEntrenadores.showDeleteEntrenadorDialog();

            }
        });

        btnConsultarEntrenador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logicaEntrenadores.mostrarConsularEntrenador();
                //showEntrenadorDialog();

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
















}
