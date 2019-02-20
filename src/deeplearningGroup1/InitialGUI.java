package deeplearningGroup1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//variables 20
//Creating the menu bar 34
//Adding Action to the menu login 46
//Login Info Page 90
//Adding Action to the menu create account 186

public  class InitialGUI extends Application {
	
	// Variables
	
	Pane root;
	MenuButton BEGIN;
	MenuItem Login, CreateAccount;
	Button Back, Back1, SignInButton, CreateAnAccountButton;
	GridPane LoginPage,CreateAccountPage; 
	Text loginTitle, writtenText, AccountTitle;
	Label userName, password, FirstName, LastName, RepeatPassword, Username, emailAddress, emailAddress1;
	TextField userTextField, FirstNameTextField, LastNameTextField, UsernameTextField, emailAddressField, emailAddressField1;
	PasswordField passwordField, RepeatPasswordField;
	HBox horisontalBox;
	Image Check, Wrong;
	CheckBox StudentCheckBox, TeacherCheckBox;
	

	@Override
	public void start(Stage theFirstOne) {
		
		root = new Pane();
		Scene scene1 = new Scene(root, 600, 600);		// new scene x = 600; y = 600
		ImageView justImage = new ImageView();
		/*Image Background = new Image("background.png");
		justImage.setImage(Background);
		root.getChildren().add(justImage);*/
	//**************************************************************
		//Creating the menu bar
		
		Label name = new Label();
		name.setText("WELCOME TO THE DEEP LEARNING - ESSAY GRADING SYSTEM");
		name.setFont(Font.font("Calibri",FontWeight.BOLD, 20));
		name.setLayoutX(50);
		name.setLayoutY(50);
		root.getChildren().add(name);
		
		BEGIN = new MenuButton("Login/CreateAccount");
		Login = new MenuItem("Login");
		CreateAccount = new MenuItem("CreateAccount");
		BEGIN.getItems().addAll(Login, CreateAccount);
		BEGIN.setLayoutX(200);
		BEGIN.setLayoutY(300);
		root.getChildren().add(BEGIN);
		
	//**************************************************************
		//Adding action to the menu login 
		
		Login.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				LoginPage = new GridPane();
				Scene scene2 = new Scene(LoginPage, 600,600);
				theFirstOne.setScene(scene2);
				theFirstOne.setTitle("LOGIN");
				
	//**************************************************************************************			
			//Login Information and Login page in general 
				
				LoginPage.setAlignment(Pos.CENTER);
				LoginPage.setHgap(10);
				LoginPage.setVgap(10);
				LoginPage.setPadding(new Insets(25,25,25,25));
				
				loginTitle = new Text(" PLEASE ENTER YOUR LOGIN INFO");
				loginTitle.setFont(Font.font("Times New Roman",FontWeight.NORMAL, 20));
				userName = new Label("User Name:");
			    userTextField = new TextField();
			    password = new Label("Password");
			    passwordField = new PasswordField();
			    
			    LoginPage.add(loginTitle, 0, 0,2,1);   //row 0, column 0, and spanning 2 columns  but only 1 row
			    LoginPage.add(userName, 0, 1);
			    LoginPage.add(userTextField, 1, 1);
			    LoginPage.add(password, 0, 2);
			    LoginPage.add(passwordField, 1, 2);
			    
			    LoginPage.setGridLinesVisible(false);
			    
			    SignInButton = new Button("Sign In");
			    horisontalBox = new HBox(10);
			    horisontalBox.setAlignment(Pos.BOTTOM_CENTER);
			    horisontalBox.getChildren().add(SignInButton);
			    LoginPage.add(horisontalBox, 1, 4);
			    
			    //Student or teacher checkbox (we will add events later)
			    StudentCheckBox = new CheckBox();
			    StudentCheckBox.setText("Student");
			    LoginPage.add(StudentCheckBox, 0, 11);
			    
			    TeacherCheckBox = new CheckBox();
			    TeacherCheckBox.setText("Teacher");
			    LoginPage.add(TeacherCheckBox, 1, 11);
			    
			    
			    writtenText = new Text();				// adding more text to the sign in box when pressing the sign in button
			    LoginPage.add(writtenText, 1, 6);
			    
			    SignInButton.setOnAction(event1 -> 
			    {
			    	System.out.println(userTextField.getText());
			    	System.out.println(passwordField.getText());
			    	writtenText.setFill(Color.FIREBRICK);
			    	writtenText.setText("Sign in button presed");
			    	
			    });
			    
			    LoginPage.addEventHandler(KeyEvent.KEY_PRESSED, ev -> 
			    
			    {
			    	if(ev.getCode() == KeyCode.ENTER)						// if enter is pressed then you can sign in as well
			    	{
			    		System.out.println(userTextField.getText());
				    	System.out.println(passwordField.getText());
			    		writtenText.setFill(Color.FIREBRICK);
				    	writtenText.setText("Enter button pressed");
			    		
			    	}
			    	
			    });
			    
			    
			
	//**************************************************************************************************	
			// Back button 
				Back = new Button("Back");
				LoginPage.add(Back, 5, 7);			//creating a back button
				
					Back.setOnAction(new EventHandler<ActionEvent>() {  // adding the back button options to go back to the main scene 
					
					@Override
					public void handle(ActionEvent event) {
						theFirstOne.setScene(scene1);
				
					}
				});
					
				
			}
		});
		
		//*******************************************************************
		//Adding action to the menu CreateAccount
		
		CreateAccount.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				CreateAccountPage = new GridPane();
				Scene scene3 = new Scene(CreateAccountPage, 600,600);
				theFirstOne.setScene(scene3);
				theFirstOne.setTitle("CREATE ACCOUNT");
				
	//Adding the CreateAccountPage
				
				CreateAccountPage.setAlignment(Pos.TOP_CENTER);
				CreateAccountPage.setHgap(10);
				CreateAccountPage.setVgap(10);
				CreateAccountPage.setPadding(new Insets(25,25,25,25));
				
			
				AccountTitle = new Text(" PLEASE ENTER INFORMATION");
				AccountTitle.setFont(Font.font("Times New Roman",FontWeight.BOLD, 20));
				CreateAccountPage.add(AccountTitle, 0, 0,2,1);   //row 0, column 0, and spanning 2 columns  but only 1 row
				
				// Enter First Name
				FirstName = new Label("First Name:");
				CreateAccountPage.add(FirstName, 0, 1);
				FirstNameTextField = new TextField();
				CreateAccountPage.add(FirstNameTextField, 1, 1);
				
				
				//Enter last name
				LastName = new Label("Last Name:");
				CreateAccountPage.add(LastName, 0, 2);
				LastNameTextField = new TextField();
				CreateAccountPage.add(LastNameTextField, 1, 2);
				
				
				//Enter Username
				Username = new Label("Username");
				CreateAccountPage.add(Username, 0, 3);
				UsernameTextField = new TextField();
				CreateAccountPage.add(UsernameTextField, 1, 3);
				
				//Enter Password
			    password = new Label("Password");
			    CreateAccountPage.add(password, 0, 4);
			    passwordField = new PasswordField();
			    CreateAccountPage.add(passwordField, 1, 4);

			    //repeat Password
			    RepeatPassword = new Label("Repeat Password");
			    CreateAccountPage.add(RepeatPassword, 0, 5);
			    RepeatPasswordField = new PasswordField();
			    CreateAccountPage.add(RepeatPasswordField, 1, 5);

			    //Enter Email Address
			    emailAddress = new Label("Enter Email Address");
			    CreateAccountPage.add(emailAddress, 0, 6);
			    emailAddressField = new TextField();
			    CreateAccountPage.add(emailAddressField, 1, 6);
			    
				
			    //Repeat Email Address
			    emailAddress1 = new Label("Verify Email Address");
			    CreateAccountPage.add(emailAddress1, 0, 7);
			    emailAddressField1 = new TextField();
			    CreateAccountPage.add(emailAddressField1, 1, 7);
			    
			    // grid lines
			    CreateAccountPage.setGridLinesVisible(false);
			    
			    // creating the button to make the account
			    CreateAnAccountButton = new Button("Create an Account");
			    horisontalBox = new HBox(10);
			    horisontalBox.setAlignment(Pos.BOTTOM_CENTER);
			    horisontalBox.getChildren().add(CreateAnAccountButton);
			    CreateAccountPage.add(horisontalBox, 1, 8);
			    
			    //Student or teacher checkbox (we will add events later)
			    StudentCheckBox = new CheckBox();
			    StudentCheckBox.setText("Student");
			    CreateAccountPage.add(StudentCheckBox, 0, 11);
			    
			    TeacherCheckBox = new CheckBox();
			    TeacherCheckBox.setText("Teacher");
			    CreateAccountPage.add(TeacherCheckBox, 1, 11);
			    
			    
				 
			    //displays the text after pressing the button to create account 
			    //writtenText = new Text();				// adding more text to the sign in box when pressing the sign in button
			    //CreateAccountPage.add(writtenText, 1, 9);
			    
			    // we are creating the button to put in  images are Check and Wrong
			    CreateAnAccountButton.setOnAction(event1 -> 
			    {
			    	
			    	System.out.println(FirstNameTextField.getText());
			    	System.out.println(LastNameTextField.getText());
			    	System.out.println(UsernameTextField.getText());
			    	
			    	
			    	
			    // comparing the password fields 
			    	
			    	System.out.println(passwordField.getText());	
			    	System.out.println(RepeatPasswordField.getText());
			    	
			    if ((passwordField.getText().isEmpty()) && RepeatPasswordField.getText().isEmpty())
			    		//if (passwordField.getText().contentEquals(RepeatPasswordField.getText()))
		    			{
								{
			    				Wrong = new Image("x image.png");
			    				Circle check1 = new Circle(10);
			    				check1.setFill(new ImagePattern(Wrong));
			    				CreateAccountPage.add(check1, 3, 4);
			    				
			    				Image Wrong2 = new Image("x image.png");
		    					Circle check2 = new Circle(10);
		    					check2.setFill(new ImagePattern(Wrong2));
		    					CreateAccountPage.add(check2, 3, 5);
		    					
		    					
			    				//ImageView checkView = new ImageView(Check);
			    				//checkView.setPreserveRatio(true);
			    				//CreateAccountPage.add(checkView, 0, 10);
			    			
			    			}
		    			}
			    else if (!passwordField.getText().isEmpty() && !RepeatPasswordField.getText().isEmpty())  // checking if the texbox is embty
			    					{
			    	
			    						
				    					Check = new Image("check image.png");
				    					Circle check3 = new Circle(10);
				    					check3.setFill(new ImagePattern(Check));
				    					CreateAccountPage.add(check3, 3, 4);
				    					
				    					Image Check2 = new Image("check image.png");
				    					Circle check4 = new Circle(10);
				    					check4.setFill(new ImagePattern(Check2));
				    					CreateAccountPage.add(check4, 3, 5);
				    					
				    					
				    					
				    					
				    					  
				    					 
			    				}
			    	 else 
			    	 {
			    		 Wrong = new Image("x image.png");
		    				Circle check1 = new Circle(10);
		    				check1.setFill(new ImagePattern(Wrong));
		    				CreateAccountPage.add(check1, 3, 4);
		    				
		    				Image Wrong2 = new Image("x image.png");
	    					Circle check2 = new Circle(10);
	    					check2.setFill(new ImagePattern(Wrong2));
	    					CreateAccountPage.add(check2, 3, 5);
	    					
	    					
	    					
	    					   
			    	 }
			    
			    			
			    	
			    	if (passwordField.getText().contentEquals(RepeatPasswordField.getText()))		// checking if the text is equal to the other repeat password
		    						{
		    						
				    					Check = new Image("check image.png");
				    					Circle check3 = new Circle(10);
				    					check3.setFill(new ImagePattern(Check));
				    					CreateAccountPage.add(check3, 3, 4);
				    					
				    					Image Check2 = new Image("check image.png");
				    					Circle check4 = new Circle(10);
				    					check4.setFill(new ImagePattern(Check2));
				    					CreateAccountPage.add(check4, 3, 5);
				    					
				    					Text WrongMessage = new Text();
				    					WrongMessage.setText("  ");
				    					CreateAccountPage.add(WrongMessage, 1, 9);
				    					WrongMessage.setFill(Color.GREEN);
				    					
		    						} else 
				    					{
		    							Wrong = new Image("x image.png");
					    				Circle check1 = new Circle(10);
					    				check1.setFill(new ImagePattern(Wrong));
					    				CreateAccountPage.add(check1, 3, 4);
					    				
					    				Image Wrong2 = new Image("x image.png");
				    					Circle check2 = new Circle(10);
				    					check2.setFill(new ImagePattern(Wrong2));
				    					CreateAccountPage.add(check2, 3, 5);
				    					
				    					Text WrongMessage1 = new Text();
				    					WrongMessage1.setText("Passwords dont match");
				    					WrongMessage1.setFill(Color.FIREBRICK);
				    					CreateAccountPage.add(WrongMessage1, 1, 9);
				    				}
    			
    	
    			
		//******************************************************************************************************************	    	
			    	//email comparison and stuff
			    	System.out.println(emailAddressField.getText()); 
			    	System.out.println(emailAddressField1.getText());
			    	
			    	if ((emailAddressField.getText().isEmpty()) && emailAddressField1.getText().isEmpty())
			    		//if (!passwordField.getText().contentEquals(RepeatPasswordField.getText()))
		    			{
								{
									
									
			    				Image imageX = new Image("x image.png");
			    				Circle check5 = new Circle(10);
			    				check5.setFill(new ImagePattern(imageX));
			    				CreateAccountPage.add(check5, 3, 6);
			    				
			    				Image imageX2 = new Image("x image.png");
		    					Circle check6 = new Circle(10);
		    					check6.setFill(new ImagePattern(imageX2));
		    					CreateAccountPage.add(check6, 3, 7);
		    					
			    				
			    				
			    				//ImageView checkView = new ImageView(Check);
			    				//checkView.setPreserveRatio(true);
			    				//CreateAccountPage.add(checkView, 0, 10);
			    			
			    			}
		    			}
			    	else if (!emailAddressField.getText().isEmpty() && !emailAddressField1.getText().isEmpty())  // checking if the textbox is embty
			    				
			    					{
			    						
			    					Image imageCheck = new Image("check image.png");
			    					Circle check7 = new Circle(10);
			    					check7.setFill(new ImagePattern(imageCheck));
			    					CreateAccountPage.add(check7, 3, 6);
			    					
			    					Image imageCheck2 = new Image("check image.png");
			    					Circle check8 = new Circle(10);
			    					check8.setFill(new ImagePattern(imageCheck2));
			    					CreateAccountPage.add(check8, 3, 7);
			    					
			    					Text WrongMessage = new Text();
			    					WrongMessage.setText(" ");
			    					CreateAccountPage.add(WrongMessage, 1, 10);
			    					WrongMessage.setFill(Color.GREEN);
			    					
			    				}
			    					else 
			    					{
			    						
					    				Image imageX = new Image("x image.png");
					    				Circle check5 = new Circle(10);
					    				check5.setFill(new ImagePattern(imageX));
					    				CreateAccountPage.add(check5, 3, 6);
					    				
					    				Image imageX2 = new Image("x image.png");
				    					Circle check6 = new Circle(10);
				    					check6.setFill(new ImagePattern(imageX2));
				    					CreateAccountPage.add(check6, 3, 7);
				    					Text WrongMessage = new Text();
				    					
				    					WrongMessage.setText("Emails dont match");
				    					CreateAccountPage.add(WrongMessage, 1, 10);
				    					WrongMessage.setFill(Color.FIREBRICK);
				    					
			    					}
			    	if (emailAddressField.getText().contentEquals(emailAddressField1.getText()))		// checking if the text is equal to the other repeat password
					{
			    		Image imageCheck = new Image("check image.png");
    					Circle check7 = new Circle(10);
    					check7.setFill(new ImagePattern(imageCheck));
    					CreateAccountPage.add(check7, 3, 6);
    					
    					Image imageCheck2 = new Image("check image.png");
    					Circle check8 = new Circle(10);
    					check8.setFill(new ImagePattern(imageCheck2));
    					CreateAccountPage.add(check8, 3, 7);
    					
    					Text WrongMessage = new Text();
    					WrongMessage.setText(" ");
    					CreateAccountPage.add(WrongMessage, 1, 10);
    					WrongMessage.setFill(Color.GREEN);
					}
			    	
			    	else {
			    		
			    		Image imageX = new Image("x image.png");
	    				Circle check5 = new Circle(10);
	    				check5.setFill(new ImagePattern(imageX));
	    				CreateAccountPage.add(check5, 3, 6);
	    				
	    				Image imageX2 = new Image("x image.png");
    					Circle check6 = new Circle(10);
    					check6.setFill(new ImagePattern(imageX2));
    					CreateAccountPage.add(check6, 3, 7);
    					
    					Text WrongMessage = new Text();
    					WrongMessage.setText("Emails dont match");
    					WrongMessage.setFill(Color.FIREBRICK);
    					CreateAccountPage.add(WrongMessage, 1, 10);
			    	}
			    			
			    	
			    	//-------------------------------------
			    	//writtenText.setFill(Color.FIREBRICK);
			    	//writtenText.setText("Create Account Pressed");
			    	
			    });
			    
			  CreateAnAccountButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> 
			    
			    {
			    	if(ev.getCode() == KeyCode.ENTER)						// if enter is pressed then you can sign in as well
			    	{
			    		System.out.println(FirstNameTextField.getText());
				    	System.out.println(LastNameTextField.getText());
				    	System.out.println(UsernameTextField.getText());
				    	System.out.println(passwordField.getText());
				    	System.out.println(RepeatPasswordField.getText());
				    	System.out.println(emailAddressField.getText());
				    	System.out.println(emailAddressField1.getText());
			    		//writtenText.setFill(Color.FIREBRICK);
				    	//writtenText.setText("Enter button pressed");
			    		
			    	}
			    	
			    });
			    
			  
			  // back button 
				Back1 = new Button("Back");
				CreateAccountPage.add(Back1, 5, 9);
				
					Back1.setOnAction(new EventHandler<ActionEvent>() {
					
					@Override
					public void handle(ActionEvent event) {
						theFirstOne.setScene(scene1);
				
					}
				});
			}
		});
		
		
		theFirstOne.setTitle(" WELCOME TO THE ESSAY GRADING SYSTEM 1.0");
		theFirstOne.setScene(scene1);
		theFirstOne.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
		
	}

