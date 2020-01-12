package pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijsktra {
	
	
	public static final int coste = 1;

	public static void shortestPath(Laberinto lab) {

		Map<Coordenada, Double> distance = new HashMap<>();

		ArrayList<Coordenada> openSet = new ArrayList<Coordenada>();

		for (Coordenada vertex : lab.getVertices()) {
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

			for (Coordenada coord : lab.getSucesores(min)) {

				Double newPath = distance.get(min) + coste;
				if (distance.get(coord) > newPath) {
					distance.put(coord, newPath);
				}
			}
		}
	}

}
