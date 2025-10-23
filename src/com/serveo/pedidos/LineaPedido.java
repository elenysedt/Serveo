package com.serveo.pedidos;

import com.serveo.productos.Producto;

public class LineaPedido {
    private final Producto producto;  // composición: un pedido tiene líneas que "tienen" productos
    private final int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser null.");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public double subtotal() {
        // Usamos el precio FINAL del producto (incluye recargos/porcentajes/descuentos)
        return producto.precioFinal() * cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s x%d -> $%.2f",
                producto.getNombre(), cantidad, subtotal());
    }
}
