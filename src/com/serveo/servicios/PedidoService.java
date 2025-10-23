package com.serveo.servicios;

import com.serveo.excepciones.CuposInsuficienteException;
import com.serveo.pedidos.LineaPedido;
import com.serveo.pedidos.Pedido;
import com.serveo.productos.Producto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();

    public Pedido crearPedido() {
        var p = new Pedido();
        pedidos.add(p);
        return p;
    }

    public void agregarLineaValidandoCupos(Pedido pedido, Producto producto, int cantidad)
            throws CuposInsuficienteException {

        if (pedido == null) throw new IllegalArgumentException("El pedido no puede ser null.");
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser null.");
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");

        int cuposDisponibles = producto.getCupos();
        if (cantidad > cuposDisponibles) {
            throw new CuposInsuficienteException(
                "Cupos insuficientes. Disponibles: " + cuposDisponibles + ", solicitados: " + cantidad
            );
        }

        pedido.agregarLinea(new LineaPedido(producto, cantidad));
    }

    public void confirmar(Pedido pedido) throws CuposInsuficienteException {
        if (pedido == null) throw new IllegalArgumentException("El pedido no puede ser null.");

        // 1) Acumular cantidades por producto (por si hay varias líneas del mismo producto)
        Map<Producto, Integer> requeridos = new HashMap<>();
        for (LineaPedido lp : pedido.getLineas()) {
            if (lp == null || lp.getProducto() == null)
                throw new IllegalArgumentException("La línea de pedido o el producto no pueden ser null.");
            if (lp.getCantidad() <= 0)
                throw new IllegalArgumentException("La cantidad en la línea debe ser mayor que cero.");

            requeridos.merge(lp.getProducto(), lp.getCantidad(), Integer::sum);
        }

        // 2) Validar cupos totales por producto
        for (var entry : requeridos.entrySet()) {
            Producto prod = entry.getKey();
            int totalNecesario = entry.getValue();
            int disponibles = prod.getCupos();
            if (totalNecesario > disponibles) {
                throw new CuposInsuficienteException(
                    "No se puede confirmar. El producto '" + prod.getNombre() +
                    "' no tiene suficientes cupos. Disponibles: " + disponibles +
                    ", necesarios: " + totalNecesario
                );
            }
        }

        // 3) Descontar cupos (aplicar cambios luego de validar todo)
        for (var entry : requeridos.entrySet()) {
            Producto prod = entry.getKey();
            int totalNecesario = entry.getValue();
            prod.setCupos(prod.getCupos() - totalNecesario);
        }
    }

    public List<Pedido> listar() {
        return new ArrayList<>(pedidos);
    }
}
