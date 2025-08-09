/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author pame
 */
public class DetalleVenta {
     private int productId;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double totalLinea;

    public DetalleVenta(int productId, String nombreProducto, int cantidad, double precioUnitario) {
        this.productId = productId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalLinea = cantidad * precioUnitario;
    }

    public int getProductId() { return productId; }
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getTotalLinea() { return totalLinea; }
}
