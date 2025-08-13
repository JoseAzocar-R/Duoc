package view;

import controller.BancoController;
import model.*;

import javax.swing.*;
import java.util.Random;

public class RegistroClienteForm extends JFrame {

    private JTextField rutField, nombreField, apellidoPField, apellidoMField, domicilioField, comunaField, telefonoField;
    private JComboBox<String> tipoCuentaBox;

    public RegistroClienteForm() {
        setTitle("Registro de Cliente");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel rutLabel = new JLabel("RUT:");
        rutLabel.setBounds(20, 20, 80, 20);
        add(rutLabel);
        rutField = new JTextField();
        rutField.setBounds(120, 20, 200, 20);
        add(rutField);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setBounds(20, 50, 80, 20);
        add(nombreLabel);
        nombreField = new JTextField();
        nombreField.setBounds(120, 50, 200, 20);
        add(nombreField);

        JLabel apellidoPLabel = new JLabel("Apellido Paterno:");
        apellidoPLabel.setBounds(20, 80, 100, 20);
        add(apellidoPLabel);
        apellidoPField = new JTextField();
        apellidoPField.setBounds(120, 80, 200, 20);
        add(apellidoPField);

        JLabel apellidoMLabel = new JLabel("Apellido Materno:");
        apellidoMLabel.setBounds(20, 110, 100, 20);
        add(apellidoMLabel);
        apellidoMField = new JTextField();
        apellidoMField.setBounds(120, 110, 200, 20);
        add(apellidoMField);

        JLabel domicilioLabel = new JLabel("Domicilio:");
        domicilioLabel.setBounds(20, 140, 80, 20);
        add(domicilioLabel);
        domicilioField = new JTextField();
        domicilioField.setBounds(120, 140, 200, 20);
        add(domicilioField);

        JLabel comunaLabel = new JLabel("Comuna:");
        comunaLabel.setBounds(20, 170, 80, 20);
        add(comunaLabel);
        comunaField = new JTextField();
        comunaField.setBounds(120, 170, 200, 20);
        add(comunaField);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        telefonoLabel.setBounds(20, 200, 80, 20);
        add(telefonoLabel);
        telefonoField = new JTextField();
        telefonoField.setBounds(120, 200, 200, 20);
        add(telefonoField);

        JLabel cuentaLabel = new JLabel("Tipo de cuenta:");
        cuentaLabel.setBounds(20, 230, 100, 20);
        add(cuentaLabel);
        tipoCuentaBox = new JComboBox<>(new String[]{"Cuenta Corriente", "Cuenta de Ahorro"});
        tipoCuentaBox.setBounds(120, 230, 200, 20);
        add(tipoCuentaBox);

        JButton registrarBtn = new JButton("Registrar");
        registrarBtn.setBounds(120, 270, 120, 30);
        add(registrarBtn);

        registrarBtn.addActionListener(e -> registrarCliente());

        setVisible(true);
    }

    private void registrarCliente() {
        String rut = rutField.getText().trim();

        if (rut.length() < 10 || rut.length() > 11) {
            JOptionPane.showMessageDialog(this, "El RUT debe tener entre 10 y 11 caracteres.");
            return;
        }

        String numero = String.valueOf(generarNumeroCuenta());
        CuentaBancaria cuenta;

        String tipoSeleccionado = (String) tipoCuentaBox.getSelectedItem();
        if ("Cuenta Corriente".equalsIgnoreCase(tipoSeleccionado)) {
            cuenta = new CuentaCorriente(numero);
        } else {
            cuenta = new CuentaAhorro(numero);
        }

        Cliente cliente = new Cliente(
                rut,
                nombreField.getText(),
                apellidoPField.getText(),
                apellidoMField.getText(),
                domicilioField.getText(),
                comunaField.getText(),
                telefonoField.getText(),
                cuenta
        );

        BancoController.agregarCliente(cliente);
        JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");
        dispose();
    }

    private int generarNumeroCuenta() {
        return 100000000 + new Random().nextInt(900000000);
    }
}