/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ControladorDatosEmpresa;
import Modelo.ClaseLogin;
import Controlador.ControladorLogin;
import Modelo.ConfiguracionDatosEmpresa;
import javax.swing.*;
import ExpresionesRegulares.*;

public class RegistroAcceso extends javax.swing.JFrame {

    /**
     * Creates new form ClaseLogin
     */
    ConfiguracionDatosEmpresa configuracionDatosEmpresa = new ConfiguracionDatosEmpresa();
    ControladorDatosEmpresa controladorDatosEmpresa = new ControladorDatosEmpresa();
    ControladorExpresionesRegulares controladorExpresionesRegulares = new ControladorExpresionesRegulares();

    public RegistroAcceso() {
        initComponents();
        datosActuales();
    }

    public void datosActuales() {
        configuracionDatosEmpresa = controladorDatosEmpresa.buscarDatos();
        labelEmpresa.setText(configuracionDatosEmpresa.getNombre());
    }

    public void validar() {
        ClaseLogin claseLogin = new ClaseLogin();
        ControladorLogin controladorLogin = new ControladorLogin();

        String nombre = txtNombre.getText();
        String rol = cbxRol.getSelectedItem().toString();
        String correo = txtCorreo.getText();
        String pass = String.valueOf(txtPass.getPassword());

        boolean validarCorreo = controladorExpresionesRegulares.limitanteCorreo(correo);
        boolean validarContrasenio = controladorExpresionesRegulares.limitanteContrasenia(pass);
        boolean validarNombre = controladorExpresionesRegulares.limitanteNombre(nombre);

        boolean setUser = controladorLogin.ubicarUsuario(nombre, correo);

        if (txtNombre.getText().equals("") || txtCorreo.getText().equals("") || txtCorreo.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Por favor rellene todos los Campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (validarCorreo == false) {
                JOptionPane.showMessageDialog(null, "Correo no valido", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (validarContrasenio == false) {
                    JOptionPane.showMessageDialog(null, "Contraseña no valida", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (validarNombre == false) {
                        JOptionPane.showMessageDialog(null, "Nombre no valido", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (setUser == false) {
                            JOptionPane.showMessageDialog(null, "El Usuario ya Existe", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            claseLogin = new ClaseLogin(nombre, correo, pass, rol);
                            controladorLogin.registrarCuenta(claseLogin);
                            this.dispose();
                        }
                    }
                }
            }
        }
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
        textPass = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        labelEmpresa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textPass.setBackground(new java.awt.Color(255, 255, 255));
        textPass.setForeground(new java.awt.Color(51, 102, 255));
        textPass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/content-creator.png"))); // NOI18N
        textPass.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 60, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setText("Correo Electronico");
        textPass.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 102, 255));
        jLabel4.setText("Password");
        textPass.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));
        textPass.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 240, 30));
        textPass.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 240, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/verify.png"))); // NOI18N
        jButton1.setText("Registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        textPass.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 190, 80));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("Nombre ");
        textPass.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 140, 20));
        textPass.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 240, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("Rol:");
        textPass.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        cbxRol.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cbxRol.setForeground(new java.awt.Color(102, 102, 255));
        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));
        textPass.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 240, 30));

        jPanel1.add(textPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 520));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 520));

        jPanel2.setBackground(new java.awt.Color(30, 128, 157));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelEmpresa.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        labelEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        labelEmpresa.setText("Empresa XYZ");
        jPanel2.add(labelEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/manager.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 250));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 320, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        validar();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroAcceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroAcceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroAcceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroAcceso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroAcceso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelEmpresa;
    private javax.swing.JPanel textPass;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}