package GUI;

import javax.swing.*;

import Logica.Celda;

public class LabelTablero extends JLabel{
	
	private Celda celdaAsociada;
	
	public LabelTablero(Celda cel) {
		super();
		celdaAsociada = cel;
	}
	
	public Celda getCeldaAsociada() {
		return celdaAsociada;
	}
}
