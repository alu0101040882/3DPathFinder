package pathfinder;

	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pathfinder.controladores.ControladorInicial;
import pathfinder.controladores.ControladorPrincipal;
import pathfinder.modelo.Laberinto;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	
	int ancho = 700;
	int largo = 1000;
	@Override
	public void start(Stage primaryStage) {
		try {
			//Pantalla inicial
			FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("vistas/InitialScene.fxml"));
			
			Parent parent1 = (Parent) fxmlLoader1.load();
			ControladorInicial controller1 = fxmlLoader1.<ControladorInicial>getController();

			Scene scene1 = new Scene(parent1);
			scene1.getStylesheets().add(getClass().getResource("vistas/application.css").toExternalForm());
			primaryStage.setScene(scene1);
			primaryStage.setTitle("Ultimate Videogame PathFinder Extreme 3000");
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
