package com.alex.appcomida.Modelo;

public class clsMenu {
    public int Codigo;
    public String Plato;
    public String Descripcion;
    public double precio;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getPlato() {
        return Plato;
    }

    public void setPlato(String plato) {
        Plato = plato;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
