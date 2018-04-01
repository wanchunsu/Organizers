
public class Reservation {
    //declaring the private attributes for the Reservation class
    private String name;
    private Room roomReserved;
    
    //Creating the constructor that takes the reserved room and reservation name as input
    public Reservation(Room roomReserved, String reservationName) {
        //intializing the attributes based on the inputs
        this.name = reservationName;
        this.roomReserved = roomReserved;
    }
    
    //get methods for the reservation name and the room reserved
    public String getName() {
        return this.name; //returns the reservation name
    }
    public Room getRoom() {
        return this.roomReserved;//returns the room reserved
    }
}
