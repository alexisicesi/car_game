package clases;

public class Carril {

	private int numeroCarril;
	private Carro carro;
	
	public Carril(int numeroCarril, Carro carro) {
		this.numeroCarril = numeroCarril;
		this.carro = carro;
	}
	
	public int getNumeroCarril() {
		return numeroCarril;
	}
	public void setNumeroCarril(int numeroCarril) {
		this.numeroCarril = numeroCarril;
	}
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	
}
