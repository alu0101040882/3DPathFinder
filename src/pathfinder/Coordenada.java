package pathfinder;

public class Coordenada {

	public int piso, fila, columna;

	public Coordenada(int piso, int fila, int columna) {
		super();
		this.piso = piso;
		this.fila = fila;
		this.columna = columna;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	@Override
	public boolean equals(Object obj) {
		Coordenada otra = (Coordenada) obj;
		return otra.piso == piso && otra.fila == fila && otra.columna == columna;

	}

	public String toString() {
		return piso + " " + fila + " " + columna;
	}

	@Override
	public int hashCode() {

		int hash = 7;
		hash = 31 * hash + (int) piso;
		hash = 31 * hash + (int) fila;
		hash = 31 * hash + (int) columna;
		return hash * 11;
	}


}
