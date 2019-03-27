/**
		 * @author Antonino Abeshi 
		 * Creating the Password Retrieval Tools nessesary to achieve success (Work in progress)
		 */



package deeplearningGroup1;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
	private Pane pane4;
	private Button clickToSubmit;
	private TextField EmailTextField;
	private InitialGUI initalGUI;

	public Stage secondOne;
	private Scene scene2;
	//private GridPane pane1;
	//private Button clickToSubmit;
	private TextField enterEssayTopic;
	private TextArea enterTextField;
	private Text essayTopic, enterText;
	private Jarvis jarvis;
	public Stage fourthOne;
	private Scene scene4;
	private Pane pane2;

		
	
	StudentAccount student= new StudentAccount();
	
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
		Back();
	}
	
	/**
	 * @Email email is entered here in order to get back the code 
	 */
	public void EnterEmail() {
		
		// Enter email
		Label Email = new Label("Enter Email:");
		pane1.add(Email, 0, 1);
		 EmailTextField = new TextField();
		pane1.add(EmailTextField, 1, 1, 2, 1);

	}
	
	/**
	 * @SubmitTheEmail this action will allow the system to sent an email to the person who is using this specific email.
	 */
	public void Submit() {
		
	
		clickToSubmit = new Button("Click To Submit");   //button to submit
		pane1.add(clickToSubmit, 4, 10, 5,1);
		clickToSubmit.setOnAction(event1 -> 
	    {
	    	
	    	//scene2 = new Scene(pane4, 600, 600);
			//secondOne = new Stage();
			//secondOne.setTitle(" Welcome Student ");
			//secondOne.setScene(scene2);
			//secondOne.show();
	    	//student.reset(EmailTextField.getText());
	    	
	    	System.out.println(EmailTextField.getText());
	    	//StudentAccount emailR = new StudentAccount();
	    	//emailR.reset(EmailTextField.getText());
	    	
	    });
		
	}
	
	public void Back()
	{
		
		Button Back = new Button("Back");
		pane1.add(Back, 1, 10);			//creating a back button
		
			Back.setOnAction(new EventHandler<ActionEvent>() {  // adding the back button options to go back to the main scene 
			
			@Override
			public void handle(ActionEvent event) {
				
		
			}
		});
	}
	
		
		
		
	}
	
	

