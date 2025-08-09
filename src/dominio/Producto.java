/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author pame
 */
public class Producto {
     private int id;
    private String nombre;
    private double precio;
    private boolean activo;

    public Producto(int id, String nombre, double precio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.activo = activo;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }
}
