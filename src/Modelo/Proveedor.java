/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

public class Proveedor extends Persona {

    private int ruc;
    
    public Proveedor() {
    }
    
    public Proveedor(int ruc,String nombre, int telefono,String direccion, String razon){
        super(nombre,telefono,direccion,razon);
        this.ruc = ruc;
    }

    public void setRuc(int ruc) {
        this.ruc = ruc;
    }

    public int getRuc() {
        return ruc;
    }
}
