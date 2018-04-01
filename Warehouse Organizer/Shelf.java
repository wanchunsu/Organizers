

public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		
		//Adding the box to the shelf and updating the required attributes
		//Accounting for the possibility that the shelf is empty
		if(this.firstBox == null && this.lastBox==null) {
			this.firstBox =b;
			this.lastBox = b;
		
		//The shelf is not empty
		} else {
			this.lastBox.next = b;
			b.previous = this.lastBox;
			this.lastBox = b;
		}
		//updating the available length on the shelf
		this.availableLength -= b.length;
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		
		//Creating a variable to keep track of the box that is being evaluated
		Box b = this.firstBox;
		//Using a while loop to run through the elements as long as the shelf is non-empty and the element is not null
		while(b != null && this.firstBox != null && this.lastBox !=null) {
			
			//The first box has a matching id
			if(this.firstBox.id.equals(identifier)){
	
				//Considering the case where there is only one box on the shelf
				if (this.firstBox.id.equals(identifier) && this.lastBox.id.equals(identifier)) {
					//Creating a variable to store the firstBox
					Box correct = this.firstBox;
					//Updating the required fields and returning the correct box
					this.firstBox= null;
					this.lastBox = null;
					this.availableLength += correct.length;
					return correct;
				}
				//Considering the case where there is more than 1 box on the shelf
				//Creating a variable to store the firstBox
				Box correct = this.firstBox;
				//updating the required fields
				this.firstBox = this.firstBox.next;
				this.firstBox.previous = null;
				correct.next = null;
				correct.previous = null;
				this.availableLength += correct.length;
				//returning the correct box
				return correct;
				
			} 
			//The last box has a matching id	
			if(this.lastBox.id.equals(identifier)) {
				//Creating a variable to store the lastBox and updating the required fields
				Box correct = this.lastBox;
				this.lastBox = this.lastBox.previous;
				this.lastBox.next = null;
				correct.next = null;
				correct.previous = null;
				this.availableLength += correct.length;
				//returning the correct box
				return correct;
				
			}
			
			//Box b has a matching id
			if(b.id.equals(identifier)){
				//Creating a variable to store the correct box and updating the required fields
				Box correct = b;
				b.next.previous = b.previous;
				b.previous.next = b.next;
				correct.next = null;
				correct.previous = null;
				this.availableLength += correct.length;
				//returning the correct box
				return correct;
			
			}
			//updating b for the next iteration
			b=b.next;
		}
		//returning null if the shelf is empty or if the box is not found
		return null;
	}
	
}
