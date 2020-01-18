package pathfinder.controladores;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import pathfinder.modelo.AStar;
import pathfinder.modelo.Celda;
import pathfinder.modelo.Coordenada;
import pathfinder.modelo.DFS;
import pathfinder.modelo.Dijkstra;
import pathfinder.modelo.Laberinto;
import pathfinder.modelo.Solucion;

public class ControladorPrincipal {
	int indiceCamino = 0, indiceExpandidos = 0;
	Solucion solucion;
	public TextField pisoText, waitTimeText;
	public BorderPane mainPane;
	Laberinto laberinto;
	public GridPane[] pisos;
	public int pisoActual = 0;
	public Button up, down;

	int waitTime = 500;

	public ChoiceBox<String> choiceAlgortimo;
	public TextField fStart, cStart, rStart, fEnd, cEnd, rEnd;

	private char teclaInicio = 'S', teclaObjetivo = 'E';

	private boolean inicio, objetivo;
	Timeline temporizador2, temporizador1;

	private boolean wait = false;

	public void initialize() {
		choiceAlgortimo.getItems().addAll("Dijkstra", "DFS", "A*");
		choiceAlgortimo.getSelectionModel().selectFirst();
	}

	public void setLaberinto(Laberinto laberinto) {
		this.laberinto = laberinto;
	}

	public void setPisoActual(int piso) {
		mainPane.setCenter(pisos[piso]);
		pisoActual = piso;
		pisoText.setText(piso + "");

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
		creaCasilla(coordenada.getPiso(), coordenada.getFila(), coordenada.getColumna());
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
					if (objetivo) {
						if (laberinto.getInicio() == null || !laberinto.getInicio().equals(casilla.getCoordenada())) {
							Coordenada obj = laberinto.getObjetivo();
							laberinto.setObjetivo(casilla.getCoordenada());
							setCoordTextObjetivo(casilla.getCoordenada());
							if (obj != null)
								creaCasilla(obj);

						}

					} else if (inicio) {
						if (laberinto.getObjetivo() == null
								|| !laberinto.getObjetivo().equals(casilla.getCoordenada())) {
							Coordenada ini = laberinto.getInicio();
							laberinto.setInicio(casilla.getCoordenada());
							setCoordTextInicio(casilla.getCoordenada());
							if (ini != null)
								creaCasilla(ini);
						}
					} else {
						laberinto.getCeldaAtCoord(casilla.getCoordenada()).setTipo(Celda.OBSTACULO);
					}

					creaCasillaYColindantes(casilla.getCoordenada());

				} else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
					Casilla casilla = (Casilla) mouseEvent.getSource();

					if (laberinto.getInicio() != null && casilla.getCoordenada().equals(laberinto.getInicio())) {
						laberinto.setInicio(null);
						setCoordTextInicio(null);
					} else if (laberinto.getObjetivo() != null
							&& casilla.getCoordenada().equals(laberinto.getObjetivo())) {
						laberinto.setObjetivo(null);
						setCoordTextObjetivo(null);
					}

					laberinto.getCeldaAtCoord(casilla.getCoordenada()).setTipo(Celda.LIBRE);

					creaCasillaYColindantes(casilla.getCoordenada());

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

		setKeyListeners();

	}

	public void setKeyListeners() {
		Scene scene = mainPane.getScene();
		scene.setOnKeyPressed(event -> {
			if (event.getCode().toString().charAt(0) == teclaObjetivo) {
				objetivo = true;
			} else if (event.getCode().toString().charAt(0) == teclaInicio) {
				inicio = true;
			}
		});

		scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (event.getCode().toString().charAt(0) == teclaObjetivo) {
				objetivo = false;

			} else if (event.getCode().toString().charAt(0) == teclaInicio) {
				inicio = false;
			}
		});

		scene.setOnKeyReleased(event -> {
			if (event.getCode().toString().charAt(0) == 'Q') {
				System.out.println("Objetivo:" + laberinto.getObjetivo());
				System.out.println("inicio: " + laberinto.getInicio());
			}
		});
	}

	public void creaCasillaYColindantes(Coordenada coord) {
		creaCasilla(coord);

		if (coord.getPiso() - 1 >= 0) {
			creaCasilla(coord.getPiso() - 1, coord.getFila(), coord.getColumna());
		}

		if (coord.getPiso() + 1 < laberinto.getnPisos()) {
			creaCasilla(coord.getPiso() + 1, coord.getFila(), coord.getColumna());
		}

	}

	public boolean isParseable(String string) {
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public void handleSetInicio() {

		if (isParseable(fStart.getText()) && isParseable(rStart.getText()) && isParseable(cStart.getText())) {
			int piso = Integer.parseInt(fStart.getText());
			int fila = Integer.parseInt(rStart.getText());
			int columna = Integer.parseInt(cStart.getText());
			if (laberinto.getObjetivo() == null
					|| !laberinto.getObjetivo().equals(new Coordenada(piso, fila, columna))) {
				if (piso >= laberinto.getnPisos())
					piso = laberinto.getnPisos() - 1;
				else if (piso < 0)
					piso = 0;

				if (fila >= laberinto.getFilas())
					fila = laberinto.getFilas() - 1;
				else if (fila < 0)
					fila = 0;

				if (columna >= laberinto.getColumnas())
					columna = laberinto.getColumnas() - 1;
				else if (columna < 0)
					columna = 0;

				Coordenada ini = laberinto.getInicio();

				laberinto.setInicio(new Coordenada(piso, fila, columna));
				setCoordTextInicio(laberinto.getInicio());

				if (ini != null)
					creaCasilla(ini);

				creaCasillaYColindantes(new Coordenada(piso, fila, columna));
			} else {
				setCoordTextInicio(laberinto.getInicio());
			}

		} else {
			setCoordTextInicio(laberinto.getInicio());
		}

	}

	public void handleSetObjetivo() {

		if (isParseable(fEnd.getText()) && isParseable(rEnd.getText()) && isParseable(cEnd.getText())) {
			int piso = Integer.parseInt(fEnd.getText());
			int fila = Integer.parseInt(rEnd.getText());
			int columna = Integer.parseInt(cEnd.getText());

			if (laberinto.getInicio() == null || !laberinto.getInicio().equals(new Coordenada(piso, fila, columna))) {
				if (piso >= laberinto.getnPisos())
					piso = laberinto.getnPisos() - 1;
				else if (piso < 0)
					piso = 0;

				if (fila >= laberinto.getFilas())
					fila = laberinto.getFilas() - 1;
				else if (fila < 0)
					fila = 0;

				if (columna >= laberinto.getColumnas())
					columna = laberinto.getColumnas() - 1;
				else if (columna < 0)
					columna = 0;

				Coordenada obj = laberinto.getObjetivo();

				laberinto.setObjetivo(new Coordenada(piso, fila, columna));

				setCoordTextObjetivo(laberinto.getObjetivo());
				if (obj != null)
					creaCasilla(obj);

				creaCasillaYColindantes(new Coordenada(piso, fila, columna));
			} else {
				setCoordTextObjetivo(laberinto.getObjetivo());
			}

		} else {
			setCoordTextObjetivo(laberinto.getObjetivo());
		}
	}

	private void setCoordTextInicio(Coordenada coord) {
		if (coord != null) {
			fStart.setText(coord.getPiso() + "");
			rStart.setText(coord.getFila() + "");
			cStart.setText(coord.getColumna() + "");
		} else {
			fStart.setText("not set");
			rStart.setText("not set");
			cStart.setText("not set");
		}
	}

	private void setCoordTextObjetivo(Coordenada coord) {
		if (coord != null) {
			fEnd.setText(coord.getPiso() + "");
			rEnd.setText(coord.getFila() + "");
			cEnd.setText(coord.getColumna() + "");
		} else {
			fEnd.setText("not set");
			rEnd.setText("not set");
			cEnd.setText("not set");
		}
	}

	public void handleTextPiso() {
		if (isParseable(pisoText.getText())) {

			if (pisoText.getText().length() <= 0) {
				pisoText.setText(pisoActual + "");
			} else {
				int piso = Integer.parseInt(pisoText.getText());
				if (piso >= laberinto.getnPisos())
					piso = laberinto.getnPisos() - 1;
				else if (piso < 0)
					piso = 0;

				setPisoActual(piso);
			}
		} else {
			pisoText.setText(pisoActual + "");
		}

	}

	public void handleOpen() throws Exception {
		FileChooser fileChooser = new FileChooser();

		File file = fileChooser.showOpenDialog((Stage) mainPane.getScene().getWindow());

		if (file != null) {

			String URL = file.toURI().toURL().toString();

			Laberinto laberinto = new Laberinto(URL.substring(5));

			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("../vistas/MainScene.fxml"));
			Parent parent2 = (Parent) fxmlLoader2.load();

			Scene scene = new Scene(parent2);
			scene.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());

			ControladorPrincipal controller2 = fxmlLoader2.<ControladorPrincipal>getController();

			controller2.setLaberinto(laberinto);
			controller2.cargaLaberinto();
			controller2.setPisoActual(0);

			Stage primaryStage = (Stage) mainPane.getScene().getWindow();
			primaryStage.setTitle("Ultimate Videogame PathFinder Extreme 3000");
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.show();

		}

	}

	public void handleClose() throws IOException {
		// Pantalla inicial
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("../vistas/InitialScene.fxml"));

		Parent parent1 = (Parent) fxmlLoader1.load();
		ControladorInicial controller1 = fxmlLoader1.<ControladorInicial>getController();

		Scene scene1 = new Scene(parent1);
		scene1.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());
		Stage primaryStage = (Stage) mainPane.getScene().getWindow();
		primaryStage.setScene(scene1);
		primaryStage.setTitle("Ultimate Videogame PathFinder Extreme 3000");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public void handleNew() throws IOException {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("../vistas/OpcionesLaberinto.fxml"));

		Parent parent1 = (Parent) fxmlLoader1.load();
		ControladorOpcionesLaberinto controller1 = fxmlLoader1.<ControladorOpcionesLaberinto>getController();
		controller1.setInitialValues();

		Scene scene1 = new Scene(parent1);
		scene1.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());

		Stage stage = new Stage();
		stage.setScene(scene1);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("New labyrinth options");
		stage.showAndWait();

		if (controller1.getLaberinto() != null) {
			Laberinto laberinto = controller1.getLaberinto();

			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("../vistas/MainScene.fxml"));
			Parent parent2 = (Parent) fxmlLoader2.load();

			Stage primaryStage = (Stage) mainPane.getScene().getWindow();
			Scene scene = new Scene(parent2, primaryStage.getWidth(), primaryStage.getHeight());
			scene.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());

			ControladorPrincipal controller2 = fxmlLoader2.<ControladorPrincipal>getController();

			controller2.setLaberinto(laberinto);
			controller2.cargaLaberinto();
			controller2.setPisoActual(0);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Ultimate Videogame PathFinder Extreme 3000");
			primaryStage.setResizable(true);
			primaryStage.show();
		}

	}

	public void handleExport() throws MalformedURLException {
		handleClearPath();

		Coordenada obj = laberinto.getObjetivo();
		Coordenada ini = laberinto.getInicio();
		laberinto.setObjetivo(null);
		laberinto.setInicio(null);

		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog((Stage) mainPane.getScene().getWindow());
		if (file != null) {
			String URL = file.toURI().toURL().toString().substring(5);
			laberinto.exportarAFichero(URL);

		}

		laberinto.setObjetivo(obj);
		laberinto.setInicio(ini);

	}

	public void handleClearPath() {
		if (solucion != null) {
			if (temporizador1 != null && temporizador2 != null) {
				temporizador1.stop();
				temporizador2.stop();
			}

			if (solucion.getCamino() != null)
				for (Coordenada coord : solucion.getCamino()) {
					if (laberinto.getCeldaAtCoord(coord).getTipo() == Celda.CAMINO) {
						laberinto.getCeldaAtCoord(coord).setTipo(Celda.LIBRE);
						creaCasilla(coord);
					}
				}

			if (solucion.getNodosExpandidos() != null)
				for (ArrayList<Coordenada> array : solucion.getNodosExpandidos()) {
					for (Coordenada coord : array) {
						if (laberinto.getCeldaAtCoord(coord).getTipo() == Celda.EXPANDIDO) {
							laberinto.getCeldaAtCoord(coord).setTipo(Celda.LIBRE);
							creaCasilla(coord);
						}
					}
				}

			wait = false;
		}
	}

	public void handleStart() throws Exception {

		handleSetInicio();
		handleSetObjetivo();
		handleTextPiso();
		handleWaitTime();

		if (laberinto.getObjetivo() != null && laberinto.getInicio() != null && !wait) {

			switch (choiceAlgortimo.getValue()) {
			case "Dijkstra":
				solucion = Dijkstra.dijkstra(laberinto);
				break;

			case "DFS":
				solucion = DFS.dfs(laberinto);
				break;

			case "A*":
				solucion = AStar.aStar(laberinto);
				break;
			default:
				throw new Exception("Algoritmo no reconocido");
			}

			if (solucion == null || !solucion.getCamino().contains(laberinto.getObjetivo())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Path Information");
				alert.setHeaderText("There is no path between the chosen nodes");
				alert.setContentText("Please select another \"Start\" and \"End\"");

				alert.showAndWait();
			} else {

				laberinto.dibujaExpandidos(solucion.getNodosExpandidos());
				indiceCamino = 0;
				indiceExpandidos = 0;

				wait = true;

				temporizador1 = new Timeline(new KeyFrame(Duration.millis(waitTime), (ActionEvent event1) -> {

					for (Coordenada coord : solucion.getNodosExpandidos().get(indiceExpandidos++)) {
						setPisoActual(coord.getPiso());
						creaCasilla(coord);
					}

					if (indiceExpandidos >= solucion.getNodosExpandidos().size()) {
						laberinto.dibujaCamino(solucion.getCamino());
						temporizador2.play();
					}

				}));
				temporizador1.setCycleCount(solucion.getNodosExpandidos().size());
				temporizador1.play();

				temporizador2 = new Timeline(new KeyFrame(Duration.millis(waitTime), (ActionEvent event1) -> {
					Coordenada coord = solucion.getCamino().get(indiceCamino++);
					setPisoActual(coord.getPiso());
					creaCasilla(coord);
				}));
				temporizador2.setCycleCount(solucion.getCamino().size());

			}

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Path Information");
			alert.setHeaderText(null);
			alert.setContentText("Please select \"Start\" and \"End\"");
		}
	}

	public void updateCamino(ArrayList<Coordenada> camino) {
		System.out.println("indice");
		creaCasilla(camino.get(indiceCamino++));
	}

	public void handleWaitTime() {
		if (isParseable(waitTimeText.getText())) {
			waitTime = Integer.parseInt(waitTimeText.getText());
		}

		waitTimeText.setText(waitTime + "");

	}
	
	public void handleUsage() throws IOException {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("../vistas/UsageScene.fxml"));

		Parent parent1 = (Parent) fxmlLoader1.load();
		UsageController controller1 = fxmlLoader1.<UsageController>getController();

		Scene scene1 = new Scene(parent1);
		scene1.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());

		Stage stage = new Stage();
		stage.setScene(scene1);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Usage");
		stage.showAndWait();
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
