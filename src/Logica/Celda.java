package Logica;

public class Celda {
	private int valor;
	
	public Celda(int numEnCelda) {
		this.valor = numEnCelda;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int nuevoValor) {
		valor = nuevoValor;
	}
	
	public void actualizarValor() {
		valor = ((valor+1)%10);
	}
}
