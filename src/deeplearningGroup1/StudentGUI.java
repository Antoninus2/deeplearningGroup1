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

/**
 * @author Antonino Abeshi
 *
 */
public class StudentGUI{

	public Stage secondOne;
	private Scene scene2;
	private GridPane pane1;
	private Button clickToSubmit;
	private TextField enterEssayTopic;
	private TextArea enterTextField;
	private Text essayTopic, enterText;
	private Jarvis jarvis;
	
	
	public StudentGUI(Jarvis jarvis) {
		this.jarvis = jarvis;
	}
   
	/**
	 * @StudentBox creating a student box for them to writte the essay in
	 * 
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
		clickToSubmit.setOnAction(event1 -> 
	    {
	    	String topic = essayTopic.getText();
	    	Essay e = new Essay(0, 300, 4, topic);
	    	e.writeEssay(enterTextField.getText());
	    	//TODO: here you go nino, call this function:
	    	String grade = jarvis.gradeEssay(e);
	    	
	    	System.out.println("Topic: " + topic);
	    	System.out.println("Essay: \n{\n" + enterTextField.getText() + "\n}");
	    	System.out.println("Grade: " + grade);
	    	
	    });
		
		
		//Timer 
		
		
		
	}
	
}
