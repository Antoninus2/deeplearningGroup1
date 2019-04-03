package deeplearningGroup1;


/**
 * 
 * @author J.McGuire
 *
 */
public class Course {
// Attributes
	int numStudents; 		// number of students enrolled in the course
	String topic; 			// topic of the essay for student GUI purposes
	int minWrdCt; 			// minimum word count for essay
	int maxWrdCt; 			// maximum word count for essay
	int grades[]; 			// array of length numStudents with all grades
	int courseID[]; 		// 4 digit course ID number for students to join
	double averageGrade; 	// average grade in course in double form
	int minGrade; 			// worst grade on essay
	int maxGrade;			// best grade on essay
	double stDev; 			// standard deviation from average essay grade
	Boolean essayStatus; 	// boolean value that essay is in progress
	
// Constructor
	public Course(int[] courseID, String courseName) {
		numStudents = 0; 	// initially there are no students in a course
		essayStatus = false; 	// initially there is no essay
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
		int verified = 0;
		for(int i = 0; i < 4; i++) {
			if(courseID[i] == enteredID[i]) {
				verified++;
			}
		}
		
		if (verified == 4) {
			// TODO add student
			numStudents++;
		}
	}
	
	public void createNewEssay(String chosenTopic, int minCt, int maxCt) {
		topic = chosenTopic;
		minWrdCt = minCt;
		maxWrdCt = maxCt;
		System.out.println("The topic of the essay is: " + topic);
	}
	
// Setters & Getters
	public void setEssayStatus(Boolean status) {
		essayStatus = status;
	}
	
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
	
	public Boolean getEssayStatus() {
		return essayStatus;
	}
	
	//TODO getGrades method
}