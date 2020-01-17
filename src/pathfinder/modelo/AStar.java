package pathfinder.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pathfinder.controladores.ControladorPrincipal;

public class AStar {

	public static final int coste = 1;

	public static Solucion aStar(Laberinto lab) {

		Coordenada inicio = lab.getInicio();
		Coordenada objetivo = lab.getObjetivo();
		Map<Coordenada, Coordenada> parent = new HashMap<>();
		Map<Coordenada, Double> distance = new HashMap<>();
		Set<Coordenada> openSet = new HashSet<>();
		Set<Coordenada> closedSet = new HashSet<>();
		ArrayList<ArrayList<Coordenada>> nodosExpandidos = new ArrayList<ArrayList<Coordenada>>();

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

			if (objetivo.equals(actual)) {
				ArrayList<Coordenada> list = makePath(lab.getInicio(), lab.getObjetivo(), parent);
				return list.size() <= 1 ? null : new Solucion(nodosExpandidos, list);
			}

			openSet.remove(actual);
			closedSet.add(actual);

			nodosExpandidos.add(new ArrayList<Coordenada>());
			for (Coordenada sucesor : lab.getSucesores(actual)) {

			

				if (!closedSet.contains(sucesor)) {
					nodosExpandidos.get(nodosExpandidos.size() - 1).add(sucesor);
					double nextDistance = (distance.get(actual) + coste);
					double heuristicDist = nextDistance + heuristica(sucesor, objetivo, 0);

					if (heuristicDist < distance.getOrDefault(sucesor, Double.POSITIVE_INFINITY)) {
						distance.put(sucesor, heuristicDist);
						parent.put(sucesor, actual);
						openSet.add(sucesor);
					}
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

	private static double heuristica(Coordenada actual, Coordenada objetivo, int tipo) {
		switch (tipo) {
		case 0://euclidea
			return Math.sqrt(Math.pow(actual.getPiso() - objetivo.getPiso(), 2)
					+ Math.pow(actual.getFila() - objetivo.getFila(), 2)
					+ Math.pow(actual.getColumna() - objetivo.getColumna(), 2));

		case 1://manhattan
			return Math.abs(actual.getPiso() + objetivo.getPiso()) +
					Math.abs(actual.getFila() + objetivo.getFila())
					+ Math.abs(actual.getColumna() + objetivo.getColumna());

		default:
			return 0.0;

		}

	}

}
