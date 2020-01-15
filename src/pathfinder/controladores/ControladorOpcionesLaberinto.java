package pathfinder.controladores;


import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pathfinder.modelo.Laberinto;

public class ControladorOpcionesLaberinto {

	Laberinto laberinto;
	
	public BorderPane mainPane;
	public TextField pisosText,filasText,columnasText;
	public RadioButton aleatorioSi,aleatorioNo;
	ToggleGroup grupoAleatorio = new ToggleGroup();
	
	public void initialize() {
		aleatorioSi.setToggleGroup(grupoAleatorio);
		aleatorioNo.setToggleGroup(grupoAleatorio);
	}
	
	public void setInitialValues() {
		pisosText.setText("5");
		filasText.setText("5");
		columnasText.setText("5");
	}
	
	public void handleAceptar() {
		int pisos = Integer.parseInt(pisosText.getText());
		int filas = Integer.parseInt(filasText.getText());
		int columnas = Integer.parseInt(columnasText.getText());
		
		laberinto = new Laberinto(pisos,filas,columnas,aleatorioSi.isSelected());
		((Stage) mainPane.getScene().getWindow()).close();
	}
	
	public void handleCancelar() {
		((Stage) mainPane.getScene().getWindow()).close();
	}

	public Laberinto getLaberinto() {
		return laberinto;
	}
	

}
