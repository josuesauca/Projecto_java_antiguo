/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.*;
import Modelo.*;
import javax.swing.*;

public class LoginInicio extends javax.swing.JFrame {

    ConfiguracionDatosEmpresa configuracionDatosEmpresa = new ConfiguracionDatosEmpresa();
    ControladorDatosEmpresa controladorDatosEmpresa = new ControladorDatosEmpresa();

    public LoginInicio() {
        initComponents();
        configuracionDatosEmpresa = controladorDatosEmpresa.buscarDatos();
        labelPrograma.setText(configuracionDatosEmpresa.getNombre());
    }

    public void validar() {
        Modelo.ClaseLogin claseLogin = new Modelo.ClaseLogin();
        ControladorLogin controladorLogin = new ControladorLogin();

        String correo = txtCorreo.getText();
        String pass = String.valueOf(txtPass.getPassword());

        if (txtCorreo.getText().equals("") || txtPass.getPassword().equals("")) {
            JOptionPane.showMessageDialog(null, "Por Favor Rellene los Campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
        } else {
            claseLogin = controladorLogin.ingresarLogin(correo, pass);
            if (claseLogin.getCorreo() != null && claseLogin.getPass() != null) {
                Sistema sistemaVentana = new Sistema(claseLogin);
                sistemaVentana.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Correo o contraseña incorrecta","Mensaje",JOptionPane.ERROR_MESSAGE);
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

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        labelPrograma = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textPass = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(106, 76, 252));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(30, 128, 157));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelPrograma.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelPrograma.setForeground(new java.awt.Color(255, 255, 255));
        labelPrograma.setText("Empresa XYZ");
        jPanel3.add(labelPrograma, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/default_avatar.png"))); // NOI18N
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, -1, 190));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/desktop.png"))); // NOI18N
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 140, 140));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 0, 320, 530));

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 310, 530));

        textPass.setBackground(new java.awt.Color(255, 255, 255));
        textPass.setForeground(new java.awt.Color(51, 102, 255));
        textPass.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setText("Correo Electronico");
        textPass.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 230, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 102, 255));
        jLabel4.setText("Password");
        textPass.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 120, 30));
        textPass.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 230, 40));
        textPass.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 230, 40));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/enter.png"))); // NOI18N
        jButton1.setText("Iniciar");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        textPass.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 160, 80));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user.png"))); // NOI18N
        textPass.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/traffic-sign.png"))); // NOI18N
        textPass.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 130, 90));

        jPanel4.add(textPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 530));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 530));

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
            java.util.logging.Logger.getLogger(LoginInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labelPrograma;
    private javax.swing.JPanel textPass;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
