package view;

import controller.BancoController;
import model.Cliente;

import javax.swing.*;

public class DepositarForm extends JFrame {
    private JTextField rutField, montoField;
    private JLabel saldoLabel;

    public DepositarForm() {
        setTitle("Depositar Dinero");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel rutLabel = new JLabel("RUT del cliente:");
        rutLabel.setBounds(20, 20, 120, 20);
        add(rutLabel);

        rutField = new JTextField();
        rutField.setBounds(150, 20, 150, 20);
        add(rutField);

        JLabel montoLabel = new JLabel("Monto a depositar:");
        montoLabel.setBounds(20, 60, 120, 20);
        add(montoLabel);

        montoField = new JTextField();
        montoField.setBounds(150, 60, 150, 20);
        add(montoField);

        JButton depositarBtn = new JButton("Depositar");
        depositarBtn.setBounds(20, 100, 100, 25);
        add(depositarBtn);

        saldoLabel = new JLabel("Saldo: ");
        saldoLabel.setBounds(20, 140, 250, 20);
        add(saldoLabel);

        depositarBtn.addActionListener(e -> realizarDeposito());

        setVisible(true);
    }

    private void realizarDeposito() {
        String rut = rutField.getText();
        int monto;

        try {
            monto = Integer.parseInt(montoField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido.");
            return;
        }

        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
            return;
        }

        Cliente cliente = BancoController.buscarClientePorRut(rut);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            return;
        }

        boolean exito = BancoController.depositarSaldo(cliente.getCuenta().getNumero(), monto);
        if (exito) {
            // Actualizar el saldo desde la base de datos
            cliente = BancoController.buscarClientePorRut(rut);
            saldoLabel.setText("Saldo: " + cliente.getCuenta().getSaldo());
            JOptionPane.showMessageDialog(this, "Depósito realizado con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al realizar el depósito.");
        }
    }
}
