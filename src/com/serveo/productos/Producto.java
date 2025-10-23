package com.serveo.productos;

// Clase ABSTRACTA = Abstracción + base para Herencia
public abstract class Producto implements Identificable {
    private static int SEQ = 1;

    private final int id;     // Encapsulamiento (private)
    private String nombre;    // = descripcion de la consigna
    private double precio;    // base
    private int cupos;

    public Producto(String nombre, double precio, int cupos) {
        this.id = SEQ++;
        setNombre(nombre);
        setPrecio(precio);
        setCupos(cupos);
    }

    @Override public int getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
    }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) {
        if (precio < 0) throw new IllegalArgumentException("El precio debe ser >= 0.");
        this.precio = precio;
    }

    public int getCupos() { return cupos; }
    public void setCupos(int cupos) {
        if (cupos < 0) throw new IllegalArgumentException("Los cupos no pueden ser negativos.");
        this.cupos = cupos;
    }

    public abstract double precioFinal();
    protected abstract String tipo();

    public abstract String reglaPrecio();

    public String detallePrecio() {
        return String.format("Base: $%.2f | %s | Final: $%.2f", getPrecio(), reglaPrecio(), precioFinal());
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | cupos=%d | %s (%s)",
                id, nombre, cupos, detallePrecio(), tipo());
    }
}