package GUI;

import javax.swing.*;

import Logica.Celda;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase LabelCelda. Define la representación gráfica de una celda.
 */
public class LabelCelda extends JLabel{
	
	private Celda celdaAsociada;
	private ImageIcon grafico;
	private boolean marcada;

	/**
	 * Inicializa la rep. gráfica de una celda asociada a la celda del juego pasada
	 * como parametro. Añade la capacidad de redimensionarse con cambios en la ventana
	 * y la capacidad de reaccionar a los clicks del usuario modificando el estado de la
	 * celda asociada.
	 * @param cel celda asociada a la celda gráfica
	 */
	public LabelCelda(Celda cel) {
		super();

		celdaAsociada = cel;
		grafico = cel.getGrafico().getImagen();
		marcada = false;

		setBackground(Color.WHITE);
		setOpaque(true);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				redimensionar(grafico);
				setIcon(grafico);
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cel.actualizarValor();
				redimensionar(grafico);
			}
		});
	}

	/**
	 * En caso de que la celda asociada sea inválida (no permita la resolución del juego)
	 * la marca gráficamente con un fondo color rojo. De lo contrario, la marca con un fondo
	 * blanco.
	 */
	public void chequearYMarcarDeSerNecesario(){
		if(!celdaAsociada.esValida()) {
			setBackground(Color.RED);
			marcada = true;
		} else{
			setBackground(Color.WHITE);
		}
	}

	/**
	 * Desmarca la celda.
	 */
	public void desmarcar(){
		marcada = false;
		setBackground(Color.WHITE);
	}

	/**
	 * Cambia el tamaño del gráfico de la celda para acomodarse con la ventana.
	 * @param grafico de la celda.
	 */
	private void redimensionar(ImageIcon grafico){
		Image imagen = grafico.getImage();
		if(imagen != null){
			Image newImg = imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
			grafico.setImage(newImg);
			repaint();

		}
	}
}
