/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExpresionesRegulares;

public class ControladorExpresionesRegulares {
    
//    public static void main(String[] args) {
//        ControladorExpresionesRegulares controladorExpresionesRegulares = new ControladorExpresionesRegulares();
//        System.out.println("Nombre-->" + controladorExpresionesRegulares.limitanteNombre("Josue Sauca"));
//        System.out.println("Cedula-->" + controladorExpresionesRegulares.limitanteCedula("1150329884"));
//        System.out.println("Telefono-->" + controladorExpresionesRegulares.limitanteTelefono("2563462"));
//        System.out.println("Usuario-->" + controladorExpresionesRegulares.limitanteUsuario("josue123"));
//        System.out.println("Contrasenia-->" + controladorExpresionesRegulares.limitanteContrasenia("josue123"));
//        System.out.println("Correo -->"+controladorExpresionesRegulares.limitanteCorreo("hola@unl.edu.ec"));
//
//    }

    public boolean limitanteNombre(String nombres) {
        return nombres.matches("^([A-Z]{1}[a-z]+[ ]?){1,2}$");
    }

    public boolean limitanteCedula(String cedula) {
        return cedula.matches("^[0-9]{10}$");
    }

    public boolean limitanteTelefono(String telefono) {
        return telefono.matches("^([0-9]{1})[0-9]{6}$");
    }

    public boolean limitanteUsuario(String user) {
        return user.matches("^[A-Za-z0-9]{1,10}$");
    }
    
    public boolean limitanteContrasenia(String password){
        return password.matches(".{1,8}$");
    }

    public boolean limitanteCorreo(String correo){
        return correo.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
}
