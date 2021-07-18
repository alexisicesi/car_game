package clases;

public class Juego {

	private Pista pista;
	private Podio podio;
	
	public Juego(Pista pista) {
		this.pista = pista;
		this.podio = null;
	}
	
	public Pista getPista() {
		return pista;
	}
	public void setPista(Pista pista) {
		this.pista = pista;
	}
	public Podio getPodio() {
		return podio;
	}
	public void setPodio(Podio podio) {
		this.podio = podio;
	}
	
	
}
