package com.serveo.productos;

public class ProductoOtros extends Producto {
    private double descuento; // 0..100

    public ProductoOtros(String nombre, double precio, int stock, double descuento) {
        super(nombre, precio, stock);
        setDescuento(descuento);
    }

    public double getDescuento() { return descuento; }

    public void setDescuento(double descuento) {
        if (descuento < 0 || descuento > 100)
            throw new IllegalArgumentException("Descuento debe estar entre 0 y 100.");
        this.descuento = descuento;
    }

    @Override
    public double precioFinal() { return getPrecio() * (1 - descuento / 100.0); }

    @Override
    protected String tipo() { return "Otros"; }
}
