package pathfinder;

public class Piso {

	private Celda[][] celdas;

	private int filas, columnas;

	public Piso(int filas, int columnas) {
		celdas = new Celda[filas][columnas];
		this.filas = filas;
		this.columnas = columnas;
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
