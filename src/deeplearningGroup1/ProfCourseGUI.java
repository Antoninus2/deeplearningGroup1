package deeplearningGroup1;

import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
	private Text studCtT;
	private Button nwEssyB, backB;
	private ToggleButton grdBkB, statsB;
	private int scale = 10;
	private int essayCt;
	private Group grdBkGroup, statsGroup;
	public Stage courseStage;
	//TODO make essay title attribute array
	
// Constructor
	public ProfCourseGUI(int[] courseID, String courseName, Course course) { 
		// Initialize Attributes
		this.course = course;
		essayCt = 0;
		grdBkGroup = new Group();
		statsGroup = new Group();
		
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
		gradeBookGUI();
		statsGUI();
		addButtons();
		
		// Make Buttons Work
		nwEssyB.setOnAction(e -> nwEssyWzrd());
		backB.setOnAction(e -> courseStage.close());
		statsB.setOnAction(e -> {grdBkB.setSelected(false);
								 grdBkGroup.setVisible(false);
								 statsGroup.setVisible(statsB.isSelected());
								});
		
		grdBkB.setOnAction(e -> {statsB.setSelected(false);
								 statsGroup.setVisible(false);
								 grdBkGroup.setVisible(grdBkB.isSelected());
								});
		
		// TODO add nodes to groups method, need to remove from pane, add node to group, and re-add group to pane
		//      ... do this with buttons probably
	}
	
// Methods
	
	private void statsGUI() { 
		
		// TODO have each essay be its own color rectangle
		
		// Create background
		Rectangle background = new Rectangle(0, scale * 16, 680, 680 - 160);
		background.setFill(Color.WHITE);
		statsGroup.getChildren().add(background);
		
		// Create graph grid
		Line hL = new Line();
		hL.setStartX(3 * scale);
		hL.setStartY(680 - 3 * scale);
		hL.setEndX(680 - 3 * scale);
		hL.setEndY(680 - 3 * scale);
		statsGroup.getChildren().add(hL);
		Line vL = new Line();
		vL.setStartX(3 * scale);
		vL.setStartY(160 + 3 * scale);
		vL.setEndX(3 * scale);
		vL.setEndY(680 - 3 * scale);
		statsGroup.getChildren().add(vL);
		
		// x Axis Labels
		int xLabPad = 15;
		Text fLab = new Text("F");
		fLab.setWrappingWidth(124);
		fLab.setTextAlignment(TextAlignment.CENTER);
		fLab.setX(3 * scale);
		fLab.setY(680 - xLabPad);
		statsGroup.getChildren().add(fLab);
		Text dLab = new Text("D");
		dLab.setWrappingWidth(124);
		dLab.setTextAlignment(TextAlignment.CENTER);
		dLab.setX(3 * scale + 124);
		dLab.setY(680 - xLabPad);
		statsGroup.getChildren().add(dLab);
		Text cLab = new Text("C");
		cLab.setWrappingWidth(124);
		cLab.setTextAlignment(TextAlignment.CENTER);
		cLab.setX(3 * scale + 2 * 124);
		cLab.setY(680 - xLabPad);
		statsGroup.getChildren().add(cLab);
		Text bLab = new Text("B");
		bLab.setWrappingWidth(124);
		bLab.setTextAlignment(TextAlignment.CENTER);
		bLab.setX(3 * scale + 3 * 124);
		bLab.setY(680 - xLabPad);
		statsGroup.getChildren().add(bLab);
		Text aLab = new Text("A");
		aLab.setWrappingWidth(124);
		aLab.setTextAlignment(TextAlignment.CENTER);
		aLab.setX(3 * scale + 4 * 124);
		aLab.setY(680 - xLabPad);
		statsGroup.getChildren().add(aLab);
		
		pcPane.getChildren().add(statsGroup);
		statsGroup.setVisible(false);
	}
	
	private void gradeBookGUI() { 
		// Create background
		Rectangle background = new Rectangle(0, scale * 16, 680, 680 - 160);
		background.setFill(Color.WHITE);
		grdBkGroup.getChildren().add(background);
		
		// Create Table Frame
		double vGap = 680 / 8;
		for(int i = 1; i < 8; i++) {
			Line vL = new Line();
			vL.setStartX(i * vGap);
			vL.setStartY(160);
			vL.setEndX(i * vGap);
			vL.setEndY(680);
			grdBkGroup.getChildren().add(vL);
		}
		
		double hGap = (680 - 160) / 25;
		for(int i = 1; i < 26; i++) {
			Line hL = new Line();
			hL.setStartX(0);
			hL.setStartY(160 + i * hGap);
			hL.setEndX(680);
			hL.setEndY(160 + i * hGap);
			grdBkGroup.getChildren().add(hL);
		}
		
		pcPane.getChildren().add(grdBkGroup);
		grdBkGroup.setVisible(false);
		// TODO Add essay titles to table
	}
	
	private void essayInSessionGUI(String topic) {
		// Remove New Essay Button
		pcPane.getChildren().remove(nwEssyB);
		
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
		
		endEssaySessionB.setOnAction(e -> { course.setEssayStatus(false); 	// tell course class the essay can be graded
											pcPane.getChildren().remove(eInProgress);
											pcPane.getChildren().remove(endEssaySessionB);
											if (essayCt < 10) {
												pcPane.getChildren().add(nwEssyB);
											}
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
										course.setEssayStatus(true); 	// tell course class there is an essay in session
										essayCt++; 						// update the number of essays in this course
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
		// Add 3 vertical spaces for far left column
		nwEssyB = new Button("New Essay");
		nwEssyB.setLayoutX(scale * 1);
		nwEssyB.setLayoutY(scale * 1);
		pcPane.getChildren().add(nwEssyB);
		
		grdBkB = new ToggleButton("Gradebook"); 
		grdBkB.setLayoutX(scale * 1);
		grdBkB.setLayoutY(scale * 4);
		pcPane.getChildren().add(grdBkB);
		
		statsB = new ToggleButton("Statistics"); 
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
		studCtT = new Text("There are currently " + count + " students in this course.");
		studCtT.setWrappingWidth(678);
		studCtT.setTextAlignment(TextAlignment.CENTER);
		studCtT.setX(1);
		studCtT.setY(scale * 6);
		pcPane.getChildren().add(studCtT);
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
	
	public void updateStudentCount() {
		pcPane.getChildren().remove(studCtT);
		int count = course.getNumStudents();
		studCtT = new Text("There are currently " + count + " students in this course.");
		studCtT.setWrappingWidth(678);
		studCtT.setTextAlignment(TextAlignment.CENTER);
		studCtT.setX(1);
		studCtT.setY(scale * 6);
		pcPane.getChildren().add(studCtT);
	}
}