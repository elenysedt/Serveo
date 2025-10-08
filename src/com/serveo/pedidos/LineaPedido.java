package com.serveo.pedidos;

import com.serveo.productos.Producto;

public class LineaPedido {
    private final Producto producto;  // composición: un pedido tiene líneas que "tienen" productos
    private final int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public double subtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return String.format("%s x%d -> $%.2f",
                producto.getNombre(), cantidad, subtotal());
    }
}
