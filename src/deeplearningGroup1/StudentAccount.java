package deeplearningGroup1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StudentAccount {
	String usn;		//username
	String pwd;		//password
	String email;	
	String fn;		//First name
	String ln;		//Last name

	File file=new File("AccountInformation.txt");
	Scanner scan= new Scanner(System.in);
	
	
	public void signUp() {			//student sign up- takes in first name, last name, email, sets password and username with first-time confirmation
		System.out.println("First name: \n");
		fn=scan.nextLine();
		System.out.println("Last name: \n");
		ln=scan.nextLine();
		
		//search no matching accounts for that name
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[0].equals(fn)) {
					if(strArray[1].equals(ln)) {
						System.out.println("Sorry, that matches a name in our database. Forgot login?\n");		//if match to database
						//allow button switch to login screen
					}
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		

		System.out.println("Create a username: \n");
		usn=scan.nextLine();
		
		//search no matching usernames
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[3].equals(usn)) {
					System.out.println("Sorry, that username is already taken.\n");
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Create a password: \n");
		pwd = scan.nextLine();
		
		
		//print account creation confirmation to user
		printConfirm();
	}
	
	public void login() {			//student login takes in username and password, compares for match, logs student in
		System.out.println("Username: ");
		usn=scan.nextLine();
		System.out.println("\nPassword: ");
		pwd=scan.nextLine();
		
		
		//compare for match, login, reject otherwise
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[3].equals(usn)) {
					if(strArray[4].equals(pwd)) {
						//login----NINO
					}
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("The username or password you have entered is incorrect.\n");
	}
	
	
	
	
	public void printInfo() {		//for admin purposes only, prints account info for ALL accounts
		
		try {
			FileWriter writer= new FileWriter(file);
			writer.append(fn + "," + ln + "," + email + "," + usn + "," + pwd); //comma separation for all fields
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void reset(String email) {		//sends reset link to email if email is in the system
		System.out.println("Enter your email: ");
		email=scan.nextLine();
		//search for email
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[2].equals(email)) {
					System.out.println("Your password is: " + strArray[4]);
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Your email was not found in the database. Try again.\n");
	}
	
	
	
	
	public void recovery(String fn, String ln) {		//attempts to recover the account by matching first and last names
		System.out.println("First name: ");
		fn=scan.nextLine();
		System.out.println("Last name: ");
		ln=scan.nextLine();
		//search name in database
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line= scanner.nextLine();
				String strArray[]=line.split(",");
				if(strArray[0].equals(fn)) {
					if(strArray[1].equals(ln)) {
						System.out.printf("Your username is: %s", usn);
					}
					break;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Your information does not match the database. Try again.\n");
	}
	
	
	
	private void printConfirm() {		//prints a confirmation of account info to USER upon creation
		System.out.printf("Your account information is as follows: \n Username: %s\n Password: %s\n First name: %s\n Last name: %s\n Email: %s\n", usn, pwd, fn, ln, email);
		System.out.println("Please keep this information for your records.\n");
	}
	
	
	
	//setters and getters to print to file from the GUI input
	public String getFirstName() {return fn;}
	public void setFirstName(String new_fn) {fn=new_fn;}
	public String getLastName() {return ln;}
	public void setLastName(String new_ln) {ln=new_ln;}
	public String getEmail() {return email;}
	public void setEmail(String new_email) {email=new_email;}
	public String getUser() {return usn;}
	public void setUser(String new_usn) {usn=new_usn;}
	public String getPass() {return pwd;}
	public void setPass(String new_pwd) {pwd=new_pwd;}
}
