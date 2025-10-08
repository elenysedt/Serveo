package com.serveo.servicios;

import com.serveo.productos.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();

    public Producto agregar(String nombre, double precio, int stock) {
        Producto p = new Producto(nombre, precio, stock);
        productos.add(p);
        return p;
    }

    public List<Producto> listar() {
        return productos;
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public Producto buscarPorNombre(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        }
        return null;
    }

    public boolean eliminar(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }
}
