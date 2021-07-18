package logica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import clases.Carril;
import clases.Carro;
import clases.Conductor;
import clases.Juego;
import clases.Pista;
import clases.Podio;
import hilos.HiloArranque;

public class VentanaPrincipal extends JFrame implements ActionListener {

	public static final int ANCHO_AUTO = 80;
	public static final int ALTO_AUTO = 120;
	public static final int XCAR1 = 93, XCAR2 = 226, XCAR3 = 359, XCAR4 = 492, XCAR5 = 625; 
	public static final int X = 500, Y = 0, ANCHO_PISTA = 1000, ALTO_PISTA = 700;
	
	private static final String CONFIGURACION = "configuracion";
	private static final String INICAR = "iniciar";
	private static final String PUNTAJES = "puntajes";
	
	private static final String RUTA_PUNTAJES = "./data/puntajes.txt";
	private static final String RUTA_PUNTAJES_TEMP = "./data/puntajesTemp.txt";
	
	private JLabel lblPistaFondo;
	private ArrayList<JLabel> arraylistCarritos;
	private JButton btnConfiguracion;
	private JButton btnIniciar;
	private JButton btnPodio;
	private boolean podiumLleno;
	
	private Juego juego;
	
	private ArrayList<Carril> carriles;
	private ArrayList<HiloArranque> arraylistHilos;
	private ArrayList<Conductor> ganadores; 
	
	public VentanaPrincipal() {
		super("Carrera F1");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, ANCHO_PISTA, ALTO_PISTA);
		setVisible(true);
		getContentPane().setLayout(null); //Permite colocar nuestros elementos en las posiciones que queramos
		setResizable(false);
		inicializarComponentes();
		
	}
	
	public void inicializarComponentes() {
		
		arraylistCarritos = new ArrayList<JLabel>();
		lblPistaFondo = new JLabel();
		getContentPane().add(lblPistaFondo);
		lblPistaFondo.setIcon(new ImageIcon(getClass().getResource("/imagenes/asfalto2.png")));
		lblPistaFondo.setBounds(200, 0, 798, 700);
		
		btnConfiguracion = new JButton("Configuración");
		btnConfiguracion.setActionCommand(CONFIGURACION);
		btnConfiguracion.addActionListener(this);
		
		btnIniciar = new JButton("Iniciar");
		btnIniciar.setActionCommand(INICAR);
		btnIniciar.addActionListener(this);
		btnIniciar.setEnabled(false);
		
		btnPodio = new JButton("Puntajes");
		btnPodio.setActionCommand(PUNTAJES);
		btnPodio.addActionListener(this);
		
		getContentPane().add(btnConfiguracion);
		btnConfiguracion.setBounds(20, 10, 160, 30);
		
		getContentPane().add(btnIniciar);
		btnIniciar.setBounds(20, 60, 160, 30);
		
		getContentPane().add(btnPodio);
		btnPodio.setBounds(20, 110, 160, 30);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		if(comando.equals(CONFIGURACION)) {
			configuracionJuego();
		}else if(comando.equals(INICAR)) {
			btnIniciar.setEnabled(false);
			iniciarJuego();
		}else if(comando.equals(PUNTAJES)) {
			puntajesJuego();
		}
		
	}
	
	public void configuracionJuego() {
		
		podiumLleno = false;
		ganadores = new ArrayList<Conductor>();
		
		for(int i = 0; i<arraylistCarritos.size(); i++) {
			lblPistaFondo.remove(arraylistCarritos.get(i));
		}
		
		arraylistCarritos.clear();
		repaint();
		
		int pistaSeleccionada = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la pista:\n 1 - pista 30 Km\n 2 - pista 60 Km\n 3 - pista 120 Km", null));
		
		while(pistaSeleccionada != 1 && pistaSeleccionada != 2 && pistaSeleccionada != 3) {
			JOptionPane.showMessageDialog(null, "Valor incorrecto. Opciones validas: 1, 2 o 3");
			pistaSeleccionada = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de la pista:\n 1 - pista 20 Km\n 2 - pista 30 Km\n 3 - pista 50 Km", null));
		}
		int cantCarriles = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de carriles de la pista (Mínimo 2, Máximo 5)", null));
		
		while(cantCarriles != 2 && cantCarriles != 3 && cantCarriles != 4 && cantCarriles != 5) {
			JOptionPane.showMessageDialog(null, "Valor incorrecto. Opciones validas: 2, 3, 4 o 5");
			cantCarriles = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de carriles de la pista (Mínimo 2, Máximo 5)", null));
		}
		
		Pista pista = new Pista(pistaSeleccionada);
		juego = new Juego(pista);
		
		carriles = new ArrayList<Carril>();
		arraylistHilos = new ArrayList<HiloArranque>();
		
		for(int i = 0; i < cantCarriles; i++) {
			
			int modeloAuto = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el número del modelo del auto: \n 1 - Modelo AZA6 (rojo)\n 2 - Modelo AWA612 (azul)\n 3 - Modelo XYZA61 (verde)\n 4 - Modelo 18827 (amarrillo)\n 5 - Modelo PWS2! (blanco)", null));
			String nombreJugador = JOptionPane.showInputDialog("Nombre del jugador:", null);
			
			Conductor conductor = new Conductor(nombreJugador);
			
			posicionAutos(i, cantCarriles, conductor, modeloAuto);
			
		}
		
		pista.setCarriles(carriles);
		
		btnIniciar.setEnabled(true);
	}
	
	public void iniciarJuego() {
		for(int i = 0; i < arraylistHilos.size();i++) {
			arraylistHilos.get(i).start();
		}
	}
	
	public void puntajesJuego() {
		BufferedReader br;
		String cadenaLineatexto, valores[];
		try {
			File datos = new File( RUTA_PUNTAJES );
			
			if(datos.exists()) {
				br = new BufferedReader( new FileReader( datos ) );
				cadenaLineatexto = br.readLine( );
				
				String textoPuntajes = "";
				
				while( cadenaLineatexto != null ) {
					valores = cadenaLineatexto.split( "\t" );
					
					textoPuntajes = textoPuntajes + "Nombre jugador: " + valores[0] + " | Posicion: " + valores[1] + " | Veces ganadas: " + valores[2] + "\n";
					
					cadenaLineatexto = br.readLine();
				}
				
				JOptionPane.showMessageDialog(null, textoPuntajes);
			}else {
				JOptionPane.showMessageDialog(null, "Aún no hay puntajes registrados, inicie una partida nueva");
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	public void guardarGanadoresEnPodio() {
		
        File archivo = new File(RUTA_PUNTAJES);
        File tempFile = new File(RUTA_PUNTAJES_TEMP);
        
        try {
			
        	if(archivo.exists()) {
                
                BufferedReader reader = new BufferedReader(new FileReader(archivo));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                
                String currentLine;
                
                boolean jugadorOro = false;
                boolean jugadorPlata = false;
                boolean jugadorBronce = false;
                
                while((currentLine = reader.readLine()) != null) {
                	
                	String valores[] = currentLine.split( "\t" );
                	if(valores[0].equals(ganadores.get(0).getNombre())) {
                		if(valores[1].equals("oro")) {
                			int vecesGanadas = Integer.parseInt(valores[2]);
                			vecesGanadas = vecesGanadas + 1;
                			
                			writer.append(valores[0] + "\t" + valores[1] + "\t" + vecesGanadas + "\n");
                			jugadorOro = true;
                		}else {
                			writer.append(valores[0] + "\t" + valores[1] + "\t" + valores[2] + "\n");
                		}
                		
                	}else if(valores[0].equals(ganadores.get(1).getNombre())) {
                		if(valores[1].equals("plata")) {
                			int vecesGanadas = Integer.parseInt(valores[2]);
                			vecesGanadas = vecesGanadas + 1;
                			
                			writer.append(valores[0] + "\t" + valores[1] + "\t" + vecesGanadas + "\n");
                			jugadorPlata = true;
                		}else {
                			writer.append(valores[0] + "\t" + valores[1] + "\t" + valores[2] + "\n");
                		}
                		
                	}else if(ganadores.size() == 3) {
                		if(valores[0].equals(ganadores.get(2).getNombre())) {
                    		if(valores[1].equals("bronce")) {
                    			int vecesGanadas = Integer.parseInt(valores[2]);
                    			vecesGanadas = vecesGanadas + 1;
                    			
                    			writer.append(valores[0] + "\t" + valores[1] + "\t" + vecesGanadas + "\n");
                    			jugadorBronce = true;
                    		}else {
                    			writer.append(valores[0] + "\t" + valores[1] + "\t" + valores[2] + "\n");
                    		}
                    		
                    	}else {
                    		writer.append(valores[0] + "\t" + valores[1] + "\t" + valores[2] + "\n");
                    	}
                	}else {
                		writer.append(valores[0] + "\t" + valores[1] + "\t" + valores[2] + "\n");
                	}
                }
                
                if(!jugadorOro) {
                	writer.append(ganadores.get(0).getNombre() + "\t" + "oro\t" + "1\n");
                }
                if(!jugadorPlata) {
                	writer.append(ganadores.get(1).getNombre() + "\t" + "plata\t" + "1\n");
                }
                if(ganadores.size() == 3) {
                	if(!jugadorBronce) {
                    	writer.append(ganadores.get(2).getNombre() + "\t" + "bronce\t" + "1\n");
                    }
                }
                
                writer.close(); 
            	reader.close();
            	//archivo.delete();
            	//tempFile.renameTo(archivo);
            	
            	FileInputStream in = new FileInputStream(tempFile);
                FileOutputStream out = new FileOutputStream(archivo);
                
                try {
                	int n;
                    // read() function to read the
                    // byte of data
                    while ((n = in.read()) != -1) {
                        // write() function to write
                        // the byte of data
                        out.write(n);
                    }
				} finally {
		            if (in != null) {
		                // close() function to close the
		                // stream
		                in.close();
		            }
		            // close() function to close
		            // the stream
		            if (out != null) {
		                out.close();
		            }
		        }
                
                tempFile.delete();
            	
            } else {
            	BufferedWriter bw;
                bw = new BufferedWriter(new FileWriter(archivo, true));
                bw.append(ganadores.get(0).getNombre() + "\toro" + "\t1\n");
                bw.append(ganadores.get(1).getNombre() + "\tplata" + "\t1\n");
                
                if(ganadores.size() == 3) {
                	bw.append(ganadores.get(2).getNombre() + "\tbronce" + "\t1\n");
                }
                
                bw.close();
            }
        	
            
        	
		} catch (Exception e) {
			System.err.println(e);
		}
        
	}
	
	public void posicionAutos(int i, int cantCarriles, Conductor conductor, int modeloAuto) {
		
		if(cantCarriles== 2) {
			if(i == 0) {
				Carro carro = new Carro(modeloAuto, XCAR2, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(1, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
				
			}else {
				Carro carro = new Carro(modeloAuto, XCAR4, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(2, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}
		}else if(cantCarriles== 3) {
			if(i == 0) {
				Carro carro = new Carro(modeloAuto, XCAR1, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(1, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else if(i == 1){
				Carro carro = new Carro(modeloAuto, XCAR3, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(2, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else {
				Carro carro = new Carro(modeloAuto, XCAR5, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(3, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}
		}else if(cantCarriles== 4) {
			if(i == 0) {
				Carro carro = new Carro(modeloAuto, XCAR1 + 66, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(1, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else if(i == 1){
				Carro carro = new Carro(modeloAuto, XCAR2 + 66, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(2, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else if(i == 2){
				Carro carro = new Carro(modeloAuto, XCAR3 + 66, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(3, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else {
				Carro carro = new Carro(modeloAuto, XCAR4 + 66, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(4, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}
		}else if(cantCarriles== 5) {
			if(i == 0) {
				Carro carro = new Carro(modeloAuto, XCAR1, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(1, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else if(i == 1){
				Carro carro = new Carro(modeloAuto, XCAR2, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(2, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else if(i == 2){
				Carro carro = new Carro(modeloAuto, XCAR3, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(3, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else if(i == 3){
				Carro carro = new Carro(modeloAuto, XCAR4, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(4, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}else {
				Carro carro = new Carro(modeloAuto, XCAR5, ALTO_PISTA- 150, ANCHO_AUTO, ALTO_AUTO, conductor);
				Carril carril = new Carril(5, carro);
				carriles.add(carril);
				
				disenarCarrito(conductor, carro);
			}
		}
	}
	
	public void disenarCarrito(Conductor conductor, Carro carro) {
		
		JLabel carritoNuevo = new JLabel();
		lblPistaFondo.add(carritoNuevo);
		carritoNuevo.setBackground(carro.getColor());
		carritoNuevo.setOpaque(true);
		//carritoNuevo.setBorder(new LineBorder(Color.black));
		carritoNuevo.setIcon(new ImageIcon(getClass().getResource("/imagenes/carrito.png")));
		carritoNuevo.setBounds(carro.getPosX(), carro.getPosY(), carro.getAncho(), carro.getAlto());
		
		JLabel textCar = new JLabel(conductor.getNombre());
		textCar.setFont (textCar.getFont ().deriveFont (20.0f));
		//textCar.setHorizontalAlignment(JLabel.CENTER);
		textCar.setLocation(5, 50);
		textCar.setSize(textCar.getPreferredSize());
		
		carritoNuevo.add(textCar);
		
		arraylistCarritos.add(carritoNuevo);
		
		HiloArranque hiloCarro = new HiloArranque(carro.getPosX(), ALTO_PISTA, juego.getPista().getDistanciaPista(), carritoNuevo, carro, this);
		
		arraylistHilos.add(hiloCarro);
	}
	
	public void registrarGanadoresAPodio(Carro carro) {
		if(juego.getPista().getCarriles().size() == 2) {
			if(ganadores.size() < 2) {
				ganadores.add(carro.getConductor());
			}
			
			if(ganadores.size() == 2) {
				if(!podiumLleno) {
					podiumLleno = true;
					System.out.println("Oro: " + ganadores.get(0).getNombre());
					System.out.println("Plata: " + ganadores.get(1).getNombre());
					
					Podio podio = new Podio(ganadores.get(0), ganadores.get(1), null);
					
					juego.setPodio(podio);
					
					guardarGanadoresEnPodio();
				}
			}
		}else {
			if(ganadores.size() < 3) {
				ganadores.add(carro.getConductor());
			}
			
			if(ganadores.size() == 3) {
				
				if(!podiumLleno) {
					podiumLleno = true;
					System.out.println("Oro: " + ganadores.get(0).getNombre());
					System.out.println("Plata: " + ganadores.get(1).getNombre());
					System.out.println("Bronce: " + ganadores.get(2).getNombre());
					
					Podio podio = new Podio(ganadores.get(0), ganadores.get(1), ganadores.get(2));
					
					juego.setPodio(podio);
					
					guardarGanadoresEnPodio();
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
		VentanaPrincipal juego = new VentanaPrincipal();
	}

}
