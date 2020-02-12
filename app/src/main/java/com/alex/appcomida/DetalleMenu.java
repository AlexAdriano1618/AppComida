package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alex.appcomida.Modelo.clsMenu;
import com.alex.appcomida.Rest.ConsumoRest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalleMenu extends AppCompatActivity {

    ImageView img;
    TextView txtNombrePlato, txtDetallePlato, txtCantidad1;
    Button btnAumentar, btnDisminuir;
    FloatingActionButton carrito;
    private int contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_menu);

        txtNombrePlato = findViewById(R.id.txtNombrePlato);
        txtDetallePlato = findViewById(R.id.txtDescripcionPlato);
        txtCantidad1= findViewById(R.id.txtCantidad1);
        btnAumentar = findViewById(R.id.btnAumentar);
        btnDisminuir = findViewById(R.id.btnDisminuir);
        carrito = findViewById(R.id.FABCarrito);
        img = findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if(contador<=0)
                {
                    contador=0;
                }else
                    {
                        contador --;
                    }

                txtCantidad1.setText(""+contador );
            }
        });


        int resid = bundle.getInt("resID");
        img.setImageResource(resid);


        String resIDT = getIntent().getStringExtra("resIDT");
        //txtNombrePlato.setText("-"+resIDT);

        clsMenu menu = new clsMenu();
        ConsumoRest consumo = new ConsumoRest();
        menu=consumo.getDataMenuDetalle(resIDT);
        txtNombrePlato.setText(menu.getPlato());
        txtDetallePlato.setText(menu.getDescripcion());


    }



}