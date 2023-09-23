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

public class ControladorProveedor implements InterfazProveedor {

    Connection connectionDB;
    Conexion conexion = new Conexion();
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public Proveedor guardarProveedor(int ruc, String nombre, int telefono, String direccion, String razon) {
        Proveedor proveedor = new Proveedor(ruc, nombre, telefono, direccion, razon);
        return proveedor;
    }

    @Override
    public boolean registrarProveedor(Proveedor proveedor) {
        String sqlConnection = "INSERT INTO proveedor(ruc,nombre,telefono,direccion,razon) VALUES (?,?,?,?,?)";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setInt(1, proveedor.getRuc());
            preparedStatement.setString(2, proveedor.getNombre());
            preparedStatement.setInt(3, proveedor.getTelefono());
            preparedStatement.setString(4, proveedor.getDireccion());
            preparedStatement.setString(5, proveedor.getRazon());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Proveedor Registrado", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
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
    public List listarProveedor() {
        List<Proveedor> listaProveedor = new ArrayList<>();
        String sqlConnection = "SELECT * FROM proveedor";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(resultSet.getInt("id"));
                proveedor.setRuc(resultSet.getInt("ruc"));
                proveedor.setNombre(resultSet.getString("nombre"));
                proveedor.setTelefono(resultSet.getInt("telefono"));
                proveedor.setDireccion(resultSet.getString("direccion"));
                proveedor.setRazon(resultSet.getString("razon"));
                listaProveedor.add(proveedor);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaProveedor;
    }

    @Override
    public boolean eliminarProveedor(int id) {
        String sqlConnection = "DELETE FROM proveedor WHERE id = ?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Proveedor Eliminado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
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
    public boolean modificarProveedor(Proveedor proveedor) {
        String sqlConnection = "UPDATE proveedor SET ruc = ?, nombre = ?,telefono= ?,direccion = ?,razon =? WHERE id = ?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setInt(1, proveedor.getRuc());
            preparedStatement.setString(2, proveedor.getNombre());
            preparedStatement.setInt(3, proveedor.getTelefono());
            preparedStatement.setString(4, proveedor.getDireccion());
            preparedStatement.setString(5, proveedor.getRazon());
            preparedStatement.setInt(6, proveedor.getId());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Informacion Actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
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
    public void consultarProveedor(JComboBox proveedor) {
        String sqlConnection = "SELECT nombre FROM proveedor";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                proveedor.addItem(resultSet.getString("nombre"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                connectionDB.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public boolean ubicarProveedor(int ruc, String nombre) {
        List<Proveedor> listaProveedores = listarProveedor();
        int i = 0, rucProveedor = 0;
        boolean bandera = true;
        String nombreProveedor;
        try {
            while (bandera && i < listaProveedores.size()) {
                rucProveedor = listaProveedores.get(i).getRuc();
                nombreProveedor = listaProveedores.get(i).getNombre();
                if (rucProveedor == ruc) {
                    if (nombreProveedor.equals(nombre)) {
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
