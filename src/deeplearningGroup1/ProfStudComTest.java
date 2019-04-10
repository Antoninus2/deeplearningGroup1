package deeplearningGroup1;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfStudComTest{
	// Attributes
	private Pane testPane;
	private Scene testScene;
	private Stage testStage;
	private Button joinB, gradeB;
	
	// Constructor
	ProfStudComTest(){
		testPane = new Pane();
		addButtons();
		
		// Make Buttons work
		
		// Create stage
		testScene = new Scene(testPane, 680, 680);
		testStage = new Stage();
		testStage.setTitle(" Student Test ");
		testStage.setScene(testScene);
		testStage.show();
	}
	
	// Methods
	
	private void addButtons() {
		joinB = new Button("Join Course");
		testPane.getChildren().add(joinB);
	}
}