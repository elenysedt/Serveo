package com.serveo.productos;

public class ProductoAutomotriz extends Producto {
    private double porcentajeExtra; // 0..100

    public ProductoAutomotriz(String nombre, double precio, int cupos, double porcentajeExtra) {
        super(nombre, precio, cupos);
        setPorcentajeExtra(porcentajeExtra);
    }

    public double getPorcentajeExtra() { return porcentajeExtra; }

    public void setPorcentajeExtra(double porcentajeExtra) {
        if (porcentajeExtra < 0 || porcentajeExtra > 100)
            throw new IllegalArgumentException("% extra debe estar entre 0 y 100.");
        this.porcentajeExtra = porcentajeExtra;
    }

    @Override
    public double precioFinal() {
        return getPrecio() * (1 + porcentajeExtra / 100.0);
    }

    @Override
    protected String tipo() { return "Automotriz"; }

    @Override
    public String reglaPrecio() {
        return String.format("Automotriz: se aplica +%.0f%% sobre el precio base (Final = Base × (1 + %.0f%%))",
                porcentajeExtra, porcentajeExtra);
    }
}
