import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class Main {

	public static void main(String[] args) {


		//try/catch for parser to include dates
		try {
			
			//try/catch for FileReader
			BufferedReader reader;
            try {
            	/*this reads over the input file and creates a class out of the data
            	  this is written so that the input file is written as follows:
            	  Movie Name
            	  Release Date
            	  Description
            	  Receive Date
            	  Release/Receive Status*/
                reader = new BufferedReader(new FileReader(
                        "E:/downloads/project1.txt"));
                while (true) {
                    String line1 = reader.readLine();
                    if (line1 == null){
                       break; //breaks at end of input file
                    }
                    String line2 = reader.readLine();
                    String line3 = reader.readLine();
                    String line4 = reader.readLine();
                    String line5 = reader.readLine();
                    Movie aMovie = new Movie(line1, new SimpleDateFormat("MM/dd/yyyy").parse(line2), line3, new SimpleDateFormat("MM/dd/yyyy").parse(line4), line5);
                    // need a line here to push movie to list
                    System.out.println(aMovie.name);
                    continue;
                }
                
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		

	}

}
