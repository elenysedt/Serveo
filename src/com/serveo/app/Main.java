package com.serveo.app;

import com.serveo.excepciones.CuposInsuficienteException;
import com.serveo.pedidos.Pedido;
import com.serveo.productos.Producto;
import com.serveo.servicios.PedidoService;
import com.serveo.servicios.ProductoService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static final ProductoService productoService = new ProductoService();
    private static final PedidoService pedidoService = new PedidoService();
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        seedUsers(); // usuarios de prueba
        seedProducts(); // servicios (productos) de ejemplo

        boolean running = true;

        while (running) {
            imprimirMenu();
            String op = in.nextLine().trim();
            switch (op) {
                case "0" -> registrarUsuario();
                case "9" -> iniciarSesion();
                case "8" -> cerrarSesion();

                case "1" -> agregarProducto(); // solo PROVIDER
                case "2" -> listarProductos();
                case "3" -> buscarActualizarProducto();
                case "4" -> eliminarProducto();
                case "5" -> crearPedido(); // solo CLIENT
                case "6" -> listarPedidos();
                case "7" -> running = false;
                default -> System.out.println("Opción inválida.");
            }
            System.out.println();
        }
        System.out.println("¡Gracias! Sistema finalizado.");
    }

    private static void imprimirMenu() {
        System.out.println("\n💡 SERVEO: El servicio que necesitas, cuando lo necesitas 💡");
        var cu = userService.getCurrentUser();
        System.out.println("Usuario actual: " + (cu == null ? "(ninguno)" : cu));
        System.out.println(
                "=================================== SISTEMA DE GESTIÓN - SERVEO ==================================");

        if (cu == null) {
            System.out.println("""
                    0) Registrar usuario
                    9) Iniciar sesión
                    7) Salir
                    Elija una opción: """);
            return;
        }

        if (cu.getRole() == Role.PROVIDER) {
            System.out.println("""
                    8) Cerrar sesión
                    1) Agregar producto
                    2) Listar productos
                    3) Buscar/Actualizar producto
                    4) Eliminar producto
                    6) Listar pedidos
                    7) Salir
                    Elija una opción: """);
        } else { // CLIENT
            System.out.println("""
                    8) Cerrar sesión
                    2) Listar productos
                    5) Crear un pedido
                    6) Listar pedidos
                    7) Salir
                    Elija una opción: """);
        }
    }

    // ---------- Autenticación ----------
    private static void registrarUsuario() {
        System.out.print("Email: ");
        String email = in.nextLine().trim();
        System.out.print("Password: ");
        String pass = in.nextLine().trim();
        System.out.print("Rol (1=CLIENT, 2=PROVIDER): ");
        String r = in.nextLine().trim();
        Role role = "2".equals(r) ? Role.PROVIDER : Role.CLIENT;
        boolean ok = userService.register(email, pass, role);
        System.out.println(ok ? "✅ Registrado." : "❌ No se pudo registrar (email ya existe o datos inválidos).");
    }

    private static void iniciarSesion() {
        System.out.print("Email: ");
        String email = in.nextLine().trim();
        System.out.print("Password: ");
        String pass = in.nextLine().trim();
        boolean ok = userService.login(email, pass);
        System.out.println(ok ? "✅ Sesión iniciada." : "❌ Credenciales inválidas.");
    }

    private static void cerrarSesion() {
        userService.logout();
        System.out.println("🔒 Sesión cerrada.");
    }

    private static boolean requireRole(Role needed) {
        User u = userService.getCurrentUser();
        if (u == null) {
            System.out.println("⚠️ Debes iniciar sesión.");
            return false;
        }
        if (u.getRole() != needed) {
            System.out.println("⚠️ No tienes permiso para esta acción. Requiere: " + needed);
            return false;
        }
        return true;
    }

    private static void agregarProducto() {
        if (!requireRole(Role.PROVIDER))
            return;

        try {
            System.out.print("Nombre (servicio): ");
            String nombre = in.nextLine().trim();

            System.out.print("Precio base (>= 0): ");
            double precio = Double.parseDouble(in.nextLine().trim());
            if (precio < 0) {
                System.out.println("Precio inválido.");
                return;
            }

            System.out.print("Cupos (>= 0): ");
            int cupos = Integer.parseInt(in.nextLine().trim());
            if (cupos < 0) {
                System.out.println("Cupos inválidos.");
                return;
            }

            System.out.println("Tipo de servicio: 1) Hogar  2) Automotriz  3) Otros");
            String tipo = in.nextLine().trim();

            Producto p;
            switch (tipo) {
                case "1" -> {
                    System.out.println("Regla Hogar: se suma un recargo fijo al precio base (Final = Base + Recargo).");
                    System.out.print("Recargo fijo (>= 0): ");
                    double recargo = Double.parseDouble(in.nextLine().trim());
                    if (recargo < 0) {
                        System.out.println("Recargo inválido.");
                        return;
                    }
                    p = productoService.agregarHogar(nombre, precio, cupos, recargo);
                    System.out.println("🔎 " + p.detallePrecio());
                }
                case "2" -> {
                    System.out.println(
                            "Regla Automotriz: se aplica un porcentaje extra sobre el precio base (Final = Base × (1 + %)).");
                    System.out.print("Porcentaje extra (0..100): ");
                    double extra = Double.parseDouble(in.nextLine().trim());
                    if (extra < 0 || extra > 100) {
                        System.out.println("% inválido.");
                        return;
                    }
                    p = productoService.agregarAutomotriz(nombre, precio, cupos, extra);
                    System.out.println("🔎 " + p.detallePrecio());
                }
                default -> {
                    System.out.println(
                            "Regla Otros: se aplica un descuento sobre el precio base (Final = Base × (1 − %)).");
                    System.out.print("Descuento (0..100): ");
                    double desc = Double.parseDouble(in.nextLine().trim());
                    if (desc < 0 || desc > 100) {
                        System.out.println("% inválido.");
                        return;
                    }
                    p = productoService.agregarOtros(nombre, precio, cupos, desc);
                    System.out.println("🔎 " + p.detallePrecio());
                }
            }
            System.out.println("✅ Agregado: " + p);

        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada numérica inválida.");
        }
    }

    private static void listarProductos() {
        List<Producto> list = productoService.listar();
        if (list.isEmpty()) {
            System.out.println("(sin productos)");
            return;
        }
        list.forEach(System.out::println);
    }

    private static void buscarActualizarProducto() {
        if (!requireRole(Role.PROVIDER))
            return;

        System.out.print("Buscar por (1) ID o (2) Nombre: ");
        String modo = in.nextLine().trim();
        Producto p = null;
        if ("1".equals(modo)) {
            try {
                System.out.print("ID: ");
                int id = Integer.parseInt(in.nextLine().trim());
                p = productoService.buscarPorId(id);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
                return;
            }
        } else {
            System.out.print("Nombre: ");
            String nombre = in.nextLine().trim();
            p = productoService.buscarPorNombre(nombre);
        }

        if (p == null) {
            System.out.println("No se encontró el producto.");
            return;
        }

        System.out.println("Encontrado: " + p);
        System.out.println("🔎 " + p.detallePrecio());
        System.out.print("¿Actualizar (1) precio, (2) cupos, (otro) nada?: ");
        String opt = in.nextLine().trim();
        try {
            if ("1".equals(opt)) {
                System.out.print("Nuevo precio: ");
                double nuevoPrecio = Double.parseDouble(in.nextLine().trim());

                System.out.printf("¿Confirmar actualización del precio de %.2f a %.2f? (s/n): ",
                        p.getPrecio(), nuevoPrecio);
                String confirm = in.nextLine().trim();

                if (confirm.equalsIgnoreCase("s")) {
                    p.setPrecio(nuevoPrecio);
                    System.out.println("✅ Precio actualizado.");
                } else {
                    System.out.println("❎ Operación cancelada. El precio no fue modificado.");
                }

            } else if ("2".equals(opt)) {
                System.out.print("Nuevos Cupos: ");
                int nuevoCupos = Integer.parseInt(in.nextLine().trim());
                if (nuevoCupos < 0) {
                    System.out.println("Los Cupos no pueden ser negativo.");
                    return;
                }

                System.out.printf("¿Confirmar actualización de los cupos de %d a %d? (s/n): ",
                        p.getCupos(), nuevoCupos);
                String confirm = in.nextLine().trim();

                if (confirm.equalsIgnoreCase("s")) {
                    p.setCupos(nuevoCupos);
                    System.out.println("✅ Cupos actualizados.");
                } else {
                    System.out.println("❎ Operación cancelada. Los Cupos no fueron modificados.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Valor inválido.");
        }
    }

    private static void eliminarProducto() {
        if (!requireRole(Role.PROVIDER))
            return;

        try {
            System.out.print("ID del producto a eliminar: ");
            int id = Integer.parseInt(in.nextLine().trim());

            Producto p = productoService.buscarPorId(id);
            if (p == null) {
                System.out.println("❌ No se encontró el producto.");
                return;
            }
            System.out.println("Se encontró: " + p); // imprime [id] nombre | $precio | cupos
            System.out.print("¿Seguro que desea eliminar este producto? (s/n): ");
            String conf = in.nextLine().trim();
            if (!conf.equalsIgnoreCase("s")) {
                System.out.println("❎ Operación cancelada. El producto no fue eliminado.");
                return;
            }
            boolean ok = productoService.eliminar(id);
            System.out.println(ok ? "✅ Eliminado." : "❌ No se pudo eliminar.");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void crearPedido() {
        if (!requireRole(Role.CLIENT))
            return;

        Pedido pedido = pedidoService.crearPedido();
        System.out.println("Creando Pedido #" + pedido.getId());

        while (true) {
            System.out.print("ID producto (o 0 para terminar): ");
            int id;
            try {
                id = Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ID inválido.");
                continue;
            }
            if (id == 0)
                break;

            Producto prod = productoService.buscarPorId(id);
            if (prod == null) {
                System.out.println("No existe ese producto.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cant;
            try {
                cant = Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            try {
                System.out.println("🔎 " + prod.detallePrecio());
                double subtotal = prod.precioFinal() * cant;
                System.out.printf("Confirmar: %s x%d (Unit: $%.2f) → Subtotal: $%.2f ? (s/n): ",
                        prod.getNombre(), cant, prod.precioFinal(), subtotal);
                String conf = in.nextLine().trim();
                if (!conf.equalsIgnoreCase("s")) {
                    System.out.println("❎ Línea cancelada.");
                    continue;
                }

                pedidoService.agregarLineaValidandoCupos(pedido, prod, cant);
                System.out.printf("  + Agregado: %s x%d%n", prod.getNombre(), cant);
            } catch (CuposInsuficienteException | IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }

        }

        try {
            pedidoService.confirmar(pedido); // descuenta cupos
            System.out.println("✅ Pedido confirmado:");
            System.out.println(pedido);
        } catch (CuposInsuficienteException e) {
            System.out.println("❌ Error al confirmar: " + e.getMessage());
        }
    }

    private static void listarPedidos() {
        var pedidos = pedidoService.listar();
        if (pedidos.isEmpty()) {
            System.out.println("(sin pedidos)");
            return;
        }
        pedidos.forEach(p -> {
            System.out.println(p);
            System.out.println("-----------------------------");
        });
    }

    // ---------- Seeds ----------
    private static void seedUsers() {
        // usuarios de prueba (podés cambiarlos o crear los tuyos desde el menú)
        userService.register("prov@serveo.com", "1234", Role.PROVIDER);
        userService.register("cli@serveo.com", "1234", Role.CLIENT);
    }

    // ---------- Seeds ----------
    private static void seedProducts() {

        productoService.agregarHogar(
                "Instalación de aire acondicionado (Split)", 45000, 5, 2500); // recargo visita
        productoService.agregarHogar(
                "Electricista domiciliario", 28000, 4, 2000); // antes: "Revisión eléctrica domiciliaria"
        productoService.agregarHogar(
                "Mantenimiento de aire acondicionado", 30000, 6, 1500);

        productoService.agregarAutomotriz(
                "Mecánico - diagnóstico computarizado", 55000, 3, 15); // +15% por complejidad

        productoService.agregarOtros(
                "Profesor de matemáticas a domicilio", 20000, 10, 10); // 10% descuento promo
    }

}
