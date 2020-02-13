package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.alex.appcomida.Modelo.clsMenu;
import com.alex.appcomida.Modelo.clsUsuarios;
import com.alex.appcomida.Rest.ConsumoRest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;


public class Pedido extends AppCompatActivity {
    GoogleMap googleMap;
    MapView mapa;
    Button btnBack;
    private ListView Lista;
    String la, lo;
    ArrayAdapter<String>adapter;

    @Override
    protected void onResume(){
        super.onResume();
        mapa.onResume();

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapa.onDestroy();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapa.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        mapa = findViewById(R.id.mapView);

        mapa.onCreate(savedInstanceState);
        //googleMap = mapa.getMapAsync();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Lista = (ListView) findViewById(R.id.listV);
        Bundle bundle = getIntent().getExtras();
        btnBack= findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regresar_car = new Intent(Pedido.this, Menu.class );
                startActivity(regresar_car);
            }
        });

        // Recibir datos de Actividad Pedido

        String resINF = getIntent().getStringExtra("resINF");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        clsMenu menu = new clsMenu();
        ConsumoRest consumo = new ConsumoRest();
        menu=consumo.getDataMenuDetalle(resINF);


        int resid = bundle.getInt("resID");

        String resIDT = getIntent().getStringExtra("resIDT");
        //txtNombrePlato.setText("-"+resIDT);


      /* clsUsuarios info = new clsUsuarios();
        ConsumoRest coor = new ConsumoRest();
        info=coor.getDataUsuarios(resIDT);


        la = info.getLatitud();
        lo = info.getLongitud();
*/
        // enviar inf a actividad Pedido
    }
}

