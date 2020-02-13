package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.appcomida.Modelo.clsMenu;
import com.alex.appcomida.Rest.AdminSqlLiteOpen;
import com.alex.appcomida.Rest.ConsumoRest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalleMenu extends AppCompatActivity {

    ImageView img;
    TextView txtNombrePlato;
    TextView txtDetallePlato;
    TextView txtCantidad1;
    TextView txtPrecio;
    Button btnAumentar, btnDisminuir;

    String  Menucodigo,Cantidad,Plato,Precio;

    FloatingActionButton carrito;
    private int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_menu);

        txtNombrePlato = findViewById(R.id.txtNombrePlato);
        txtDetallePlato = findViewById(R.id.txtDescripcionPlato);
        txtCantidad1= findViewById(R.id.txtCantidad1);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnAumentar = findViewById(R.id.btnAumentar);
        btnDisminuir = findViewById(R.id.btnDisminuir);
        carrito = findViewById(R.id.FABCarrito);
        img = findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnviarDatos();
                Intent abrir_car = new Intent(DetalleMenu.this, Pedido.class );
                startActivity(abrir_car);
            }
        });

        btnAumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                txtCantidad1.setText(""+contador);
            }
        });

        btnDisminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador<1)
                {
                    contador=1;
                }else
                    {
                        contador --;
                    }

                txtCantidad1.setText(""+contador );
            }
        });
        contador = 1;
        txtCantidad1.setText(""+contador);

        int resid = bundle.getInt("resID");
        img.setImageResource(resid);
        String resIDT = getIntent().getStringExtra("resIDT");
        clsMenu menu = new clsMenu();
        ConsumoRest consumo = new ConsumoRest();
        menu=consumo.getDataMenuDetalle(resIDT);
        txtNombrePlato.setText(menu.getPlato());
        txtDetallePlato.setText(menu.getDescripcion());
        txtPrecio.setText("$"+Double.toString(menu.getPrecio()));

        Menucodigo  = resIDT;
        Cantidad= String.valueOf(contador);
        Plato = menu.getPlato();
        Precio = Double.toString(menu.getPrecio());
        // enviar inf a actividad Pedido
    }

    public void EnviarDatos(){
        AdminSqlLiteOpen admin = new  AdminSqlLiteOpen (this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        BaseDeDatos.delete("pedidos",null,null);
        String codigo = Menucodigo;
        String cantidad = Integer.toString(contador);
        String plato = Plato;
        String pecio = Precio;
            ContentValues registro = new ContentValues();
            registro.put("codigousuario", 1);
            registro.put("codigomenu", codigo);
            registro.put("cantidad", cantidad);
            registro.put("plato", plato);
            registro.put("precio", pecio);
            BaseDeDatos.insert("pedidos", null, registro);
            BaseDeDatos.close();
    }

}