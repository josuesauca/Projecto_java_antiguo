/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Modelo.Cliente;
import java.util.List;

public interface InterfazCliente {

    public boolean registrarCliente(Cliente cliente);

    public List listarCliente();

    public boolean eliminarCliente(int id);

    public boolean modificarCliente(Cliente cliente);

    public Cliente buscarCliente(int dni);

    public boolean ubicarCliente(int dni, String nombre);
}
