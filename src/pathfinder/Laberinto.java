package pathfinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Laberinto {

	Piso[] pisos;

	int nPisos, filas, columnas;

	public Laberinto(String nombreFichero) {
		leerFichero(nombreFichero);
	}

	public Laberinto(int nPisos, int filas, int columnas, boolean random) {

		this.nPisos = nPisos;
		this.filas = filas;
		this.columnas = columnas;

		pisos = new Piso[nPisos];
		if (random) {
			randomize();
		} else {
			initialize();
		}
	}

	public void randomize() {
		for (int piso = 0; piso < nPisos; piso++) {
			pisos[piso] = new Piso(filas, columnas);
			for (int fila = 0; fila < filas; fila++) {
				for (int columna = 0; columna < columnas; columna++) {
					pisos[piso].setCelda(fila, columna,
							new Celda(Math.random() <= 0.5 ? Celda.LIBRE : Celda.OBSTACULO, piso, fila, columna));
				}
			}
		}
	}

	public void initialize() {
		for (int piso = 0; piso < nPisos; piso++) {
			pisos[piso] = new Piso(filas, columnas);
			for (int fila = 0; fila < filas; fila++) {
				for (int columna = 0; columna < columnas; columna++) {
					pisos[piso].setCelda(fila, columna, new Celda(Celda.LIBRE, piso, fila, columna));
				}
			}
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
			salida += pisos[piso].toString();
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

			pisos = new Piso[nPisos];
			for (int i = 0; i < nPisos; i++) {
				pisos[i] = new Piso(filas, columnas);
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

						Celda newCelda = new Celda(linea[columna].charAt(0), piso, fila, columna);

						pisos[piso].setCelda(fila, columna, newCelda);
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
		return pisos[coord.piso].getCelda(coord.fila, coord.columna);
	}

	public Celda getCeldaAt(int piso, int fila, int columna) {
		return pisos[piso].getCelda(fila, columna);
	}

	public ArrayList<Coordenada> getSucesores(Coordenada coordenada) {

		ArrayList<Coordenada> sucesores = new ArrayList<Coordenada>();

		int[][] coordenadas = { { -1, 0, 0 }, { 1, 0, 0 }, { 0, 1, 0 }, { 0, -1, 0 }, { 0, 0, -1 }, { 0, 0, 1 } };

		for (int[] coord : coordenadas) {

			int pCoord = coordenada.getPiso() + coord[0];
			int fCoord = coordenada.getFila() + coord[1];
			int cCoord = coordenada.getColumna() + coord[2];

			if (getCeldaAtCoord(coordenada).getTipo() != Celda.OBSTACULO && pCoord >= 0 && pCoord < nPisos
					&& fCoord >= 0 && fCoord < filas && cCoord >= 0 && cCoord < columnas) {

				sucesores.add(getCeldaAt(pCoord, fCoord, cCoord).getCoordenada());
			}
		}

		return sucesores;
	}

	public void dibujaCamino(ArrayList<Coordenada> camino) {
		if (camino != null)
			for (Coordenada coord : camino) {
				getCeldaAtCoord(coord).setTipo(Celda.CAMINO);
			}
	}

	public ArrayList<Coordenada> getSucesores(Celda celda) {
		return getSucesores(celda.getCoordenada());
	}

	public static void main(String args[]) {
		Laberinto l = new Laberinto("Laberintos/Laberinto1.lab");
		l.dibujaCamino(Algoritmos.aStar(l, new Coordenada(0, 0, 0), new Coordenada(1, 0, 0)));

		Laberinto l2 = new Laberinto(5, 3, 4, true);

		System.out.println(l);

	}
}
