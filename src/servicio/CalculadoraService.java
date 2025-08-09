/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author pame
 */
public class CalculadoraService {
     public double suma(double a, double b) { return a + b; }
    public double resta(double a, double b) { return a - b; }
    public double multiplicar(double a, double b) { return a * b; }
    public double dividir(double a, double b) { 
        if (b == 0) throw new ArithmeticException("División entre 0");
        return a / b; 
    }
    public double potencia(double a, double b) { return Math.pow(a, b); }
    public double porcentaje(double a, double b) { return (a * b) / 100; }
}

