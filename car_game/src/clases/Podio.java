package clases;

public class Podio {

	private Conductor oro;
	private Conductor plata;
	private Conductor bronce;
	
	public Podio(Conductor oro, Conductor plata, Conductor bronce) {
		this.oro = oro;
		this.plata = plata;
		this.bronce = bronce;
	}
	
	public Conductor getOro() {
		return oro;
	}
	public void setOro(Conductor oro) {
		this.oro = oro;
	}
	public Conductor getPlata() {
		return plata;
	}
	public void setPlata(Conductor plata) {
		this.plata = plata;
	}
	public Conductor getBronce() {
		return bronce;
	}
	public void setBronce(Conductor bronce) {
		this.bronce = bronce;
	}
	
	
}
