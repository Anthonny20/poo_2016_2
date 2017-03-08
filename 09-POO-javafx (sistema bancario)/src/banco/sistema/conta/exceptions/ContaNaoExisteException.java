package banco.sistema.conta.exceptions;

public class ContaNaoExisteException extends Exception {

    private String numeroInexistente;
    
    public ContaNaoExisteException(String num) {
        super("A conta de n�mero " + num + " n�o existe");
        this.numeroInexistente = num;
    }

    public String getNumeroInexistente() {
        return numeroInexistente;
    }

}
