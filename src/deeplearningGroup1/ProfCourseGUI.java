package deeplearningGroup1;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * 
 * @author J.McGuire
 *
 */
public class ProfCourseGUI{
// Attributes
	private Course course;
	private Scene pcScene;
	private Pane pcPane;
	private Button nwEssyB, grdBkB, statsB, backB;
	private int scale = 10;
	private Text alreadyEssay;
	public Stage courseStage;
	private Boolean essayErrorFlag;
	
// Constructor
	public ProfCourseGUI(int[] courseID, String courseName, Course course) { 
		// Set Attributes
		this.course = course;
		essayErrorFlag = false;
		
		// Set up GUI
		pcPane = new Pane();
		Image image = new Image ("ERAUlogo.jpeg");
		pcPane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
		pcScene = new Scene(pcPane, 680, 680);
		courseStage = new Stage();
		courseStage.setScene(pcScene);
		courseStage.setTitle("" + courseName + " Homepage");
		
		// Call Methods
		displayCourseName(courseName);
		displayCourseID(courseID);
		displayStudentCount();
		addButtons();
		
		// Make Buttons Work
		nwEssyB.setOnAction(e -> { if(course.getEssayStatus() && essayErrorFlag) {
										alreadyEssay = new Text("Please end this essay before creating a new one.");
										alreadyEssay.setFill(Color.RED);
										alreadyEssay.setWrappingWidth(678);
										alreadyEssay.setTextAlignment(TextAlignment.CENTER);
										alreadyEssay.setX(1);
										alreadyEssay.setY(scale * 10);
										pcPane.getChildren().add(alreadyEssay);
										essayErrorFlag = true; 	// throw the flag to prevent duplicate messages
								   } else {
									   nwEssyWzrd();
								   }
								 });
		backB.setOnAction(e -> courseStage.close()); // TODO this needs to be fixed
		// TODO stats button
		// TODO grade book button
		// TODO figure out how to keep an essay going
	}
	
// Methods
	
	private void essayInSessionGUI(String topic) {
		Text eInProgress = new Text("" + topic + " essay is in session.");
		eInProgress.setFill(Color.RED);
		eInProgress.setWrappingWidth(678);
		eInProgress.setTextAlignment(TextAlignment.CENTER);
		eInProgress.setX(1);
		eInProgress.setY(scale * 8);
		pcPane.getChildren().add(eInProgress);
		
		Button endEssaySessionB = new Button("End " + topic + " Essay");
		endEssaySessionB.setLayoutX(scale * 1);
		endEssaySessionB.setLayoutY(scale * 13);
		pcPane.getChildren().add(endEssaySessionB);
		
		endEssaySessionB.setOnAction(e -> { course.setEssayStatus(false);
											essayErrorFlag = false; 	//TODO
											pcPane.getChildren().remove(eInProgress);
											pcPane.getChildren().remove(endEssaySessionB);
											pcPane.getChildren().remove(alreadyEssay); 	//TODO this may be causing problems...maybe....
										  });
	}
	
	private void nwEssyWzrd() {
		// Create wizard
		Pane nwEssyP = new Pane();
		nwEssyP.setStyle("-fx-background-color: lightskyblue");
		Scene nwEssySc = new Scene(nwEssyP, 400, 300);
		Stage nwEssySt = new Stage();
		nwEssySt.setTitle("New Essay Creation Wizard");
		nwEssySt.setScene(nwEssySc);
		nwEssySt.show();
		
		// Add nodes
		int scale = 20; 	// scale for pane nodes;
		Label topicLabel = new Label("Essay Topic:");
		topicLabel.setLayoutX(3);
		topicLabel.setLayoutY(3);
		nwEssyP.getChildren().add(topicLabel);
		TextField topicInput = new TextField("Not Specified");
		topicInput.setLayoutX(5 * scale);
		topicInput.setLayoutY(1);
		nwEssyP.getChildren().add(topicInput);
		Button crtEssyB = new Button("Assign Essay");
		crtEssyB.setLayoutX(8 * scale);
		crtEssyB.setLayoutY(9 * scale);
		nwEssyP.getChildren().add(crtEssyB);
		
		Label minWordL = new Label("Minimum Word Count: ");
		minWordL.setLayoutX(3);
		minWordL.setLayoutY(2.3 * scale);
		nwEssyP.getChildren().add(minWordL);
		TextField minCtIn = new TextField("0");
		minCtIn.setLayoutX(8 * scale);
		minCtIn.setLayoutY(2 * scale);
		minCtIn.setPrefWidth(60);
		nwEssyP.getChildren().add(minCtIn);
		
		Label maxWordL = new Label("Maximum Word Count: ");
		maxWordL.setLayoutX(3);
		maxWordL.setLayoutY(3.8 * scale);
		nwEssyP.getChildren().add(maxWordL);
		TextField maxCtIn = new TextField("1");
		maxCtIn.setLayoutX(8 * scale);
		maxCtIn.setLayoutY(3.5 * scale);
		maxCtIn.setPrefWidth(60);
		nwEssyP.getChildren().add(maxCtIn);
		
		// Make wizard work
		crtEssyB.setOnAction(e -> { int minCt = Integer.parseInt(minCtIn.getCharacters().toString());
									int maxCt = Integer.parseInt(maxCtIn.getCharacters().toString());
									if (checkWordCount(minCt, maxCt) && !course.getEssayStatus()) {
										nwEssySt.close();
										course.createNewEssay(topicInput.getCharacters().toString(), minCt, maxCt);
										course.setEssayStatus(true);
										essayInSessionGUI(topicInput.getCharacters().toString());
									} else if(!checkWordCount(minCt, maxCt)) {
										Text badCt = new Text("Invalid Word Count Parameters");
										badCt.setFill(Color.RED);
										badCt.setWrappingWidth(398);
										badCt.setTextAlignment(TextAlignment.CENTER);
										badCt.setX(1);
										badCt.setY(scale * 6);
										nwEssyP.getChildren().add(badCt);
									} 
								  });
	}
	
	private Boolean checkWordCount(int minCt, int maxCt) {
		Boolean good = true;
		// 1. Check max is larger than min
		if(minCt >= maxCt) {
			good = false;
		// 2. Check both are positive values
		} else if (minCt < 0) {
			good = false;
		}
		
		return good;
	}
	
	private void addButtons() {
		// Add 3 vertical spaces for far right column
		nwEssyB = new Button("New Essay");
		nwEssyB.setLayoutX(scale * 1);
		nwEssyB.setLayoutY(scale * 1);
		pcPane.getChildren().add(nwEssyB);
		
		grdBkB = new Button("Gradebook");
		grdBkB.setLayoutX(scale * 1);
		grdBkB.setLayoutY(scale * 4);
		pcPane.getChildren().add(grdBkB);
		
		statsB = new Button("Statistics");
		statsB.setLayoutX(scale * 1);
		statsB.setLayoutY(scale * 7);
		pcPane.getChildren().add(statsB);
		
		backB = new Button("Exit Course");
		backB.setLayoutX(scale * 1);
		backB.setLayoutY(scale * 10);
		pcPane.getChildren().add(backB);
		
	}
	
	private void displayCourseName(String courseName) {
		Text title = new Text("" + courseName);
		//title.setFont(new Font("Impact", 60));
		title.setWrappingWidth(678);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setX(1);
		title.setY(scale * 2);
		pcPane.getChildren().add(title);
	}
	
	private void displayStudentCount() {
		int count = course.getNumStudents();
		Text text = new Text("There are currently " + count + " students in this course.");
		text.setWrappingWidth(678);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setX(1);
		text.setY(scale * 6);
		pcPane.getChildren().add(text);
	}
	
	private void displayCourseID(int[] courseID) {
		Text text = new Text("ID: " + courseID[0] + courseID[1] + courseID[2] + courseID[3]);
		text.setWrappingWidth(678);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setX(1);
		text.setY(scale * 4);
		pcPane.getChildren().add(text);
	}
	
// Setters & Getters
}