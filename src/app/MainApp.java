/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import servicio.AuthService;
import servicio.ProductoService;
import servicio.CalculadoraService;
import servicio.VentaService;
import dominio.Producto;
import dominio.Venta;
import dominio.DetalleVenta;

import javax.swing.JOptionPane;
/**
 *
 * @author Ben
 */
public class MainApp {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        ProductoService productoService = new ProductoService();
        VentaService ventaService = new VentaService(productoService);
        CalculadoraService calculadora = new CalculadoraService();

        boolean loggedIn = false;

        for (int i = 0; i < 3 && !loggedIn; i++) {
            String user = JOptionPane.showInputDialog("Usuario:");
            String pass = JOptionPane.showInputDialog("Contraseña:");
            if (auth.login(user, pass)) {
                loggedIn = true;
                JOptionPane.showMessageDialog(null, "Bienvenido " + user);
                mostrarMenu(productoService, calculadora, ventaService);
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
            }
        }
        if (!loggedIn) {
            JOptionPane.showMessageDialog(null, "Número máximo de intentos alcanzado. Saliendo.");
            System.exit(0);
        }
    }

    private static void mostrarMenu(ProductoService productoService, CalculadoraService calculadora, VentaService ventaService) {
        String opcion;
        do {
            opcion = JOptionPane.showInputDialog("""
                Menú Principal:
                1. Listar productos
                2. Agregar producto
                3. Activar/Desactivar producto
                4. Calculadora
                5. Registrar venta
                6. Consultar ventas del día
                7. Salir
                Elige una opción:
                """);

            if (opcion == null) opcion = "7"; // si cancela, sale

            switch (opcion) {
                case "1" -> listarProductos(productoService);
                case "2" -> agregarProducto(productoService);
                case "3" -> activarDesactivarProducto(productoService);
                case "4" -> usarCalculadora(calculadora);
                case "5" -> registrarVenta(productoService, ventaService);
                case "6" -> mostrarVentasDelDia(ventaService);
                case "7" -> JOptionPane.showMessageDialog(null, "Saliendo...");
                default -> JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        } while (!"7".equals(opcion));
    }

    private static void listarProductos(ProductoService productoService) {
        StringBuilder lista = new StringBuilder("Productos:\n");
        for (Producto p : productoService.listar()) {
            lista.append(p.getId())
                 .append(" - ")
                 .append(p.getNombre())
                 .append(" - $").append(p.getPrecio())
                 .append(" - ").append(p.isActivo() ? "Activo" : "Inactivo")
                 .append("\n");
        }
        JOptionPane.showMessageDialog(null, lista.toString());
    }

    private static void agregarProducto(ProductoService productoService) {
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del producto:");
            if (nombre == null || nombre.isBlank()) {
                JOptionPane.showMessageDialog(null, "Nombre inválido.");
                return;
            }
            String precioStr = JOptionPane.showInputDialog("Precio:");
            double precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                JOptionPane.showMessageDialog(null, "Precio no puede ser negativo.");
                return;
            }
            productoService.agregarProducto(nombre, precio);
            JOptionPane.showMessageDialog(null, "Producto agregado.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Precio inválido.");
        }
    }

    private static void activarDesactivarProducto(ProductoService productoService) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID del producto:"));
            String estado = JOptionPane.showInputDialog("Escribir 'activar' o 'desactivar':");
            if (estado == null) return;
            boolean activar = estado.equalsIgnoreCase("activar");
            boolean encontrado = false;
            for (Producto p : productoService.listar()) {
                if (p.getId() == id) {
                    productoService.cambiarEstado(id, activar);
                    JOptionPane.showMessageDialog(null, "Estado cambiado.");
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(null, "Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido.");
        }
    }

    private static void usarCalculadora(CalculadoraService calculadora) {
        try {
            double a = Double.parseDouble(JOptionPane.showInputDialog("Primer número:"));
            double b = Double.parseDouble(JOptionPane.showInputDialog("Segundo número:"));
            String op = JOptionPane.showInputDialog("Operación (+, -, *, /, ^, %):");
            if (op == null) return;
            double res = switch (op) {
                case "+" -> calculadora.suma(a, b);
                case "-" -> calculadora.resta(a, b);
                case "*" -> calculadora.multiplicar(a, b);
                case "/" -> {
                    try {
                        yield calculadora.dividir(a, b);
                    } catch (ArithmeticException e) {
                        JOptionPane.showMessageDialog(null, "Error: División entre 0 no permitida.");
                        yield Double.NaN;
                    }
                }
                case "^" -> calculadora.potencia(a, b);
                case "%" -> calculadora.porcentaje(a, b);
                default -> {
                    JOptionPane.showMessageDialog(null, "Operación no válida.");
                    yield Double.NaN;
                }
            };
            if (!Double.isNaN(res)) {
                JOptionPane.showMessageDialog(null, "Resultado: " + res);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido.");
        }
    }

    private static void registrarVenta(ProductoService productoService, VentaService ventaService) {
        String username = JOptionPane.showInputDialog("Ingrese su nombre de usuario para la venta:");
        if (username == null || username.isBlank()) {
            JOptionPane.showMessageDialog(null, "Usuario inválido.");
            return;
        }
        Venta venta = ventaService.crearVenta(username);

        boolean agregarMas = true;
        while (agregarMas) {
            StringBuilder listaProductos = new StringBuilder("Productos disponibles:\n");
            for (Producto p : productoService.listar()) {
                if (p.isActivo()) {
                    listaProductos.append(p.getId()).append(" - ").append(p.getNombre())
                        .append(" - $").append(p.getPrecio()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, listaProductos.toString());

            try {
                String idStr = JOptionPane.showInputDialog("ID del producto a agregar:");
                if (idStr == null) break;
                int idProd = Integer.parseInt(idStr);

                String cantStr = JOptionPane.showInputDialog("Cantidad:");
                if (cantStr == null) break;
                int cantidad = Integer.parseInt(cantStr);

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida.");
                    continue;
                }

                Producto p = productoService.listar().stream()
                    .filter(prod -> prod.getId() == idProd && prod.isActivo())
                    .findFirst()
                    .orElse(null);
                if (p == null) {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado o inactivo.");
                    continue;
                }

                ventaService.agregarDetalle(venta, idProd, cantidad);

                int resp = JOptionPane.showConfirmDialog(null, "¿Agregar otro producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                agregarMas = (resp == JOptionPane.YES_OPTION);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida.");
            }
        }

        double descuento = 0;
        try {
            String descStr = JOptionPane.showInputDialog("Ingrese descuento (0 si no aplica):");
            if (descStr != null) {
                descuento = Double.parseDouble(descStr);
                if (descuento < 0) descuento = 0;
            }
        } catch (Exception e) {
            descuento = 0;
        }

        venta.setDescuento(descuento);

        StringBuilder resumen = new StringBuilder("Resumen de la venta:\n");
        resumen.append("ID Venta: ").append(venta.getId()).append("\n");
        resumen.append("Usuario: ").append(venta.getUsername()).append("\n");
        resumen.append("Fecha: ").append(venta.getFechaHora()).append("\n");
        resumen.append("Detalle:\n");
        for (DetalleVenta d : venta.getDetalles()) {
            resumen.append("- ").append(d.getNombreProducto())
                .append(", Cantidad: ").append(d.getCantidad())
                .append(", Precio Unit: $").append(d.getPrecioUnitario())
                .append(", Total: $").append(d.getTotalLinea())
                .append("\n");
        }
        resumen.append(String.format("Subtotal: $%.2f\n", venta.getSubtotal()));
        resumen.append(String.format("IVA (7%%): $%.2f\n", venta.getImpuestoIVA()));
        resumen.append(String.format("IVI (13%%): $%.2f\n", venta.getImpuestoIVI()));
        resumen.append(String.format("Descuento: $%.2f\n", venta.getDescuento()));
        resumen.append(String.format("Total: $%.2f\n", venta.getTotal()));

        JOptionPane.showMessageDialog(null, resumen.toString());
    }

    private static void mostrarVentasDelDia(VentaService ventaService) {
        var ventasHoy = ventaService.getVentasDelDia();
        if (ventasHoy.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay ventas registradas hoy.");
            return;
        }

        StringBuilder sb = new StringBuilder("Ventas del día:\n");
        for (var venta : ventasHoy) {
            sb.append("ID: ").append(venta.getId())
              .append(", Usuario: ").append(venta.getUsername())
              .append(", Fecha: ").append(venta.getFechaHora())
              .append(", Total: $").append(String.format("%.2f", venta.getTotal()))
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
