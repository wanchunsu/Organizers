

import java.util.NoSuchElementException;
public class Hotel {
    //declaring the three private attributes 
    private String name;
    private Room[] rooms;
    private Reservation[] reservations;
    
    public Hotel(String hotelName, Room[] rooms) {
        this.name = hotelName;
        //initializing the Room array to store the hotel rooms
        this.rooms = new Room[rooms.length];
        //populating the rooms of the hotel
        for(int i=0; i<rooms.length; i++) {
            this.rooms[i] = rooms[i];
        } 
    }
    
    //A private method to add a reservation
    private void addReservation(Reservation r) {
        //Checking for a null reservation array and initializing it with the input reservation
        if (this.reservations == null) {
            Reservation[] initial = new Reservation[1];
            initial[0]=r;
            this.reservations = initial;
             
        } else {
            //Creating a new array with an extra entry
            Reservation[] reservations = new Reservation[this.reservations.length+1];
            //populating the elements of the new array by copying the elements of the old reservations array
            for(int i=0; i<this.reservations.length; i++) {
                reservations[i] = this.reservations[i];
            }
            //populating the last element of the new array with the input reservation
            reservations[this.reservations.length] = r; 
            this.reservations = reservations; //updating the reference of the attribute to point to the new array  
        }   
    }
    
    //A helper method that finds the index of the respective reservation in the reservations array
    private int findIndex(String name, String roomType) {
        int index=100; //an integer that will never be achieved because the max number of reservations is 50
        //using a for loop to find the reservation
        for(int i=0; i<this.reservations.length; i++) {
            Room reserved = this.reservations[i].getRoom();//setting a variable for the reserved room
            //Finding the appropriate reservation
            if(this.reservations[i].getName().equals(name)&&reserved.getType().equals(roomType)) {
                    index = i; //updating the index to store entry index of the reservation
                    break; //breaking out of the loop once the appropriate reservation is found
            }
        }
        return index; //returning the index
    }
    
    //A method to remove a reservation
    private void removeReservation(String name, String roomType) {
        
        //Using conditional statements to remove the reservation, if present
        if (this.reservations == null) {//throwing the exception if the reservation array is null
            throw new NoSuchElementException("This reservation was not found");
        } else {
            /*Calling the findIndex method to find the reservation index and storing it into a variable. 
             Or storing 100 if no matching reservation was found*/
            int index = (findIndex(name,roomType));
            if(index==100) {//throwing the exception if no matching reservation was found
                throw new NoSuchElementException("This reservation was not found");
            } else { //a matching reservation was found
                //creating a new array to store the updated reservations list
                Reservation[] updatedReservations = new Reservation[this.reservations.length-1];
                //Using a for loop to popualtae the  new array
                for(int j=0; j<updatedReservations.length; j++) {
                    if(j < index) {//populating the elements before the index
                        updatedReservations[j] = this.reservations[j];
                    } else {//populating the elements after the index by skipping the position with the index in the original array
                        updatedReservations[j] = this.reservations[j+1];
                    }
                }
                //updating the attribute for the reservations array
                this.reservations = updatedReservations;
                //Using a for loop to change the availability of the removed reservation room
                for(int k=0; k<this.rooms.length; k++) {
                    if(this.rooms[k].getType().equals(roomType)&&this.rooms[k].getAvailability()==false) {
                        this.rooms[k].changeAvailability();
                        break;//breaking out of the loop as soon as a room of the same type is found and its availability is changed
                    }  
                }
            }
        }
    }
    //A method to create a reservation
    public void createReservation(String name, String roomType) {
        //Letting the user know that there are no rooms of the given type available
        if(Room.findAvailableRoom(this.rooms, roomType)==null) {
            System.out.println("Sorry, there are no " + roomType + " rooms available at this moment."); 
        } else { //There are rooms of the given type available 
            for(int i=0; i<this.rooms.length; i++) { //Using a for loop to find a room of the given type
                //Using a conditional statement and the getAvailability method to find a room of the inputted type 
                if(this.rooms[i].getType().equals(roomType)&& this.rooms[i].getAvailability()==true){
                   //Creating a new variable of type reservation with the respective inputs
                   Reservation newReservation = new Reservation(rooms[i], name);
                   //changing the availability of the room
                   this.rooms[i].changeAvailability();
                   //Using addReservation method to create a new reservatiom
                   addReservation(newReservation);
                   //Letting the user know that the reservation was made
                   System.out.println("You have successfully reserved a " + roomType + " room under the name of " + name + 
                                      ". We look forward to having you at " + this.name + ".");
                   break;//breaking out of the loop, once a room was found and the its availability was changed
                } 
            }
            
        }
    }
    
    //A method to cancel a reservation
    public void cancelReservation(String name, String roomType) {
        //Using a try/catch block to cancel a reservation or to catch the NoSuchElementException, if no reservation was found
        try{
            //Calling the removeReservation method with the respective inputs
            removeReservation(name,roomType);  
            //Letting the user know that the reservation was successfully cancelled
            System.out.println(name + ", your reservation for a " + roomType + " room was successfully cancelled.");
        } catch(NoSuchElementException e) {//The exception was caught
            //Letting the user know that no matching reservation was found
            System.out.println("No matching reservation under the name of " + name + 
                               " for a " + roomType + " room was made.");
        }
    }
    //A method to print the invoice
    public void printInvoice(String name) {
        //Declaring and initializing a variable to store the amount of money the user owes
        double moneyOwed= 0.0; 
        //Checking if the reservations array is null 
        if (this.reservations==null) {
            moneyOwed = 0.0;
        } else {
            //Using a for loop to update the amount of money owed by the user
            for(int i=0; i<this.reservations.length; i++) {
                //Checking if the reservations array entries are null
                if(this.reservations[i]==null) {
                    moneyOwed = 0.0;
                } else if(this.reservations[i].getName().equals(name)) {
                    //Updating the amount of money owed each time a reservation of the user's name if found
                    moneyOwed += this.reservations[i].getRoom().getPrice();
                }
            }
        
        }
        System.out.println(name + ", your bill is $" + moneyOwed); //Printing out the user's bill
    }
    //A toString method to print out the hotel info
    public String toString() {
        //declaring and initializing the number of rooms available
        int doubleRooms = 0;
        int queenRooms = 0;
        int kingRooms = 0;
        //Using a for loop to find the amount of rooms available of each type
        for(int i=0; i<rooms.length; i++) {
            if(rooms[i].getType().equals("double") && rooms[i].getAvailability()==true){
                doubleRooms++;//updating the amount of double rooms available
            } else if (rooms[i].getType().equals("queen") && rooms[i].getAvailability()==true) {
                queenRooms++;//updating the amount of queen rooms available
            } else if (rooms[i].getType().equals("king") && rooms[i].getAvailability()==true) {
                kingRooms++;//updating the amount of king rooms available
            }   
        }
        //Storing the strings for the hotel name and avaialble rooms into two variables
        String s1 = "Hotel name: " + this.name;
        String s2 = "Available rooms: " + doubleRooms + " double, " + queenRooms + " queen, " + kingRooms + " king.";
        String s3 = s1 + "\n" + s2;//combining the two strings from above
        return s3;//returning the combined string
    }
} 
