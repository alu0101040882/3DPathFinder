package pathfinder;

import java.util.ArrayList;
import java.util.HashSet;

public class DFS {

	public static ArrayList<Coordenada> dfs(Laberinto lab) {
		
		HashSet<Coordenada> visitados = new HashSet<>();
		ArrayList<Coordenada> camino = new ArrayList<Coordenada>();
		search(lab.getInicio(), lab.getObjetivo(), visitados, camino, lab);
		System.out.println(camino);
		return camino;
	}

	public static void search(Coordenada source, Coordenada objetivo, HashSet<Coordenada> visitados,
			ArrayList<Coordenada> camino, Laberinto lab) {

		visitados.add(source);
		camino.add(source);

		for (Coordenada neighbor : lab.getSucesores(source)) {
			System.out.println(lab.getSucesores(source));
			if (neighbor == objetivo) {
				camino.add(neighbor);
				break;
			} else if (!visitados.contains(neighbor)) {
				search(neighbor, objetivo, visitados, camino, lab);
			}
		}
	}

}
