package com.serveo.pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int SEQ = 1;

    private final int id;
    private final List<LineaPedido> lineas = new ArrayList<>();

    public Pedido() { this.id = SEQ++; }

    public int getId() { return id; }
    public List<LineaPedido> getLineas() { return lineas; }

    public void agregarLinea(LineaPedido lp) { lineas.add(lp); }

    public double total() {
        double t = 0.0;
        for (LineaPedido lp : lineas) {
            t += lp.subtotal();
        }
        return t;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pedido #" + id + "\n");
        for (LineaPedido lp : lineas) {
            sb.append("  - ").append(lp).append("\n");
        }
        sb.append(String.format("  TOTAL: $%.2f", total()));
        return sb.toString();
    }
}
