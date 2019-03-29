package deeplearningGroup1;

import javafx.stage.Stage;

public class ProfCourseGUI{
	// Attributes
	int[] courseID;
	String courseName;
	Course course;
	
	// Constructor
	public ProfCourseGUI(int[] courseID, String courseName, Stage profStage, Course course) {
		// Set Attributes
		this.courseName = courseName;
		this.course = course;
		
		// Call Methods
		displayCourseName(courseName);
		displayCourseID(courseID);
	}
	
	// Methods
	private void displayCourseName(String courseName) {
		
	}
	
	private void displayCourseID(int[] courseID) {
		
	}
	
	// Setters & Getters
}