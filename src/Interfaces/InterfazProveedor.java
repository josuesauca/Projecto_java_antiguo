package Interfaces;

import Modelo.Proveedor;
import java.util.List;
import javax.swing.JComboBox;

public interface InterfazProveedor {

    public boolean registrarProveedor(Proveedor proveedor);

    public List listarProveedor();

    public boolean eliminarProveedor(int id);

    public boolean modificarProveedor(Proveedor proveedor);

    public void consultarProveedor(JComboBox proveedor);

    public boolean ubicarProveedor(int ruc, String nombre);

}
