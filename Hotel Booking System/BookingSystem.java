import java.util.Scanner;
import java.util.Random;

public class BookingSystem {
    
    private static String[] typeOfRooms = {"double","queen","king"};
    private static Random r = new Random(123);
    
    //returns a random String from the above array. 
    private static String getRandomType(){
        int index = r.nextInt(typeOfRooms.length);
        return typeOfRooms[index];
    }
    //returns a random number of rooms between 5 and 50.
    private static int getRandomNumberOfRooms(){
        return r.nextInt(50)+1;
    }
    //End of provided code. 
    
   public static void main(String[] args){
        //Student Name: Wan-Chun Su
        //Student Number: 260729936
        //Your code goes here. 
       
        //Printing out a welcome message
        System.out.println("Welcome to the COMP 202 booking system" + "\n" + 
                           "Please enter the name of the hotel that you'd like to book");
        
        //Declaring and initializing the scanner
        Scanner userInput = new Scanner(System.in);
        //Storing the hotel name from the user's input
        String hotelName = userInput.nextLine();
        //Using getRandomNumberOfRooms method to generate a random number of rooms
        //Declaring and initializing the rooms for the hotel
        Room[] hotelRooms = new Room[getRandomNumberOfRooms()];
        //Using a for loop to populate the array for the rooms fo the hotel using getRandomType method
        for(int i=0; i<hotelRooms.length; i++) {
            //Storing the random room type
            String type = getRandomType();
            //initializing the elements of the hotel room array
            hotelRooms[i] = new Room(type);
        }
        //Creating a new variable of type hotel
        Hotel hotel = new Hotel(hotelName, hotelRooms); 
        //Printing out a menu at the start
        System.out.println("****************************************************************");
        System.out.println("Welcome to " + hotelName + ". Choose one of the following options:");
        
        System.out.println("1) Make a reservation" + "\n" + "2) Cancel a reservation" + "\n" + "3) See an invoice"
                               + "\n" + "4) See the hotel info" + "\n" + "5) Exit the Booking System");
        System.out.println("****************************************************************");
        //Storing the option that the user chooses into a variable
        int option = userInput.nextInt();
        //Declaring the variables to store the name of the user and the preferred room type
        String userName;
        String roomType;
        //Setting a while loop to for options 1-4
        while(option!=5) {
            //Making a reservation if the user inputs 1
            if(option==1) {
                //Asking the user for his/her name and storing it into the variable userName
                System.out.println("Please enter your name");
                userName = userInput.next();
                //Asking the user for his/her preferred room
                System.out.println("Hello " + userName + ", what type of room would you like to reserve?");
                //Storing the user's reserved room into a variable
                roomType = userInput.next();
                //Using createReservation with the respective inputs
                hotel.createReservation(userName, roomType);
                System.out.println();
  
            } else if(option==2) {//Cancelling a reservation if the user inputs 2
                //Asking for the respective inputs and storing them into variables
                System.out.println("Please enter the name used to make the reservation");
                userName = userInput.next();
                System.out.println("What type of room did you reserve?");
                roomType = userInput.next();
                //Using cancelReservation with the respective inputs
                hotel.cancelReservation(userName,roomType);
                System.out.println(); 
                
            } else if(option==3) {//Showing the invoice if the user inputs 3
                //Asking the user for his/or name and storing it in the variable userName
                System.out.println("Please enter your name");
                userName = userInput.next();
                //Calling printInvoice to print the user's invoice
                hotel.printInvoice(userName);
                System.out.println();
            
            } else if (option==4) {//Printing out the hotel information if the user inputs 4
                System.out.println(hotel); 
                System.out.println();
            }
            //Displaying the menu again after the option has been performed
            System.out.println("****************************************************************");
            System.out.println("Welcome to " + hotelName + ". Choose one of the following options:");
        
            System.out.println("1) Make a reservation" + "\n" + "2) Cancel a reservation" + "\n" + "3) See an invoice"
                               + "\n" + "4) See the hotel info" + "\n" + "5) Exit the Booking System");
            System.out.println("****************************************************************");
            //Asking for another option input
            option = userInput.nextInt();
        }
        //Exiting the booking system and printing out a final statement if the user inputs option 5
        System.out.println("It was a pleasure doing business with you!");
        //Closing the scanner
        userInput.close();
    }
  
}