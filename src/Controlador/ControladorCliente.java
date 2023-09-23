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

public class ControladorCliente implements InterfazCliente {

    Conexion conexion = new Conexion();
    Connection connectionDB;
    PreparedStatement prepareStatement;
    ResultSet resultSet;
    
    public Cliente crearCliente(int dni,String nombre, int telefono, String razon,String direccion) {
        Cliente cliente = new Cliente(dni, nombre, telefono, direccion, razon);
        return cliente;
    }

    @Override
    public boolean registrarCliente(Cliente cliente) {
        String sqlConnection = "INSERT INTO clientes (dni,nombre,telefono,direccion,razon) VALUES (?,?,?,?,?) ";
        try {
            connectionDB = conexion.getConnection();
            prepareStatement = connectionDB.prepareStatement(sqlConnection);
            prepareStatement.setInt(1, cliente.getDni());
            prepareStatement.setString(2, cliente.getNombre());
            prepareStatement.setInt(3, cliente.getTelefono());
            prepareStatement.setString(4, cliente.getDireccion());
            prepareStatement.setString(5, cliente.getRazon());
            prepareStatement.execute();
            JOptionPane.showMessageDialog(null, "Cliente Registrado", "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
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
    public List listarCliente() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sqlConnection = "SELECT * FROM clientes";
        try {
            connectionDB = conexion.getConnection();
            prepareStatement = connectionDB.prepareStatement(sqlConnection);
            resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(resultSet.getInt("id"));
                cliente.setDni(resultSet.getInt("dni"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setTelefono(resultSet.getInt("telefono"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setRazon(resultSet.getString("razon"));
                listaClientes.add(cliente);
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
        return listaClientes;
    }

    @Override
    public boolean eliminarCliente(int id) {
        String sqlConnection = "DELETE FROM clientes WHERE id = ?";
        try {
            connectionDB = conexion.getConnection();
            prepareStatement = connectionDB.prepareStatement(sqlConnection);
            prepareStatement.setInt(1, id);
            prepareStatement.execute();
            JOptionPane.showMessageDialog(null, "Cliente Eliminado","Mensaje",JOptionPane.INFORMATION_MESSAGE);
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
    public boolean modificarCliente(Cliente cliente) {

        String sqlConnection = "UPDATE clientes SET dni =?,nombre=?,telefono =?,direccion =? , razon =? WHERE id =?";
        try {
            connectionDB = conexion.getConnection();
            prepareStatement = connectionDB.prepareStatement(sqlConnection);

            prepareStatement.setInt(1, cliente.getDni());
            prepareStatement.setString(2, cliente.getNombre());
            prepareStatement.setInt(3, cliente.getTelefono());
            prepareStatement.setString(4, cliente.getDireccion());
            prepareStatement.setString(5, cliente.getRazon());
            prepareStatement.setInt(6, cliente.getId());
            prepareStatement.execute();
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
    public Cliente buscarCliente(int dni) {
        Cliente cliente = new Cliente();
        String sqlConnection = "SELECT * FROM clientes WHERE dni = ?";
        try {
            connectionDB = conexion.getConnection();
            prepareStatement = connectionDB.prepareStatement(sqlConnection);
            prepareStatement.setInt(1, dni);
            resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setTelefono(Integer.parseInt(resultSet.getString("telefono")));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setRazon(resultSet.getString("razon"));
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
        return cliente;
    }
    
    @Override
    public boolean ubicarCliente(int dni,String nombre){
        List<Cliente> listaClientes = listarCliente();
        int i = 0, cedulaCliente = 0;
        boolean bandera = true;
        String nombreCliente;
        try{
            while (bandera && i< listaClientes.size()) {
                cedulaCliente = listaClientes.get(i).getDni();
                nombreCliente = listaClientes.get(i).getNombre();
                if(cedulaCliente == dni ){
                    if(nombreCliente.equals(nombre)){
                        bandera = false;
                    }
                }
                i++;
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return bandera;
    }

}
