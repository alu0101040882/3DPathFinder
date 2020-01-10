package pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Algoritmos {

	public static final int coste = 1;

	public static ArrayList<Coordenada> aStar(Laberinto lab, Coordenada inicio, Coordenada objetivo) {
		Map<Coordenada, Coordenada> parent = new HashMap<>();
		Map<Coordenada, Double> distance = new HashMap<>();
		Set<Coordenada> openSet = new HashSet<>();
		Set<Coordenada> closedSet = new HashSet<>();

		distance.put(inicio, 0.0);
		openSet.add(inicio);

		while (!openSet.isEmpty()) {
			Coordenada actual = null;
			double min = Double.POSITIVE_INFINITY;
			for (Coordenada point : openSet) {
				if (distance.get(point) < min) {
					actual = point;
					min = distance.get(point);
				}
			}

			if (objetivo.equals(actual))
				return makePath(inicio, objetivo, parent);
			System.out.println();

			openSet.remove(actual);
			closedSet.add(actual);

			for (Coordenada sucesor : lab.getSucesores(actual)) {
				System.out.println(sucesor);
				if (!closedSet.contains(sucesor)) {

					double nextDistance = (distance.get(actual) + coste);
					double heuristicDist = nextDistance + heuristica(sucesor, objetivo, 0);

					System.out.println(nextDistance);
					System.out.println(heuristicDist);
					if (heuristicDist < distance.getOrDefault(sucesor, Double.POSITIVE_INFINITY)) {
						distance.put(sucesor, heuristicDist);
						parent.put(sucesor, actual);
						openSet.add(sucesor);
					}
				}
			}

		}

		ArrayList<Coordenada> list = makePath(inicio, objetivo, parent);
		return list.size() <= 1 ? null : list;
	}

	private static ArrayList<Coordenada> makePath(Coordenada inicio, Coordenada objetivo,
			Map<Coordenada, Coordenada> parent) {

		ArrayList<Coordenada> list = new ArrayList<Coordenada>();
		Coordenada point = objetivo;

		System.out.println(parent);
		while (point != null) {
			System.out.println("point" + point);
			list.add(point);
			point = parent.get(point);
		}

		return list;
	}

	private static double heuristica(Coordenada actual, Coordenada objetivo, int tipo) {
		switch (tipo) {
		case 0:
			return Math.sqrt(Math.pow(actual.getPiso() - objetivo.getPiso(), 2)
					+ Math.pow(actual.getFila() - objetivo.getFila(), 2)
					+ Math.pow(actual.getColumna() - objetivo.getColumna(), 2));

		case 1:
			return 0.0;

		default:
			return 0.0;

		}

	}

}
