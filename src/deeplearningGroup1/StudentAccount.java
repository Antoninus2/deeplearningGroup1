package deeplearningGroup1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class StudentAccount {
	String usn;		//username
	String pwd;		//password
	String email;	
	String fn;		//First name
	String ln;		//Last name

	Scanner scan= new Scanner(System.in);

	File file = new File("AccountInformation.txt");
	

	
	public void signUp() {			//student sign up- takes in first name, last name, email, sets password and username with first-time confirmation
	/*	System.out.println("First name: \n");		//Nino- from GUI
		fn=scan.nextLine();
		System.out.println("Last name: \n");		//Nino- from GUI
		ln=scan.nextLine();
		*/
		//Tim- search no matching accounts for that name in database
		try {

			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[1].equals(fn)) {
					if(strArray[2].equals(ln)) {
						System.out.println("Sorry, that matches a name "
								+ "in our database. Forgot login?\n");		//Nino- if match to database print to GUI
						//allow button switch to login screen
					}
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	/*	
		System.out.println("Enter email\n");
		email=scan.nextLine();
	*/	
	/*	System.out.println("Create a username: \n");		
		usn=scan.nextLine();
		
		*/
		
		//search no matching usernames
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[3].equals(email)) {
					System.out.println("Sorry, that username is already taken.\n");
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		//System.out.println("Create a password: \n");  //Nino- from GUI
		//pwd = scan.nextLine();
		
		
		//print account creation confirmation to user
		printConfirm();
	}
	
	
	
	
	
	
	public void login() {			//student login takes in username and password, compares for match, logs student in (need database)
	/*	System.out.println("Username: ");		//Nino- From GUI
		usn=scan.nextLine();
		System.out.println("\nPassword: ");		//Nino- From GUI
		pwd=scan.nextLine();
		
	*/	
		//Tim- compare for match in database, login, reject otherwise
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[3].equals(email)) {
					if(strArray[0].equals(pwd)) {
						//login----NINO
					}
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	//	System.out.println("The username or password you have entered is incorrect.\n");		//Nino
	}
	
	
	
	
	private void printInfo() {		//for admin purposes only, prints account info for ALL accounts
		
		try {
			FileWriter writer= new FileWriter(file);
			writer.append(fn + "," + ln + "," + email + "," + usn + "," + pwd); //comma separation for all fields
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	protected String reset() {
		/**
	 * @return A sting password by generating a new one
	 * Antoinon Abeshi
	 */
		 String PASSWORD = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";  //CREATES RANDOM STRING
	        StringBuilder pwd = new StringBuilder();
	        Random rnd = new Random();
	        while (pwd.length() < 8) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * PASSWORD.length());
	            pwd.append(PASSWORD.charAt(index));
	        }
	        String pwdStr = pwd.toString();
	        
	       System.out.println("Your new password is: " + pwdStr);
	       
	       try {

			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[3].equals(email)) {
					System.out.println("Your new password is: " + pwd);	//Nino-GUI DONE
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Your email was not found in the database. Try again.\n");  //Nino- GUI
			return pwdStr;	//takes in email and gives back new random password
		
		
		/*String newPassArray[]= {};
		String alphaArray[]= {"A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u"
				+ "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z", "1", "!", "2", "@", "3", "#", "4", "$", "5", "%", "6", "^", "7", "&", "8", "*", "9", "(", "0", ")", "-", "=", "+", "<", ">", "?"};
		
		
		for (int i=0; i<=10; i++) {
			int randElement = (int) Math.random() * 76 + 1;
			newPassArray[i]=alphaArray[randElement];			//CREATES NEW RANDOM PASSWORD DO NOT TOUCH
		}

		pwd=newPassArray[0]+ newPassArray[1]+ newPassArray[2]+ newPassArray[3]
				+newPassArray[4]+newPassArray[5]+ newPassArray[6]+ newPassArray[7]+ newPassArray[8]+newPassArray[9];/*		//prints new password
		*/
		
		//System.out.println("Enter your email: ");		//Nino-GUI
		//email=scan.nextLine();
		//Tim- search for email in database
		
	}
	
	
	
	
	public void recovery(String fn, String ln) {		//attempts to recover the account by matching first and last names
	/*	
		System.out.println("First name: ");		//Nino-GUI
		fn=scan.nextLine();
		System.out.println("Last name: ");		//Nino-GUI
		ln=scan.nextLine();
		*/
		//Tim- search name in database
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[1].equals(fn)) {
					if(strArray[2].equals(ln)) {
						System.out.printf("Your username is: %s", usn);		//Nino-GUI
					}
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Your information does not match the database. Try again.\n");		//Nino-Gui
	}
	
	
	
	public void printConfirm() {		//prints a confirmation of account info to USER upon creation---NINO
		System.out.printf("Your account information is as follows: \n  Password: %s\n First name: %s\n Last name: %s\n Email: %s\n",  pwd, fn, ln, email);
		System.out.println("Please keep this information for your records.\n");
	}
	
	
	
	//setters and getters to print to file from the GUI input
	public String getFirstName() {return fn;}
	public void setFirstName(String new_fn) {fn=new_fn;}
	public String getLastName() {return ln;}
	public void setLastName(String new_ln) {ln=new_ln;}
	public String getEmail() {return email;}
	public void setEmail(String new_email) {email=new_email;}
	/*
	public String getUser() {return usn;}
	public void setUser(String new_usn) {usn=new_usn;}
	*/
	public String getPass() {return pwd;}
	public void setPass(String new_pwd) {pwd=new_pwd;}
}
