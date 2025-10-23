package com.serveo.productos;

public class ProductoHogar extends Producto {
    private double recargo; // >= 0 

    public ProductoHogar(String nombre, double precio, int cupos, double recargo) {
        super(nombre, precio, cupos);
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

    @Override
    public String reglaPrecio() {
        return String.format("Hogar: se suma un recargo fijo de $%.2f (Final = Base + Recargo)", recargo);
    }
}
