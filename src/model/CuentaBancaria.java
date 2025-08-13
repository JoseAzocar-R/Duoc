package model;

public abstract class CuentaBancaria {
    protected String numero;
    protected int saldo;
    protected String descripcion;

    public CuentaBancaria(String numero, String descripcion) {
        this.numero = numero;
        this.saldo = 0;
        this.descripcion = descripcion;
    }

    public abstract void depositar(int monto);
    public abstract boolean girar(int monto);

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
}