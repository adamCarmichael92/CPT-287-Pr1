import java.util.*;


public class Movie {
	  String name;
	  Date releaseDate;
	  String description;
	  Date receiveDate;
	  String rStatus;
	  enum movieStatus {RELEASED, RECEIVED;};
	  


	  //constructor
	  public Movie(String moviename, Date moviereleasedate, String moviedescription, Date moviereceivedate, String movstatus) {
		 name = moviename;
		 releaseDate = moviereleasedate;
	     description = moviedescription;
	     receiveDate = moviereceivedate;
	     rStatus = movstatus;

	  }
}

	  