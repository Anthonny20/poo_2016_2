package br.ufrpe.sistema_bancario.exceptions;

public class SaldoInsuficienteException extends Exception {
    
    private String numero;
    
    public SaldoInsuficienteException(String num) {
        super("O saldo da conta de n�mero " + num + " � insuficiente "
                + "para realizar a transa�ao.");
        this.numero = num;
    }

    public String getNumero() {
        return numero;
    }
}
