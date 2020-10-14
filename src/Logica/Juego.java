package Logica;

import java.util.Random;
import java.io.*;

public class Juego {
		
	private static final int TAMANO_TABLERO = 9;
	private static final String ARCHIVO_ORIGEN = "/home/facundo/devstuff/sudoku1.txt";
	private Celda[][] tablero;
	
	private boolean[] auxNumerosEncontrados;
	
	private Random rand;
	
	public Juego() {
		tablero = new Celda[TAMANO_TABLERO][TAMANO_TABLERO];
		
		auxNumerosEncontrados = new boolean[9];		
		for(int i=0; i<9; i++) {
			auxNumerosEncontrados[i] = false;
		}
		
	
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
	
	private void refrescarAuxNumEncontrados() {
		for(int i=0; i<9; i++) {
			auxNumerosEncontrados[i] = false;
		}
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
	
	private boolean chequearFilas() {
		boolean todasValidas = true;
		int elem;
		Celda celActual;
		
		for(int f=0; f<TAMANO_TABLERO; f++) {
			
			for(int c=0; c<TAMANO_TABLERO; c++) {				
				celActual = tablero[f][c];
				elem = celActual.getValor();
				if(auxNumerosEncontrados[elem] == true) {
					celActual.setValidez(false);
					todasValidas = false;
				} else {
					auxNumerosEncontrados[elem] = true;
				}				
			}
			refrescarAuxNumEncontrados();
		}
		
		return todasValidas;
	}
	
	private boolean chequearColumnas() {
		boolean todasValidas = true;
		int elem;
		Celda celActual;
		
		for(int c=0; c<TAMANO_TABLERO; c++) {
			for(int f=0; f<TAMANO_TABLERO; f++) {
				
				celActual = tablero[f][c];
				elem = celActual.getValor();
				if(auxNumerosEncontrados[elem] == true) {
					celActual.setValidez(false);
					todasValidas = false;
				} else {
					auxNumerosEncontrados[elem] = true;				
				}							
			}
			refrescarAuxNumEncontrados();
		}
		
		return todasValidas;
	}
	
	private boolean chequearCuadrantes() {
		boolean todosValidos = true;
		
		return todosValidos;
	}
	
	
	
	public boolean gano() {
		return chequearFilas() && chequearColumnas() && chequearCuadrantes();
	}
	
}
