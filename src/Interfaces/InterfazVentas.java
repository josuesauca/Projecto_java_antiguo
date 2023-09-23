/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelo.Detalle;
import Modelo.Venta;
import java.util.List;

public interface InterfazVentas {

    public boolean RegistrarVenta(Venta venta);

    public boolean RegistrarDetalle(Detalle detalle);

    public int id_Venta();

    public boolean actualizarStock(int cantidad, String codigo);

    public List listarVentas();

}
