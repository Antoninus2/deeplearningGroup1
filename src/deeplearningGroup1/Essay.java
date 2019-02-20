package deeplearningGroup1;

public class Essay {
	
	private int[] length; //length of 2; min, max
	//private Student student;
	private int timeLimit; //length of time (in min) for student to type essay
	private String topic, essay;
	
	public Essay(int minLength, int maxLength, int timeLimit, String topic) {
		this.length = new int[]{minLength, maxLength};
		this.timeLimit = timeLimit;
		this.topic = topic;
	}
	
	
	
}
