package com.serveo.productos;

public class ProductoHogar extends Producto {
    private double recargo; // >= 0 (ej.: visita)

    public ProductoHogar(String nombre, double precio, int stock, double recargo) {
        super(nombre, precio, stock);
        setRecargo(recargo);
    }

    public double getRecargo() { return recargo; }

    public void setRecargo(double recargo) {
        if (recargo < 0)
            throw new IllegalArgumentException("Recargo debe ser >= 0.");
        this.recargo = recargo;
    }

    @Override
    public double precioFinal() { return getPrecio() + recargo; }

    @Override
    protected String tipo() { return "Hogar"; }
}
