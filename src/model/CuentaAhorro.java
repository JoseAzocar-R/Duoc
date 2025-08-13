package model;

public class CuentaAhorro extends CuentaBancaria {
    public CuentaAhorro(String numero) {
        super(numero, "Ahorro");
    }
    @Override
    public void depositar(int monto) {
        if (monto > 0) saldo += monto;
    }
    @Override
    public boolean girar(int monto) {
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            return true;
        }
        return false;
    }
}
// Subclase cuenta ahorro