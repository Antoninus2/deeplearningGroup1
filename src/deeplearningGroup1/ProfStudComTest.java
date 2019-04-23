package deeplearningGroup1;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ProfStudComTest{
// Attributes
	private Pane testPane;
	private Scene testScene;
	public Stage testStage;
	private Button joinB;
	private ProfStudComTest student;
	private Course course;
	
// Constructor
	ProfStudComTest(CourseList cl){
		testPane = new Pane();
		joinB = new Button("Join Course");
		testPane.getChildren().add(joinB);
		
		// Make buttons work
		joinB.setOnAction(e -> { cl.setStudent(student); 
								 cl.clStage.show(); 
							   });
		
		// Create stage
		testScene = new Scene(testPane, 680, 680);
		testStage = new Stage();
		testStage.setTitle(" Student Test ");
		testStage.setScene(testScene);
	}
	
// Methods
	public void addCourse(Course course, int studNum) {
		this.course = course;
		createCourseButton(course, studNum);
	}
	
	private void createCourseButton(Course course, int studNum) {
		// TODO
	}
	
	public void setStudent(ProfStudComTest student) {
		this.student = student;
	}
	
}