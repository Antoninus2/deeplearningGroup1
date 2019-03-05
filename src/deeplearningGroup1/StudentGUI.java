/**
 * @author Antonino Abeshi
 *
 */

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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class StudentGUI{

	public Stage secondOne;
	private Scene scene2;
	private GridPane pane1;
	private Button clickToSubmit;
	private TextField enterEssayTopic;
	private TextArea enterTextField;
	private Text essayTopic, enterText;
	private Jarvis jarvis;
	public Stage fourthOne;
	private Scene scene4;
	private Pane pane2;
	
	
	public StudentGUI(Jarvis jarvis) {
		this.jarvis = jarvis;
	}
   
	/**
	 * @StudentBox creating a student box for them to writte the essay in
	 */
	public void StudentBox() 
	{
		pane1 = new GridPane();
		Image image2 = new Image ("sure.jpg");
		pane1.setBackground(new Background(new BackgroundImage(image2,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
		
		pane1.setPadding(new Insets(25,25,25,25));
		pane1.setAlignment(Pos.TOP_LEFT);
		pane1.setHgap(50);
		pane1.setVgap(10);
		
		scene2 = new Scene(pane1, 600, 600);
		secondOne = new Stage();
		secondOne.setTitle(" Welcome Student ");
		secondOne.setScene(scene2);
		secondOne.show();
		
		pane1.setGridLinesVisible(false);
		
		essayTopic = new Text(" This is your topic");
		essayTopic.setFont(Font.font("Times New Roman",FontWeight.BOLD, 15));
		pane1.add(essayTopic, 0, 0,2,1);
		
		enterEssayTopic = new TextField();					//topic entry 
		pane1.add(enterEssayTopic, 0, 1,10,1);
		
		enterText = new Text(" Enter Your Essay");
		enterText.setFont(Font.font("Times New Roman",FontWeight.BOLD, 15));
		pane1.add(enterText, 0,3,1,1);
		
		
		enterTextField = new TextArea();				//text are to enter the essay
		enterTextField.setWrapText(true);
		pane1.add(enterTextField, 0, 4, 10, 10);
		
		clickToSubmit = new Button("Click To Submit");   //button to submit
		pane1.add(clickToSubmit, 4, 25, 5,1);
		
		/**
		 * @ButtonToSubmit this button makes sure that we retrieve the data from the typing and its graded by Jarvis
		 */
		clickToSubmit.setOnAction(event1 -> 
	    {
	    	/**
			 * @Author Steven Rose Adding the connection from Jarvis to the sumbit button
			 */
	    	String topic = essayTopic.getText();
	    	Essay e = new Essay(0, 300, 4, topic);
	    	e.writeEssay(enterTextField.getText());
	    	//TODO: Done
	    	String grade = jarvis.gradeEssay(e);
	    	//*******************************************************************************************
	    	/**
	    	 *@Author Antonino Abeshi
	    	 *@GradePage  creating the switch statement in order to open the grade 
	    	 */
	    	
	    	switch(grade = jarvis.gradeEssay(e))
	    	{
	    					//A Panel
	    		case "A":	pane2 = new Pane();
							scene4 = new Scene(pane2, 600, 600);
							Label A = new Label();
							A.setText("A");
							A.setFont(Font.font("Calibri",FontWeight.BOLD, 100));
							A.setLayoutX(250);
							A.setLayoutY(200);
							pane2.getChildren().add(A);
							fourthOne = new Stage();
							fourthOne.setTitle(" Letter Grade");
							fourthOne.setScene(scene4);
							fourthOne.show();
							secondOne.hide();
							break;
							// B Panel
		    	case "B":	pane2 = new Pane();
							scene4 = new Scene(pane2, 600, 600);
							Label B = new Label();
							B.setText("B");
							B.setFont(Font.font("Calibri",FontWeight.BOLD, 100));
							B.setLayoutX(250);
							B.setLayoutY(200);
							pane2.getChildren().add(B);
							fourthOne = new Stage();
							fourthOne.setTitle(" Letter Grade");
							fourthOne.setScene(scene4);
							fourthOne.show();
							secondOne.hide();
							break;
							// C Panel
		    	case "C":	pane2 = new Pane();
							scene4 = new Scene(pane2, 600, 600);
							Label C = new Label();
							C.setText("D");
							C.setFont(Font.font("Calibri",FontWeight.BOLD, 100));
							C.setLayoutX(250);
							C.setLayoutY(200);
							pane2.getChildren().add(C);
							fourthOne = new Stage();
							fourthOne.setTitle(" Letter Grade");
							fourthOne.setScene(scene4);
							fourthOne.show();
							secondOne.hide();
							break;
							//D Panel
		    	case "D":  	pane2 = new Pane();
							scene4 = new Scene(pane2, 600, 600);
							Label D = new Label();
							D.setText("D");
							D.setFont(Font.font("Calibri",FontWeight.BOLD, 100));
							D.setLayoutX(250);
							D.setLayoutY(200);
							pane2.getChildren().add(D);
							fourthOne = new Stage();
							fourthOne.setTitle(" Letter Grade");
							fourthOne.setScene(scene4);
							fourthOne.show();
							secondOne.hide();
							break;
		    	case "F":  	pane2 = new Pane();
							scene4 = new Scene(pane2, 600, 600);
							Label F = new Label();
							F.setText("F");
							F.setFont(Font.font("Calibri",FontWeight.BOLD, 100));
							F.setLayoutX(250);
							F.setLayoutY(200);
							pane2.getChildren().add(F);
							fourthOne = new Stage();
							fourthOne.setTitle(" Letter Grade");
							fourthOne.setScene(scene4);
							fourthOne.show();
							secondOne.hide();
							break;
				default:    System.out.println("No Grade");			
	    	}
	    			
	    	System.out.println("Topic: " + topic);
	    	System.out.println("Essay: \n{\n" + enterTextField.getText() + "\n}");
	    	System.out.println("Grade: " + grade);
	    	
	    });
		
		
		//Timer 
		
		
		
	}
	
}
