package clases;

import java.awt.Color;

public class Carro {

	private Color color;
	private int posX;
	private int posY;
	private int ancho;
	private int alto;
	private Conductor conductor;
	
	public Carro(int color, int posX, int posY, int ancho, int alto, Conductor conductor) {
		this.color = validarColor(color);
		this.posX = posX;
		this.posY = posY;
		this.ancho = ancho;
		this.alto = alto;
		this.conductor = conductor;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public int getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public Conductor getConductor() {
		return conductor;
	}
	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}
	
	public Color validarColor(int numeroColor) {
		if(numeroColor == 1) {
			return Color.RED;
		}else if(numeroColor == 2) {
			return Color.BLUE;
		}else if(numeroColor == 3) {
			return Color.GREEN;
		}else if(numeroColor == 4) {
			return Color.YELLOW;
		}else if(numeroColor == 5) {
			return Color.WHITE;
		}else {
			return Color.BLACK;
		}
	}
}
