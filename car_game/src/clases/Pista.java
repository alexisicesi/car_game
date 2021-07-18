package clases;

import java.util.ArrayList;

public class Pista {

	private ArrayList<Carril> carriles;
	private int distanciaPista;
	
	public Pista(int numeroDePista) {
		this.distanciaPista = validarPista(numeroDePista);
		this.carriles = new ArrayList<Carril>();
	}

	public ArrayList<Carril> getCarriles() {
		return carriles;
	}

	public void setCarriles(ArrayList<Carril> carriles) {
		this.carriles = carriles;
	}

	public int getDistanciaPista() {
		return distanciaPista;
	}

	public void setDistanciaPista(int distanciaPista) {
		this.distanciaPista = distanciaPista;
	}
	
	public void setCarril(Carril carril) {
		this.carriles.add(carril);
	}
	
	public int validarPista(int numeroPista) {
		if(numeroPista == 1) {
			return 30;
		}else if(numeroPista == 2) {
			return 60;
		}else if(numeroPista == 3) {
			return 120;
		}else {
			return 0;
		}
		
	}
}
