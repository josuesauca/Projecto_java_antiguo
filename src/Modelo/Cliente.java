/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

public class Cliente extends Persona{
    
    private int dni;

    public Cliente(){
    }

    public Cliente(int dni, String nombre, int telefono, String razon, String direccion) {
        super(nombre, telefono, direccion, razon);
        this.dni = dni;
    }
    
    public Cliente(int dni, int id, String nombre, int telefono, String direccion, String razon) {
        super(id, nombre, telefono, direccion, razon);
        this.dni = dni;
    }
    
    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getDni() {
        return dni;
    }
}
