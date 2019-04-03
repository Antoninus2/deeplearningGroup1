package deeplearningGroup1;

import java.io.*;

public class ReadFile {

	public void ReadFile() {


			
	}
	
	public void Read() {
		try {
			File file = new File("words.txt");
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String string;
			System.out.println(buff.readLine());
			while ((string = buff.readLine()) != null) {
				System.out.println(string);
			} 
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}