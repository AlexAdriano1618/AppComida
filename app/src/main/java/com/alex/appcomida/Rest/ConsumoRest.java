package com.alex.appcomida.Rest;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.alex.appcomida.Modelo.clsUsuarios;
import com.alex.appcomida.Modelo.clsMenu;
import java.util.ArrayList;
public class ConsumoRest {
    public  ArrayList<clsMenu> getDataMenu()
    {
        ArrayList<clsMenu> lstMenu = new ArrayList<clsMenu>();
        //direccion de web service
        String sql = "https://appcomida.azurewebsites.net/api/Menus";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url=null;
        HttpURLConnection conn;


        try {
            url = new URL(sql);
            conn=(HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json="";

            while((inputLine=in.readLine())!=null){
                response.append(inputLine);

            }
            json = response.toString();
            JSONArray jsonArray = null;

            jsonArray = new JSONArray(json);
            String Plato = "";
            String Precio = "";
            String Descrip = "";


            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                clsMenu dtoMenu = new clsMenu();
                dtoMenu.setCodigo(Integer.parseInt(jsonObject.optString("codigo")));
                dtoMenu.setPlato(jsonObject.optString("plato"));
                dtoMenu.setDescripcion(jsonObject.optString("descripcion"));
                dtoMenu.setPrecio(Double.parseDouble(jsonObject.optString("precio")));
                lstMenu.add(dtoMenu);

            }
            //txt1.setText(Plato);
            // pre1.setText(Precio);

        }
        catch (IOException e){



        } catch (JSONException e) {


        }
        return lstMenu;
    }

    public  clsMenu getDataMenuDetalle(String id)
    {
        clsMenu dtoMenu = new clsMenu();
        //direccion de web service
        String sql = "https://appcomida.azurewebsites.net/api/Menus/"+id;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url=null;
        HttpURLConnection conn;


        try {
            url = new URL(sql);
            conn=(HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json="";

            while((inputLine=in.readLine())!=null){
                response.append(inputLine);

            }
            json = response.toString();

            JSONObject jsonObject = new JSONObject(json);

            dtoMenu.setCodigo(Integer.parseInt(jsonObject.optString("codigo")));
            dtoMenu.setPlato(jsonObject.optString("plato"));
            dtoMenu.setDescripcion(jsonObject.optString("descripcion"));
            dtoMenu.setPrecio(Double.parseDouble(jsonObject.optString("precio")));


        }
        catch (IOException e)
        {
        } catch (JSONException e) {
        }
        return dtoMenu;
    }

    public  int getDataUsuarios(String user , String pass)
    {
        Integer respuesta = 0;
        String sql = "https://appcomida.azurewebsites.net/api/Usuarios";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url=null;
        HttpURLConnection conn;


        try {
            url = new URL(sql);
            conn=(HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json="";

            while((inputLine=in.readLine())!=null){
                response.append(inputLine);

            }
            json = response.toString();
            JSONArray jsonArray = null;

            jsonArray = new JSONArray(json);
            String Usua = "";
            String Passw = "";

            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Usua =jsonObject.optString("usuar");
                Passw = jsonObject.optString("pass");
                if(Usua.equals(user) && Passw.equals(pass))
                {
                    respuesta= Integer.parseInt(jsonObject.optString("codigo"));
                }


            }
        }
        catch (IOException e){



        } catch (JSONException e) {


        }
        return respuesta;
    }

    public  clsUsuarios getDataUsuario(String id )
    {
        clsUsuarios respuesta = new clsUsuarios();
        String sql = "https://appcomida.azurewebsites.net/api/Usuarios/"+id;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url=null;
        HttpURLConnection conn;
        try {
            url = new URL(sql);
            conn=(HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json="";

            while((inputLine=in.readLine())!=null){
                response.append(inputLine);

            }
            json = response.toString();

            JSONObject jsonObject = new JSONObject(json);

            respuesta.setCodigo(Integer.parseInt(jsonObject.optString("codigo")));
            respuesta.setNombre(jsonObject.optString("nombre"));
            respuesta.setApellido(jsonObject.optString("apellido"));
            respuesta.setCelular(jsonObject.optString("celular"));
            respuesta.setUser(jsonObject.optString("usuar"));
            respuesta.setPass(jsonObject.optString("pass"));
            respuesta.setDireccion(jsonObject.optString("direccion"));
            respuesta.setLatitud(jsonObject.optString("latitud"));
            respuesta.setLongitud(jsonObject.optString("longitud"));
        }
        catch (IOException e)
        {
        } catch (JSONException e) {
        }
        return respuesta;
    }

}
