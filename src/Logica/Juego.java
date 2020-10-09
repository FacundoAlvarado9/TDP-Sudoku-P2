package Logica;

import java.util.Random;

public class Juego {
	
	private Celda[][] tablero;
	private int tamanoSudoku = 9;
	
	private Random rand;
	
	public Juego() {
		tablero = new Celda[tamanoSudoku][tamanoSudoku];
	
		rand = new Random();
		
		//Inicializo los valores del tablero
		int numEnCelda;
		
		for(int f=0; f<tamanoSudoku; f++) {
			for(int c=0; c<tamanoSudoku; c++) {
				numEnCelda = rand.nextInt(tamanoSudoku + 1); //random de 0 a 9
				tablero[f][c] = new Celda(numEnCelda); //Manejo al cero (0) como celda vacia
			}
		}
	}
	
	public Celda getCelda(int f, int c) {
		return tablero[f][c];
	}
	
	
}
