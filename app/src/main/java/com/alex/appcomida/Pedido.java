package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.alex.appcomida.Modelo.clsMenu;
import com.alex.appcomida.Rest.ConsumoRest;
import com.alex.appcomida.Rest.AdminSqlLiteOpen;

import java.util.ArrayList;


public class Pedido extends AppCompatActivity {

    Button btnBack;
    private ListView Lista;
    ArrayAdapter<String>adapter;
    private ArrayList<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        Lista = (ListView) findViewById(R.id.listV);
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
        buscar();
    }
    public void buscar() {
        AdminSqlLiteOpen admin = new AdminSqlLiteOpen(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

            Cursor fila = BaseDeDatos.rawQuery("select codigousuario,codigomenu,cantidad, plato,precio from pedidos", null);

            //Nos aseguramos de que existe al menos un registro
        if (fila.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Double total = Double.valueOf(fila.getString(2)) * Double.valueOf(fila.getString(4));
                names = new ArrayList<String>();
                names.add(""+ fila.getString(2)+"     "+fila.getString(3)+"    $"+total);
            } while(fila.moveToNext());
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
            Lista.setAdapter(adapter1);
        }
    }
}
