🛠️ Serveo

"El servicio que necesitas, cuando lo necesitas"


📖 Descripción del Proyecto

Serveo es una aplicación de consola desarrollada en Java que simula un pequeño marketplace de servicios profesionales.
Permite registrar, listar, buscar, actualizar y eliminar servicios, además de que los clientes pueden crear pedidos con varios servicios solicitados.

El objetivo de este trabajo es aplicar los conocimientos de Programación Orientada a Objetos (POO), manejo de colecciones, control de excepciones e implementación de herencia, polimorfismo, encapsulamiento e interfaces, cumpliendo con los requisitos de la preentrega.

Se implementó una estructura de clases más completa, donde los servicios se dividen en tres categorías:

🏠 Hogar (por ejemplo: instalación de aire acondicionado, electricista).

🚗 Automotriz (por ejemplo: mecánico).

💼 Otros (por ejemplo: profesor particular).

Cada categoría hereda de una clase abstracta base (Producto) e implementa su propio comportamiento, aplicando principios de POO.


⚙️ Funcionalidades

Agregar nuevos servicios (solo para proveedores).

Listar todos los servicios disponibles.

Buscar, actualizar o eliminar servicios existentes.

Crear pedidos con validación de cupos disponibles (reemplazando el antiguo manejo de stock).

Confirmar pedidos y descontar automáticamente los cupos de cada servicio.

Menú interactivo totalmente en consola.

Roles de usuario diferenciados: CLIENTE y PROVEEDOR.

Estructura organizada por paquetes (app, productos, servicios, pedidos, excepciones).


🧩 Lógica de Cupos y Categorías

Cada tipo de servicio aplica una regla de cálculo distinta para el precio final:

🏠 Hogar: precioFinal = precio base + recargo fijo

🚗 Automotriz: precioFinal = precio base + (precio base * % extra)

💼 Otros: precioFinal = precio base - (precio base * % descuento)

El sistema informa estas reglas al proveedor al momento de registrar el servicio, para que sepa exactamente cómo se calcula el precio final según la categoría elegida.

Además, el sistema valida los cupos antes de confirmar un pedido, y muestra mensajes claros si no hay disponibilidad suficiente.



👤 Datos de prueba para inicio de sesión

🔧 Proveedor

Correo: prov@serveo.com
Contraseña: 1234


🧑‍💼 Cliente

Correo: cli@serveo.com
Contraseña: 1234


(Es necesario iniciar sesión antes de acceder al menú principal.)



🚀 Futuras Mejoras

Registro de usuarios reales (clientes y proveedores).

Persistencia con base de datos o archivos externos para guardar información.

Versión web con HTML, CSS y JavaScript.

Sistema de reseñas y calificaciones para los servicios.

Control de cupos a nivel temporal (por fechas u horarios).



👩‍💻 Autora

Elenys del Carmen Díaz Tejidor
