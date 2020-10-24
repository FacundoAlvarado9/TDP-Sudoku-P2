package GUI;

import javax.swing.*;

import Logica.Celda;

public class LabelCelda extends JLabel{
	
	private Celda celdaAsociada;
	
	public LabelCelda(Celda cel) {
		super();
		celdaAsociada = cel;
	}
	
	public Celda getCeldaAsociada() {
		return celdaAsociada;
	}
}
