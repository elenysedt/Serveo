package com.serveo.productos;

public class Producto {
    private static int SEQ = 1;   // contador de IDs compartido por TODA la clase

    private final int id;         // identificador único del producto
    private String nombre;        // ej: "Instalación de aire acondicionado"
    private double precio;        // double por consigna
    private int stock;            // unidades/cupos disponibles

    public Producto(String nombre, double precio, int stock) {
        this.id = SEQ++;          // asigna y luego incrementa
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y Setters (encapsulamiento)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("[%d] %s | $%.2f | stock=%d", id, nombre, precio, stock);
    }
}
