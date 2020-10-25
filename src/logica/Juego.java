package logica;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Random;
import java.io.*;

public class Juego {
		
	private static final int TAMANO_TABLERO = 9;

	private Celda[][] tablero;

	private LocalTime horaComienzo; //Tiempo de comienzo del juego que será de ayuda para el cronómetro y la finalización del juego

	/*
	 * estructura auxiliar que ayuda a evaluar la repetición de dígitos en una fila/columna/cuadrante.
	 * Básicamente es un arreglo de apariciones por cada número del 1 al 9
	 * */
	private int[] auxCantApariciones;
	
	private Random randomVaciamientoAleatorioDeCeldas;

	/**
	 * Inicializa un juego de Sudoku.
	 */
	public Juego() throws ArchivoInvalidoException {

		tablero = new Celda[TAMANO_TABLERO][TAMANO_TABLERO];

		randomVaciamientoAleatorioDeCeldas = new Random();

		//Se inicializa la estructura auxiliar para los chequeos.
		auxCantApariciones = new int[TAMANO_TABLERO];
		for(int i=0; i<TAMANO_TABLERO; i++) {
			auxCantApariciones[i] = 0;
		}
		
		cargarTablero();

		horaComienzo = LocalTime.now();
	}
	
	private void refrescarAuxNumEncontrados() {
		for(int i=0; i<9; i++) {
			auxCantApariciones[i] = 0;
		}
	}

	/**
	 * A partir de un archivo, inicializa el tablero de Sudoku. Posteriormente, se chequea si es una
	 * solución Sudoku-válida. La estructura del archivo se supone válida.
	 */
	private void cargarTablero() throws ArchivoInvalidoException {

		//Para la apertura del archivo
		URL locacionArchivoEnCarpetaRecursos;
		File archivoOrigen;

		//Para la lectura e incializacion del tablero
		String[] lineaDeNumerosArchivo;
		int fila = 0;
		int numAInsertarEnCelda;

		int auxRandom; //random auxiliar para vaciar celdas aleatoriamente
		
		//Llenamos el tablero de los digitos en el archivo de texto
		try {

			//Abro el archivo con el buffered reader a partir de la carpeta de recursos del proyecto.
			BufferedReader br = new BufferedReader(new
					InputStreamReader(getClass().getClassLoader().getResourceAsStream("fuente_juego.txt")));

			String str;
			
			while((str = br.readLine()) != null) {
				lineaDeNumerosArchivo = str.split(" ");
				
				for(int col=0; col<9; col++) {

					//inicializo el random para el vaciamiento aleatorio de celdas
					auxRandom = randomVaciamientoAleatorioDeCeldas.nextInt();

					if(auxRandom%3 == 0){
						numAInsertarEnCelda = 0;
					} else{
						numAInsertarEnCelda = Integer.valueOf(lineaDeNumerosArchivo[col]);
					}

					tablero[fila][col] = new Celda(numAInsertarEnCelda);
				}
				
				fila++;
			}			
			
			br.close();

		} catch(IOException e) {
			e.printStackTrace();
			throw new ArchivoInvalidoException();
		}


	}

	/**
	 * Devuelve la celda en la pos pasada como parametro.
	 * @param f fila del tablero
	 * @param c columna del tablero
	 * @return celda en la posicion del tablero pasada como parametro
	 */
	public Celda getCelda(int f, int c) {
		return tablero[f][c];
	}

	/**
	 * Devuelve el tiempo de inicio del juego.
	 * @return tiempo de inicio del juego.
	 */
	public LocalTime getTimeInicio() {
		return horaComienzo;
	}

	/**
	 * Verifica si las filas no tienen elementos repetidos o vacios.
	 * @return verdadero si las filas no tienen celdas con numeros repetidos o vacias. Falso en caso contrario.
	 */
	private boolean chequearFilas() {
		boolean todasValidas = true;
		int elem;
		Celda celdaActual;

		//Para cada fila
		for(int f=0; f<TAMANO_TABLERO; f++) {

			//Recorre dos veces cada elemento, una para contar las repeticiones y almacenarlas en la estructura auxiliar
			for(int c=0; c<TAMANO_TABLERO; c++) {				
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem != 0){
					auxCantApariciones[elem - 1]++;
				}
			}

			//Y la siguiente para marcar como inválidas las celdas con numeros que se repiten.
			for(int c=0; c<TAMANO_TABLERO; c++) {
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem != 0 && auxCantApariciones[elem-1]>1){
					celdaActual.setValidez(false);
					todasValidas = false;
				}
			}

			refrescarAuxNumEncontrados(); //Refresca la estructura auxiliar para el chequeo
		}
		
		return todasValidas;
	}

	/**
	 * Verifica si las columnas no tienen elementos repetidos o vacios.
	 * @return verdadero si las filas no tienen celdas con numeros repetidos o vacias. Falso en caso contrario.
	 */
	private boolean chequearColumnas() {
		boolean todasValidas = true;
		int elem;
		Celda celdaActual;

		//Para cada columna
		for(int c=0; c<TAMANO_TABLERO; c++) {

			//Recorre dos veces cada elemento, una para contar las repeticiones y almacenarlas en la estructura auxiliar
			for(int f=0; f<TAMANO_TABLERO; f++) {
				celdaActual = tablero[f][c];
				elem = celdaActual.getValor();
				if(elem != 0) {
					auxCantApariciones[elem-1]++;
				}
			}

			//Y la siguiente para marcar como inválidas las celdas con numeros que se repiten.
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

	/**
	 * Verifica los cuadrantes uno a uno para verificar que no tengan elementos repetidos.
	 * @return Verdadero si los cuadrantes no tienen elementos repetidos. Falso en caso contrario.
	 */
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

	//Recordemos cuadrante 3x3 (f,c)
	// 0,0 / 0,1 / 0,2
	// 1,0 / 1,1 / 1,2
	// 2,0 / 2,1 / 2,2


	/**
	 * Chequea un cuadrante para verificar que no tenga elementos repetidos.
	 * @param f fila del subcuadrante
	 * @param c columna del subcuadrante
	 * @return verdadero si el cuadrante revisado no tiene elementos repetidos. Falso en caso contrario.
	 */
	private boolean chequearCuadrante(int f, int c){

		boolean todasValidas = true;
		Celda celdaActual;
		int elem;

		int topeFila = 3*(f+1);
		int topeColumna = 3*(c+1);

		//Para cada cuadrante
		//Lo recorre dos veces, la primera para guardar las repeticiones de cada numero en el mismo
		for(int indiceF = 3*f; indiceF<topeFila; indiceF++){
			for(int indiceC = 3*c; indiceC<topeColumna; indiceC++){
				celdaActual = tablero[indiceF][indiceC];
				elem = celdaActual.getValor();
				if(elem != 0) {
					auxCantApariciones[elem-1]++;
				}
			}
		}

		//Y la segunda para marcar las celdas con numeros repetidas.
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

	/**
	 * Valida las celdas del tablero. Si están marcadas como invalidas, se las marca como válidas.
	 */
	private void refrescarValidezCeldas(){
		for(int f=0; f<TAMANO_TABLERO; f++){
			for(int c=0; c<TAMANO_TABLERO; c++){
				tablero[f][c].setValidez(true);
			}
		}
	}


	/**
	 * Verifica si el juego está resuelto.
	 * @return verdadero si el juego está resuelto. Falso en caso contrario. Esto es, verdadero si ninguna fila, ninguna
	 * columna y ningún cuadrante posee numeros repetidos.
	 */
	public boolean gano() {
		boolean gano;

		refrescarValidezCeldas(); //Marco todas las celdas como validas, para deshacer chequeos anteriores.

		//No hace "y" exclusivo porque necesito realizar todos los chequeos para marcar las celdas invalidas.
		gano = chequearFilas() & chequearColumnas() & chequearCuadrantes();

		return gano;
	}
}
