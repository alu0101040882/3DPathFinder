package pathfinder.modelo;

import java.util.ArrayList;
import java.util.HashSet;

public class DFS {

	public static Solucion dfs(Laberinto lab) {

		HashSet<Coordenada> visitados = new HashSet<>();
		ArrayList<Coordenada> camino = new ArrayList<Coordenada>();
		ArrayList<ArrayList<Coordenada>> nodosExpandidos = new ArrayList<ArrayList<Coordenada>>();
		return search(lab.getInicio(), lab.getObjetivo(), visitados, camino, nodosExpandidos, lab);

	}

	public static Solucion search(Coordenada source, Coordenada objetivo, HashSet<Coordenada> visitados,
			ArrayList<Coordenada> camino, ArrayList<ArrayList<Coordenada>> nodosExpandidos, Laberinto lab) {

		visitados.add(source);
		camino.add(source);

		nodosExpandidos.add(new ArrayList<Coordenada>());
		for (Coordenada neighbor : lab.getSucesores(source)) {
			nodosExpandidos.get(nodosExpandidos.size() - 1).add(neighbor);
			if (neighbor == objetivo) {
				camino.add(neighbor);
				return new Solucion(nodosExpandidos, camino);
			} else if (!visitados.contains(neighbor)) {
				return search(neighbor, objetivo, visitados, camino,nodosExpandidos, lab);
			}
		}

		return new Solucion(nodosExpandidos,camino);
	}
}
