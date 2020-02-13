package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.appcomida.Rest.ConsumoRest;

public class MainActivity extends AppCompatActivity {

    //defining view objects
    EditText TextUsuario, TextPassword;
    Button btnRegistrar, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextUsuario = findViewById(R.id.txt_usuario);
        TextPassword = findViewById(R.id.txt_password);
        btnRegistrar = findViewById(R.id.btn_registrar);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumoRest consumo = new ConsumoRest();
                Integer Codigousuario = consumo.getDataUsuarios(TextUsuario.getText().toString(), TextPassword.getText().toString());
                if(Codigousuario>0){
                    Intent abrir_reg = new Intent(MainActivity.this, Menu.class );
                    startActivity(abrir_reg);
                } else {
                    Toast.makeText(getApplicationContext(),"Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }
    private void registrarUsuario() {

        Intent abrir_reg = new Intent(MainActivity.this, Registro.class );
        startActivity(abrir_reg);
    }



}
