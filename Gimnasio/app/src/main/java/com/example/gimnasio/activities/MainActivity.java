package com.example.gimnasio.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gimnasio.R;
import com.example.gimnasio.logica.LogicaLoginYLogout;

public  class MainActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword, txtBody;
    Button btnLogin,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtUsuario = findViewById(R.id.etLogin);
        txtPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);
        LogicaLoginYLogout logicaLoginYLogout=new LogicaLoginYLogout(MainActivity.this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                logicaLoginYLogout.login(txtUsuario.getText().toString(),txtPassword.getText().toString());
            }
        });


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                logicaLoginYLogout.salirDeLaApp();
            }
        });


    }







}
