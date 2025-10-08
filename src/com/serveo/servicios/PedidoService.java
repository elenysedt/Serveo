package com.serveo.servicios;

import com.serveo.excepciones.StockInsuficienteException;
import com.serveo.pedidos.LineaPedido;
import com.serveo.pedidos.Pedido;
import com.serveo.productos.Producto;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {
    private final List<Pedido> pedidos = new ArrayList<>();

    public Pedido crearPedido() {
        Pedido p = new Pedido();
        pedidos.add(p);
        return p;
    }

    public void agregarLineaValidandoStock(Pedido pedido, Producto producto, int cantidad)
            throws StockInsuficienteException {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException("Stock insuficiente para: " + producto.getNombre());
        }
        pedido.agregarLinea(new LineaPedido(producto, cantidad));
    }

    public void confirmar(Pedido pedido) throws StockInsuficienteException {
        // Descontar stock de TODAS las líneas del pedido
        for (LineaPedido lp : pedido.getLineas()) {
            Producto prod = lp.getProducto();
            int nuevoStock = prod.getStock() - lp.getCantidad();
            if (nuevoStock < 0) {
                // por si el stock cambió "en el medio"
                throw new StockInsuficienteException("Stock cambió y ahora es insuficiente para: " + prod.getNombre());
            }
            prod.setStock(nuevoStock);
        }
    }

    public List<Pedido> listar() { return pedidos; }
}
