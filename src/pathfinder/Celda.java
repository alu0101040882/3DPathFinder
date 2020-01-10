package pathfinder;

public class Celda {

	public static final char LIBRE = '_', OBSTACULO = 'O', CAMINO = 'X';

	private char tipo;
	private Coordenada coordenada;

	public Celda(char tipo, int piso, int fila, int columna) {
		if (tipo != LIBRE && tipo != OBSTACULO && tipo != CAMINO)
			throw new IllegalArgumentException("No se reconoce el tipo " + tipo);

		this.tipo = tipo;

		setCoordenada(new Coordenada(piso, fila, columna));
	}

	public Celda(char tipo, Coordenada coordenada) {
		if (tipo != LIBRE && tipo != OBSTACULO && tipo != CAMINO)
			throw new IllegalArgumentException("No se reconoce el tipo " + tipo);

		this.tipo = tipo;

		setCoordenada(coordenada);
	}

	Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	public int getPiso() {
		return coordenada.piso;
	}

	public void setPiso(int piso) {
		this.coordenada.piso = piso;
	}

	public int getFila() {
		return coordenada.fila;
	}

	public void setFila(int fila) {
		this.coordenada.fila = fila;
	}

	public int getColumna() {
		return coordenada.columna;
	}

	public void setColumna(int columna) {
		this.coordenada.columna = columna;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public String toString() {
		return tipo + "";
	}
}
