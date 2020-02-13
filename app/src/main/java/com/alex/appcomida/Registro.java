package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class Registro extends AppCompatActivity {

    EditText nom, ape, usu, cel, con, dir;
    Button guar;
    FloatingActionButton mygps;
    String lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nom = findViewById(R.id.txtNom);
        ape = findViewById(R.id.txtApe);
        usu = findViewById(R.id.txtUsu);
        cel = findViewById(R.id.txtCel);
        con = findViewById(R.id.txtPwd);
        dir = findViewById(R.id.txtDir);
        guar = findViewById(R.id.btnGuardar);

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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }



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
            parametrosPost.put("direccion", dir.getText());
            parametrosPost.put("latitud", lat.toString());
            parametrosPost.put("longitud", lon.toString());


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
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Registro Erroneo", Toast.LENGTH_SHORT).show();
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

    //Obtener ubicacion



    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        Toast.makeText(this,"Localización obtenida", Toast.LENGTH_SHORT).show();
        //lat.toString("Localizacion agregada");
        //lon= ';
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    //dir.setText(DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
/* Aqui empieza la Clase Localizacion */
public class Localizacion implements LocationListener {
    Registro registro;

    public Registro getRegistro() {
        return registro;
    }

    public void setMainActivity(Registro registro) {
        this.registro = registro;
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la deteccion de un cambio de ubicacion

        loc.getLatitude();
        loc.getLongitude();

        String Text = "Mi ubicacion actual es: " + "\n Lat = "
                + loc.getLatitude() + "\n Long = " + loc.getLongitude();
        Double Lati = loc.getLatitude();
        Double Longi = loc.getLongitude();
        lat = Double.toString(Lati) ;
        lon =  Double.toString(Longi) ;
        this.registro.setLocation(loc);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es desactivado
        //lat.toString("GPS Desactivado");
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Este metodo se ejecuta cuando el GPS es activado
        //lat.toString("GPS Activado");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }
}
}
