package pathfinder.controladores;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pathfinder.modelo.Laberinto;

public class ControladorInicial {

	
	public BorderPane mainPane;
	

	public void handleCargarLaberinto() throws Exception {
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
	
	public void handleNuevoLaberinto() throws IOException {
		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("../vistas/OpcionesLaberinto.fxml"));
		
		Parent parent1 = (Parent) fxmlLoader1.load();
		ControladorOpcionesLaberinto controller1 = fxmlLoader1.<ControladorOpcionesLaberinto>getController();
		controller1.setInitialValues();
		
		Scene scene1 = new Scene(parent1);
		scene1.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());
		
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL); 
		stage.setScene(scene1);
		stage.setResizable(false);
		stage.setTitle("New labyrinth options");
		stage.showAndWait();
		
		if(controller1.getLaberinto()!=null) {
			Laberinto laberinto =controller1.getLaberinto();
			
			FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("../vistas/MainScene.fxml"));
			Parent parent2 = (Parent) fxmlLoader2.load();
			
			Scene scene = new Scene(parent2);
			scene.getStylesheets().add(getClass().getResource("../vistas/application.css").toExternalForm());
			
			ControladorPrincipal controller2 = fxmlLoader2.<ControladorPrincipal>getController();
			
			controller2.setLaberinto(laberinto);
			controller2.cargaLaberinto();
			controller2.setPisoActual(0);
	

			Stage primaryStage = (Stage) mainPane.getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ultimate Videogame PathFinder Extreme 3000");
			primaryStage.setResizable(true);
			primaryStage.show();
		}
	}
}
