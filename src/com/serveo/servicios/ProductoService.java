package com.serveo.servicios;

import com.serveo.productos.Producto;
import com.serveo.productos.ProductoHogar;
import com.serveo.productos.ProductoAutomotriz;
import com.serveo.productos.ProductoOtros;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();

    public Producto agregarHogar(String nombre, double precio, int cupos, double recargo) {
        Producto p = new ProductoHogar(nombre, precio, cupos, recargo);
        productos.add(p);
        return p;
    }

    public Producto agregarAutomotriz(String nombre, double precio, int cupos, double porcentajeExtra) {
        Producto p = new ProductoAutomotriz(nombre, precio, cupos, porcentajeExtra);
        productos.add(p);
        return p;
    }

    public Producto agregarOtros(String nombre, double precio, int cupos, double descuento) {
        Producto p = new ProductoOtros(nombre, precio, cupos, descuento);
        productos.add(p);
        return p;
    }

    public Producto agregar(String nombre, double precio, int cupos) {
        return agregarOtros(nombre, precio, cupos, 0);
    }

    public List<Producto> listar() {
        return new ArrayList<>(productos);
    }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) if (p.getId() == id) return p;
        return null;
    }

    public Producto buscarPorNombre(String nombre) {
        if (nombre == null) return null;
        String n = nombre.trim();
        for (Producto p : productos)
            if (p.getNombre().equalsIgnoreCase(n)) return p;
        return null;
    }

    public boolean eliminar(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }
}
