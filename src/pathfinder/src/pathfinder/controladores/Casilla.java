package pathfinder.controladores;

import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import pathfinder.modelo.Celda;
import pathfinder.modelo.Coordenada;

public class Casilla extends Pane {

	int tipo;
	Coordenada coordenada;

	Polygon flechaAbajo, flechaArriba;

	Casilla(int tipo, Coordenada coordenada) {
		super();
		setMinSize(10,10);
		setStyle("-fx-border-color: black");
		this.tipo = tipo;
		this.coordenada = coordenada;

		setTipo(tipo);

	}
	
	
	public void borrarFlechaArriba() {
		getChildren().remove(flechaArriba);
	}
	
	public void borrarFlechaAbajo() {
		getChildren().remove(flechaAbajo);
	}

	public void dibujaFlechaArriba() {

		flechaArriba = new Polygon();
		flechaArriba.setFill(Color.AQUA);
		flechaArriba.getPoints()
				.addAll(new Double[] { getWidth() / 2 - 1, 0.0, 0.0, getHeight() / 2, getWidth(), getHeight() / 2,

				});
		getChildren().add(flechaArriba);

		widthProperty().addListener((obs, oldVal, newVal) -> {
			flechaArriba.getPoints().clear();
			flechaArriba.getPoints().addAll(
					new Double[] { (double) newVal / 2 - 1, 0.0, 0.0, getHeight() / 2, (double) newVal, getHeight() / 2,

					});
		});

		heightProperty().addListener((obs, oldVal, newVal) -> {
			flechaArriba.getPoints().clear();
			flechaArriba.getPoints().addAll(
					new Double[] { getWidth() / 2 - 1, 0.0, 0.0, (double) newVal / 2, getWidth(), (double) newVal / 2,

					});
		});
	}

	public void dibujaFlechaAbajo() {

		flechaAbajo = new Polygon();
		flechaAbajo.setFill(Color.BLUE);
		flechaAbajo.getPoints().addAll(
				new Double[] { getWidth() / 2 - 1, getHeight(), 0.0, getHeight() / 2, getWidth(), getHeight() / 2,

				});
		getChildren().add(flechaAbajo);

		widthProperty().addListener((obs, oldVal, newVal) -> {
			flechaAbajo.getPoints().clear();
			flechaAbajo.getPoints().addAll(new Double[] { (double) newVal / 2 - 1, getHeight(), 0.0, getHeight() / 2,
					(double) newVal, getHeight() / 2,

			});
		});

		heightProperty().addListener((obs, oldVal, newVal) -> {
			flechaAbajo.getPoints().clear();
			flechaAbajo.getPoints().addAll(new Double[] { getWidth() / 2 - 1, getHeight(), 0.0, (double) newVal / 2,
					getWidth(), (double) newVal / 2,

			});
		});
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;

		switch (tipo) {
		case Celda.CAMINO:
			setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		//	getStyleClass().add("bg-camino");
			break;
		case Celda.INICIO:
			setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		//	getStyleClass().add("bg-inicio");
			break;

		case Celda.LIBRE:
			setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		//	getStyleClass().add("bg-libre");
			break;
		case Celda.OBJETIVO:
			setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
			//getStyleClass().add("bg-objetivo");
			break;
		case Celda.OBSTACULO:
			setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		//	getStyleClass().add("bg-obstaculo");
			break;
		}
	}

	public Coordenada getCoordenada() {
		return coordenada;
	}

	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

}
