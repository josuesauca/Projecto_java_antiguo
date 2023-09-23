/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import Interfaces.*;

public class ControladorVentas implements InterfazVentas{

    Connection connectionDB;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Conexion conexion = new Conexion();

    public ControladorVentas() {
    }
    
    public Venta crearVenta(String clienteVenta, String vendedorVenta, double totalVenta) {
        Venta venta = new Venta(clienteVenta, vendedorVenta, totalVenta);
        return venta;
    }

    @Override
    public boolean RegistrarVenta(Venta venta) {
        String sqlConecction = "INSERT INTO ventas (cliente,vendedor,total) VALUES (?,?,?)";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConecction);
            preparedStatement.setString(1, venta.getClienteVenta());
            preparedStatement.setString(2, venta.getVendedorVenta());
            preparedStatement.setDouble(3, venta.getTotalVenta());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public boolean RegistrarDetalle(Detalle detalle) {
        String sqlConnection = "INSERT INTO detalle(cod_producto,cantidad,precio,id_venta) VALUES (?,?,?,?)";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, detalle.getCodigoProducto());
            preparedStatement.setInt(2, detalle.getCantidad());
            preparedStatement.setDouble(3, detalle.getPrecioVenta());
            preparedStatement.setInt(4, detalle.getId_venta());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public int id_Venta() {
        int id = 0;
        String sqlConnection = "SELECT MAX(id) FROM ventas";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        return id;
    }

    @Override
    public boolean actualizarStock(int cantidad, String codigo) {
        String sqlConnection = "UPDATE productos SET stock = ? WHERE codigo =?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setInt(1, cantidad);
            preparedStatement.setString(2, codigo);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public List listarVentas() {
        List<Venta> listaVenta = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Venta venta = new Venta();
                venta.setId(resultSet.getInt("id"));
                venta.setClienteVenta(resultSet.getString("cliente"));
                venta.setVendedorVenta(resultSet.getString("vendedor"));
                venta.setTotalVenta(resultSet.getDouble("total"));
                listaVenta.add(venta);
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
        return listaVenta;
    }
}
