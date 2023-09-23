/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.ConfiguracionDatosEmpresa;
import java.sql.*;
import javax.swing.JOptionPane;
import Interfaces.*;

public class ControladorDatosEmpresa implements InterfazDatosEmpresa {

    Connection connectionDB;
    Conexion conexion = new Conexion();
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public ConfiguracionDatosEmpresa ingresoDatosEmpresa(int id, int ruc, String nombre, int telefono, String direccion, String razon) {
        ConfiguracionDatosEmpresa configuracionDatosEmpresa = new ConfiguracionDatosEmpresa(id, ruc, nombre, telefono, direccion, razon);
        return configuracionDatosEmpresa;
    }

    @Override
    public ConfiguracionDatosEmpresa buscarDatos() {
        ConfiguracionDatosEmpresa configuracionDatosEmpresa = new ConfiguracionDatosEmpresa();
        String sqlConnection = "SELECT * FROM config";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                configuracionDatosEmpresa.setId(resultSet.getInt("id"));
                configuracionDatosEmpresa.setRuc(resultSet.getInt("ruc"));
                configuracionDatosEmpresa.setNombre(resultSet.getString("nombre"));
                configuracionDatosEmpresa.setTelefono(resultSet.getInt("telefono"));
                configuracionDatosEmpresa.setDireccion(resultSet.getString("direccion"));
                configuracionDatosEmpresa.setRazon(resultSet.getString("razon"));
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
        return configuracionDatosEmpresa;
    }

    @Override
    public boolean modificarDatosEmpresa(ConfiguracionDatosEmpresa configuracionDatosEmpresa) {
        String sqlConnection = "UPDATE config SET nombre =?,ruc=?,telefono=?,direccion=? , razon=? WHERE id =?";
        try {
            connectionDB = conexion.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, configuracionDatosEmpresa.getNombre());
            preparedStatement.setInt(2, configuracionDatosEmpresa.getRuc());
            preparedStatement.setInt(3, configuracionDatosEmpresa.getTelefono());
            preparedStatement.setString(4, configuracionDatosEmpresa.getDireccion());
            preparedStatement.setString(5, configuracionDatosEmpresa.getRazon());
            preparedStatement.setInt(6, configuracionDatosEmpresa.getId());
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

}
