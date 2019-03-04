package deeplearningGroup1;

import javafx.animation.Timeline;
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

/**
 * @author Antonino Abeshi
 *
 */
public class PasswordRetrievalGUI{

	public Stage thirdOne;
	private Scene scene3;
	private GridPane pane1;
	private Button clickToSubmit;
	private TextField EmailTextField;

	
	public PasswordRetrievalGUI() {
		
	}
   
	/**
	 * @PasswordRetrieval making sure that we retrieve the password
	 * 
	 */
	public void PasswordReset() 
	{
		pane1 = new GridPane();
		
		pane1.setPadding(new Insets(25,25,25,25));
		pane1.setAlignment(Pos.TOP_LEFT);
		pane1.setHgap(50);
		pane1.setVgap(10);
		
		scene3 = new Scene(pane1, 600, 600);
		thirdOne = new Stage();
		thirdOne.setTitle(" Retrieve Password ");
		thirdOne.setScene(scene3);
		thirdOne.show();
		
		pane1.setGridLinesVisible(false);
		
		EnterEmail();
		Submit();
	}
	
	public void EnterEmail() {
		
		// Enter email
		Label Email = new Label("Enter Email:");
		pane1.add(Email, 0, 1);
		 EmailTextField = new TextField();
		pane1.add(EmailTextField, 1, 1, 2, 1);

	}
	
	public void Submit() {
		
	
		clickToSubmit = new Button("Click To Submit");   //button to submit
		pane1.add(clickToSubmit, 4, 10, 5,1);
		clickToSubmit.setOnAction(event1 -> 
	    {
	    	
	    	
	    	System.out.println(EmailTextField.getText());
	    	StudentAccount emailR = new StudentAccount();
	    	emailR.reset(EmailTextField.getText());
	    	
	    });
		
	}
	
		
		
		
	}
	
	
