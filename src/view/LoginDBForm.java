package view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import pruebamysql.dbConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginDBForm extends JFrame {

    private JTextField userField, hostField, portField;
    private JPasswordField passField;

    public LoginDBForm() {
        setTitle("Conexión a la base de datos WestBank");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel hostLabel = new JLabel("Host:");
        hostLabel.setBounds(30, 20, 80, 25);
        add(hostLabel);

        hostField = new JTextField("localhost");
        hostField.setBounds(120, 20, 200, 25);
        add(hostField);

        JLabel portLabel = new JLabel("Puerto:");
        portLabel.setBounds(30, 55, 80, 25);
        add(portLabel);

        portField = new JTextField("3306");
        portField.setBounds(120, 55, 200, 25);
        add(portField);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setBounds(30, 90, 80, 25);
        add(userLabel);

        userField = new JTextField("root");
        userField.setBounds(120, 90, 200, 25);
        add(userField);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setBounds(30, 125, 80, 25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(120, 125, 200, 25);
        add(passField);

        JButton conectarBtn = new JButton("Conectar");
        conectarBtn.setBounds(140, 170, 120, 30);
        add(conectarBtn);

        conectarBtn.addActionListener(e -> conectar());

        setVisible(true);
    }

    private void conectar() {
        String host = hostField.getText().trim();
        String puerto = portField.getText().trim();
        String usuario = userField.getText().trim();
        String contraseña = new String(passField.getPassword());

     
        Connection conn = dbConnection.conectar();

        if (conn != null) {
            JOptionPane.showMessageDialog(this, "✅ Conexión exitosa.");
            dispose();
            new VentanaPrincipal().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Falló la conexión. Verifica tus datos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginDBForm::new);
    }
}