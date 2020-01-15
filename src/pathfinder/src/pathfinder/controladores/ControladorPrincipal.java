package pathfinder.controladores;

import java.awt.Rectangle;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import pathfinder.modelo.Celda;
import pathfinder.modelo.Coordenada;
import pathfinder.modelo.Laberinto;

public class ControladorPrincipal {

	public BorderPane mainPane;
	Laberinto laberinto;
	public GridPane[] pisos;
	public int pisoActual = 0;
	public Button up, down;

	public void initialize() {
	}

	public void setLaberinto(Laberinto laberinto) {
		this.laberinto = laberinto;
	}

	public void setPisoActual(int piso) {
		mainPane.setCenter(pisos[piso]);
		pisoActual = piso;

	}

	public void actualizaCasilla(Coordenada coord) {
		actualizaCasilla(coord.getPiso(), coord.getFila(), coord.getColumna());
	}

	public void actualizaCasilla(int piso, int fila, int columna) {

		Celda celda = laberinto.getCeldaAt(piso, fila, columna);
		Casilla casilla = getCasillaAt(piso, fila, columna);

		if (celda.getTipo() != Celda.OBSTACULO) {
			if (piso + 1 < laberinto.getnPisos()
					&& laberinto.getCeldaAt(piso + 1, fila, columna).getTipo() != Celda.OBSTACULO) {
				casilla.dibujaFlechaArriba();
			} else {
				casilla.borrarFlechaArriba();
			}

			if (piso - 1 >= 0 && laberinto.getCeldaAt(piso - 1, fila, columna).getTipo() != Celda.OBSTACULO) {
				casilla.dibujaFlechaAbajo();
			} else {
				casilla.borrarFlechaAbajo();
			}
		} else {
			casilla.borrarFlechaArriba();
			casilla.borrarFlechaAbajo();
		}

		casilla.setTipo(celda.getTipo());
	}

	public Casilla getCasillaAt(final int piso, final int fila, final int columna) {

		Node result = null;
		ObservableList<Node> childrens = pisos[piso].getChildren();

		for (Node node : childrens) {
			if (GridPane.getRowIndex(node) == fila && GridPane.getColumnIndex(node) == columna) {
				result = node;
				break;
			}
		}

		return (Casilla) result;
	}
	
	public void creaCasilla(Coordenada coordenada) {
		creaCasilla(coordenada.getPiso(),coordenada.getFila(),coordenada.getColumna());
	}


	public void creaCasilla(int piso, int fila, int columna) {
		Celda celda = laberinto.getCeldaAt(piso, fila, columna);
		Casilla casilla = new Casilla(celda.getTipo(), new Coordenada(piso, fila, columna));

		pisos[piso].add(casilla, columna, fila);

		if (celda.getTipo() != Celda.OBSTACULO) {
			if (piso + 1 < laberinto.getnPisos()
					&& laberinto.getCeldaAt(piso + 1, fila, columna).getTipo() != Celda.OBSTACULO) {
				casilla.dibujaFlechaArriba();
			}

			if (piso - 1 >= 0 && laberinto.getCeldaAt(piso - 1, fila, columna).getTipo() != Celda.OBSTACULO) {
				casilla.dibujaFlechaAbajo();
			}
		}
		casilla.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseButton.PRIMARY) {
					Casilla casilla = (Casilla) mouseEvent.getSource();
					laberinto.getCeldaAtCoord(casilla.getCoordenada()).setTipo(Celda.OBSTACULO);

					creaCasilla(casilla.getCoordenada());

					if (casilla.getCoordenada().getPiso() - 1 >= 0) {
						creaCasilla(casilla.getCoordenada().getPiso() - 1, casilla.getCoordenada().getFila(),
								casilla.getCoordenada().getColumna());
					}

					if (casilla.getCoordenada().getPiso() + 1 < laberinto.getnPisos()) {
						creaCasilla(casilla.getCoordenada().getPiso() + 1, casilla.getCoordenada().getFila(),
								casilla.getCoordenada().getColumna());
					}

				} else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
					Casilla casilla = (Casilla) mouseEvent.getSource();
					laberinto.getCeldaAtCoord(casilla.getCoordenada()).setTipo(Celda.LIBRE);
					creaCasilla(casilla.getCoordenada());

					if (casilla.getCoordenada().getPiso() - 1 >= 0) {
						creaCasilla(casilla.getCoordenada().getPiso() - 1, casilla.getCoordenada().getFila(),
								casilla.getCoordenada().getColumna());
					}

					if (casilla.getCoordenada().getPiso() + 1 < laberinto.getnPisos()) {
						creaCasilla(casilla.getCoordenada().getPiso() + 1, casilla.getCoordenada().getFila(),
								casilla.getCoordenada().getColumna());
					}

				}
			}
		});

	}

	public void cargaLaberinto() {

		pisos = new GridPane[laberinto.getnPisos()];

		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(100d / laberinto.getColumnas());

		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(100d / laberinto.getFilas());

		for (int piso = 0; piso < laberinto.getnPisos(); piso++) {
			pisos[piso] = new GridPane();

			for (int fila = 0; fila < laberinto.getFilas(); fila++) {

				pisos[piso].getRowConstraints().add(rc);
				for (int columna = 0; columna < laberinto.getColumnas(); columna++) {
					creaCasilla(piso, fila, columna);

				}
			}
			for (int columna = 0; columna < laberinto.getColumnas(); columna++) {

				pisos[piso].getColumnConstraints().add(cc);
			}
		}

	}

	public void handleStart() {

	}

	public void handleUp() {
		if (pisoActual + 1 < laberinto.getnPisos())
			setPisoActual(pisoActual + 1);
	}

	public void handleDown() {
		if (pisoActual - 1 >= 0)
			setPisoActual(pisoActual - 1);
	}
}
