package model;

public class CuentaCorriente extends CuentaBancaria {
    public CuentaCorriente(String numero) {
        super(numero, "Corriente");
    }

    public void depositar(int monto) {
        if (monto > 0) saldo += monto;
    }

    public boolean girar(int monto) {
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }

    public boolean transferir(CuentaCorriente destino, int monto) {
        if (destino != null && monto > 0 && monto <= saldo) {
            saldo -= monto;
            destino.depositar(monto);
            return true;
        }
        return false;
    }
}
