package pathfinder.modelo;

import java.util.ArrayList;

public class Solucion {
	ArrayList<ArrayList<Coordenada>> nodosExpandidos;
	ArrayList<Coordenada> camino;
	public ArrayList<ArrayList<Coordenada>> getNodosExpandidos() {
		return nodosExpandidos;
	}
	public void setNodosExpandidos(ArrayList<ArrayList<Coordenada>> nodosExpandidos) {
		this.nodosExpandidos = nodosExpandidos;
	}
	public ArrayList<Coordenada> getCamino() {
		return camino;
	}
	public void setCamino(ArrayList<Coordenada> camino) {
		this.camino = camino;
	}
	public Solucion(ArrayList<ArrayList<Coordenada>> nodosExpandidos, ArrayList<Coordenada> camino) {
		super();
		this.nodosExpandidos = nodosExpandidos;
		this.camino = camino;
	}
	
	

}
