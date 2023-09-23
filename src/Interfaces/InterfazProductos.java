/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelo.Productos;
import java.util.List;

public interface InterfazProductos {

    public boolean RegistrarProductos(Productos productos);

    public List listarProductos();

    public boolean eliminarProductos(int id);

    public boolean modificarProductos(Productos productos);

    public Productos buscarProductos(String codigoProducto);
        
    public boolean ubicarProducto(String codigo,String descripcion);
}
