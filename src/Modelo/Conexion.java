/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.*;
import javax.swing.*;

public class Conexion {
    Connection connection;
    public Connection getConnection(){
        try{
            String myBD = "jdbc:mysql://localhost:3306/sistemaventa?serverTimezone=UTC";
            connection = DriverManager.getConnection(myBD,"root","");
            return connection;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }    
}
