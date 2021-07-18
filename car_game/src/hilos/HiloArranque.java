package hilos;

import javax.swing.JLabel;

import clases.Carro;
import logica.VentanaPrincipal;

public class HiloArranque extends Thread {

	private int posicionInicialX;
	private int limitePista;
	private int distanciaPista;
	private JLabel auto;
	private Carro carro;
	private VentanaPrincipal ventana;
	
	public HiloArranque(int posicionInicialX, int limitePista, int distanciaPista, JLabel auto, Carro carro, VentanaPrincipal ventana) {
		this.posicionInicialX = posicionInicialX;
		this.limitePista = limitePista;
		this.distanciaPista = distanciaPista;
		this.auto = auto;
		this.carro = carro;
		this.ventana = ventana;
	}
	
	@Override
	public void run() {
		super.run();
		int avance = 0;
		for(int i = limitePista - 150; i > 0; i=i-avance) {
			auto.setBounds(posicionInicialX, i, VentanaPrincipal.ANCHO_AUTO, VentanaPrincipal.ALTO_AUTO);
			avance = avanceAleatorioDado();
			try {
				sleep(20);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		yield();
		System.out.println("Llegó: " + carro.getConductor().getNombre());
		ventana.registrarGanadoresAPodio(carro);
	}
	
	public int avanceAleatorioDado() {
		
		int dado = (int) (Math.random() * 6) + 1;
		
		double recorridoMetros = dado *100;
		
		double porcentajeRecorrido = recorridoMetros / (distanciaPista*1000);
		
		double avancePrevio = porcentajeRecorrido * ((double)limitePista);
		
		int avanceFinal = (int)avancePrevio;
		
		return avanceFinal;
	}
}
