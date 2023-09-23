/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

public class Detalle {

    private int id;
    private String codigoProducto;
    private int cantidad;
    private double precioVenta;
    private int id_venta;

    public Detalle() {

    }

    public Detalle(int id, String codigoProducto, int cantidad, double precioVenta, int id_venta) {
        this.id = id;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.id_venta = id_venta;
    }

    public Detalle(String codigoProducto, int cantidad, double precioVenta, int id_venta) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.id_venta = id_venta;
    }
    
     public Detalle(String codigoProducto, int cantidad, double precioVenta) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }
    
}
