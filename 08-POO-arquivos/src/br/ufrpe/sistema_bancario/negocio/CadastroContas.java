package br.ufrpe.sistema_bancario.negocio;
import br.ufrpe.sistema_bancario.dados.IRepositorioContas;
import br.ufrpe.sistema_bancario.dados.RepositorioContasArray;
import br.ufrpe.sistema_bancario.exceptions.ContaJaExisteException;
import br.ufrpe.sistema_bancario.exceptions.ContaNaoExisteException;
import br.ufrpe.sistema_bancario.exceptions.SaldoInsuficienteException;
import br.ufrpe.sistema_bancario.negocio.beans.Conta;
import br.ufrpe.sistema_bancario.negocio.beans.Poupanca;

public class CadastroContas {

    private IRepositorioContas repositorio;
    
    public CadastroContas(IRepositorioContas instanciaRepositorio) {
        this.repositorio = instanciaRepositorio; 
    }
    
    public void cadastrar(Conta c) throws ContaJaExisteException {
        if (c == null) {
            throw new IllegalArgumentException("Par�metro inv�lido");
        } else {
            if (!this.existe(c.getNumero())) {
                this.repositorio.cadastrar(c);
                this.repositorio.salvarArquivo();
            } else {
                throw new ContaJaExisteException(c.getNumero());
            }
        }        
    }
    
    public void descadastrar(String numConta) throws ContaNaoExisteException {
    	Conta c = this.repositorio.procurar(numConta);
    	if (c != null &&
    			c.getSaldo() == 0) {
    		this.repositorio.remover(numConta);
    		this.repositorio.salvarArquivo();
		} else {
			throw new ContaNaoExisteException(numConta);
		}
    }

    public Conta procurar(String num) {
        return this.repositorio.procurar(num);
    }
    
    public boolean existe(String numConta) {
    	return this.repositorio.existe(numConta);
    }

    public void remover(String num) throws ContaNaoExisteException {
        this.repositorio.remover(num);
        this.repositorio.salvarArquivo();
    }

    /**
     * Procura a conta cujo n�mero � passado como par�metro e credita o valor
     * tamb�m passado como par�metro
     * 
     * @param num N�mero da conta a ser procurada
     * @param valor Valor a ser creditado na conta encontrada
     */
    public void creditar(String num, double valor) {
        Conta contaCredito = this.repositorio.procurar(num);
        if (contaCredito != null) {
            contaCredito.creditar(valor);
            this.repositorio.salvarArquivo();
        }
    }
    
    /**
     * Procura a conta cujo n�mero � passado como par�metro e debita o valor
     * tamb�m passado como par�metro. Note que n�o � preciso testar se o saldo �
     * v�lido para realizar o d�bito porque o m�todo "debitar" da classe Conta
     * j� testa essa situa��o
     * 
     * @param num N�mero da conta a ser procurada
     * @param valor Valor a ser debitado na conta encontrada
     */
    public void debitar(String num, double valor) {
        Conta contaDebito = this.repositorio.procurar(num);
        if (contaDebito != null) {
            contaDebito.debitar(valor);
            this.repositorio.salvarArquivo();
        }
    }
    
    /**
     * Realiza transfer�ncia de valores entre as duas contas a partir dos
     * n�meros das contas passados como par�metro
     * 
     * @param numOrigem N�mero da conta origem
     * @param numDestino N�mero da conta destino
     * @param valor Valor a ser transferido
     * @throws ContaNaoExisteException 
     * @throws SaldoInsuficienteException 
     */
    public void transferir(String numOrigem, String numDestino, double valor) 
            throws ContaNaoExisteException, SaldoInsuficienteException {
        Conta origem = this.repositorio.procurar(numOrigem);
        Conta destino = this.repositorio.procurar(numDestino);
        if (origem != null && destino != null && origem.getSaldo() >= valor) {
            origem.debitar(valor);
            destino.creditar(valor);
            this.repositorio.salvarArquivo();
        } else {
            if (origem == null) {
                throw new ContaNaoExisteException(numOrigem);
            } else if (origem.getSaldo() < valor){
                throw new SaldoInsuficienteException(numOrigem);
            }
            if (destino == null) {
                throw new ContaNaoExisteException(numDestino);
            }
        }
    }
    
    public double getSaldo(String num) {
        Conta c = this.repositorio.procurar(num);
        return c.getSaldo();
    }
    
    public static final double TAXA_JUROS = 0.01;
    
    public void renderJuros(String num) throws ContaNaoExisteException {
        Conta c = this.repositorio.procurar(num);
        if (c instanceof Poupanca) {
            ((Poupanca) c).renderJuros(TAXA_JUROS);
            this.repositorio.salvarArquivo();
        } else {
            throw new ContaNaoExisteException(num);
        }
    }
}
