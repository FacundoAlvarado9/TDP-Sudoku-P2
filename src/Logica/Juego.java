package Logica;

import java.util.Random;
import java.io.*;

public class Juego {
	
	
	private static final String ARCHIVO_ORIGEN = "/home/facundo/devstuff/sudoku1.txt";
	private Celda[][] tablero;
	private int tamanoSudoku = 9;
	
	private Random rand;
	
	public Juego() {
		tablero = new Celda[tamanoSudoku][tamanoSudoku];
		System.out.println("Llegue");
	
		rand = new Random();
		
		iniciarJuego();
		
		/*
		//Inicializo los valores del tablero
		int numEnCelda;
		
		for(int f=0; f<tamanoSudoku; f++) {
			for(int c=0; c<tamanoSudoku; c++) {
				numEnCelda = rand.nextInt(tamanoSudoku + 1); //random de 0 a 9
				tablero[f][c] = new Celda(numEnCelda); //Manejo al cero (0) como celda vacia
			}
		} */
	}
	
	private void iniciarJuego() {
		String[] lineaNum;
		int fila = 0;
		//Llenamos el tablero de los digitos en el archivo de texto
		try {			
			FileReader fr = new FileReader(ARCHIVO_ORIGEN);
			BufferedReader br = new BufferedReader(fr);			
			String str;
			while((str = br.readLine()) != null) {
				lineaNum = str.split(" ");
				for(int i=0; i<9; i++) {
					tablero[fila][i] = new Celda(Integer.valueOf(lineaNum[i]));
				}
				fila++;
			}			
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		testTablero();
		
		
	}
	
	private void testTablero() {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				System.out.print(tablero[i][j].getValor() + " ");
			}
			System.out.println();
		}
	}
	
	public Celda getCelda(int f, int c) {
		return tablero[f][c];
	}
	
	public int getValorCelda(int f, int c) {
		return tablero[f][c].getValor();
	}
	
	public void actualizarValorCelda(int f, int c, int v) {
		tablero[f][c].setValor(v);
	}
	
}
