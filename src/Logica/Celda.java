package Logica;

public class Celda {
	
	private int valor;
	private boolean validez;
	
	//Los cuadrantes se numeran por fila, de izq a der,
	//comenzando con la fila de arriba, quedando
	/* 
	 * 1 / 2 / 3 
	 * 4 / 5 / 6
	 * 7 / 8 / 9
	 */
	//private int cuadrante;
	
	public Celda(int numEnCelda) {
		this.valor = numEnCelda;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int nuevoValor) {
		valor = nuevoValor;
	}
	
	public boolean esValida() {
		return validez;
	}
	
	public void setValidez(boolean validez) {
		this.validez = validez;
	}
	
	public void actualizarValor() {
		valor = ((valor+1)%10);
	}
}
