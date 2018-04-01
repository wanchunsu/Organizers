
public class Room {
    private String type;
    private double price;
    private boolean availability;
    
    //Creating a constructor to initialize the attributes
    public Room(String type) {
        //Usingan if statement to check for a valid input for the room type
        if(type.equals("double") || type.equals("queen") || type.equals("king")) {
            this.type = type; //initializing the room type if the input is valid
            //initializing the price attribute for each respective room
            if(type.equals("double")) {
                this.price = 90.0;
            } else if (type.equals("queen")) {
                this.price = 110.0;
            } else if (type.equals("king")) {
                this.price = 150.0;
            }

        } else { //throwing an exception if the input is invalid
            throw new IllegalArgumentException("No room of such type can be created.");
        }
        this.availability = true;//initializing the avaiability attribute to true
    }
    //get methods for the room type, price and availability
    public String getType() {
        return this.type; //returns the type of room
    }
    public double getPrice() {
        return this.price; //returns the price of the room
    }
    public boolean getAvailability() {
        return this.availability; //returns the availability of the room
    }
    
    //Method to change the availability of the room to the opposite of the current state
    public void changeAvailability() { 
        if(this.availability==true) {
            this.availability = false;
        } else {
            this.availability = true;
        }
    }
    /*A method that takes an array of rooms and a String denoting the room type
    as input and returns the first available room of that type or null*/
    public static Room findAvailableRoom(Room[] rooms, String type) {
        //Using a for loop to run through the array of rooms
        for(int i=0; i<rooms.length; i++) {
            if(rooms[i].type.equals(type)&&rooms[i].availability==true) {
                return rooms[i];//returning the first available room of the specified type
            }
        }
        return null;//returning null if no rooms of the specified type are available
    }
 
}
