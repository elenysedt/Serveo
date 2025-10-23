/*package com.serveo.productos;

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
}*/

package com.serveo.productos;

// Clase ABSTRACTA = Abstracción + base para Herencia
public abstract class Producto implements Identificable {
    private static int SEQ = 1;

    private final int id;     // Encapsulamiento (private)
    private String nombre;    // = descripcion de la consigna
    private double precio;    // base
    private int stock;

    public Producto(String nombre, double precio, int stock) {
        this.id = SEQ++;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
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

    public int getStock() { return stock; }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo.");
        this.stock = stock;
    }

    // POLIMORFISMO: cada subtipo calcula distinto
    public abstract double precioFinal();
    protected abstract String tipo();

    @Override
    public String toString() {
        return String.format("[%d] %s | $%.2f | stock=%d | final=$%.2f (%s)",
                id, nombre, precio, stock, precioFinal(), tipo());
    }
}
