package view;

/*import controller.BancoController;
import model.Cliente;*/

import controller.BancoController;
import javax.swing.*;
import model.Cliente;

public class GirarForm extends JFrame {
    private JTextField rutField, montoField;
    private JLabel saldoLabel;

    public GirarForm() {
        setTitle("Girar Dinero");
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

        JLabel montoLabel = new JLabel("Monto a girar:");
        montoLabel.setBounds(20, 60, 120, 20);
        add(montoLabel);

        montoField = new JTextField();
        montoField.setBounds(150, 60, 150, 20);
        add(montoField);

        JButton girarBtn = new JButton("Girar");
        girarBtn.setBounds(20, 100, 100, 25);
        add(girarBtn);

        saldoLabel = new JLabel("Saldo: ");
        saldoLabel.setBounds(20, 140, 250, 20);
        add(saldoLabel);

        girarBtn.addActionListener(e -> realizarGiro());

        setVisible(true);
    }

    private void realizarGiro() {
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

    String numeroCuenta = cliente.getCuenta().getNumero();

    boolean exito = BancoController.girarSaldo(numeroCuenta, monto);
    if (exito) {
        // Actualizar el cliente para mostrar nuevo saldo
        Cliente actualizado = BancoController.buscarClientePorRut(rut);
        saldoLabel.setText("Saldo: " + actualizado.getCuenta().getSaldo());
        JOptionPane.showMessageDialog(this, "Giro realizado con éxito.");
    } else {
        JOptionPane.showMessageDialog(this, "Saldo insuficiente o error en la transacción.");
    }
}

}
