package GUI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Logica.Celda;
import Logica.Juego;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel tableroPanel;
	private JPanel panelBotones;
	
	private Juego juego;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		arrancarTableroPanel();
		arrancarPanelBotones();
		
		contentPane.add(tableroPanel, BorderLayout.CENTER);
		contentPane.add(panelBotones, BorderLayout.SOUTH);
	}
	
	public void arrancarTableroPanel() {
		tableroPanel = new JPanel();
		tableroPanel.setBackground(Color.WHITE);
		tableroPanel.setLayout(new GridLayout(3, 3));
		
		JPanel subPanel[][] = new JPanel[3][3];

		for(int f=0; f<3; f++){
			for(int c=0; c<3; c++){
				subPanel[f][c] = new JPanel();
				subPanel[f][c].setLayout(new GridLayout(3, 3));
				subPanel[f][c].setBackground(Color.WHITE);
				subPanel[f][c].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
				//subPanel[f][c].add(new JLabel("Hola soy panel f:" +  f + " c: " + c));
				tableroPanel.add(subPanel[f][c]);
			}
		}
		
		juego = new Juego();
	
		int tamanoSudoku = 9;
		
		for(int f=0; f<tamanoSudoku; f++) {
			for(int c=0; c<tamanoSudoku; c++) {		
				
				Celda cel = juego.getCelda(f, c);
				ImageIcon grafico = cel.getGrafico().getGrafico();
				LabelTablero label = new LabelTablero(cel);

				label.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentResized(ComponentEvent e) {
						redimensionar(label, grafico);
						label.setIcon(grafico);
					}
				});
				
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						juego.actualizarValorCelda(cel);
						redimensionar(label, grafico);
					}
				});

				//tableroPanel.add(label);
				int filaSubPanel, colSubPanel;
				if(f<3){
					filaSubPanel = 0;
				} else if(f<6){
					filaSubPanel = 1;
				} else{
					filaSubPanel = 2;
				}

				if(c<3){
					colSubPanel = 0;
				} else if(c<6){
					colSubPanel = 1;
				} else{
					colSubPanel = 2;
				}

				subPanel[filaSubPanel][colSubPanel].add(label);

			}
		}	
	}

	private void redimensionar(LabelTablero label, ImageIcon grafico){
		Image imagen = grafico.getImage();
		if(imagen != null){
			Image newImg = imagen.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
			grafico.setImage(newImg);
			label.repaint();
		}
	}
	
	public void arrancarPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		JLabel titulo = new JLabel("Bienvenido al sudoku");
		
		JLabel reloj = new JLabel("00:00");
		JButton btnReiniciarJuego = new JButton("Reiniciar");
		JButton btnChequearSolucion = new JButton("Chequear solución");
		
		btnChequearSolucion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {


				if(juego.gano()) {
					JOptionPane.showMessageDialog(null, "Ganó!");
				}

				for(Component c : tableroPanel.getComponents()) {
					JPanel subPanel = (JPanel) c;

					for(Component label : subPanel.getComponents()){
						LabelTablero labelCelda = (LabelTablero) label;

						if(!labelCelda.getCeldaAsociada().esValida()) {
							labelCelda.setBackground(Color.RED);
						} else{
							labelCelda.setBackground(Color.WHITE);
						}
						labelCelda.setOpaque(true);
						labelCelda.repaint();
					}

				}


				

			}
		});
		
		
		panelBotones.add(titulo);
		panelBotones.add(btnReiniciarJuego);
		panelBotones.add(btnChequearSolucion);
		panelBotones.add(reloj);
	}

}
