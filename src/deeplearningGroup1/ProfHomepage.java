package deeplearningGroup1;

import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * 
 * @author J.McGuire
 *
 */
public class ProfHomepage{
	
	public Stage profStage;
	private Scene scene;
	private Pane homePane;
	private Button nwClssB;
	private int numClasses = 0;
	private int scale = 10;
	
// Constructor
	public ProfHomepage(){
		// Create background
		homePane = new Pane();
		Image image = new Image ("ERAUlogo.jpeg");
		homePane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
		
		// Create Buttons
		nwClssB = new Button("Create New Section");
		nwClssB.setLayoutX(scale * 1);
		nwClssB.setLayoutY(scale * 2);
		homePane.getChildren().add(nwClssB);
		
		// Appareance
		Line blueL = new Line();
		// TODO clean up appearance
		// TODO update number of students in each GUI
		
		// Place scene in stage
		scene = new Scene(homePane, 680, 680);
		profStage = new Stage();
		profStage.setTitle(" Welcome Professor ");
		profStage.setScene(scene);
		profStage.show();
		
		// Assign actions to buttons
		nwClssB.setOnAction(e -> createCourseWizard());
	}
	
// Methods
	
	/**
	 * @author J.McGuire
	 * @param none
	 * @return void
	 * Creates a new section for
	 * students to join.
	 */
	private void createCourseWizard() {
		int scale = 20; 	// scale for pane nodes;
		// Create Section Creation Wizard
		Pane ncPane = new Pane();
		Scene ncScene = new Scene(ncPane, 300,100);
		Stage ncStage = new Stage();
		ncStage.setTitle("New Section Creation Wizard");
		ncStage.setScene(ncScene);
		ncStage.show();
		
		// Add nodes to wizard
		Label courseLabel = new Label("Course Name:");
		courseLabel.setLayoutX(3);
		courseLabel.setLayoutY(3);
		ncPane.getChildren().add(courseLabel);
		TextField secInput = new TextField("Course " + (numClasses + 1) + "");
		secInput.setLayoutX(5 * scale);
		secInput.setLayoutY(1);
		ncPane.getChildren().add(secInput);
		Button crtCourseB = new Button("Create");
		crtCourseB.setLayoutX(5 * scale);
		crtCourseB.setLayoutY(3 * scale);
		ncPane.getChildren().add(crtCourseB);
		
		// Make wizard work
		crtCourseB.setOnAction(e -> {ncStage.close();
								  	 numClasses++; 	// TODO possibly move this to create button method
								  	 createCourseBttn(secInput.getCharacters().toString());
								  	});
	}
	
	
	/**
	 * @author J.McGuire
	 * @param courseName
	 * @return void
	 * Creates a button for a section.
	 * Assigns section ID to section.
	 * Allows user to open Course page.
	 */
	private void createCourseBttn(String courseName) {
		// TODO create multiple rows
		Button courseB = new Button(courseName);
		courseB.setLayoutX(numClasses * scale * 10);
		courseB.setLayoutY(6 * scale);
		homePane.getChildren().add(courseB);
		// Generate section ID
		int[] courseID = new int[4];
		for(int i = 0; i < 4; i++) {
			courseID[i] = randomFrom(0,9);
		}
		// creation of the course object
		Course course = new Course(courseID, courseName);
		ProfCourseGUI courseGUI = new ProfCourseGUI(courseID, courseName, course);   
		// go to course GUI when button clicked
		courseB.setOnAction(e -> { courseGUI.updateStudentCount();
								   courseGUI.courseStage.show();
								 }); 
	}
	
	
	// Random Number Generator
	private int randomFrom(int low, int high) {
		int randNum = 0;
		randNum = (int)(Math.random() * (high - low) + low);
		return randNum;
	}
	
}