package deeplearningGroup1;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
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
	private GridPane homePane;
	private Button nwClssB;
	private int numClasses = 0;
	
	//TODO make this a normal pane
	
// Constructor
	public ProfHomepage(){
//<<<<<<< HEAD
		// Create background
		homePane = new GridPane();
		Image image = new Image ("ERAUlogo.jpeg");
		homePane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
		
		homePane.setPadding(new Insets(25,25,25,25));
		homePane.setAlignment(Pos.TOP_LEFT);
		homePane.setHgap(25);
		homePane.setVgap(5);
		
		// Create Buttons
		nwClssB = new Button("Create New Section");
		homePane.add(nwClssB, 4, 5);
		
		// Appareance
		Line blueL = new Line();
		// TODO clean up appearance
		
		// Place scene in stage
		scene = new Scene(homePane, 680, 680);
		profStage = new Stage();
		profStage.setTitle(" Welcome Professor ");
		profStage.setScene(scene);
		profStage.show();
		
		homePane.setGridLinesVisible(false);
		
		// Assign actions to buttons
		nwClssB.setOnAction(e -> createCourseWizard());
	}
	
// Methods

		
	
	
	public void ProfHomepage2() {
		// Create background
				homePane = new GridPane();
				Image image = new Image ("redbackground.jpg");
				homePane.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
				
				homePane.setPadding(new Insets(25,25,25,25));
				homePane.setAlignment(Pos.TOP_LEFT);
				homePane.setHgap(25);
				homePane.setVgap(5);
				
				// Create Buttons
				nwClssB = new Button("Create New Section");
				homePane.add(nwClssB, 4, 5);
				
				// Place scene in stage
				scene = new Scene(homePane, 600, 600);
				profStage = new Stage();
				profStage.setTitle(" Welcome Professor ");
				profStage.setScene(scene);
				profStage.show();
				
				homePane.setGridLinesVisible(false);
				
				// Assign actions to buttons
				nwClssB.setOnAction(e -> createNewCourse());
		
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
								  	 numClasses++;
								  	 createCourseBttn(secInput.getCharacters().toString());
								  	});
	}
	
	
	/**
	 * @author J.McGuire
	 * @param secName
	 * @return void
	 * Creates a button for a section.
	 * Assigns section ID to section.
	 * Allows user to open Course page.
	 */
	private void createCourseBttn(String courseName) {
		// TODO create multiple rows
		Button courseB = new Button(courseName);
		homePane.add(courseB, numClasses, 6);
		// Generate section ID
		int[] courseID = new int[4];
		for(int i = 0; i < 4; i++) {
			courseID[i] = randomFrom(0,9);
		}
		// creation of the course object
		Course course = new Course(courseID, courseName);
		ProfCourseGUI courseGUI = new ProfCourseGUI(courseID, courseName, course);   
		// go to course GUI when button clicked
		courseB.setOnAction(e -> courseGUI.courseStage.show()); 
	}
	
	
	// Random Number Generator
	private int randomFrom(int low, int high) {
		int randNum = 0;
		randNum = (int)(Math.random() * (high - low) + low);
		return randNum;
	}
	
}