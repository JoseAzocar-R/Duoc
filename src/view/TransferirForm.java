package view;

import controller.BancoController;
import model.Cliente;
import model.CuentaCorriente;

import javax.swing.*;

public class TransferirForm extends JFrame {
    private JTextField origenField, destinoField, montoField;
    private JLabel resultadoLabel;

    public TransferirForm() {
        setTitle("Transferencia entre Cuentas Corrientes");
        setSize(400, 280);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel origenLabel = new JLabel("RUT cliente origen:");
        origenLabel.setBounds(20, 20, 150, 20);
        add(origenLabel);

        origenField = new JTextField();
        origenField.setBounds(170, 20, 180, 20);
        add(origenField);

        JLabel destinoLabel = new JLabel("RUT cliente destino:");
        destinoLabel.setBounds(20, 60, 150, 20);
        add(destinoLabel);

        destinoField = new JTextField();
        destinoField.setBounds(170, 60, 180, 20);
        add(destinoField);

        JLabel montoLabel = new JLabel("Monto:");
        montoLabel.setBounds(20, 100, 150, 20);
        add(montoLabel);

        montoField = new JTextField();
        montoField.setBounds(170, 100, 100, 20);
        add(montoField);

        JButton transferirBtn = new JButton("Transferir");
        transferirBtn.setBounds(20, 140, 120, 25);
        add(transferirBtn);

        resultadoLabel = new JLabel("");
        resultadoLabel.setBounds(20, 180, 350, 30);
        add(resultadoLabel);

        transferirBtn.addActionListener(e -> realizarTransferencia());

        setVisible(true);
    }

    private void realizarTransferencia() {
        String rutOrigen = origenField.getText().trim();
        String rutDestino = destinoField.getText().trim();

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

        Cliente clienteOrigen = BancoController.buscarClientePorRut(rutOrigen);
        Cliente clienteDestino = BancoController.buscarClientePorRut(rutDestino);

        if (clienteOrigen == null || !(clienteOrigen.getCuenta() instanceof CuentaCorriente)) {
            JOptionPane.showMessageDialog(this, "El cliente origen no existe o no tiene cuenta corriente.");
            return;
        }

        if (clienteDestino == null || !(clienteDestino.getCuenta() instanceof CuentaCorriente)) {
            JOptionPane.showMessageDialog(this, "El cliente destino no existe o no tiene cuenta corriente.");
            return;
        }

        String cuentaOrigen = clienteOrigen.getCuenta().getNumero();
        String cuentaDestino = clienteDestino.getCuenta().getNumero();

        boolean exito = BancoController.transferirSaldo(cuentaOrigen, cuentaDestino, monto);

        if (exito) {
            resultadoLabel.setText("✅ Transferencia realizada con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error: saldo insuficiente o cuentas incorrectas.");
        }
    }
}
