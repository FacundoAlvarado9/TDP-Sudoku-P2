import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Logica.Celda;
import Logica.Juego;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JPanel tableroPanel;
	private JPanel panelBotones;

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
		contentPane.add(panelBotones, BorderLayout.EAST);
	}
	
	public void arrancarTableroPanel() {
		tableroPanel = new JPanel();
		tableroPanel.setBackground(Color.WHITE);
		tableroPanel.setLayout(new GridLayout(9, 9));
		
		Juego juego = new Juego();
	
		int tamanoSudoku = 9;
		
		for(int f=0; f<tamanoSudoku; f++) {
			for(int c=0; c<tamanoSudoku; c++) {		
				
				Celda cel = juego.getCelda(f, c);
				
				JLabel label = new JLabel(String.valueOf(cel.getValor()));
				
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cel.actualizarValor();
						label.setText(String.valueOf(cel.getValor()));
						label.repaint();
					}
				});			
				
				tableroPanel.add(label);
				
			}
		}	
	}
	
	public void arrancarPanelBotones() {
		panelBotones = new JPanel();
		panelBotones.setLayout(new BorderLayout());
		
		JLabel titulo = new JLabel("Bienvenido al sudoku");
		
		JLabel reloj = new JLabel("00:00");
		JButton btnReiniciarJuego = new JButton("Reiniciar");
		
		
		panelBotones.add(titulo, BorderLayout.CENTER);
		panelBotones.add(btnReiniciarJuego, BorderLayout.NORTH);		
		panelBotones.add(reloj, BorderLayout.SOUTH);
	}

}
