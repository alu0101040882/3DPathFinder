package pathfinder.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Laberinto {

	ArrayList<Piso> pisos;

	Coordenada inicio, objetivo;

	int nPisos, filas, columnas;

	public Coordenada getInicio() {
		return inicio;
	}

	public void setInicio(Coordenada inicio) {
		this.inicio = inicio;
		getCeldaAtCoord(inicio).setTipo(Celda.INICIO);
	}

	public Coordenada getObjetivo() {
		return objetivo;
	}

	public int getnPisos() {
		return nPisos;
	}

	public void setnPisos(int nPisos) {
		this.nPisos = nPisos;
	}

	public int getFilas() {
		return filas;
	}

	public void setFilas(int filas) {
		this.filas = filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public void setObjetivo(Coordenada objetivo) {
		this.objetivo = objetivo;
		getCeldaAtCoord(objetivo).setTipo(Celda.OBJETIVO);
	}

	public Laberinto(String nombreFichero) {
		leerFichero(nombreFichero);
	}

	public Laberinto(int nPisos, int filas, int columnas, boolean random) {

		this.nPisos = nPisos;
		this.filas = filas;
		this.columnas = columnas;

		pisos = new ArrayList<Piso>();
		for (int piso = 0; piso < nPisos; piso++) {
			pisos.add(new Piso(filas, columnas, random));
		}
	}

	public Laberinto(int pisos, int filas, int columnas) {
		this(pisos, filas, columnas, false);
	}

	public String toString() {
		String salida = "";

		salida += nPisos + "\n";
		salida += filas + "\n";
		salida += columnas + "\n\n";

		for (int piso = 0; piso < nPisos; piso++) {
			salida += pisos.get(piso).toString();
			salida += "\n";
		}

		return salida;

	}

	public void exportarAFichero(String nombreFichero) {

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(nombreFichero));
			writer.write(this.toString());
			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void leerFichero(String nombreFichero) {

		System.out.println(nombreFichero);
		try {
			BufferedReader br = new BufferedReader(new FileReader(nombreFichero));

			String line = br.readLine().trim();
			while (line.length() <= 0) {
				line = br.readLine();
			}

			nPisos = Integer.parseInt(line.trim());

			line = br.readLine().trim();
			while (line.length() <= 0) {
				line = br.readLine();
			}

			filas = Integer.parseInt(line.trim());

			line = br.readLine().trim();
			while (line.length() <= 0) {
				line = br.readLine();
			}

			columnas = Integer.parseInt(line.trim());

			pisos = new ArrayList<Piso>();
			for (int i = 0; i < nPisos; i++) {
				pisos.add(new Piso(filas, columnas));
			}

			for (int piso = 0; piso < nPisos; piso++) {

				for (int fila = 0; fila < filas; fila++) {

					line = br.readLine().trim();
					while (line.length() <= 0) {
						line = br.readLine();
					}

					String[] linea = line.split("\\s+");
					if (linea.length != columnas) {
						throw new IllegalArgumentException("Fichero mal definido");
					}

					for (int columna = 0; columna < linea.length; columna++) {

						Celda newCelda = new Celda(linea[columna].charAt(0));

						pisos.get(piso).setCelda(fila, columna, newCelda);
					}

				}

			}
			System.out.println();
			br.close();

		} catch (Exception e) {

			e.printStackTrace();
			System.exit(1);
		}

	}

	public Celda getCeldaAtCoord(Coordenada coord) {
		//System.out.println(coord);
		return pisos.get(coord.piso).getCelda(coord.fila, coord.columna);
	}

	public Celda getCeldaAt(int piso, int fila, int columna) {
		return pisos.get(piso).getCelda(fila, columna);
	}

	public ArrayList<Coordenada> getSucesores(Coordenada coordenada) {

		ArrayList<Coordenada> sucesores = new ArrayList<Coordenada>();

		int[][] coordenadas = { { -1, 0, 0 }, { 1, 0, 0 }, { 0, 1, 0 }, { 0, -1, 0 }, { 0, 0, -1 }, { 0, 0, 1 } };

		for (int[] coord : coordenadas) {

			int pCoord = coordenada.getPiso() + coord[0];
			int fCoord = coordenada.getFila() + coord[1];
			int cCoord = coordenada.getColumna() + coord[2];

			Coordenada newCoord = new Coordenada(pCoord, fCoord, cCoord);

			if (pCoord >= 0 && pCoord < nPisos && fCoord >= 0 && fCoord < filas && cCoord >= 0 && cCoord < columnas
					&& getCeldaAtCoord(newCoord).getTipo() != Celda.OBSTACULO) {

				System.out.println(pCoord + " " + fCoord + " " + cCoord);
				sucesores.add(newCoord);
			}
		}

		return sucesores;
	}

	public ArrayList<Coordenada> getVertices() {

		ArrayList<Coordenada> vertices = new ArrayList<Coordenada>();

		for (int piso = 0; piso < nPisos; piso++) {
			for (int fila = 0; fila < fila; fila++) {
				for (int columna = 0; columna < columnas; columna++) {
					if (getCeldaAt(piso, fila, columna).getTipo() == Celda.LIBRE) {
						vertices.add(new Coordenada(piso, fila, columna));
					}
				}
			}
		}

		return vertices;

	}

	public ArrayList<Coordenada> getAdjacentVertices() {

		ArrayList<Coordenada> vertices = new ArrayList<Coordenada>();

		getAdjacentRecursive(inicio, vertices);

		return vertices;

	}

	public void getAdjacentRecursive(Coordenada verticeActual, ArrayList<Coordenada> vertices) {
		for (Coordenada vertice : getSucesores(verticeActual)) {
			if (!vertices.contains(vertice)) {
				vertices.add(vertice);
				getAdjacentRecursive(vertice, vertices);
			}
		}
	}

	public void dibujaCamino(ArrayList<Coordenada> camino) {
		if (camino != null)
			for (Coordenada coord : camino) {
				if (coord != inicio && coord != objetivo)
					getCeldaAtCoord(coord).setTipo(Celda.CAMINO);
			}
	}

	public static void main(String args[]) {
		// Laberinto l = new Laberinto("Laberintos/Laberinto1.lab");
		Laberinto l = new Laberinto(6, 6, 6, true);
		// l.dibujaCamino(AStar.aStar(l, new Coordenada(0, 0, 0), new Coordenada(1, 0,
		// 2)));

		
		l.setInicio(new Coordenada(0, 0, 0));
		l.setObjetivo(new Coordenada(5, 2, 2));

		l.dibujaCamino(Dijkstra.dijkstra(l));
System.out.println("apsdpsa");
		Laberinto l2 = new Laberinto(5, 3, 4, true);

		System.out.println(l);

	}
}
