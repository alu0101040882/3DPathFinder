package pathfinder;

public class Celda {

	public static final char LIBRE = '_', OBSTACULO = 'O', CAMINO = 'X', INICIO = 'S' , OBJETIVO ='F';

	private char tipo;

	public Celda(char tipo) {
		if (tipo != LIBRE && tipo != OBSTACULO && tipo != CAMINO)
			throw new IllegalArgumentException("No se reconoce el tipo " + tipo);

		this.tipo = tipo;

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
