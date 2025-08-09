/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dominio.DetalleVenta;
import dominio.Producto;
import dominio.Venta;
import java.util.ArrayList;

/**
 *
 * @author pame
 */
public class VentaService {
     private ArrayList<Venta> ventas = new ArrayList<>();
    private int nextId = 1;
    private ProductoService productoService;

    // Inyectamos ProductoService para buscar productos
    public VentaService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Venta crearVenta(String username) {
        Venta v = new Venta(nextId++, username);
        ventas.add(v);
        return v;
    }

    public void agregarDetalle(Venta venta, int productoId, int cantidad) {
        Producto p = buscarProductoPorId(productoId);
        if (p != null && p.isActivo() && cantidad > 0) {
            DetalleVenta detalle = new DetalleVenta(p.getId(), p.getNombre(), cantidad, p.getPrecio());
            venta.agregarDetalle(detalle);
        }
    }

    private Producto buscarProductoPorId(int id) {
        for (Producto p : productoService.listar()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public ArrayList<Venta> getVentasDelDia() {
        ArrayList<Venta> ventasHoy = new ArrayList<>();
        for (Venta v : ventas) {
            if (v.getFechaHora().toLocalDate().equals(java.time.LocalDate.now())) {
                ventasHoy.add(v);
            }
        }
        return ventasHoy;
    }
}
