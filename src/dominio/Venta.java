/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author pame
 */
public class Venta {
   private int id;
    private String username; // usuario que hizo la venta
    private LocalDateTime fechaHora;
    private ArrayList<DetalleVenta> detalles;

    private double subtotal;
    private double impuestoIVA; // 7%
    private double impuestoIVI; // 13%
    private double descuento;
    private double total;

    public Venta(int id, String username) {
        this.id = id;
        this.username = username;
        this.fechaHora = LocalDateTime.now();
        this.detalles = new ArrayList<>();
        this.subtotal = 0;
        this.impuestoIVA = 0;
        this.impuestoIVI = 0;
        this.descuento = 0;
        this.total = 0;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public ArrayList<DetalleVenta> getDetalles() { return detalles; }
    public double getSubtotal() { return subtotal; }
    public double getImpuestoIVA() { return impuestoIVA; }
    public double getImpuestoIVI() { return impuestoIVI; }
    public double getDescuento() { return descuento; }
    public double getTotal() { return total; }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        subtotal += detalle.getTotalLinea();
        calcularTotales();
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
        calcularTotales();
    }

    private void calcularTotales() {
        impuestoIVA = subtotal * 0.07;
        impuestoIVI = subtotal * 0.13;
        total = subtotal + impuestoIVA + impuestoIVI - descuento;
    }  
}

