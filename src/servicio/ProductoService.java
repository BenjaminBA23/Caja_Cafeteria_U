/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import dominio.Producto;
import java.util.ArrayList;

/**
 *
 * @author pame
 */
public class ProductoService {
   private final ArrayList<Producto> productos;

    public ProductoService() {
        productos = new ArrayList<>();
        // Agregar productos iniciales
        productos.add(new Producto(1, "Café Americano", 1.50, true));
        productos.add(new Producto(2, "Café con leche", 2.00, true));
        productos.add(new Producto(3, "Pan dulce", 1.00, true));
    }

    public ArrayList<Producto> listar() {
        return productos;
    }

    public void agregarProducto(String nombre, double precio) {
        int nuevoId = productos.size() + 1;
        Producto nuevo = new Producto(nuevoId, nombre, precio, true);
        productos.add(nuevo);
    }

    public void cambiarEstado(int id, boolean activar) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                p.setActivo(activar);
                break;
            }
        }
    } 
}
