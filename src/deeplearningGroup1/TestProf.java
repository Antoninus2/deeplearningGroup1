package deeplearningGroup1;

import javafx.stage.Stage;
import javafx.application.Application;


public class TestProf extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		ProfHomepage t = new ProfHomepage();
		ProfStudComTest test = new ProfStudComTest();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}