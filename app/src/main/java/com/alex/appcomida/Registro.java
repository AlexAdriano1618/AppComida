package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Registro extends AppCompatActivity {

    EditText nom, ape, usu, cel, con;
    Button guar, miubi;
    ImageButton mygps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nom = findViewById(R.id.txtNom);
        ape = findViewById(R.id.txtApe);
        usu = findViewById(R.id.txtUsu);
        cel = findViewById(R.id.txtCel);
        con = findViewById(R.id.txtPwd);
        guar = findViewById(R.id.btnGuardar);
        miubi = findViewById(R.id.btnMyGPS);
        mygps = findViewById(R.id.imgbtnubi);

        miubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miUbicacion();
            }
        });
        mygps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miUbicacion();
            }
        });
        guar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        post();
                    }
                }).start();

            }
        });

    }
    public void miUbicacion(){
        Intent ubi = new Intent(Registro.this, MapsActivity.class );
        startActivity(ubi);
    }

    public void  post ()
    {
        String sql ="https://appcomida.azurewebsites.net/api/Usuarios";
        URL url = null;
        try {
            // se crea la conexion al api: http://localhost:15009/WEBAPIREST/api/persona
            url = new URL(sql);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //crear el objeto json para enviar por POST
            JSONObject parametrosPost= new JSONObject();
            parametrosPost.put("codigo", 0);
            parametrosPost.put("nombre", nom.getText());
            parametrosPost.put("apellido", ape.getText());
            parametrosPost.put("celular", cel.getText());
            parametrosPost.put("usuar", usu.getText());
            parametrosPost.put("pass", con.getText());


            //DEFINIR PARAMETROS DE CONEXION
            urlConnection.setReadTimeout(15000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");// se puede cambiar por delete ,put ,etc
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);


            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(parametrosPost.toString());

            os.flush();
            os.close();

            int responseCode=urlConnection.getResponseCode();// conexion OK?
            if(responseCode== 201)
            {
                Toast.makeText(this, "Hola soy un Toast", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Hola soy un Toast", Toast.LENGTH_SHORT).show();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
