/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import ExpresionesRegulares.ValidarEventos;
import Controlador.*;
import ExpresionesRegulares.ControladorExpresionesRegulares;
import Modelo.*;
import java.awt.Desktop;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.event.KeyEvent;
import java.io.*;

public class Sistema extends javax.swing.JFrame {

    //Controladores
    ControladorCliente controladorCliente = new ControladorCliente();
    ControladorProveedor controladorProveedor = new ControladorProveedor();
    ControladorVentas controladorVentas = new ControladorVentas();
    ControladorProductos controladorProductos = new ControladorProductos();
    ControladorDatosEmpresa controladorDatosEmpresa = new ControladorDatosEmpresa();
    ControladorExpresionesRegulares controladorExpresionesRegulares = new ControladorExpresionesRegulares();
    ValidarEventos validarEventos = new ValidarEventos();

    //Clases del jtable
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel modeloNuevaVenta = new DefaultTableModel();

    //Clases del Proyecto
    Cliente cliente = new Cliente();
    Proveedor proveedor = new Proveedor();
    Productos productos = new Productos();
    Venta venta = new Venta();
    Detalle detalle = new Detalle();
    ConfiguracionDatosEmpresa configuracionDatosEmpresa = new ConfiguracionDatosEmpresa();

    RegistroAcceso registroAcceso = new RegistroAcceso();

    int item;
    double totalPagar = 0;

    public Sistema() {
        initComponents();
        txtIdVentanaPrincipal.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdCliente.setVisible(false);
        txtIdProducto.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdConfiguracion.setVisible(false);
        configuracionDatosEmpresa = controladorDatosEmpresa.buscarDatos();
        labelEmpresa.setText(configuracionDatosEmpresa.getNombre());
        /*AutoCompleteDecorator.decorate(cdxProveedorProducto);
        controladorProveedor.consultarProveedor(cdxProveedorProducto);*/
        registroAcceso.datosActuales();
        listarConfiguracion();
    }

    public void accesoPersonal() {
        btnProveedor.setEnabled(false);
        btnConfiguracion.setEnabled(false);
        btnRegistrar.setEnabled(false);
        labelEmpresa.setText(configuracionDatosEmpresa.getNombre());

        /*
            Privilegios Cliente
         */
        txtDniCliente.setEnabled(false);
        txtNombreCliente.setEnabled(false);
        txtTelefonoCliente.setEnabled(false);
        txtDireccionCliente.setEnabled(false);
        txtRazonCliente.setEnabled(false);

        btnGuardarCliente.setEnabled(false);
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnNuevoCliente.setEnabled(false);
        /*
            Privilegios Productos
         */
        txtCodigoProducto.setEnabled(false);
        txtDescripcionProducto.setEnabled(false);
        txtCantidadProducto.setEnabled(false);
        txtPrecioProducto.setEnabled(false);
        cdxProveedorProducto.setEnabled(false);

        btnGuardarProducto.setEnabled(false);
        btnEditarProducto.setEnabled(false);
        btnEliminarProducto.setEnabled(false);
        btnNuevoProducto.setEnabled(false);
    }

    public Sistema(ClaseLogin claseLogin) {
        initComponents();
        configuracionDatosEmpresa = controladorDatosEmpresa.buscarDatos();
        labelEmpresa.setText(configuracionDatosEmpresa.getNombre());
        txtIdVentanaPrincipal.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdCliente.setVisible(false);
        txtIdProducto.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdConfiguracion.setVisible(false);
        registroAcceso.datosActuales();
        listarConfiguracion();

        if (claseLogin.getRol().equals("Asistente")) {
            accesoPersonal();
        } else {
            AutoCompleteDecorator.decorate(cdxProveedorProducto);
            controladorProveedor.consultarProveedor(cdxProveedorProducto);
            labelEmpresa.setText(configuracionDatosEmpresa.getNombre());
        }
    }

    public void listarCliente() {
        List<Cliente> listarClientes = controladorCliente.listarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarClientes.size(); i++) {
            ob[0] = listarClientes.get(i).getId();
            ob[1] = listarClientes.get(i).getDni();
            ob[2] = listarClientes.get(i).getNombre();
            ob[3] = listarClientes.get(i).getTelefono();
            ob[4] = listarClientes.get(i).getDireccion();
            ob[5] = listarClientes.get(i).getRazon();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);
    }

    public void limpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i--;
        }
    }

    public void listarProveedor() {
        List<Proveedor> listarProveedor = controladorProveedor.listarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < listarProveedor.size(); i++) {
            ob[0] = listarProveedor.get(i).getId();
            ob[1] = listarProveedor.get(i).getRuc();
            ob[2] = listarProveedor.get(i).getNombre();
            ob[3] = listarProveedor.get(i).getTelefono();
            ob[4] = listarProveedor.get(i).getDireccion();
            ob[5] = listarProveedor.get(i).getRazon();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);
    }

    public void listarProductos() {
        List<Productos> listarProductos = controladorProductos.listarProductos();
        modelo = (DefaultTableModel) TableProducto.getModel();

        Object[] ob = new Object[6];
        for (int i = 0; i < listarProductos.size(); i++) {
            ob[0] = listarProductos.get(i).getId();
            ob[1] = listarProductos.get(i).getCodigo();
            ob[2] = listarProductos.get(i).getNombre();
            ob[3] = listarProductos.get(i).getProveedor();
            ob[4] = listarProductos.get(i).getStock();
            ob[5] = listarProductos.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);
    }

    public void listarConfiguracion() {
        configuracionDatosEmpresa = controladorDatosEmpresa.buscarDatos();
        txtIdConfiguracion.setText("" + configuracionDatosEmpresa.getId());
        txtRucConfiguracion.setText("" + configuracionDatosEmpresa.getRuc());
        txtNombreConfiguracion.setText("" + configuracionDatosEmpresa.getNombre());
        txtTelefonoConfiguracion.setText("" + configuracionDatosEmpresa.getTelefono());
        txtDireccionConfiguracion.setText("" + configuracionDatosEmpresa.getDireccion());
        txtRazonConfiguracion.setText("" + configuracionDatosEmpresa.getRazon());
    }

    public void listarVentas() {
        List<Venta> listarVentas = controladorVentas.listarVentas();
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < listarVentas.size(); i++) {
            ob[0] = listarVentas.get(i).getId();
            ob[1] = listarVentas.get(i).getClienteVenta();
            ob[2] = listarVentas.get(i).getVendedorVenta();
            ob[3] = listarVentas.get(i).getTotalVenta();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfiguracion = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        labelEmpresa = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        txtRazonCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        txtIdCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        txtDescripcionProducto = new javax.swing.JTextField();
        txtCantidadProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        cdxProveedorProducto = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        btnEditarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnNuevoProducto = new javax.swing.JButton();
        txtIdProducto = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnPdfVentas = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jPanelCliente = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnEliminarVenta = new javax.swing.JButton();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVenta = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtRucVenta = new javax.swing.JTextField();
        txtNombreClienteVenta = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtTelefonoClienteVenta = new javax.swing.JTextField();
        txtDireccionClienteVenta = new javax.swing.JTextField();
        txtRazonClienteVenta = new javax.swing.JTextField();
        txtIdVentanaPrincipal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanelProveedor = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtRucProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtRazonProveedor = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        txtGuardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtRucConfiguracion = new javax.swing.JTextField();
        txtNombreConfiguracion = new javax.swing.JTextField();
        txtTelefonoConfiguracion = new javax.swing.JTextField();
        txtDireccionConfiguracion = new javax.swing.JTextField();
        txtRazonConfiguracion = new javax.swing.JTextField();
        btnActualizarConfiguracion = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        txtIdConfiguracion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(30, 128, 157));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNuevaVenta.setBackground(new java.awt.Color(213, 214, 235));
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/producto.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 200, 50));

        btnClientes.setBackground(new java.awt.Color(213, 214, 235));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cliente.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jPanel1.add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 200, 50));

        btnProveedor.setBackground(new java.awt.Color(213, 214, 235));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        jPanel1.add(btnProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 200, 50));

        btnProductos.setBackground(new java.awt.Color(213, 214, 235));
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/paquete.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        jPanel1.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 200, 50));

        btnVentas.setBackground(new java.awt.Color(213, 214, 235));
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bienes.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        jPanel1.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 200, 50));

        btnConfiguracion.setBackground(new java.awt.Color(213, 214, 235));
        btnConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/configuraciones.png"))); // NOI18N
        btnConfiguracion.setText("Configuracion");
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });
        jPanel1.add(btnConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 200, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/default_avatar.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 130));

        labelEmpresa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        labelEmpresa.setText("Empresa XYZ");
        jPanel1.add(labelEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, 16));

        btnRegistrar.setBackground(new java.awt.Color(213, 214, 235));
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar-usuario.png"))); // NOI18N
        btnRegistrar.setText("Registrar Acceso");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 200, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 600));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 880, 140));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("DNI/RUC:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 80, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("NOMBRE :");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 80, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("TELEFONO :");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("DIRECCION :");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, 30));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("RAZON SOCIAL : ");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, 30));

        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 150, 30));

        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 150, 30));

        txtTelefonoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 150, 30));
        jPanel3.add(txtDireccionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 150, 30));

        txtRazonCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonClienteKeyTyped(evt);
            }
        });
        jPanel3.add(txtRazonCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 150, 30));

        btnGuardarCliente.setBackground(new java.awt.Color(255, 255, 253));
        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/floppy.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 110, 70));

        btnEditarCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizado.png"))); // NOI18N
        btnEditarCliente.setBorder(null);
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnEditarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 110, 70));

        btnEliminarCliente.setBackground(new java.awt.Color(255, 255, 253));
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 110, 70));

        btnNuevoCliente.setBackground(new java.awt.Color(255, 255, 253));
        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-file.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 110, 70));

        txtIdCliente.setEnabled(false);
        jPanel3.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 26, 30));

        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI/RCU", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableCliente.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableCliente.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableCliente.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 540, 430));

        jTabbedPane1.addTab("tab2", jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("CODIGO:");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 80, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("DESCRIPCION:");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("CANTIDAD:");
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, 90, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setText("PRECIO:");
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 62, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("PROVEEDOR:");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 110, 30));

        txtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyTyped(evt);
            }
        });
        jPanel5.add(txtCodigoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 130, 30));

        txtDescripcionProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionProductoKeyTyped(evt);
            }
        });
        jPanel5.add(txtDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 130, 30));

        txtCantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProductoKeyTyped(evt);
            }
        });
        jPanel5.add(txtCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 130, 30));

        txtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductoKeyTyped(evt);
            }
        });
        jPanel5.add(txtPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 130, 30));

        cdxProveedorProducto.setEditable(true);
        jPanel5.add(cdxProveedorProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 150, 30));

        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCION", "PROVEEDOR", "CANTIDAD", "PRECIO"
            }
        ));
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(1).setPreferredWidth(50);
            TableProducto.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProducto.getColumnModel().getColumn(3).setPreferredWidth(60);
            TableProducto.getColumnModel().getColumn(4).setPreferredWidth(40);
            TableProducto.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 540, 430));

        btnGuardarProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/floppy.png"))); // NOI18N
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });
        jPanel5.add(btnGuardarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 110, 70));

        btnEditarProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnEditarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizado.png"))); // NOI18N
        btnEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProductoActionPerformed(evt);
            }
        });
        jPanel5.add(btnEditarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 110, 70));

        btnEliminarProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jPanel5.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 110, 70));

        btnNuevoProducto.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevoProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-file.png"))); // NOI18N
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });
        jPanel5.add(btnNuevoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 110, 70));

        txtIdProducto.setEnabled(false);
        jPanel5.add(txtIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 30, 30));

        jTabbedPane1.addTab("tab4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setPreferredWidth(20);
            TableVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(2).setPreferredWidth(60);
            TableVentas.getColumnModel().getColumn(3).setPreferredWidth(60);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 840, 340));

        btnPdfVentas.setBackground(new java.awt.Color(255, 255, 255));
        btnPdfVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf.png"))); // NOI18N
        btnPdfVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfVentasActionPerformed(evt);
            }
        });
        jPanel6.add(btnPdfVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, 80));
        jPanel6.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 40, 30));

        jTabbedPane1.addTab("tab5", jPanel6);

        jPanelCliente.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCliente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Codigo");
        jPanelCliente.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Descripcion");
        jPanelCliente.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Cantidad");
        jPanelCliente.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Precio");
        jPanelCliente.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 255));
        jLabel7.setText("Stock Disponible");
        jPanelCliente.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, -1, -1));

        btnEliminarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVentaActionPerformed(evt);
            }
        });
        jPanelCliente.add(btnEliminarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, -1, 70));

        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });
        jPanelCliente.add(txtCodigoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 100, 30));

        txtDescripcionVenta.setEnabled(false);
        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });
        jPanelCliente.add(txtDescripcionVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 204, 30));

        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });
        jPanelCliente.add(txtCantidadVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 90, 30));

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setEnabled(false);
        jPanelCliente.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 80, 30));

        txtStockDisponible.setText(" ");
        txtStockDisponible.setEnabled(false);
        jPanelCliente.add(txtStockDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, 110, 30));

        tableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tableVenta);
        if (tableVenta.getColumnModel().getColumnCount() > 0) {
            tableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        jPanelCliente.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 800, 220));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("DNI/RUC");
        jPanelCliente.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("NOMBRE");
        jPanelCliente.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, -1, -1));

        txtRucVenta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtRucVenta.setText("                                 ");
        txtRucVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtRucVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucVentaKeyTyped(evt);
            }
        });
        jPanelCliente.add(txtRucVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 90, 30));

        txtNombreClienteVenta.setEditable(false);
        txtNombreClienteVenta.setText("                               ");
        txtNombreClienteVenta.setEnabled(false);
        jPanelCliente.add(txtNombreClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 100, 30));

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/printer.png"))); // NOI18N
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanelCliente.add(btnGenerarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 350, 100, 80));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salary.png"))); // NOI18N
        jLabel10.setText("TOTAL A PAGAR");
        jPanelCliente.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 380, 140, 30));

        LabelTotal.setText("--------------");
        jPanelCliente.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 400, 40, -1));

        txtTelefonoClienteVenta.setEnabled(false);
        jPanelCliente.add(txtTelefonoClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 80, 30));

        txtDireccionClienteVenta.setEnabled(false);
        jPanelCliente.add(txtDireccionClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 380, 90, 30));

        txtRazonClienteVenta.setEnabled(false);
        jPanelCliente.add(txtRazonClienteVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 380, 90, 30));

        txtIdVentanaPrincipal.setEnabled(false);
        jPanelCliente.add(txtIdVentanaPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, 30, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("TELEFONO");
        jPanelCliente.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("DIRECCION");
        jPanelCliente.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel34.setText("RAZON");
        jPanelCliente.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, -1, -1));

        jTabbedPane1.addTab("tab1", jPanelCliente);

        jPanelProveedor.setBackground(new java.awt.Color(255, 255, 255));
        jPanelProveedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("RUC/CEDULA:");
        jPanelProveedor.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 110, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("NOMBRE :");
        jPanelProveedor.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("TELEFONO:");
        jPanelProveedor.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("DIRECCION:");
        jPanelProveedor.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("RAZON SOCIAL:");
        jPanelProveedor.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, 30));

        txtRucProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 150, 30));

        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 150, 30));

        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 150, 30));
        jPanelProveedor.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 150, 30));

        txtRazonProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtRazonProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 150, 30));

        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELEFONO", "DIRECCION", "RAZON SOCIAL"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(1).setPreferredWidth(40);
            TableProveedor.getColumnModel().getColumn(2).setPreferredWidth(100);
            TableProveedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            TableProveedor.getColumnModel().getColumn(4).setPreferredWidth(80);
            TableProveedor.getColumnModel().getColumn(5).setPreferredWidth(70);
        }

        jPanelProveedor.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 540, 430));

        txtGuardarProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtGuardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/floppy.png"))); // NOI18N
        txtGuardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGuardarProveedorActionPerformed(evt);
            }
        });
        jPanelProveedor.add(txtGuardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 110, 70));

        btnEditarProveedor.setBackground(new java.awt.Color(255, 255, 255));
        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizado.png"))); // NOI18N
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });
        jPanelProveedor.add(btnEditarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 110, 70));

        btnEliminarProveedor.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanelProveedor.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 110, 70));

        btnNuevoProveedor.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add-file.png"))); // NOI18N
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });
        jPanelProveedor.add(btnNuevoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 110, 70));

        txtIdProveedor.setEnabled(false);
        jPanelProveedor.add(txtIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 30, 30));

        jTabbedPane1.addTab("tab3", jPanelProveedor);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(148, 169, 232));
        jLabel27.setText("RUC/CEDULA");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 110, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(148, 169, 232));
        jLabel28.setText("NOMBRE DE LA EMPRESA");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 147, 190, 30));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(148, 169, 232));
        jLabel29.setText("TELEFONO");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 147, 80, 30));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(148, 169, 232));
        jLabel30.setText("DIRECCION");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 90, 30));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(148, 169, 232));
        jLabel31.setText("RAZON SOCIAL");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 120, 30));

        txtRucConfiguracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucConfiguracionKeyTyped(evt);
            }
        });
        jPanel7.add(txtRucConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 140, 30));
        jPanel7.add(txtNombreConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 190, 30));

        txtTelefonoConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoConfiguracionActionPerformed(evt);
            }
        });
        txtTelefonoConfiguracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoConfiguracionKeyTyped(evt);
            }
        });
        jPanel7.add(txtTelefonoConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 160, 30));
        jPanel7.add(txtDireccionConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 140, 30));

        txtRazonConfiguracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonConfiguracionKeyTyped(evt);
            }
        });
        jPanel7.add(txtRazonConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 190, 30));

        btnActualizarConfiguracion.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizarConfiguracion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizarConfiguracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizado.png"))); // NOI18N
        btnActualizarConfiguracion.setText("ACTUALIZAR");
        btnActualizarConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfiguracionActionPerformed(evt);
            }
        });
        jPanel7.add(btnActualizarConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 350, 200, 70));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(148, 169, 232));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("DATOS DE LA EMPRESA");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, -1, -1));
        jPanel7.add(txtIdConfiguracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 50, 30));

        jTabbedPane1.addTab("tab6", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 950, 500));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logout.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, 70, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed

        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (txtDniCliente.getText().equals("") || txtNombreCliente.getText().equals("") || txtTelefonoCliente.getText().equals("") || txtDireccionCliente.getText().equals("") || txtRazonCliente.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor llene todos los parametros", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar los datos de la fila seleccionada", "Titulo", JOptionPane.INFORMATION_MESSAGE);

                boolean validarCedula = controladorExpresionesRegulares.limitanteCedula(txtDniCliente.getText());
                boolean validarTelefono = controladorExpresionesRegulares.limitanteTelefono(txtTelefonoCliente.getText());
                boolean validarNombre = controladorExpresionesRegulares.limitanteNombre(txtNombreCliente.getText());

                if (pregunta == 0) {
                    if (validarCedula == false) {
                        JOptionPane.showMessageDialog(null, "Cedula no Valida", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (validarTelefono == false) {
                            JOptionPane.showMessageDialog(null, "Telefono no valido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            if (validarNombre == false) {
                                JOptionPane.showMessageDialog(null, "Nombre no Valido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                cliente.setDni(Integer.parseInt(txtDniCliente.getText()));
                                cliente.setNombre(txtNombreCliente.getText());
                                cliente.setTelefono(Integer.parseInt(txtTelefonoCliente.getText()));
                                cliente.setDireccion(txtDireccionCliente.getText());
                                cliente.setRazon(txtRazonCliente.getText());
                                cliente.setId(Integer.parseInt(txtIdCliente.getText()));

                                controladorCliente.modificarCliente(cliente);
                                limpiarTabla();
                                limpiarCliente();
                                listarCliente();
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void txtTelefonoConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoConfiguracionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoConfiguracionActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        if (txtDniCliente.getText().equals("") || txtNombreCliente.getText().equals("") || txtTelefonoCliente.getText().equals("") || txtDireccionCliente.getText().equals("") || txtRazonCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Los Campos estan vacios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int dniCliente = Integer.parseInt(txtDniCliente.getText());
            int telefonoCliente = Integer.parseInt(txtTelefonoCliente.getText());
            boolean bandera = controladorCliente.ubicarCliente(dniCliente, txtNombreCliente.getText());

            boolean validarCedula = controladorExpresionesRegulares.limitanteCedula(txtDniCliente.getText());
            boolean validarTelefono = controladorExpresionesRegulares.limitanteTelefono(txtTelefonoCliente.getText());
            boolean validarNombre = controladorExpresionesRegulares.limitanteNombre(txtNombreCliente.getText());

            if (bandera == false) {
                JOptionPane.showMessageDialog(null, "El Cliente ya Existe", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (validarCedula == false) {
                    JOptionPane.showMessageDialog(null, "Cedula no Valida", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (validarTelefono == false) {
                        JOptionPane.showMessageDialog(null, "Telefono no valido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (validarNombre == false) {
                            JOptionPane.showMessageDialog(null, "Nombre no Valido", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            cliente = controladorCliente.crearCliente(dniCliente, txtNombreCliente.getText(), telefonoCliente, txtDireccionCliente.getText(), txtRazonCliente.getText());
                            controladorCliente.registrarCliente(cliente);
                        }
                    }
                }
            }
            limpiarTabla();
            limpiarCliente();
            listarCliente();
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        limpiarTabla();
        listarCliente();
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        int fila = TableCliente.rowAtPoint(evt.getPoint());

        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDireccionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
        txtRazonCliente.setText(TableCliente.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        if (txtIdCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No ha elegido ninguna fila", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                controladorCliente.eliminarCliente(id);
                limpiarTabla();
                limpiarCliente();
                listarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        limpiarCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void txtGuardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGuardarProveedorActionPerformed
        if (txtRucProveedor.getText().equals("") || txtNombreProveedor.getText().equals("") || txtTelefonoProveedor.getText().equals("") || txtDireccionProveedor.getText().equals("") || txtRazonProveedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Los Campos estan vacios", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int rucProveedor = Integer.parseInt(txtRucProveedor.getText());
            String nombreProveedor = txtNombreProveedor.getText();
            boolean bandera = controladorProveedor.ubicarProveedor(rucProveedor, nombreProveedor);

            boolean validarRuc = controladorExpresionesRegulares.limitanteCedula(txtRucProveedor.getText());
            boolean validarNombre = controladorExpresionesRegulares.limitanteNombre(nombreProveedor);
            boolean validarTelefono = controladorExpresionesRegulares.limitanteTelefono(txtTelefonoProveedor.getText());

            if (bandera == false) {
                JOptionPane.showMessageDialog(null, "El Proveedor ya Existe", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (validarRuc == false) {
                    JOptionPane.showMessageDialog(null, "El Ruc no es Valido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (validarNombre == false) {
                        JOptionPane.showMessageDialog(null, "El Nombre no es Valido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (validarTelefono == false) {
                            JOptionPane.showMessageDialog(null, "El telefono no es Valido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                        } else {
                            proveedor = controladorProveedor.guardarProveedor(Integer.parseInt(txtRucProveedor.getText()), txtNombreProveedor.getText(), Integer.parseInt(txtTelefonoProveedor.getText()), txtDireccionProveedor.getText(), txtRazonProveedor.getText());
                            controladorProveedor.registrarProveedor(proveedor);
                        }
                    }
                }
            }

            limpiarTabla();
            limpiarProveedor();
            listarProveedor();
        }
    }//GEN-LAST:event_txtGuardarProveedorActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        limpiarTabla();
        listarProveedor();
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreProveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
        txtRazonProveedor.setText(TableProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        if (txtIdProveedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No ha elegido ninguna fila", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                controladorProveedor.eliminarProveedor(id);
                limpiarTabla();
                limpiarProveedor();
                listarProveedor();
            }
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (txtRucProveedor.getText().equals("") || txtNombreProveedor.getText().equals("") || txtTelefonoProveedor.getText().equals("") || txtDireccionProveedor.getText().equals("") || txtRazonProveedor.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor llene todos los parametros", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean validarRuc = controladorExpresionesRegulares.limitanteCedula(txtRucProveedor.getText());
                boolean validarNombre = controladorExpresionesRegulares.limitanteNombre(txtNombreProveedor.getText());
                boolean validarTelefono = controladorExpresionesRegulares.limitanteTelefono(txtTelefonoProveedor.getText());

                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar los datos de la fila seleccionada", "Titulo", JOptionPane.INFORMATION_MESSAGE);

                if (pregunta == 0) {
                    if (validarRuc == false) {
                        JOptionPane.showMessageDialog(null, "El Ruc no es Valido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (validarNombre == false) {
                            JOptionPane.showMessageDialog(null, "El Nombre no es Valido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                        } else {
                            if (validarTelefono == false) {
                                JOptionPane.showMessageDialog(null, "El telefono no es Valido", "Mensaje", JOptionPane.ERROR_MESSAGE);
                            } else {
                                proveedor.setRuc(Integer.parseInt(txtRucProveedor.getText()));
                                proveedor.setNombre(txtNombreProveedor.getText());
                                proveedor.setTelefono(Integer.parseInt(txtTelefonoProveedor.getText()));
                                proveedor.setDireccion(txtDireccionProveedor.getText());
                                proveedor.setRazon(txtRazonProveedor.getText());
                                proveedor.setId(Integer.parseInt(txtIdProveedor.getText()));

                                controladorProveedor.modificarProveedor(proveedor);
                                limpiarTabla();
                                limpiarProveedor();
                                listarProveedor();
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        limpiarProveedor();
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        if (txtCodigoProducto.getText().equals("") || txtDescripcionProducto.getText().equals("") || txtCantidadProducto.getText().equals("") || txtPrecioProducto.getText().equals("") || cdxProveedorProducto.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los parametros", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String codidoProducto = txtCodigoProducto.getText();
            String descripcionProducto = txtDescripcionProducto.getText();
            boolean bandera = controladorProductos.ubicarProducto(codidoProducto, descripcionProducto);

            if (bandera == false) {
                JOptionPane.showMessageDialog(null, "Producto ya Registrado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                productos = controladorProductos.crearProductos(txtCodigoProducto.getText(), txtDescripcionProducto.getText(), cdxProveedorProducto.getSelectedItem().toString(), Integer.parseInt(txtCantidadProducto.getText()), Double.parseDouble(txtPrecioProducto.getText()));
                controladorProductos.RegistrarProductos(productos);
            }
            limpiarTabla();
            limpiarProductos();
            listarProductos();
            AutoCompleteDecorator.decorate(cdxProveedorProducto);
            cdxProveedorProducto.removeAllItems();
            controladorProveedor.consultarProveedor(cdxProveedorProducto);
        }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        limpiarTabla();
        listarProductos();
        jTabbedPane1.setSelectedIndex(1);
        AutoCompleteDecorator.decorate(cdxProveedorProducto);
        cdxProveedorProducto.removeAllItems();
        controladorProveedor.consultarProveedor(cdxProveedorProducto);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        int fila = TableProducto.rowAtPoint(evt.getPoint());

        txtIdProducto.setText(TableProducto.getValueAt(fila, 0).toString());
        txtCodigoProducto.setText(TableProducto.getValueAt(fila, 1).toString());
        txtDescripcionProducto.setText(TableProducto.getValueAt(fila, 2).toString());
        cdxProveedorProducto.setSelectedItem(TableProducto.getValueAt(fila, 3).toString());
        txtCantidadProducto.setText(TableProducto.getValueAt(fila, 4).toString());
        txtPrecioProducto.setText(TableProducto.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_TableProductoMouseClicked

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        if (txtIdProducto.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No ha elegido ninguna fila", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProducto.getText());
                controladorProductos.eliminarProductos(id);
                limpiarTabla();
                limpiarProductos();
                listarProductos();
                AutoCompleteDecorator.decorate(cdxProveedorProducto);
                cdxProveedorProducto.removeAllItems();
                controladorProveedor.consultarProveedor(cdxProveedorProducto);
            }
        }
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void btnEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProductoActionPerformed
        if ("".equals(txtIdProducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            if (txtCodigoProducto.getText().equals("") || txtDescripcionProducto.getText().equals("") || txtCantidadProducto.getText().equals("") || txtPrecioProducto.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor llene todos los parametros", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar los datos de la fila seleccionada", "Titulo", JOptionPane.INFORMATION_MESSAGE);
                if (pregunta == 0) {
                    productos.setCodigo(txtCodigoProducto.getText());
                    productos.setNombre(txtDescripcionProducto.getText());
                    productos.setProveedor(cdxProveedorProducto.getSelectedItem().toString());
                    productos.setStock(Integer.parseInt(txtCantidadProducto.getText()));
                    productos.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
                    productos.setId(Integer.parseInt(txtIdProducto.getText()));
                    controladorProductos.modificarProductos(productos);

                    limpiarTabla();
                    limpiarProductos();
                    listarProductos();
                    AutoCompleteDecorator.decorate(cdxProveedorProducto);
                    cdxProveedorProducto.removeAllItems();
                    controladorProveedor.consultarProveedor(cdxProveedorProducto);
                }
            }
        }
    }//GEN-LAST:event_btnEditarProductoActionPerformed

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCodigoVenta.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese el codigo del Producto", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String codigoProducto = txtCodigoVenta.getText();
                productos = controladorProductos.buscarProductos(codigoProducto);
                if (productos.getNombre() != null) {
                    txtDescripcionVenta.setText("" + productos.getNombre());
                    txtPrecioVenta.setText("" + productos.getPrecio());
                    txtStockDisponible.setText("" + productos.getStock());
                    txtCantidadVenta.requestFocus();
                } else {
                    limpiarVenta();
                    JOptionPane.showMessageDialog(null, "El producto no Existe", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    txtCodigoVenta.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtCantidadVenta.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Por Favor Ingrese una cantidad", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String codigo = txtCodigoVenta.getText();
                String descripcion = txtDescripcionVenta.getText();
                int cantidad = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double totalVenta = cantidad * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());

                if (stock >= cantidad) {
                    item = item + 1;
                    modeloNuevaVenta = (DefaultTableModel) tableVenta.getModel();
                    for (int i = 0; i < tableVenta.getRowCount(); i++) {
                        if (tableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }

                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(codigo);
                    lista.add(descripcion);
                    lista.add(cantidad);
                    lista.add(precio);
                    lista.add(totalVenta);
                    Object object[] = new Object[5];

                    object[0] = lista.get(1);
                    object[1] = lista.get(2);
                    object[2] = lista.get(3);
                    object[3] = lista.get(4);
                    object[4] = lista.get(5);
                    modeloNuevaVenta.addRow(object);
                    tableVenta.setModel(modeloNuevaVenta);
                    totalPagar();
                    limpiarVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void btnEliminarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVentaActionPerformed
        try {
            modelo = (DefaultTableModel) tableVenta.getModel();
            modelo.removeRow(tableVenta.getSelectedRow());
            totalPagar();
            txtCodigoVenta.requestFocus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarVentaActionPerformed

    private void txtRucVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txtRucVenta.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Ingrese una cedula", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int dni = Integer.parseInt(txtRucVenta.getText());
                cliente = controladorCliente.buscarCliente(dni);
                if (cliente.getNombre() != null) {
                    txtNombreClienteVenta.setText("" + cliente.getNombre());
                    txtTelefonoClienteVenta.setText("" + cliente.getTelefono());
                    txtDireccionClienteVenta.setText("" + cliente.getDireccion());
                    txtRazonClienteVenta.setText("" + cliente.getRazon());
                } else {
                    txtRucVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_txtRucVentaKeyPressed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        if (tableVenta.getRowCount() > 0) {
            if (txtNombreClienteVenta.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debes Buscar algun cliente", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                registrarVenta();
                registrarDetalle();
                actualizarStock();
                generarPdf();
                limpiarVentanaPrincipal();
                limpiarClienteVenta();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped

        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void txtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductoKeyTyped
        validarEventos.numberDecimalKeyPress(evt, txtPrecioProducto);
    }//GEN-LAST:event_txtPrecioProductoKeyTyped

    private void btnActualizarConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfiguracionActionPerformed
        if (txtRucConfiguracion.getText().equals("") || txtDireccionConfiguracion.getText().equals("") || txtNombreConfiguracion.getText().equals("") || txtTelefonoConfiguracion.getText().equals("") || txtRazonConfiguracion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los parametros", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean validarRuc = controladorExpresionesRegulares.limitanteCedula(txtRucConfiguracion.getText());
            boolean validarNombreEmpresa = controladorExpresionesRegulares.limitanteNombre(txtNombreConfiguracion.getText());
            boolean validarTelefono = controladorExpresionesRegulares.limitanteTelefono(txtTelefonoConfiguracion.getText());

            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar los datos", "Titulo", JOptionPane.INFORMATION_MESSAGE);
            if (pregunta == 0) {
                if (validarRuc == false) {
                    JOptionPane.showMessageDialog(null, "Ruc no Valido", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (validarNombreEmpresa == false) {
                        JOptionPane.showMessageDialog(null, "Nombre no Valido", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (validarTelefono == false) {
                            JOptionPane.showMessageDialog(null, "Telefono no Valido", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            configuracionDatosEmpresa = controladorDatosEmpresa.ingresoDatosEmpresa(Integer.parseInt(txtIdConfiguracion.getText()), Integer.parseInt(txtRucConfiguracion.getText()), txtNombreConfiguracion.getText(), Integer.parseInt(txtTelefonoConfiguracion.getText()), txtDireccionConfiguracion.getText(), txtRazonConfiguracion.getText());
                            controladorDatosEmpresa.modificarDatosEmpresa(configuracionDatosEmpresa);
                            listarConfiguracion();
                            labelEmpresa.setText(configuracionDatosEmpresa.getNombre());

                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnActualizarConfiguracionActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        limpiarTabla();
        listarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnPdfVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentasActionPerformed
        try {
            int id = Integer.parseInt(txtIdVenta.getText());
            File file = new File("src/ReportesPdf/venta" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPdfVentasActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        RegistroAcceso registroClientes = new RegistroAcceso();
        registroClientes.setVisible(true);
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        limpiarProductos();
    }//GEN-LAST:event_btnNuevoProductoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        LoginInicio loginInicio = new LoginInicio();
        loginInicio.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtRazonClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonClienteKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonClienteKeyTyped

    private void txtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProductoKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoProductoKeyTyped

    private void txtDescripcionProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionProductoKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionProductoKeyTyped

    private void txtCantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProductoKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadProductoKeyTyped

    private void txtRazonProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonProveedorKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonProveedorKeyTyped

    private void txtRucVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucVentaKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucVentaKeyTyped

    private void txtRazonConfiguracionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonConfiguracionKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtRazonConfiguracionKeyTyped

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void txtTelefonoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClienteKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoClienteKeyTyped

    private void txtRucProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucProveedorKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        validarEventos.textKeyPress(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtRucConfiguracionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucConfiguracionKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtRucConfiguracionKeyTyped

    private void txtTelefonoConfiguracionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoConfiguracionKeyTyped
        validarEventos.numberKeyPress(evt);
    }//GEN-LAST:event_txtTelefonoConfiguracionKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    public void limpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtNombreProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtRazonProveedor.setText("");
    }

    public void limpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtTelefonoCliente.setText("");
        txtNombreCliente.setText("");
        txtDireccionCliente.setText("");
        txtRazonCliente.setText("");
    }

    public void limpiarProductos() {
        txtIdProducto.setText("");
        txtCodigoProducto.setText("");
        cdxProveedorProducto.setSelectedItem(null);
        txtDescripcionProducto.setText("");
        txtCantidadProducto.setText("");
        txtPrecioProducto.setText("");
        AutoCompleteDecorator.decorate(cdxProveedorProducto);
        cdxProveedorProducto.removeAllItems();
        controladorProveedor.consultarProveedor(cdxProveedorProducto);
    }

    private void totalPagar() {
        totalPagar = 0;
        int numeroFila = tableVenta.getRowCount();
        double calcularPrecioVenta = 0;
        for (int i = 0; i < numeroFila; i++) {
            calcularPrecioVenta = Double.parseDouble(String.valueOf(tableVenta.getValueAt(i, 4)));
            totalPagar = totalPagar + calcularPrecioVenta;
        }
        LabelTotal.setText(String.format("%.2f", totalPagar));
    }

    private void limpiarVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVenta.setText("");
    }

    private void registrarVenta() {
        String nombreCliente = txtNombreClienteVenta.getText();
        String vendedor = labelEmpresa.getText();
        double montoTotal = totalPagar;
        venta = controladorVentas.crearVenta(nombreCliente, vendedor, montoTotal);
        controladorVentas.RegistrarVenta(venta);
    }

    private void registrarDetalle() {
        int id = controladorVentas.id_Venta();
        for (int i = 0; i < tableVenta.getRowCount(); i++) {
            String codigo = tableVenta.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(tableVenta.getValueAt(i, 3).toString());
            detalle = controladorProductos.detalleVenta(codigo, cantidad, precio, id);
            controladorVentas.RegistrarDetalle(detalle);
        }
    }

    private void actualizarStock() {
        String codigo;
        int cantidad = 0;
        int stockActual = 0;
        for (int i = 0; i < tableVenta.getRowCount(); i++) {
            codigo = tableVenta.getValueAt(i, 0).toString();
            cantidad = Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
            productos = controladorProductos.buscarProductos(codigo);
            stockActual = productos.getStock() - cantidad;
            controladorVentas.actualizarStock(stockActual, codigo);
        }
    }

    private void limpiarVentanaPrincipal() {
        modeloNuevaVenta = (DefaultTableModel) tableVenta.getModel();
        int filas = tableVenta.getRowCount();
        for (int i = 0; i < filas; i++) {
            modeloNuevaVenta.removeRow(0);
        }
    }

    private void limpiarClienteVenta() {
        txtRucVenta.setText("");
        txtNombreClienteVenta.setText("");
        txtTelefonoClienteVenta.setText("");
        txtDireccionClienteVenta.setText("");
        txtRazonClienteVenta.setText("");
    }

    private void generarPdf() {
        try {
            int id = controladorVentas.id_Venta();
            FileOutputStream archivo;
            File file = new File("src/ReportesPdf/venta" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document document = new Document();
            PdfWriter.getInstance(document, archivo);
            document.open();
            Image image = Image.getInstance("src/Imagenes/default_avatar.png");

            Paragraph fecha = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fecha.add(Chunk.NEWLINE);
            Date date = new Date();
            fecha.add("Factura : " + id + "\n" + "Fecha : " + new SimpleDateFormat("dd-mm-yyyy").format(date) + "\n\n");

            PdfPTable Encabezado = new PdfPTable(4);

            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float columnaEncabezado[] = new float[]{20f, 30f, 70f, 40f};
            Encabezado.setWidths(columnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);

            Encabezado.addCell(image);

            String ruc = txtRucConfiguracion.getText();
            String nombre = txtNombreConfiguracion.getText();
            String telefono = txtTelefonoConfiguracion.getText();
            String direccion = txtDireccionConfiguracion.getText();
            String razon = txtRazonConfiguracion.getText();

            Encabezado.addCell("");
            Encabezado.addCell("Ruc : " + ruc + "\nNombre de la Empresa : " + nombre + "\nTelefono : " + telefono + "\nDireccion : " + direccion + "\nRazon : " + razon);
            Encabezado.addCell(fecha);

            document.add(Encabezado);

            Paragraph datosCliente = new Paragraph();
            datosCliente.add(Chunk.NEWLINE);
            datosCliente.add("Datos de los Clientes" + "\n\n");
            document.add(datosCliente);

            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            tablaCliente.getDefaultCell().setBorder(0);
            float columnaCliente[] = new float[]{10f, 50f, 15f, 20f};
            tablaCliente.setWidths(columnaCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cliente1 = new PdfPCell(new Phrase("Dni/Ruc", negrita));
            PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono", negrita));
            PdfPCell cliente4 = new PdfPCell(new Phrase("Direccion", negrita));

            cliente1.setBorder(0);
            cliente2.setBorder(0);
            cliente3.setBorder(0);
            cliente4.setBorder(0);

            tablaCliente.addCell(cliente1);
            tablaCliente.addCell(cliente2);
            tablaCliente.addCell(cliente3);
            tablaCliente.addCell(cliente4);

            tablaCliente.addCell(txtRucVenta.getText());
            tablaCliente.addCell(txtNombreClienteVenta.getText());
            tablaCliente.addCell(txtTelefonoClienteVenta.getText());
            tablaCliente.addCell(txtDireccionClienteVenta.getText());
            document.add(tablaCliente);

            //Productos 
            PdfPTable tablaProductos = new PdfPTable(4);
            tablaProductos.setWidthPercentage(100);
            tablaProductos.getDefaultCell().setBorder(0);
            float columnaProductos[] = new float[]{20f, 50f, 30f, 40f};
            tablaCliente.setWidths(columnaProductos);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell producto1 = new PdfPCell(new Phrase("Cantidad", negrita));
            PdfPCell producto2 = new PdfPCell(new Phrase("Descripcion", negrita));
            PdfPCell producto3 = new PdfPCell(new Phrase("Precio Unitario.", negrita));
            PdfPCell producto4 = new PdfPCell(new Phrase("Precio Total.", negrita));

            producto1.setBorder(0);
            producto2.setBorder(0);
            producto3.setBorder(0);
            producto4.setBorder(0);

            producto1.setBackgroundColor(BaseColor.DARK_GRAY);
            producto2.setBackgroundColor(BaseColor.DARK_GRAY);
            producto3.setBackgroundColor(BaseColor.DARK_GRAY);
            producto4.setBackgroundColor(BaseColor.DARK_GRAY);

            tablaProductos.addCell(producto1);
            tablaProductos.addCell(producto2);
            tablaProductos.addCell(producto3);
            tablaProductos.addCell(producto4);

            for (int i = 0; i < tableVenta.getRowCount(); i++) {
                String producto = tableVenta.getValueAt(i, 1).toString();
                String cantidad = tableVenta.getValueAt(i, 2).toString();
                String precio = tableVenta.getValueAt(i, 3).toString();
                String total = tableVenta.getValueAt(i, 4).toString();

                tablaProductos.addCell(cantidad);
                tablaProductos.addCell(producto);
                tablaProductos.addCell(precio);
                tablaProductos.addCell(total);
            }

            document.add(tablaProductos);

            Paragraph informacion = new Paragraph();
            informacion.add(Chunk.NEWLINE);
            informacion.add("Total a Pagar :" + totalPagar);
            informacion.setAlignment(Element.ALIGN_RIGHT);
            document.add(informacion);

            Paragraph firma = new Paragraph();
            firma.add(Chunk.NEWLINE);
            firma.add("Cancelacion y Firma\n\n");
            firma.add("-------------------------");
            firma.setAlignment(Element.ALIGN_CENTER);
            document.add(firma);

            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su Compra\n\n");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            document.add(mensaje);

            document.close();
            archivo.close();

            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizarConfiguracion;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfiguracion;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProducto;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarVenta;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnPdfVentas;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox<String> cdxProveedorProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelCliente;
    private javax.swing.JPanel jPanelProveedor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelEmpresa;
    private javax.swing.JTable tableVenta;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDireccionClienteVenta;
    private javax.swing.JTextField txtDireccionConfiguracion;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JButton txtGuardarProveedor;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfiguracion;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdVentanaPrincipal;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteVenta;
    private javax.swing.JTextField txtNombreConfiguracion;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRazonCliente;
    private javax.swing.JTextField txtRazonClienteVenta;
    private javax.swing.JTextField txtRazonConfiguracion;
    private javax.swing.JTextField txtRazonProveedor;
    private javax.swing.JTextField txtRucConfiguracion;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtRucVenta;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoClienteVenta;
    private javax.swing.JTextField txtTelefonoConfiguracion;
    private javax.swing.JTextField txtTelefonoProveedor;
    // End of variables declaration//GEN-END:variables

}
