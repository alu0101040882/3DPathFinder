package pathfinder.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {

	public static final int coste = 1;

	public static Solucion dijkstra(Laberinto lab) {
		Map<Coordenada, Coordenada> parent = new HashMap<>();
		Map<Coordenada, Double> distance = new HashMap<>();
		ArrayList<ArrayList<Coordenada>> nodosExpandidos = new ArrayList<ArrayList<Coordenada>>();

		ArrayList<Coordenada> openSet = new ArrayList<Coordenada>();

		for (Coordenada vertex : lab.getAdjacentVertices()) {
			if (lab.getInicio().equals(vertex))
				distance.put(lab.getInicio(), 0d);
			else
				distance.put(vertex, Double.POSITIVE_INFINITY);
			openSet.add(vertex);
		}

		while (!openSet.isEmpty()) {

			Coordenada min = openSet.get(0);
			double minDis = Double.POSITIVE_INFINITY;
			for (Coordenada vertex : openSet) {

				if (minDis > distance.get(vertex)) {
					minDis = distance.get(vertex);
					min = vertex;
				}
			}

			openSet.remove(min);

			nodosExpandidos.add(new ArrayList<Coordenada>());
			for (Coordenada coord : lab.getSucesores(min)) {

				nodosExpandidos.get(nodosExpandidos.size() - 1).add(coord);
				Double newPath = distance.get(min) + coste;
				if (distance.get(coord) > newPath) {
					distance.put(coord, newPath);
					parent.put(coord, min);
				}

				if (coord.equals(lab.getObjetivo())) {
					ArrayList<Coordenada> list = makePath(lab.getInicio(), lab.getObjetivo(), parent);
					return list.size() <= 1 ? null : new Solucion(nodosExpandidos, list);
				}
			}
		}
		ArrayList<Coordenada> list = makePath(lab.getInicio(), lab.getObjetivo(), parent);
		return list.size() <= 1 ? null : new Solucion(nodosExpandidos, list);
	}

	private static ArrayList<Coordenada> makePath(Coordenada inicio, Coordenada objetivo,
			Map<Coordenada, Coordenada> parent) {

		ArrayList<Coordenada> list = new ArrayList<Coordenada>();
		Coordenada point = objetivo;

		while (point != null) {

			list.add(point);
			point = parent.get(point);
		}

		Collections.reverse(list);
		return list;
	}

}
