package deeplearningGroup1;

import javafx.stage.Stage;

/**
 * 
 * @author J.McGuire
 *
 */
public class Course{
	// Attributes
	int numStudents;
	String topic;
	int grades[];
	int courseID[];
	double averageGrade;
	int minGrade;
	int maxGrade;
	double stDev;
	
	// Constructor
	public Course(int[] courseID, String courseName) {
		System.out.println("Your course ID is: " + courseID[0]); 	// a test that the ID is sent
		numStudents = 0; 	// initially there are no students in a course
		initializeID(courseID);
	}
	
	// Methods
	private void initializeID(int[] courseID) {
		this.courseID = new int[4];
		for(int i = 0; i < 4; i++) {
			this.courseID[i] = courseID[i];
		}
	}
	
	public void addStudent(int[] enteredID) {
		
	}
	
	public void createNewEssay(String chosenTopic) {
		
	}
	
	// Setters & Getters
	public String getTopic() {
		return topic;
	}
	
	public int getNumStudents() {
		return numStudents;
	}
	
	public double[] getStatistics(){
		double[] statistics = new double[] {averageGrade, minGrade, maxGrade};
		return statistics;
	}
	
	//TODO getGrades method
}