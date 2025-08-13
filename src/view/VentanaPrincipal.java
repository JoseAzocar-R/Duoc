// Menú principal de la app
package view;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("West Bank - Gestión de Clientes");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuCliente = new JMenu("Clientes");
        JMenuItem itemRegistrar = new JMenuItem("Registrar Cliente");
        JMenuItem itemBuscar = new JMenuItem("Buscar Cliente");

        JMenu menuCuenta = new JMenu("Cuentas");
        JMenuItem itemDepositar = new JMenuItem("Depositar");
        JMenuItem itemGirar = new JMenuItem("Girar");
        JMenuItem itemTransferir = new JMenuItem("Transferir");

        itemRegistrar.addActionListener(e -> new RegistroClienteForm());
   
        itemBuscar.addActionListener(e -> new BuscarClienteForm());
        itemDepositar.addActionListener(e -> new DepositarForm());
        itemGirar.addActionListener(e -> new GirarForm());
        itemTransferir.addActionListener(e -> new TransferirForm());

        menuCliente.add(itemRegistrar);
        menuCliente.add(itemBuscar);
        menuCuenta.add(itemDepositar);
        menuCuenta.add(itemGirar);
        menuCuenta.add(itemTransferir);

        menuBar.add(menuCliente);
        menuBar.add(menuCuenta);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}