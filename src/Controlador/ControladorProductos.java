/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import Interfaces.*;

public class ControladorProductos implements InterfazProductos {

    Connection connectionDB;
    Conexion conexion = new Conexion();
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Productos crearProductos(String codigo, String nombre, String proveedor, int stock, double precio) {
        Productos productos = new Productos(codigo, nombre, proveedor, stock, precio);
        return productos;
    }

    public Detalle detalleVenta(String codigoProducto, int cantidad, double precioVenta, int id_venta) {
        Detalle detalle = new Detalle(codigoProducto, cantidad, precioVenta, id_venta);
        return detalle;
    }

    @Override
    public boolean RegistrarProductos(Productos productos) {
        String sqlConnection = "INSERT INTO productos(codigo,nombre,proveedor,stock,precio) VALUES (?,?,?,?,?)";

        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, productos.getCodigo());
            preparedStatement.setString(2, productos.getNombre());
            preparedStatement.setString(3, productos.getProveedor());
            preparedStatement.setInt(4, productos.getStock());
            preparedStatement.setString(5, productos.getCodigo());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Se ha registrado el Producto", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public List listarProductos() {
        List<Productos> listaProductos = new ArrayList<>();
        String sqlConnection = "SELECT * FROM productos";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Productos productos = new Productos();
                productos.setId(resultSet.getInt("id"));
                productos.setCodigo(resultSet.getString("codigo"));
                productos.setNombre(resultSet.getString("nombre"));
                productos.setProveedor(resultSet.getString("proveedor"));
                productos.setStock(resultSet.getInt("stock"));
                productos.setPrecio(resultSet.getDouble("precio"));
                listaProductos.add(productos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaProductos;
    }

    @Override
    public boolean eliminarProductos(int id) {
        String sqlConnection = "DELETE FROM productos WHERE id = ?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public boolean modificarProductos(Productos productos) {
        String sqlConnection = "UPDATE productos SET codigo =?,nombre=?,proveedor =?,stock =? , precio =? WHERE id =?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, productos.getCodigo());
            preparedStatement.setString(2, productos.getNombre());
            preparedStatement.setString(3, productos.getProveedor());
            preparedStatement.setInt(4, productos.getStock());
            preparedStatement.setDouble(5, productos.getPrecio());
            preparedStatement.setInt(6, productos.getId());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Datos Actualizados!!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public Productos buscarProductos(String codigoProducto) {
        Productos producto = new Productos();
        String sqlConnection = "SELECT * FROM productos WHERE codigo = ?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, codigoProducto);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                producto.setNombre(resultSet.getString("nombre"));
                producto.setStock(resultSet.getInt("stock"));
                producto.setPrecio(resultSet.getDouble("precio"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return producto;
    }

    @Override
    public boolean ubicarProducto(String codigo, String descripcion) {
        List<Productos> listaProveedores = listarProductos();
        int i = 0;
        boolean bandera = true;
        String codigoProducto, descripcionProducto;
        try {
            while (bandera && i < listaProveedores.size()) {
                codigoProducto = listaProveedores.get(i).getCodigo();
                descripcionProducto = listaProveedores.get(i).getNombre();
                if (codigoProducto.equals(codigo)) {
                    if (descripcionProducto.equals(descripcion)) {
                        bandera = false;
                    }
                }
                i++;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return bandera;
    }
}
