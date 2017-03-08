package banco.sistema.conta;

import java.io.Serializable;

public class Conta implements Serializable {

	private String numero;
	private String cliente;
	protected double saldo;
	
	public Conta(String cliente, String numero, double saldo){
		this.setCliente(cliente);
		this.setNumero(numero);
		this.setSaldo(saldo);
	}
	
	public String getNumero() {
		return numero;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setNumero(String numero) {
		if (numero != null) {
			this.numero = numero;	
		} else {
			System.out.println("N�mero inv�lido");
		}
	}

	public void setSaldo(double saldo) {
		if (saldo >= 0.0) {
			this.saldo = saldo;	
		}else{
			System.out.println("Saldo inv�lido");
		}
	}
	
	public boolean creditar (double valor) {
		boolean resultado;
		if (valor > 0) {
			this.saldo = this.saldo + valor;
			resultado = true;
		} else {
			System.out.println("Cr�dito inv�lido");
			resultado = false;
		}
		return resultado;
	}
	
	public boolean debitar (double valor) {
		boolean resultado;
		if (valor > 0) {
			this.saldo = this.saldo - valor;
			resultado = true;
		} else {
			System.out.println("D�bito inv�lido");
			resultado = false;
		}
		return resultado;
	}
	
	public boolean transferir(double valor, Conta contaDestino) {
		boolean resultado;
		if (valor > 0) {
			if (contaDestino != null) {
				if (this.saldo >= valor) {
					this.debitar(valor);
					contaDestino.creditar(valor);
					resultado = true;
				} else {
					System.out.println("Saldo insuficiente");
					resultado = false;
				}
			} else {
				System.out.println("Conta destino inv�lida");
				resultado = false;
			}				
		} else {
			System.out.println("Transfer�ncia inv�lida");
			resultado = false;
		}
		return resultado;
	}
	
	
}
