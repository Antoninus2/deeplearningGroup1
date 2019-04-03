
/**
 * @author Antonino Abeshi
 *
 */
package deeplearningGroup1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TemporaryPasswordReturnGUI extends Application {
	
	public Stage thirdOne;
	private Scene scene3;
	private GridPane pane1; 
	public Button Back;
	public Stage secondOne;
	private TextField TemporaryPassword;
	
	StudentAccount studentaccount = new StudentAccount();
	

	public TemporaryPasswordReturnGUI() {
		
	
	}
	
	public void OpenTemp() {
		
		pane1 = new GridPane();
		
		pane1.setPadding(new Insets(25,25,25,25));    
		pane1.setAlignment(Pos.TOP_LEFT);
		pane1.setHgap(50);
		pane1.setVgap(10);
		
		scene3 = new Scene(pane1, 600, 600);  //creating a new stage
		thirdOne = new Stage();
		thirdOne.setTitle(" Retrieve Password ");
		thirdOne.setScene(scene3);
		thirdOne.show();
		
		pane1.setGridLinesVisible(false);
		
		Label Email = new Label("Here is your temporary Password");
		pane1.add(Email, 0, 1);
		TemporaryPassword = new TextField();
		pane1.add(TemporaryPassword, 1, 1, 2, 1);
		
		TemporaryPassword.setText(studentaccount.reset());
		TemporaryPassword.setEditable(false);
		
		Back = new Button("Back");
		pane1.add(Back, 1, 5);			//creating a back button
	
	
	
		
		
			Back.setOnAction(e-> {  // adding the back button options to go back to the main scene 
			
				//prg.PasswordReset();
				thirdOne.hide();
		
			
		});
	}
	

	@Override
	public void start(Stage arg0) throws Exception {
		
	}
	

}

