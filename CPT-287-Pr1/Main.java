import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



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
            	
            	//the below path will likely need to be changed, this points to where it is looking for the file
                reader = new BufferedReader(new FileReader("input.txt"));
                
                //create a doubly-linked list to store the movies
                Linked_List<Movie> showingList = new Linked_List<Movie>();
                Linked_List<Movie> comingList = new Linked_List<Movie>();
                
                //loop to read over text file
                while (true) {
                    String line1 = reader.readLine();
                    if (line1 == null){
                       break; //breaks at end of input file
                    }
                    String line2 = reader.readLine();
                    String line3 = reader.readLine();
                    String line4 = reader.readLine();
                    String line5 = reader.readLine();
                    //create a movie with the data
                    Movie aMovie = new Movie(line1, new SimpleDateFormat("MM/dd/yyyy").parse(line2), line3, new SimpleDateFormat("MM/dd/yyyy").parse(line4), line5);
                    //push the movie to the list
                    //if the movie release date is after today's date, it's in the coming list
                    //if the movie release date is before today's date, it's in the showing list
                    if (aMovie.releaseDate.after(getDate())) {
                        comingList.addFirst(aMovie);
                    }
                    else {
                        showingList.addFirst(aMovie);
                    }
                    continue;
                }
                reader.close();
 
                //start our loop for the menu
                int menuKeeper = 1; //keeps the menu running
                while (menuKeeper == 1) {
                	Scanner var = new Scanner(System.in);
                	System.out.println("Please choose from the options below: \r\n"
                			+ 		   "1) List Showing Movies \r\n"
                			+ 		   "2) List Coming Movies \r\n"
                			+		   "3) Add a Movie \r\n"
                			+		   "4) Edit Movie Release Date \r\n"
                			+		   "5) Edit Movie Description \r\n"
                			+		   "6) Show Movies Before a Date \r\n"
                			+		   "7) Save Changes \r\n"
                			+		   "8) Move Coming Movie to Showing Movie \r\n"
                			+		   "9) Exit \r\n");
                	try {
                		//make sure user input is a number
                    	String userInp = var.nextLine();
                    	int number = Integer.parseInt(userInp);
                    	
                    	//display showing movies
                    	if (number == 1) {
                    		Iterator sit = showingList.iterator();
                    		while (sit.hasNext()) {
                    			System.out.println(sit.next().toString());
                    		}
                    		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    	}
                    	
                    	//display coming movies
                    	if (number == 2) {
                    		Iterator cit = comingList.iterator();
                    		while (cit.hasNext()) {
                    			System.out.println(cit.next().toString());
                    		}
                    		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    	}
                    	
                    	//add a movie (Adam, Lindsey, and Evan)
                    	
                    	//TODO: probably need to sort the list as movies are put in it
                    	if (number == 3) {
                    		
                    		System.out.println("Please enter the movie title: \r\n");
                    		String userMovie = var.nextLine();
                    		//Checks to see if movie already exists in the coming list (Adam Carmichael)
                    		Iterator<Movie> cit = comingList.iterator();
                    		while (cit.hasNext()) {
                    			Movie temp = cit.next();
                    			if (userMovie.equalsIgnoreCase(temp.getName())) {
                    				System.out.println("Movie already exists in list.\n");
                    				System.out.println("Enter new movie title: ");
						cit.reset();
                    				userMovie = var.nextLine();	
                    			}
                    		}
                    		
                    		System.out.println("Please enter the movie release date: \r\n");
                    		String userRelDate = var.nextLine();
                    		//Handles invalid inputs from user and prompts them to enter again (Adam Carmichael)
                       		while (validateDate(userRelDate) == false) {
                				System.out.println("Invalid input, try again.\n");
                				System.out.println("Enter new Date (\"MM/dd/yyyy\"): ");
                				userRelDate = var.nextLine();
                			}
                    		System.out.println("Please enter the movie description: \r\n");
                    		String userDescription = var.nextLine();
                    		System.out.println("Please enter the receive date: \r\n");
                    		String userRecDate = var.nextLine();
                    		//Handles invalid inputs from user and prompts them to enter again (Adam Carmichael)
                       		while (validateDate(userRecDate) == false) {
                				System.out.println("Invalid input, try again.\n");
                				System.out.println("Enter new Date (\"MM/dd/yyyy\"): ");
                				userRecDate = var.nextLine();
                			}
                    		System.out.println("Please enter the movie status, released or received: \r\n");
                    		String userStatus = var.nextLine();
                    		
                    		//Lindsey Erwin
                    		//create new movie object with user input and add it to a class based on its date
                    		//is the date if after today, it goes into coming list, if date is before today, showing list
                    		Movie bMovie = new Movie(userMovie, new SimpleDateFormat("MM/dd/yyyy").parse(userRelDate), userDescription, new SimpleDateFormat("MM/dd/yyyy").parse(userRecDate), userStatus);
                    		
                    		//Check that release date is after receive date
                    		if (bMovie.receiveDate.after(new SimpleDateFormat("MM/dd/yyyy").parse(userRelDate))) {
                    			System.out.println("Invalid entry, receive date must be before release date\n");
                    			System.out.println("Please make another entry.\n");
                    			bMovie = null;
                    		} else {
                    			if (bMovie.releaseDate.after(getDate())) {
                    				comingList.addFirst(bMovie);
                    				System.out.println("Movie has been added to Coming List.\n");
                    				
                    			}
                    			else {
                    				showingList.addFirst(bMovie);
                    				System.out.println("Movie has been added to Showing List.\n");
                    			}
                    			
                    		}
                    		
                    		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    	}
                    	
                    	//edit release date of movie (Adam Carmichael)
                    	if (number == 4) {
                    		
                    		printMovieList(comingList);
                    		//Prompt user to choose a movie by name
                    		System.out.println("Please select movie name from the above list to edit: \r\n");
                    		String movName = var.nextLine();
                    		//method to find movie by name
                    		Movie movie = findMovieByName(movName, comingList);
                    		if (movie != null) {
                    			//prompt user to enter new date
                    			System.out.println("Enter new Date (\"MM/dd/yyyy\"): ");
                    			String date = var.nextLine();
                    			//Handles invalid inputs from user and prompts them to enter again
                           		while (validateDate(date) == false) {
                    				System.out.println("Invalid input, try again.\n");
                    				System.out.println("Enter new Date (\"MM/dd/yyyy\"): ");
                        			date = var.nextLine();
                    			}
                           		Date userDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                    			//Set new date
                    			movie.setReleaseDate(userDate);
                    			//acknowledge edit and print list
                    			System.out.println("Movie has been edited. \n");
                    			printMovieList(comingList);
                    		} else {
                    			System.out.println("Movie not found in list.");
                    		}
                    		
                    		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    	}
                    		
                    		
                    	
                    	//Adam Carmichael
                    	//Edits the description of a movie in the "coming" list
                    	if (number == 5) {
                    		printMovieList(comingList);
                    		//Prompt user to choose a movie by name
                    		System.out.println("Please select movie name from the above list to edit: \r\n");
                    		String movName = var.nextLine();
                    		//method to find movie by name
                    		Movie movie = findMovieByName(movName, comingList);
                    		if (movie != null) {
                    			//prompt user to enter new description
                    			System.out.println("Enter new description: ");
                    			String movDesc = var.nextLine();
                    			//Set new description
                    			movie.setDescription(movDesc);
                    			//acknowledge edit and print list
                    			System.out.println("Movie has been edited. \n");
                    			printMovieList(comingList);
                    		} else {
                    			System.out.println("Movie not found in list.");
                    		}
                    		
                    		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    	}
                    	
                    //Lindsey Erwin	
                   	 //Display all movies before MM/dd/yyyy date
                   	if (number == 6) {
                   		// Get date from user input and convert to type Date
                   		System.out.println("Please enter a date: \r\n");
                   		String date = var.nextLine();
                   		Date userDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                   		//Handles invalid inputs from user and prompts them to enter again
                   		while (validateDate(date) == false) {
            				System.out.println("Invalid input, try again.\n");
            				System.out.println("Enter new Date (\"MM/dd/yyyy\"): ");
                			date = var.nextLine();
            			}
                   		
                   		Iterator<Movie> cit = comingList.iterator();
                   		//Counts number of coming films with release date before give date
                   		int count = 0;

                       	while (cit.hasNext()) {
                       		Movie temp = cit.next();
                       		if (temp.releaseDate.before(userDate)) {
                       			System.out.println(temp.toString());
                       			count++;
                       		}
                       	}
                       	System.out.println("Number of coming films before "+ userDate + ": "+ count);
                       	
                       	System.out.println("Hit enter to continue.");
                		var.nextLine();
                   	}
                    	
                    	//TODO: don't really have to do anything since the lists are saved as they are edited?
                    	if (number == 7) {
                    		System.out.println("All changes are saved automatically. Thank you. \r\n");
                    		
                    		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    	}
                    	
			//Adam Carmichael
                    	//Moves a coming movie to the showing movie list when given a release date
                    	if (number == 8) {
                    		
                    		// Get date from user input and convert to type Date
                       		System.out.println("Please enter a release date: \r\n");
                       		String date = var.nextLine();
                       		Date userDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
                       		
                       		//Handles invalid inputs from user and prompts them to enter again
                       		while (validateDate(date) == false) {
                				System.out.println("Invalid input, try again.\n");
                				System.out.println("Enter new Date (\"MM/dd/yyyy\"): ");
                    			date = var.nextLine();
                			}
                       		moveMovieToShowing(userDate, comingList, showingList);
                       		
                       		System.out.println("Hit enter to continue.");
                    		var.nextLine();
                    		
                    	}
                    	//exit program
                    	if (number == 9) {
                    		menuKeeper = 2;
                    	}
                	}
                	catch (NumberFormatException e) {
                		System.out.println("That is not a valid selection.");
                	}

                }
            
                
                
                
                
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		

	}
	//Adam Carmichael
	/**
	 * finds movie object by name
	 * @param movieName: Name of the movie to be found in list
	 * @param movieList: Either the coming or showing list
	 * @return: Movie object
	 */
	private static Movie findMovieByName(String movieName, Linked_List<Movie> movieList) {
		List_Iterator<Movie> cit = movieList.iterator();
		Movie movie = null;
		while (cit.hasNext()) {
			Movie temp = cit.next();
			if (movieName.equalsIgnoreCase(temp.getName())) {
				movie = temp;	
			}
		}
		return movie;
	}
	
	//Adam Carmichael
		/**
		 * finds movie object by release date and removes it and adds to the showing list
		 * @param movieRelDate: Release date of the movie to be found in list
		 * @param comingList: coming list to search for release dates
		 * @param showingList: showing list to add movies to
		 */
		private static void moveMovieToShowing(Date movieRelDate, Linked_List<Movie> comingList, Linked_List<Movie> showingList) {
			List_Iterator<Movie> cit = comingList.iterator();
			List_Iterator<Movie> sit = showingList.iterator();
			//This loop checks to see if the coming list has release dates that match what the user entered
			while (cit.hasNext()) {
				Movie temp = cit.next();
				if (movieRelDate.toString().equals(temp.getReleaseDate().toString())) {
					temp = cit.removePrevious();
					//This loop checks showing list to see if that movie title is already showing
					while (sit.hasNext()) {
						Movie p = sit.next();
						if (temp.getName().equalsIgnoreCase(p.getName())) {
							break;
						} else {
							sit.reset();
							sit.add(temp);
							break;
						}
					}
				} 
			}
		}
	
	//Evan Colyer
	//function to get today's date
	public static Date getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyy");
		Date date = new Date();
		return date;
	}
	//Adam Carmichael
	/**
	 * Prints either movie list
	 * @param movieList: Either the coming or showing list
	 */
	public static void printMovieList(Linked_List<Movie> movieList) {
		List_Iterator<Movie> cit = movieList.iterator();
		while (cit.hasNext()) {
			System.out.println(cit.next().toString());
		}
	}
	
	//Lindsey Erwin
	//function to check if date input is valid
		public static boolean validateDate(String userDate) {
			
			    SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
			    sdfrmt.setLenient(false);
			
			    try
			    {
			        sdfrmt.parse(userDate); 
			    }
			    // Date format is invalid 
			    catch (ParseException e)
			    {
			        return false;
			    }
			    // Return true if date format is valid 
			    return true;
				}

}

