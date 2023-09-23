/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

public class Venta {
    private int id;
    private String clienteVenta;
    private String vendedorVenta;
    private double totalVenta;
    
    public Venta(){
    }

    public Venta(int id, String clienteVenta, String vendedorVenta, double totalVenta) {
        this.id = id;
        this.clienteVenta = clienteVenta;
        this.vendedorVenta = vendedorVenta;
        this.totalVenta = totalVenta;
    }

    public Venta(String clienteVenta, String vendedorVenta, double totalVenta) {
        this.clienteVenta = clienteVenta;
        this.vendedorVenta = vendedorVenta;
        this.totalVenta = totalVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClienteVenta() {
        return clienteVenta;
    }

    public void setClienteVenta(String clienteVenta) {
        this.clienteVenta = clienteVenta;
    }

    public String getVendedorVenta() {
        return vendedorVenta;
    }

    public void setVendedorVenta(String vendedorVenta) {
        this.vendedorVenta = vendedorVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

}
