package controller;

import model.*;
import pruebamysql.dbConnection;

import java.sql.*;

public class BancoController {

    public static void agregarCliente(Cliente c) {
        String insertCuenta = "INSERT INTO cuenta (numero, descripcion, saldo) VALUES (?, ?, ?)";
        String insertCliente = "INSERT INTO cliente (rut, nombre, apellido_p, apellido_m, domicilio, comuna, telefono, numero_cuenta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.conectar()) {
            conn.setAutoCommit(false);

            // Insertar cuenta
            try (PreparedStatement psCuenta = conn.prepareStatement(insertCuenta)) {
                psCuenta.setString(1, c.getCuenta().getNumero());
                psCuenta.setString(2, c.getCuenta().getDescripcion());
                psCuenta.setInt(3, 0); // saldo inicial
                psCuenta.executeUpdate();
            }

            // Insertar cliente
            try (PreparedStatement psCliente = conn.prepareStatement(insertCliente)) {
                psCliente.setString(1, c.getRut());
                psCliente.setString(2, c.getNombre());
                psCliente.setString(3, c.getApellidoP());
                psCliente.setString(4, c.getApellidoM());
                psCliente.setString(5, c.getDomicilio());
                psCliente.setString(6, c.getComuna());
                psCliente.setString(7, c.getTelefono());
                psCliente.setString(8, c.getCuenta().getNumero());
                psCliente.executeUpdate();
            }

            conn.commit();
            System.out.println("✅ Cliente y cuenta registrados correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  public static Cliente buscarClientePorRut(String rut) {
    String sql = """
            SELECT c.rut, c.nombre, c.apellido_p, c.apellido_m, c.domicilio, c.comuna, c.telefono,
                   cu.numero, cu.descripcion, cu.saldo
            FROM cliente c
            JOIN cuenta cu ON c.numero_cuenta = cu.numero
            WHERE c.rut = ?
            """;

    try (Connection conn = dbConnection.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, rut);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Recuperar datos de la cuenta
            String tipoCuenta = rs.getString("descripcion");
            String numeroCuenta = rs.getString("numero");
            int saldo = rs.getInt("saldo");

            // Crear cuenta bancaria según tipo
            CuentaBancaria cuenta;
            if ("Corriente".equalsIgnoreCase(tipoCuenta)) {
                cuenta = new CuentaCorriente(numeroCuenta);
            } else {
                cuenta = new CuentaAhorro(numeroCuenta);
            }
            cuenta.setSaldo(saldo);

            // Crear y retornar cliente completo
            return new Cliente(
                    rs.getString("rut"),
                    rs.getString("nombre"),
                    rs.getString("apellido_p"),
                    rs.getString("apellido_m"),
                    rs.getString("domicilio"),
                    rs.getString("comuna"),
                    rs.getString("telefono"),
                    cuenta
            );
        }

    } catch (SQLException e) {
        System.err.println("Error al buscar cliente por RUT: " + e.getMessage());
        e.printStackTrace();
    }

    return null; // cliente no encontrado
}
  
  public static boolean depositarSaldo(String numeroCuenta, int monto) {
    String updateSaldo = "UPDATE cuenta SET saldo = saldo + ? WHERE numero = ?";
    String registrarMovimiento = "INSERT INTO movimiento (numero_cuenta, tipo, monto) VALUES (?, 'DEPOSITO', ?)";

    try (Connection conn = dbConnection.conectar()) {
        conn.setAutoCommit(false);

        // Actualizar saldo
        try (PreparedStatement ps = conn.prepareStatement(updateSaldo)) {
            ps.setInt(1, monto);
            ps.setString(2, numeroCuenta);
            int updated = ps.executeUpdate();
            if (updated == 0) {
                conn.rollback();
                return false;
            }
        }

        // Registrar movimiento
        try (PreparedStatement ps = conn.prepareStatement(registrarMovimiento)) {
            ps.setString(1, numeroCuenta);
            ps.setInt(2, monto);
            ps.executeUpdate();
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
public static boolean girarSaldo(String numeroCuenta, int monto) {
    String updateSaldo = "UPDATE cuenta SET saldo = saldo - ? WHERE numero = ? AND saldo >= ?";
    String registrarMovimiento = "INSERT INTO movimiento (numero_cuenta, tipo, monto) VALUES (?, 'GIRO', ?)";

    try (Connection conn = dbConnection.conectar()) {
        conn.setAutoCommit(false);

        // Validar y descontar saldo
        try (PreparedStatement ps = conn.prepareStatement(updateSaldo)) {
            ps.setInt(1, monto);
            ps.setString(2, numeroCuenta);
            ps.setInt(3, monto);
            int updated = ps.executeUpdate();
            if (updated == 0) {
                conn.rollback(); // saldo insuficiente o cuenta no existe
                return false;
            }
        }

        // Registrar movimiento
        try (PreparedStatement ps = conn.prepareStatement(registrarMovimiento)) {
            ps.setString(1, numeroCuenta);
            ps.setInt(2, monto);
            ps.executeUpdate();
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

 public static boolean transferirSaldo(String cuentaOrigen, String cuentaDestino, int monto) {
    String debitar = "UPDATE cuenta SET saldo = saldo - ? WHERE numero = ? AND saldo >= ?";
    String acreditar = "UPDATE cuenta SET saldo = saldo + ? WHERE numero = ?";
    String registrarGiro = "INSERT INTO movimiento (numero_cuenta, tipo, monto) VALUES (?, 'GIRO', ?)";
    String registrarDeposito = "INSERT INTO movimiento (numero_cuenta, tipo, monto) VALUES (?, 'DEPOSITO', ?)";

    try (Connection conn = dbConnection.conectar()) {
        conn.setAutoCommit(false);

        // Descontar saldo de la cuenta origen
        try (PreparedStatement ps = conn.prepareStatement(debitar)) {
            ps.setInt(1, monto);
            ps.setString(2, cuentaOrigen);
            ps.setInt(3, monto);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                conn.rollback(); // saldo insuficiente o cuenta inexistente
                return false;
            }
        }

        // Acreditar saldo a la cuenta destino
        try (PreparedStatement ps = conn.prepareStatement(acreditar)) {
            ps.setInt(1, monto);
            ps.setString(2, cuentaDestino);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                conn.rollback(); // cuenta destino inexistente
                return false;
            }
        }

        // Registrar movimiento tipo GIRO en cuenta origen
        try (PreparedStatement ps = conn.prepareStatement(registrarGiro)) {
            ps.setString(1, cuentaOrigen);
            ps.setInt(2, monto);
            ps.executeUpdate();
        }

        // Registrar movimiento tipo DEPOSITO en cuenta destino
        try (PreparedStatement ps = conn.prepareStatement(registrarDeposito)) {
            ps.setString(1, cuentaDestino);
            ps.setInt(2, monto);
            ps.executeUpdate();
        }

        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}  
}
