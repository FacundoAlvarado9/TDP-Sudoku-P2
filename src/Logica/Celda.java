package Logica;

/**
 * Clase Celda. Representa una celda de un tablero de Sudoku de una partida determinada.
 * Con valores del 1 al 9, y el valor 0 (cero) que representa una celda vacía.
 */
public class Celda {
	
	private int valor;
	private AyudanteGraficoCelda img;

	//Una elda es invalida si no permite la resolución del juego.
	private boolean validez;

	/**
	 * Inicializa una celda de Sudoku con el numero pasado por parametro.
	 * La celda se inicializa junto con su ayudante gráfico, y se supone
	 * válida.
	 * @param numEnCelda numero en la celda
	 */
	public Celda(int numEnCelda) {
		this.valor = numEnCelda;
		this.img = new AyudanteGraficoCelda(numEnCelda);
		validez = true;
	}
	
	public int getValor() {
		return valor;
	}

	public AyudanteGraficoCelda getGrafico(){
		return this.img;
	}
	
	public boolean esValida() {
		return validez;
	}
	
	public void setValidez(boolean validez) {
		this.validez = validez;
	}

	/**
	 * Actualiza el valor de la celda. Si el valor anterior era menor a 9, se suma uno al numero.
	 * De lo contrario, se regresa a 0 (cero).
	 */
	public void actualizarValor() {
		valor = ((valor+1)%10);
		this.img.actualizar(valor);
	}
}
