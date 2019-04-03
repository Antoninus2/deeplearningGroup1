package deeplearningGroup1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentAccount {
	String usn;		//username
	String pwd;		//password
	String email;	
	String fn;		//First name
	String ln;		//Last name

	/*
	Scanner scan= new Scanner(System.in);

	File file = new File("AccountInformation.txt");



	private void printInfo() {		//for admin purposes only, prints account info for ALL accounts

		try {
			FileWriter writer= new FileWriter(file);
			writer.append(fn + "," + ln + "," + email + "," + usn + "," + pwd); //comma separation for all fields
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

*/



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

		/*
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

*/

		// Connects to sql
		SQLConnection connecting = new SQLConnection();
		String connectionurl = connecting.connect();

		ResultSet resultSet;
		try (Connection connection = DriverManager.getConnection(connectionurl);
				Statement statement = connection.createStatement();) {

			// Updates the account with the new password when they request it
			String string1 = "'"+pwdStr+"'";
			String string2 = "'"+email+"'";
			String selectSql = "update dbo.User_Info set Password = " + string1+ " where Username = " + string2; //dbo.Essays
			resultSet = statement.executeQuery(selectSql);


		} catch (SQLServerException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


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

		return pwdStr;	//takes in email and gives back new random password
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
