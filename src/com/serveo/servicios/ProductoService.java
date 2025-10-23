/*package com.serveo.servicios;

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
}*/
package com.serveo.servicios;

import com.serveo.productos.Producto;
import com.serveo.productos.ProductoHogar;
import com.serveo.productos.ProductoAutomotriz;
import com.serveo.productos.ProductoOtros;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final List<Producto> productos = new ArrayList<>();

    // Altas por tipo (para elegir en el menú)
    public Producto agregarHogar(String nombre, double precio, int stock, double recargo) {
        Producto p = new ProductoHogar(nombre, precio, stock, recargo);
        productos.add(p);
        return p;
    }

    public Producto agregarAutomotriz(String nombre, double precio, int stock, double porcentajeExtra) {
        Producto p = new ProductoAutomotriz(nombre, precio, stock, porcentajeExtra);
        productos.add(p);
        return p;
    }

    public Producto agregarOtros(String nombre, double precio, int stock, double descuento) {
        Producto p = new ProductoOtros(nombre, precio, stock, descuento);
        productos.add(p);
        return p;
    }

    // Compatibilidad con tu seedProducts() actual (agrega como "Otros" sin descuento)
    public Producto agregar(String nombre, double precio, int stock) {
        return agregarOtros(nombre, precio, stock, 0);  // <-- OJO: 0, no "descuento:0"
    }

    public List<Producto> listar() { return productos; }

    public Producto buscarPorId(int id) {
        for (Producto p : productos) if (p.getId() == id) return p;
        return null;
    }

    public Producto buscarPorNombre(String nombre) {
        for (Producto p : productos)
            if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        return null;
    }

    public boolean eliminar(int id) {
        return productos.removeIf(p -> p.getId() == id);
    }
}

