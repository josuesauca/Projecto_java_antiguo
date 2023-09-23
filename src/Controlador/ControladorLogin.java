/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ClaseLogin;
import Modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ControladorLogin {

    Connection connectionDB;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Conexion connection = new Conexion();

    public ClaseLogin ingresarLogin(String correo, String pass) {
        ClaseLogin ingresoLogin = new ClaseLogin();
        String sqlConnection = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        try {
            connectionDB = connection.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ingresoLogin.setId(resultSet.getInt("id"));
                ingresoLogin.setNombre(resultSet.getString("nombre"));
                ingresoLogin.setCorreo(resultSet.getString("correo"));
                ingresoLogin.setPass(resultSet.getString("pass"));
                ingresoLogin.setRol(resultSet.getString("rol"));
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
        return ingresoLogin;
    }

    public boolean registrarCuenta(ClaseLogin registroDatos) {
        String sqlConnection = "INSERT INTO usuarios(nombre,correo,pass,rol) VALUES(?,?,?,?)";
        try {
            connectionDB = connection.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            preparedStatement.setString(1, registroDatos.getNombre());
            preparedStatement.setString(2, registroDatos.getCorreo());
            preparedStatement.setString(3, registroDatos.getPass());
            preparedStatement.setString(4, registroDatos.getRol());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Datos Registrados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
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
    
     public List listarUsarios() {
        List<ClaseLogin> listarUsarios = new ArrayList<>();
        String sqlConnection = "SELECT * FROM usuarios";
        try {
            connectionDB = connection.getConnection();
            preparedStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClaseLogin claseLogin = new ClaseLogin();
                claseLogin.setId(resultSet.getInt("id"));
                claseLogin.setNombre(resultSet.getString("nombre"));
                claseLogin.setCorreo(resultSet.getString("correo"));
                claseLogin.setPass(resultSet.getString("pass"));
                claseLogin.setRol(resultSet.getString("rol"));
                listarUsarios.add(claseLogin);
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
        return listarUsarios;
    }
    
    public boolean ubicarUsuario(String nombreUsuario, String correoUsuario) {
        List<ClaseLogin> listaUsarios = listarUsarios();
        int i = 0;
        boolean bandera = true;
        String userName, userMail;
        try {
            while (bandera && i < listaUsarios.size()) {
                userName = listaUsarios.get(i).getNombre();
                userMail = listaUsarios.get(i).getCorreo();
                if (userName.equals(nombreUsuario)) {
                    if (userMail.equals(correoUsuario)) {
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
