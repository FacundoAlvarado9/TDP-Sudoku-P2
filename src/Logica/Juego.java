package Logica;

import java.util.Random;
import java.io.*;

public class Juego {
		
	private static final int TAMANO_TABLERO = 9;
	private static final String ARCHIVO_ORIGEN = "/home/facundo/devstuff/sudoku1.txt";
	private Celda[][] tablero;
	
	private int[] auxCantApariciones;
	
	private Random rand;
	
	public Juego() {
		tablero = new Celda[TAMANO_TABLERO][TAMANO_TABLERO];
		
		auxCantApariciones = new int[9];
		for(int i=0; i<9; i++) {
			auxCantApariciones[i] = 0;
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
			auxCantApariciones[i] = 0;
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
		
		//testTablero();
		
		
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
	
	public void actualizarValorCelda(Celda c) {
		c.actualizarValor();
	}
	
	private boolean chequearFilas() {
		boolean todasValidas = true;
		int elem;
		Celda celdaActual;
		
		for(int f=0; f<TAMANO_TABLERO; f++) {
			
			for(int c=0; c<TAMANO_TABLERO; c++) {				
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem != 0){
					auxCantApariciones[elem - 1]++;
				}
			}

			for(int c=0; c<TAMANO_TABLERO; c++) {
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem != 0 && auxCantApariciones[elem-1]>1){
					celdaActual.setValidez(false);
					todasValidas = false;
				}
			}

			refrescarAuxNumEncontrados();
		}
		
		return todasValidas;
	}
	
	private boolean chequearColumnas() {
		boolean todasValidas = true;
		int elem;
		Celda celdaActual;
		
		for(int c=0; c<TAMANO_TABLERO; c++) {

			for(int f=0; f<TAMANO_TABLERO; f++) {
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem != 0) {
					auxCantApariciones[elem-1]++;
				}
			}

			for(int f=0; f<TAMANO_TABLERO; f++) {
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem == 0 || auxCantApariciones[elem-1] > 1) {
					celdaActual.setValidez(false);
					todasValidas = false;
				}
			}

			refrescarAuxNumEncontrados();
		}
		
		return todasValidas;
	}
	
	private boolean chequearCuadrantes() {
		boolean todosValidos = true;
		boolean esValidoCuadranteActual;

		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				esValidoCuadranteActual = chequearCuadrante(i, j);
				todosValidos = todosValidos & esValidoCuadranteActual;
			}
		}
		
		return todosValidos;
	}

	//Recordemos cuadrante 3x3
	// 0,0 / 0,1 / 0,2
	// 1,0 / 1,1 / 1,2
	// 2,0 / 2,1 / 2,2
	private boolean chequearCuadrante(int f, int c){
		boolean todasValidas = true;
		Celda celdaActual;
		int elem;
		int topeFila = 3*(f+1);
		int topeColumna = 3*(c+1);

		for(int indiceF = 3*f; indiceF<topeFila; indiceF++){
			for(int indiceC = 3*c; indiceC<topeColumna; indiceC++){
				celdaActual = tablero[indiceF][indiceC];
				elem = celdaActual.getValor();
				if(elem != 0) {
					auxCantApariciones[elem-1]++;
				}
			}
		}

		for(int indiceF = 3*f; indiceF<topeFila; indiceF++){
			for(int indiceC = 3*c; indiceC<topeColumna; indiceC++){
				celdaActual = tablero[indiceF][indiceC];
				elem = celdaActual.getValor();
				if(elem == 0 || auxCantApariciones[elem-1] > 1) {
					celdaActual.setValidez(false);
					todasValidas = false;
				}
			}
		}

		refrescarAuxNumEncontrados();
		return todasValidas;
	}

	private void refrescarValidezCeldas(){
		for(int f=0; f<TAMANO_TABLERO; f++){
			for(int c=0; c<TAMANO_TABLERO; c++){
				tablero[f][c].setValidez(true);
			}
		}
	}
	
	
	
	public boolean gano() {
		refrescarValidezCeldas();
		return chequearFilas() & chequearColumnas() & chequearCuadrantes();
	}
	
}
