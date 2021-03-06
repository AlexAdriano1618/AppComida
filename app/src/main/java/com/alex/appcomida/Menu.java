package com.alex.appcomida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alex.appcomida.Modelo.clsMenu;

import com.alex.appcomida.Modelo.clsUsuarios;
import com.alex.appcomida.Rest.AdminSqlLiteOpen;
import com.alex.appcomida.Rest.ConsumoRest;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

public class Menu extends AppCompatActivity  {

    TextView txt1, txt2, txt3, txt4, pre1, pre2, pre3, pre4;
    String cod1, cod2, cod3, cod4;
    int residu;
    ImageView img1,img2,img3,img4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txt1 = findViewById(R.id.txtProd1);
        txt2 = findViewById(R.id.txtprod2);
        txt3 = findViewById(R.id.txtProd3);
        txt4 = findViewById(R.id.txtProd4);
        pre1 = findViewById(R.id.txtPrecio1);
        pre2 = findViewById(R.id.txtprecio2);
        pre3 = findViewById(R.id.txtprecio3);
        pre4 = findViewById(R.id.txtPrecio4);

        Bundle bundle = getIntent().getExtras();
        residu = bundle.getInt("resIDU");

        getData();

        
        // get FB
      /*  if (AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }*/
    }

 // fb
    private void goLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view){
        LoginManager.getInstance().logOut();
        goLoginScreen();

    }




    public void EnviarDatosPlato1 (View view){
        Intent abrir_prod1 = new Intent(Menu.this, DetalleMenu.class);
        abrir_prod1.putExtra("resID",R.drawable.seco_guanta);
        abrir_prod1.putExtra("resIDT", cod1.toString());
        abrir_prod1.putExtra("resIDU", residu);
        startActivity(abrir_prod1);
    }

    public void EnviarDatosPlato2 (View view){
        Intent abrir_prod2 = new Intent(Menu.this, DetalleMenu.class);
        abrir_prod2.putExtra("resID",R.drawable.hamburguesa);
        abrir_prod2.putExtra("resIDT", cod2.toString());
        abrir_prod2.putExtra("resIDU", residu);
        startActivity(abrir_prod2);
    }

    public void EnviarDatosPlato3 (View view){
        Intent abrir_prod3 = new Intent(Menu.this, DetalleMenu.class);
        abrir_prod3.putExtra("resID",R.drawable.fritada);
        abrir_prod3.putExtra("resIDT", cod3.toString());
        abrir_prod3.putExtra("resIDU", residu);
        startActivity(abrir_prod3);
    }

    public void EnviarDatosPlato4 (View view){
        Intent abrir_prod4 = new Intent(Menu.this, DetalleMenu.class);
        abrir_prod4.putExtra("resID",R.drawable.helado);
        abrir_prod4.putExtra("resIDT", cod4.toString());
        abrir_prod4.putExtra("resIDU", residu);
        startActivity(abrir_prod4);
    }



    public void getData() {

        ConsumoRest datos = new ConsumoRest();
        ArrayList<clsMenu> Datas = datos.getDataMenu();
        for (int i = 0; i < Datas.size(); i++) {
            if (i == 0 )  {
                cod1 = Integer.toString(Datas.get(i).getCodigo());
                txt1.setText(Datas.get(i).getPlato());
                pre1.setText("$"+Double.toString(Datas.get(i).getPrecio()));
            } else if (i == 1) {
                cod2 = Integer.toString(Datas.get(i).getCodigo());
                txt2.setText(Datas.get(i).getPlato());
                pre2.setText("$"+Double.toString(Datas.get(i).getPrecio()));
            } else if (i == 2) {
                cod3 = Integer.toString(Datas.get(i).getCodigo());
                txt3.setText(Datas.get(i).getPlato());
                pre3.setText("$"+Double.toString(Datas.get(i).getPrecio()));
            } else if (i == 3) {
                cod4 = Integer.toString(Datas.get(i).getCodigo());
                txt4.setText(Datas.get(i).getPlato());
                pre4.setText("$"+Double.toString(Datas.get(i).getPrecio()));
            }
        }

    }


    public void plato3(){
        Intent abrir_prod1 = new Intent(Menu.this, DetalleMenu.class);
        abrir_prod1.putExtra("resID",R.drawable.fritada);
        startActivity(abrir_prod1);

    }
    public void plato4(){
        Intent abrir_prod1 = new Intent(Menu.this, DetalleMenu.class);
        abrir_prod1.putExtra("resID",R.drawable.ceviche_camaron);
        startActivity(abrir_prod1);

    }


}
