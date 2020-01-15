package pathfinder.modelo;

public class Piso {

	private Celda[][] celdas;
	
	private int filas, columnas;
	
	public Piso(int filas, int columnas, boolean random) {
		celdas = new Celda[filas][columnas];
		this.filas = filas;
		this.columnas = columnas;
		
		if(random) {
			randomize();
		}else {
			initialize();
		}
	}
	
	public Piso(int filas, int columnas) {
		this(filas,columnas,false);
	}
	
	public void randomize() {
		for(int i = 0 ; i < celdas.length; i++) {
			for(int j = 0; j < celdas[i].length; j++) {
				celdas[i][j] = new Celda(Math.random()<=0.5 ? Celda.LIBRE : Celda.OBSTACULO);
			}
		}
	}
	
	public void initialize() {
		for(int i = 0 ; i < celdas.length; i++) {
			for(int j = 0; j < celdas[i].length; j++) {
				celdas[i][j] = new Celda(Celda.LIBRE);
			}
		}
	}
	
	
	public Celda getCelda(int fila, int columna) {
		return celdas[fila][columna];
	}
	
	
	public void setCelda(int fila, int columna, Celda celda) {
		celdas[fila][columna] = celda;
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}
	
	public String toString() {
		
		String salida = "";
		for (int fila = 0; fila < filas; fila++) {
			for (int columna = 0; columna < columnas; columna++) {
				salida += celdas[fila][columna].toString() + " ";
			}
			salida += "\n";
		}
		
		return salida;
	}
	
	
	
	
}
