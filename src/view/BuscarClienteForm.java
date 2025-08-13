package view;

import controller.BancoController;
import model.Cliente;

import javax.swing.*;

public class BuscarClienteForm extends JFrame {
    private JTextField rutField;
    private JTextArea resultadoArea;

    public BuscarClienteForm() {
        setTitle("Buscar Cliente");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel rutLabel = new JLabel("Ingrese RUT:");
        rutLabel.setBounds(20, 20, 100, 20);
        add(rutLabel);

        rutField = new JTextField();
        rutField.setBounds(120, 20, 150, 20);
        add(rutField);

        JButton buscarBtn = new JButton("Buscar");
        buscarBtn.setBounds(280, 20, 80, 20);
        add(buscarBtn);

        resultadoArea = new JTextArea();
        resultadoArea.setBounds(20, 60, 340, 180);
        resultadoArea.setEditable(false);
        add(resultadoArea);

        buscarBtn.addActionListener(e -> buscarCliente());

        setVisible(true);
    }

    private void buscarCliente() {
        String rut = rutField.getText();
        Cliente cliente = BancoController.buscarClientePorRut(rut);
        if (cliente != null) {
            resultadoArea.setText("Nombre: " + cliente.getNombre() + " " + cliente.getApellidoP() + " " + cliente.getApellidoM() +
                                  "\nDomicilio: " + cliente.getDomicilio() +
                                  "\nComuna: " + cliente.getComuna() +
                                  "\nTeléfono: " + cliente.getTelefono() +
                                  "\nCuenta: " + cliente.getCuenta().getDescripcion() +
                                  "\nNúmero: " + cliente.getCuenta().getNumero() +
                                  "\nSaldo: " + cliente.getCuenta().getSaldo());
        } else {
            resultadoArea.setText("Cliente no encontrado.");
        }
    }
}
