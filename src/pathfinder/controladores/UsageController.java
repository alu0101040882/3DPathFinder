package pathfinder.controladores;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class UsageController {

	
	public BorderPane mainPane;
	
	public void handleOk() {
		((Stage) mainPane.getScene().getWindow()).close();
	}
}
