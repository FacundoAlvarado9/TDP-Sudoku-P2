package GUI;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import GUI.Cronometro.Cronometro;
import logica.ArchivoInvalidoException;
import logica.Celda;
import logica.Juego;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel tableroPanel;
	private JPanel[][] subPanel;
	private JPanel panelBotones;

	private JButton btnChequearSolucion;
	private JButton btnIniciarFinalizarJuego;
	private JLabel estadoDelJuego;

	private Cronometro cronometro;
	
	private Juego juego;
	private boolean estanCeldasMarcadas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension(800, 600));
		setTitle("Sudoku by Facundo Alvarado");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);



		cronometro = new Cronometro();

		inicializarTableroPanel();

		inicializarPanelBotones();

		contentPane.add(tableroPanel, BorderLayout.CENTER);
		contentPane.add(cronometro, BorderLayout.NORTH);
		contentPane.add(panelBotones, BorderLayout.SOUTH);
	}

	/**
	 * Inicializa el tablero (gráfico) del juego. Si el juego no está iniciado,
	 * solo inicializa los subpaneles 3x3 del sudoku.
	 */
	private void inicializarTableroPanel() {
		tableroPanel = new JPanel();
		tableroPanel.setMinimumSize(new Dimension(500, 500));
		tableroPanel.setBackground(Color.WHITE);
		tableroPanel.setLayout(new GridLayout(3, 3));

		inicializarSubPaneles();

	}

	/**
	 * Inicializa los subpaneles 3x3 del Sudoku, y los añade al panel del Tablero.
	 */
	private void inicializarSubPaneles(){
		subPanel = new JPanel[3][3];
		for(int f=0; f<3; f++){
			for(int c=0; c<3; c++){
				subPanel[f][c] = new JPanel();
				subPanel[f][c].setLayout(new GridLayout(3, 3));
				subPanel[f][c].setBackground(Color.WHITE);
				subPanel[f][c].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
				tableroPanel.add(subPanel[f][c]);
			}
		}
	}

	/**
	 * Inicializa el panel inferior de la ventana, que contiene los botones que controlan
	 * el juego. Boton 'Iniciar/Finalizar' juego, y Boton 'Chequear Solución'
	 */
	private void inicializarPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());

		estadoDelJuego = new JLabel("No estás jugando, inicia un juego con el botón ->");

		btnIniciarFinalizarJuego = new JButton("Iniciar juego");
		btnChequearSolucion = new JButton("Chequear solución");

		btnIniciarFinalizarJuego.setEnabled(true);
		btnChequearSolucion.setEnabled(false);

		btnIniciarFinalizarJuego.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				iniciarJuego();
				btnIniciarFinalizarJuego.setEnabled(false);
			}
		});

		btnChequearSolucion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {

				if(juego.gano()) {
					finalizarJuego();
				} else {
					marcarCeldasInvalidas();
				}

			}
		});

		panelBotones.add(estadoDelJuego);
		panelBotones.add(btnIniciarFinalizarJuego);
		panelBotones.add(btnChequearSolucion);
	}

	/**
	 * Inicializa una partida de Sudoku incluyendo los componentes gráficos: crea las celdas, les añade
	 * un chequeo de marcado de celdas inválidas, y añade cada celda a su panel correspondiente.
	 */
	private void iniciarJuego(){

		try {
			juego = new Juego();

			//Para cada fila y columna del tablero gráfico
			for(int fila=0; fila<9; fila++) {
				for(int col=0; col<9; col++) {

					Celda cel = juego.getCelda(fila, col); //Recupera la celda del juego correspondiente
					ImageIcon grafico = cel.getGrafico().getImagen();

					LabelCelda labelCelda = new LabelCelda(cel); //Inicializa una celda gráfica en la GUI

					//A la hora de actualizar la celda, chequea el marcado gráfico de celdas invalidas. Si están marcadas,
					//las desmarca.
					labelCelda.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(estanCeldasMarcadas){
								desmarcarCeldasInvalidas();
							}
						}
					});

					int filaSubPanel = computarFilaOColumnaSubPanel(fila);
					int colSubPanel = computarFilaOColumnaSubPanel(col);

					subPanel[filaSubPanel][colSubPanel].add(labelCelda);

				}
			}
			tableroPanel.revalidate();
			cronometro.arrancar(juego.getTimeInicio());

			estadoDelJuego.setText("Juego en curso ");
			btnChequearSolucion.setEnabled(true);

		} catch (ArchivoInvalidoException e) {
			JOptionPane.showMessageDialog(null,
					"Hubo un problema al iniciar el Sudoku.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Calcula la fila o columna del subpanel al que pertenece una celda.
	 * @param fc fila o columna de la celda a computar su subpanel
	 * @return fila o columna del subpanel correspondiente.
	 */
	private int computarFilaOColumnaSubPanel(int fc){
		int fcSubPanel;
		if(fc<3){
			fcSubPanel = 0;
		} else if(fc<6){
			fcSubPanel = 1;
		} else{
			fcSubPanel = 2;
		}
		return fcSubPanel;
	}


	/**
	 * Establece un fondo rojo para las celdas invalidas.
	 * Las celdas invalidas son aquellas que no cumplen las condiciones
	 * de un juego ganado.
	 */
	private void marcarCeldasInvalidas(){
		for(Component compSubPanel : tableroPanel.getComponents()) {
			JPanel subPanel = (JPanel) compSubPanel;

			//Para cada celda de cada subpanel
			for(Component label : subPanel.getComponents()){
				LabelCelda labelCelda = (LabelCelda) label;

				//Si su celda asociada es inválida (no permite la resolución del juego),
				//marcarla gráficamente (ver clase LabelCelda).
				labelCelda.chequearYMarcarDeSerNecesario();
				labelCelda.repaint();
			}
		}
		estanCeldasMarcadas = true;
	}

	/**
	 * Reestablece el fondo blanco de las celdas del tablero.
	 */
	private void desmarcarCeldasInvalidas(){
		for(Component c : tableroPanel.getComponents()) {
			JPanel subPanel = (JPanel) c;

			for(Component label : subPanel.getComponents()){
				LabelCelda labelCelda = (LabelCelda) label;

				labelCelda.desmarcar();
				labelCelda.repaint();
			}

			estanCeldasMarcadas = false;
		}
	}

	/**
	 * Muestra un dialogo felicitando al ganador e inhabilita las celdas
	 * del sudoku, cambiando su fondo a un color gris oscuro.
	 */
	private void finalizarJuego() {

		//Se muestra un dialogo felicitando al jugador, junto con el tiempo de juego.
		Duration d = cronometro.parar();

		String duracionJuego = String.format("%02d:%02d:%02d",
				d.toHoursPart(),
				d.toMinutesPart(),
				d.toSecondsPart());

		JOptionPane.showMessageDialog(null,
				"¡Ganó! En una duración de juego de " + duracionJuego,
				"¡Ganaste!",
				JOptionPane.INFORMATION_MESSAGE);

		//Se inhabilita el botón para chequear solución
		btnChequearSolucion.setEnabled(false);

		estadoDelJuego.setText("¡Ganaste! con un tiempo de " + duracionJuego);

		//Se inhabilitan las celdas del juego.
		for(Component c : tableroPanel.getComponents()) {
			JPanel subPanel = (JPanel) c;

			for(Component label : subPanel.getComponents()){
				LabelCelda labelCelda = (LabelCelda) label;

				labelCelda.setBackground(Color.DARK_GRAY);
				labelCelda.removeMouseListener(labelCelda.getMouseListeners()[0]);

				labelCelda.setOpaque(true);
				labelCelda.repaint();
			}
		}
	}

}
