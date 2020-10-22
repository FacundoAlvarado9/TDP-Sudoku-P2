package GUI;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Logica.Celda;
import Logica.Juego;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel tableroPanel;
	private JPanel[][] subPanel;
	private JPanel panelBotones;
	
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
		
		inicializarTableroPanel();
		inicializarPanelBotones();
		
		contentPane.add(tableroPanel, BorderLayout.CENTER);
		contentPane.add(panelBotones, BorderLayout.SOUTH);
	}

	public void inicializarSubPaneles(){
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
	
	public void inicializarTableroPanel() {
		tableroPanel = new JPanel();
		tableroPanel.setBackground(Color.WHITE);
		tableroPanel.setLayout(new GridLayout(3, 3));

		inicializarSubPaneles();
		
		juego = new Juego();

		for(int fila=0; fila<9; fila++) {
			for(int col=0; col<9; col++) {
				
				Celda cel = juego.getCelda(fila, col);
				ImageIcon grafico = cel.getGrafico().getImagen();
				LabelCelda labelCelda = new LabelCelda(cel);
				labelCelda.setOpaque(true);

				labelCelda.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						redimensionar(labelCelda, grafico);
						labelCelda.setIcon(grafico);
					}
				});
				
				labelCelda.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(estanCeldasMarcadas) {
							desmarcarCeldasInvalidas();
						}
						juego.actualizarValorCelda(cel);
						redimensionar(labelCelda, grafico);
					}
				});

				int filaSubPanel = computarFilaOColumnaSubPanel(fila);
				int colSubPanel = computarFilaOColumnaSubPanel(col);

				subPanel[filaSubPanel][colSubPanel].add(labelCelda);

			}
		}	
	}

	private void redimensionar(LabelCelda label, ImageIcon grafico){
		Image imagen = grafico.getImage();
		if(imagen != null){
			Image newImg = imagen.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
			grafico.setImage(newImg);
			label.repaint();
		}
	}

	/**
	 * Reestablece el fondo blanco de las celdas del tablero.
	 */
	private void desmarcarCeldasInvalidas(){
		for(Component c : tableroPanel.getComponents()) {
			JPanel subPanel = (JPanel) c;

			for(Component label : subPanel.getComponents()){
				LabelCelda labelCelda = (LabelCelda) label;

				labelCelda.setBackground(Color.WHITE);

				labelCelda.setOpaque(true);
				labelCelda.repaint();
			}

			estanCeldasMarcadas = false;
		}
	}

	/**
	 * Establece un fondo rojo para las celdas invalidas.
	 * Las celdas invalidas son aquellas que no cumplen las condiciones
	 * de un juego ganado.
	 */
	public void marcarCeldasInvalidas(){
		for(Component compSubPanel : tableroPanel.getComponents()) {
			JPanel subPanel = (JPanel) compSubPanel;

			for(Component label : subPanel.getComponents()){
				LabelCelda labelCelda = (LabelCelda) label;

				if(!labelCelda.getCeldaAsociada().esValida()) {
					labelCelda.setBackground(Color.RED);
					estanCeldasMarcadas = true;
				} else{
					labelCelda.setBackground(Color.WHITE);
				}

				labelCelda.repaint();
			}
		}
	}
	
	public void inicializarPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());

		JLabel mensajeSaludo = new JLabel("Bienvenido al sudoku");
		//CronometroGUI cronometro = new CronometroGUI(juego.getTimeInicio());
		CronometroGUI2 cronometro = new CronometroGUI2(juego.getTimeInicio());

		JButton btnReiniciarJuego = new JButton("Reiniciar");
		JButton btnChequearSolucion = new JButton("Chequear solución");
		
		btnChequearSolucion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {

				if(juego.gano()) {
					Duration d = cronometro.parar();

					String hora_ = String.format("%02d:%02d:%02d",
							d.toHours(),
							d.toMinutesPart(),
							d.toSecondsPart());

					JOptionPane.showMessageDialog(null,
							"¡Ganó! En una duración de juego de " + hora_,
							"¡Ganaste!",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					marcarCeldasInvalidas();
				}



			}
		});
		
		panelBotones.add(mensajeSaludo);
		panelBotones.add(btnReiniciarJuego);
		panelBotones.add(btnChequearSolucion);
		panelBotones.add(cronometro);
	}

}
